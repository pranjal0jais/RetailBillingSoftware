package com.pranjal.repository;

import com.pranjal.entity.OrderEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderId(String id);

    List<OrderEntity> findAllByOrderByCreatedAtDesc();

    boolean existsByOrderId(String orderId);

    @Query("SELECT o FROM OrderEntity o WHERE DATE(o.createdAt) = :date")
    List<OrderEntity> findOrderByDate(@Param("date") LocalDate date);

    @Query("SELECT o FROM OrderEntity o ORDER BY o.createdAt DESC")
    List<OrderEntity> findRecentOrders(PageRequest pageable);
}
