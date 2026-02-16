package com.Expense_Tracker.expense.Consumer;

import com.Expense_Tracker.expense.Model.ExpenseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Deserializer implements org.apache.kafka.common.serialization.Deserializer<ExpenseDto> {

    @Override
    public ExpenseDto deserialize(String arg0, byte[] arg1) {
        ObjectMapper objectMapper = new ObjectMapper();
        ExpenseDto expenseDto = null;
        try {
            expenseDto = objectMapper.readValue(arg1 , ExpenseDto.class);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return expenseDto;
    }
}
