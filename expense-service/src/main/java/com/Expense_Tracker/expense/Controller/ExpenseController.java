package com.Expense_Tracker.expense.Controller;

import com.Expense_Tracker.expense.Model.ExpenseDto;
import com.Expense_Tracker.expense.Service.ExpenseService;
import jakarta.websocket.server.PathParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Nullable;
import java.util.List;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService;

    ExpenseController(ExpenseService expenseService){
        this.expenseService = expenseService;
    }

    @GetMapping("/expense/v1/{userId}")
    public ResponseEntity<List<ExpenseDto>> getExpense(@PathParam("user_id") @Nullable String userId){
        try{
            List<ExpenseDto> expenseDtoList = expenseService.getExpense(userId);
            return new ResponseEntity<>(expenseDtoList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/expense/v1/")
    public Boolean createExpense(@RequestBody ExpenseDto expenseDto){
        try{
            return expenseService.createExpense(expenseDto);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , ex.getMessage());
        }
    }

    @PatchMapping("/expense/v1/")
    public Boolean updateExpense(@RequestBody ExpenseDto expenseDto){
        try{
            return expenseService.updateExpense(expenseDto);
        }catch (Exception exx){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , exx.getMessage());
        }
    }

    @GetMapping("/expense/v1/list")
    public ResponseEntity<List<ExpenseDto>> getByTimeBetween(@RequestBody ExpenseDto expenseDto){
        try {
            List<ExpenseDto> expenseByTime = expenseService.getByTimeBetween(expenseDto);
            return ResponseEntity.ok(expenseByTime);
        }catch (Exception ee){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST , ee.getMessage());
        }
    }
}
