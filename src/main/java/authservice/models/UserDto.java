package authservice.models;

import authservice.Entities.User;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;


//When you need to ensure that two objects with the same fields in the superclass and the subclass are considered equal. Then use callSuper=true
@EqualsAndHashCode(callSuper = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends User {

    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private Number phoneNo;
    @NonNull
    private String emailId;

}
