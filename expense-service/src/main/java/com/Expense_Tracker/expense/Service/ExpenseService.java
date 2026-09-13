package com.Expense_Tracker.expense.Service;

import com.Expense_Tracker.expense.Entity.Expense;
import com.Expense_Tracker.expense.Model.ExpenseDto;
import com.Expense_Tracker.expense.Repository.ExpenseRepo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseService {

    private ExpenseRepo expenseRepo;

    public ExpenseService(ExpenseRepo expenseRepo){
        this.expenseRepo  = expenseRepo;
    }

    private ObjectMapper objectMapper;

    public boolean createExpense(ExpenseDto expenseDto){
        setCurrency(expenseDto);
        try{
            expenseRepo.save(objectMapper.convertValue(expenseDto , Expense.class));
            return true;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }

    }

    private void setCurrency(ExpenseDto expenseDto){
        if(Objects.isNull(expenseDto.getCurrency())){
            expenseDto.setCurrency("INR");
        }
    }

    public boolean updateExpense(ExpenseDto expenseDto){
        Optional<Expense> expenseFound = expenseRepo.findByIdAndExternalId(expenseDto.getUserId() , expenseDto.getExternalId());
        if (expenseFound.isEmpty()) {
            return false;
        }
        Expense expense = expenseFound.get();
        // setCurrency  : if user rewrite new currency then set that currency and not then nothing to change
        expense.setCurrency(Strings.isNotBlank(expenseDto.getCurrency())? expenseDto.getCurrency():expense.getCurrency());
        expense.setMerchant(Strings.isNotBlank(expenseDto.getMerchant())?expenseDto.getMerchant():expense.getMerchant());
        expense.setAmount(Strings.isNotBlank(expenseDto.getAmount())?expenseDto.getAmount():expense.getAmount());
        expenseRepo.save(expense);
        return true;
    }

    public List<ExpenseDto> getExpense(String userId){
        List<Expense> expenseList = expenseRepo.findByUserId(userId);
        return objectMapper.convertValue(expenseList, new TypeReference<List<ExpenseDto>>() {});
    }

    public List<ExpenseDto> getByTimeBetween(ExpenseDto expenseDto){
        List<Expense> expenseListByTime = expenseRepo.findByUserIdAndCreatedAtBetween(expenseDto.getUserId() , expenseDto.getStartTime() , expenseDto.getEndTime());
        return objectMapper.convertValue(expenseListByTime, new TypeReference<List<ExpenseDto>>() {});
    }

}
