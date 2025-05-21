package com.pranjal.controller;

import com.pranjal.dto.DashboardResponse;
import com.pranjal.dto.OrderResponse;
import com.pranjal.dto.PaymentDetail;
import com.pranjal.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public DashboardResponse getDashboardData(){
        LocalDate date = LocalDate.now();
        Integer totalCount = orderService.getTodayOrderCount(date);
        Double totalSales = orderService.getTodayOrderTotal(date);
        List<OrderResponse> orders = orderService.getRecentOrders().stream()
                .filter((order)->(order.getPaymentDetail() != null)&&
                        (order.getPaymentDetail().getStatus()== PaymentDetail.PaymentStatus.COMPLETED )).toList();
        return new DashboardResponse((totalSales == null)?0.0:totalSales,
                (totalCount == null)?0:totalCount,
                orders);
    }
}
