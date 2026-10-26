package com.alexstefan.expense_tracker.repository;

import com.alexstefan.expense_tracker.dto.transaction.ChartSavingsYearlyRequestDTO;
import com.alexstefan.expense_tracker.dto.transaction.ChartTransactionRequestDTO;
import com.alexstefan.expense_tracker.dto.transaction.SavingsListRequestDTO;
import com.alexstefan.expense_tracker.dto.transaction.TransactionListRequestDTO;
import com.alexstefan.expense_tracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("""
    SELECT t FROM Transaction t
    WHERE t.user.id = :userId
      AND ((YEAR(t.date) = :year AND :month IS NULL)
          OR
          (YEAR(t.date) = :year AND MONTH(t.date) = :month))
      AND t.type <> com.alexstefan.expense_tracker.constants.TransactionType.SAVINGS_WITHDRAWAL
    ORDER BY t.date DESC
    """)
    List<Transaction> findTransactionsByTimeIntervalWhichAreNotWithdrawals(
            @Param("userId") Long userId, @Param("year") Integer year, @Param("month") Integer month);

    @Query("""
    SELECT t FROM Transaction t 
    WHERE t.user.id = :userId
        AND t.type = com.alexstefan.expense_tracker.constants.TransactionType.EXPENSE
        AND ((YEAR(t.date) = :year AND :month IS NULL)
            OR
            (YEAR(t.date) = :year AND MONTH(t.date) = :month))
      
    """)
    List<Transaction> findTransactionsByTimeIntervalWhichAreExpenses(
            @Param("userId") Long userId, @Param("year") Integer year, @Param("month") Integer month);

    @Query("""
    SELECT t FROM Transaction t 
    WHERE :userId = t.user.id AND
        (t.type = com.alexstefan.expense_tracker.constants.TransactionType.SAVINGS_WITHDRAWAL OR
         t.type = com.alexstefan.expense_tracker.constants.TransactionType.SAVINGS_DEPOSIT)
    ORDER BY t.date DESC
    """)
    List<Transaction> findSavings(@Param("userId") Long userId);


    @Query("""
     SELECT t FROM Transaction t
     WHERE t.user.id = :userId AND
        :year = YEAR(t.date) AND
        t.type = com.alexstefan.expense_tracker.constants.TransactionType.SAVINGS_DEPOSIT
     ORDER BY t.date
     """)
    List<Transaction> getSavingsByYear(@Param("userId") Long userId, @Param("year") Integer year);
}
