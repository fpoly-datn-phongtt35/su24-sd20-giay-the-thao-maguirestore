package com.datn.maguirestore.service;

import com.datn.maguirestore.config.Constants;
import com.datn.maguirestore.config.Constants.PAYMENT_METHOD;
import com.datn.maguirestore.config.PaypalConfig;
import com.datn.maguirestore.dto.PaymentDTO;
import com.datn.maguirestore.entity.Address;
import com.datn.maguirestore.entity.Cart;
import com.datn.maguirestore.entity.Order;
import com.datn.maguirestore.entity.Payment;
import com.datn.maguirestore.entity.User;
import com.datn.maguirestore.repository.AddressRepository;
import com.datn.maguirestore.repository.CartDetailsRepository;
import com.datn.maguirestore.repository.CartRepository;
import com.datn.maguirestore.repository.PaymentRepository;
import com.datn.maguirestore.repository.ShoesDetailsRepository;
import com.datn.maguirestore.repository.UserRepository;
import com.datn.maguirestore.service.mapper.PaymentMapper;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

  private final PaymentRepository paymentRepository;
  private final PaymentMapper paymentMapper;
  private final UserRepository userRepository;
//  private final OrderRepository orderRepository;
  private final ShoesDetailsRepository shoesDetailsRepository;
//  private final OrderDetailsRepository orderDetailsRepository;
  private final CartDetailsRepository cartDetailsRepository;
  private final CartRepository cartRepository;
  private final AddressRepository addressRepository;

  public PaymentDTO save(PaymentDTO paymentDTO) {
    log.debug("Request to save Payment : {}", paymentDTO);
    Payment payment = paymentMapper.toEntity(paymentDTO);
    payment = paymentRepository.save(payment);
    return paymentMapper.toDto(payment);
  }

  public PaymentDTO update(PaymentDTO paymentDTO) {
    log.debug("Request to update Payment : {}", paymentDTO);
    Payment payment = paymentMapper.toEntity(paymentDTO);
    payment = paymentRepository.save(payment);
    return paymentMapper.toDto(payment);
  }

  public Optional<PaymentDTO> partialUpdate(PaymentDTO paymentDTO) {
    log.debug("Request to partially update Payment : {}", paymentDTO);

    return paymentRepository
        .findById(paymentDTO.getId())
        .map(existingPayment -> {
          paymentMapper.partialUpdate(existingPayment, paymentDTO);

          return existingPayment;
        })
        .map(paymentRepository::save)
        .map(paymentMapper::toDto);
  }

  public Page<PaymentDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Payments");
    return paymentRepository.findAll(pageable).map(paymentMapper::toDto);
  }

  public Optional<PaymentDTO> findOne(Long id) {
    log.debug("Request to get Payment : {}", id);
    return paymentRepository.findById(id).map(paymentMapper::toDto);
  }

  public void delete(Long id) {
    log.debug("Request to delete Payment : {}", id);
    paymentRepository.deleteById(id);
  }


  public String createPayment(
      BigDecimal price,
      String receivedBy,
      String phone,
      String email,
      String address,
      Integer province,
      Integer district,
      Integer ward,
      BigDecimal shipPrice,
      String idOwner,
      String arrSanPham,
      String arrQuantity,
      String arrPriceDiscount
  ) throws UnsupportedEncodingException {
    String vnp_Version = "2.1.0";
    String vnp_Command = "pay";
    String orderType = "other";
    BigDecimal amount = price.multiply(new BigDecimal("100")).setScale(0, RoundingMode.HALF_UP);
    String bankCode = "NCB";
    String vnp_TxnRef = PaypalConfig.getRandomNumber(8);
    String vnp_IpAddr = "127.0.0.1";
    String vnp_TmnCode = PaypalConfig.vnp_TmnCode;

    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnp_Version);
    vnp_Params.put("vnp_Command", vnp_Command);
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(amount));
    vnp_Params.put("vnp_CurrCode", "VND");

    vnp_Params.put("vnp_BankCode", bankCode);
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
    vnp_Params.put("vnp_OrderType", orderType);

    vnp_Params.put("vnp_Locale", "vn");
    vnp_Params.put(
        "vnp_ReturnUrl",
        PaypalConfig.vnp_ReturnUrl +
            "?order=" +
            receivedBy +
            "_" +
            phone +
            "_" +
            email +
            "_" +
            address +
            "_" +
            province +
            "_" +
            district +
            "_" +
            ward +
            "_" +
            shipPrice +
            "_" +
            idOwner +
            "_" +
            arrSanPham +
            "_" +
            arrQuantity +
            "_" +
            arrPriceDiscount
    );
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    cld.add(Calendar.MINUTE, 15);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    List fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String) itr.next();
      String fieldValue = (String) vnp_Params.get(fieldName);
      if ((fieldValue != null) && (!fieldValue.isEmpty())) {
        //Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
        //Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash = PaypalConfig.hmacSHA512(PaypalConfig.secretKey, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    return PaypalConfig.vnp_PayUrl + "?" + queryUrl;
  }

  public int orderReturn(HttpServletRequest request) {
    Map fields = new HashMap();
    for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
      String fieldName = null;
      String fieldValue = null;
      fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII);
      fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII);
      if ((fieldValue != null) && (!fieldValue.isEmpty())) {
        fields.put(fieldName, fieldValue);
      }
    }
    String vnp_SecureHash = request.getParameter("vnp_SecureHash");
    if (fields.containsKey("vnp_SecureHashType")) {
      fields.remove("vnp_SecureHashType");
    }
    if (fields.containsKey("vnp_SecureHash")) {
      fields.remove("vnp_SecureHash");
    }
    String signValue = PaypalConfig.hashAllFields(fields);
    if (signValue.equals(vnp_SecureHash)) {
      if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
        return 1;
      } else {
        return 0;
      }
    } else {
      return -1;
    }
  }

  private Order newOrder(
      String orderCode,
      String phone,
      BigDecimal shipPrice,
      BigDecimal price,
      String receivedBy,
      User owner,
      String email,
      Payment payment,
      Address address
  ) {
    Order order = new Order();
    order.setCode(orderCode);
    order.setUserAddress(address);
    order.setPhone(phone);
//    order.setPaidMethod(Constants.PAYMENT_METHOD.CREDIT);
    order.setShipPrice(shipPrice);
    order.setTotalPrice(price);
    order.setReceivedBy(receivedBy);
    order.setStatus(Constants.ORDER_STATUS.PENDING);
    order.setCreatedBy("system");
    order.setCreatedDate(Instant.now());
    order.setOwner(owner);
    order.setMailAddress(email);
    order.setPayment(payment);
    return order;
  }

  private Address saveAddress(Integer province, Integer district, Integer ward, String addressDetail) {
    Address address = new Address();
    address.setAddressDetails(addressDetail);
    address.setProvince(province);
    address.setDistrict(district);
    address.setWard(ward);
    addressRepository.save(address);
    return address;
  }

}
