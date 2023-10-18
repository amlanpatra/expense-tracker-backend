package org.amlan.expensetracker.repository;

import org.amlan.expensetracker.entity.PaymentBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentBlockRepository extends JpaRepository<PaymentBlock, Long> {
}
