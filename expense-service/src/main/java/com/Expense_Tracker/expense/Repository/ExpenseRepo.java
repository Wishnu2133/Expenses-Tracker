package com.Expense_Tracker.expense.Repository;

import com.Expense_Tracker.expense.Entity.Expense;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepo extends CrudRepository<Expense, String> {

     List<Expense> findByUserId(String Id);

     List<Expense> findByUserIdAndCreatedAtBetween(String userId , Timestamp startTime , Timestamp endTime);

     Optional<Expense> findByIdAndExternalId(String userId , String externalId);


}
