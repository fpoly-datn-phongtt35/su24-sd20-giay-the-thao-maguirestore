package com.datn.maguirestore.repository;

import com.datn.maguirestore.entity.Order;
import com.datn.maguirestore.repository.custom.OrderRepositoryCustom;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

  Page<Order> getOrderByOwnerId(Long id, Pageable pageable);

  List<Order> findAllByIdInAndStatus(List<Long> orderIds, Integer status);

  List<Order> findAllByIdIn(List<Long> orderIds);

  @Query(value = "SELECT * FROM order jo WHERE jo.created_date LIKE :date", nativeQuery = true)
  List<Order> findByCreatedDate(@Param("date") String date);

  @Query(value = "select count(*) from order jo where week(now()) = week(jo.created_date) ", nativeQuery = true)
  Integer getOrderNumberInWeek();

  @Query(
      value = "select sum(jo.total_price) revenue  from order jo \n" + "where week(now()) = week(jo.created_date) and jo.status = 3",
      nativeQuery = true
  )
  BigDecimal getRevenueInWeek();

  @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'ROLE_USER' AND u.activated = true")
  long getTotalCustomer();

  @Query(
      value = "select sum(jo.total_price) revenue  from order jo \n" +
          "where week(now()) = week(jo.created_date) and jo.status = 3 and jo.paid_method = 1",
      nativeQuery = true
  )
  BigDecimal getRevenueOnShop();

  @Query(
      value = "select coalesce(sum(jo.total_price),0)  revenue  from order jo \n" +
          "where week(now()) = week(jo.created_date) and jo.status = 3 and jo.paid_method = 2",
      nativeQuery = true
  )
  BigDecimal getRevenueOnline();

  List<Order> getOrderByStatusAndOwnerLogin(Integer status, String login);

  Order getOrderByCode(String code);

  @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.status = 3")
  BigDecimal sumTotalPriceForStatusThree();

  @Query("SELECT SUM(o.totalPrice) FROM Order o WHERE o.createdDate >= :startDate AND o.createdDate <= :endDate AND o.status = 3")
  BigDecimal calculateRevenueForLastSevenDays(@Param("startDate") Instant startDate, @Param("endDate") Instant endDate);

  @Query(
      value = "SELECT MONTH(o.created_date) AS month, SUM(o.total_price) AS revenue FROM order o where status = 3 GROUP BY MONTH(o.created_date)",
      nativeQuery = true
  )
  List<Object[]> getMonthlyRevenue();

  @Query(
      value = "SELECT " +
          "(SELECT SUM(o.total_price) FROM order o WHERE o.created_date >= CURRENT_DATE - INTERVAL 1 WEEK AND o.created_date < CURRENT_DATE) AS this_week_revenue, " +
          "(SELECT SUM(o.total_price) FROM order o WHERE o.created_date >= CURRENT_DATE - INTERVAL 2 WEEK AND o.created_date < CURRENT_DATE - INTERVAL 1 WEEK) AS last_week_revenue",
      nativeQuery = true
  )
  Map<String, BigDecimal> getRevenueComparison();
  
}
