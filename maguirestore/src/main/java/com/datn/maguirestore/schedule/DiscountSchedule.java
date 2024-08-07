package com.datn.maguirestore.schedule;

import com.datn.maguirestore.service.DiscountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DiscountSchedule {

  private final DiscountService discountService;

  @Scheduled(fixedDelayString = "${schedule.interval_time:1000)}")
  public void scanDiscount() {
    log.info("Start discount scan: ");
    discountService.scanDiscount();
  }
}
