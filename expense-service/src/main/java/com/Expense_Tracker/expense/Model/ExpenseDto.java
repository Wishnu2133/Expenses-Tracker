package com.Expense_Tracker.expense.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExpenseDto {

    private String externalId;

    @JsonProperty(value = "amount")
    private  String amount;

    @JsonProperty(value = "user_id")
    private String userId;

    @JsonProperty(value = "merchant")
    private  String merchant;

    @JsonProperty(value = "currency")
    private  String currency;

    @JsonProperty(value = "start_time")
    private Timestamp startTime;

    @JsonProperty(value = "end_time")
    private Timestamp endTime;


    public ExpenseDto(String json){
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
            ExpenseDto expenseDto = mapper.readValue(json , ExpenseDto.class);
            this.currency = expenseDto.currency;
            this.amount = expenseDto.amount;
            this.merchant = expenseDto.merchant;
            this.userId = expenseDto.userId;
        }catch (Exception e){
            throw new RuntimeException("Fail to deserialize the JSON String",e);
        }
    }
}
