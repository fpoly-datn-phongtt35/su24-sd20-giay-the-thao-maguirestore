package com.datn.maguirestore.repository.custom.impl;

import com.datn.maguirestore.dto.OrderSearchReqDTO;
import com.datn.maguirestore.dto.OrderSearchResDTO;
import com.datn.maguirestore.dto.OrderStatusDTO;
import com.datn.maguirestore.dto.RevenueDTO;
import com.datn.maguirestore.repository.custom.OrderRepositoryCustom;
import com.datn.maguirestore.util.DataUtils;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderSearchResDTO> search(OrderSearchReqDTO orderSearchReqDTO) {
        Query query = this.buildQuery(orderSearchReqDTO);
        List<OrderSearchResDTO> list = query.getResultList();
        return list;
    }

    @Override
    public List<OrderStatusDTO> getQuantityOrders() {
        Query query = this.buildQueryGetQuantity();
        List<OrderStatusDTO> list = query.getResultList();
        return list;
    }

    @Override
    public List<RevenueDTO> getRevenueInYear(Integer on) {
        Query query = this.buildQueryGetRevenue(on);
        List<RevenueDTO> list = query.getResultList();
        return list;
    }

    public Query buildQuery(OrderSearchReqDTO orderSearchReqDTO) {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder(
                "select o.id,o.code,o.phone ,o.received_by as receivedBy, " +
                        "o.last_modified_by as lastModifiedBy , o.created_date as createdDate , " +
                        "o.total_price as totalPrice ,o.owner_id as idCustomer ," +
                        "concat(ju.first_name , ' ' ,ju.last_name) as customer, o.status  " +
                        "from `shoes-store`.order o   \n" +
                        "left join `shoes-store`.users ju on ju.id = o.owner_id \n" +
                        " where 1 = 1"
        );
        if (StringUtils.isNotBlank(orderSearchReqDTO.getSearchText())) {
            query.append(" and (o.code like :searchText ) ");
            params.put("searchText", DataUtils.makeLikeStr(DataUtils.likeSpecialToStr(orderSearchReqDTO.getSearchText())));
        }
        if (Objects.nonNull(orderSearchReqDTO.getStatus())) {
            query.append(" and o.status = :status");
            params.put("status", orderSearchReqDTO.getStatus());
        }
        query.append(" order by o.last_modified_date desc");
        Query query1 = entityManager.createNativeQuery(query.toString(), "orders_result");
        params.forEach(query1::setParameter);
        return query1;
    }

    public Query buildQueryGetQuantity() {
        Map<String, Object> params = new HashMap<>();
        StringBuilder query = new StringBuilder(
                " select jo.status ,count(*) as quantity from `shoes-store`.order jo\n" + " group by jo.status "
        );
        Query query1 = entityManager.createNativeQuery(query.toString(), "orders_quantity_result");
        params.forEach(query1::setParameter);
        return query1;
    }

    public Query buildQueryGetRevenue(Integer on) {
        StringBuilder query = new StringBuilder(
                "select sum(total_price) 'value',month(jo.created_date) 'month' from order jo \n" +
                        "where year(now()) = year(jo.created_date) and jo.status = 3 and paid_method = :method\n" +
                        "group by month(jo.created_date)"
        );
        Query query1 = entityManager.createNativeQuery(query.toString(), "orders_revenue_result");
        query1.setParameter("method", on);
        return query1;
    }

}
