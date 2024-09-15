package com.datn.maguirestore.controller;

import com.datn.maguirestore.dto.*;
import com.datn.maguirestore.entity.Order;
import com.datn.maguirestore.errors.BadRequestAlertException;
import com.datn.maguirestore.repository.OrderRepository;
import com.datn.maguirestore.service.MailService;
import com.datn.maguirestore.service.OrderService;
import com.datn.maguirestore.util.HeaderUtil;
import com.datn.maguirestore.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrdersController {

    private final OrderRepository orderRepository;
    private final OrderService orderService;
    private static final String ENTITY_NAME = "order";
    private final MailService mailService;

    @Value("${clientApp.name}")
    private String applicationName;

    @GetMapping("/orders/revenue/monthly")
    public ResponseEntity<BigDecimal[]> getMonthlyRevenue() {
        List<Object[]> monthlyRevenues = orderRepository.getMonthlyRevenue();
        BigDecimal[] revenues = new BigDecimal[12];
        Arrays.fill(revenues, BigDecimal.ZERO);
        for (Object[] monthRevenue : monthlyRevenues) {
            int month = (Integer) monthRevenue[0] - 1; // Chuyển đổi sang chỉ số mảng (0 đến 11)
            BigDecimal revenue = (BigDecimal) monthRevenue[1];
            revenues[month] = revenue;
        }
        return ResponseEntity.ok().body(revenues);
    }

    @GetMapping("revenue/growth-percentage")
    public BigDecimal getRevenueGrowthPercentage() {
        Map<String, BigDecimal> revenues = orderRepository.getRevenueComparison();
        BigDecimal thisWeekRevenue = revenues.get("this_week_revenue");
        BigDecimal lastWeekRevenue = revenues.get("last_week_revenue");

        if (lastWeekRevenue == null || lastWeekRevenue.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO; // Tránh chia cho zero
        }

        return thisWeekRevenue
                .subtract(lastWeekRevenue)
                .divide(lastWeekRevenue, 2, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal("100"));
    }

    @GetMapping("/orders/count")
    public ResponseEntity<Long> getAllOrders() {
//        log.debug("REST request to get a page of Orders");
        return ResponseEntity.ok().body(orderRepository.count());
    }

    @GetMapping("/orders/seven-day")
    public ResponseEntity<BigDecimal> getAllOrdersss() {
//        log.debug("REST request to get a page of Orders");
        Instant endDate = ZonedDateTime.now().toInstant();
        Instant startDate = endDate.minusSeconds(60 * 60 * 24 * 7);
        return ResponseEntity.ok().body(orderRepository.calculateRevenueForLastSevenDays(startDate, endDate));
    }

    @GetMapping("/orders/price")
    public ResponseEntity<BigDecimal> getAllOrderss() {
//        log.debug("REST request to get a page of Orders");
        return ResponseEntity.ok().body(orderRepository.sumTotalPriceForStatusThree());
    }

    @PostMapping("/orders/search")
    public ResponseEntity<List<OrderSearchResDTO>> search(@RequestBody OrderSearchReqDTO orderSearchReqDTO) {
        return ResponseEntity.ok(orderService.search(orderSearchReqDTO));
    }

    @GetMapping("/orders/quantity")
    public ResponseEntity<Map<Integer, Integer>> getQuantity() {
        return ResponseEntity.ok(orderService.getQuantityPerOrderStatus());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
//        log.debug("REST request to get a page of Orders");
        Page<OrderDTO> page = orderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/orders/verifyOrder")
    public ResponseEntity<Void> verifyOrder(@RequestBody List<Long> orderIds) {
        this.orderService.verifyOrder(orderIds);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders/update-status/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable("id") Long id) {
        this.orderService.updateStatus(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderCreateDTO orderDTO) throws URISyntaxException {
//        log.debug("REST request to save Order : {}", orderDTO);
        if (orderDTO.getId() != null) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderDTO result = orderService.save(orderDTO);
        byte[] byteArrayResource = this.orderService.getMailVerify(result.getId());
        //            System.out.println(byteArrayResource);
//        mailService.sendEmail1(result.getMailAddress(), "[MAGUIRE_STORE] Thông báo đặt hàng thành công", "", byteArrayResource, true, true);
        return ResponseEntity
                .created(new URI("/api/orders/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @GetMapping("/orders/cancel/{id}")
    public ResponseEntity<Void> cancelOrder(@PathVariable("id") List<Long> id) {
        this.orderService.cancelOrder(id);
        for (Long ids : id) {
            Order order = orderRepository.findById(ids).orElse(new Order());
            byte[] byteArrayResource = this.orderService.getCancelOrderMail(ids);
            mailService.sendEmail1(
                    order.getMailAddress(),
                    "[MAGUIRE_STORE] Thông báo đơn hàng của bạn đã bị hủy",
                    "",
                    byteArrayResource,
                    true,
                    true
            );
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderResDTO> getOrder(@PathVariable Long id) {
//        log.debug("REST request to get Order : {}", id);
        OrderResDTO orderDTO = orderService.findOne(id);
        return ResponseEntity.ok(orderDTO);
    }
}
