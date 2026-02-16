package com.Expense_Tracker.expense.Consumer;

import com.Expense_Tracker.expense.Model.ExpenseDto;
import com.Expense_Tracker.expense.Service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseConsumer {

    private final ExpenseService expenseService;

    @KafkaListener(topics = "${spring.kafka.topic.json.name}" , groupId = "${spring.kafka.consumer.group.id}")
    public void listen(ExpenseDto expenseDto){
        try {
            if(expenseDto == null){
                System.out.println("There is no data");
            }
            expenseService.createExpense(expenseDto);
        }catch (Exception e){
           throw new RuntimeException(e.getMessage());
        }
    }
}
