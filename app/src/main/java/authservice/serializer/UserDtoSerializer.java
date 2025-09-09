package authservice.serializer;

import authservice.models.UserEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

public class UserDtoSerializer implements Serializer<UserEvent> {

    // Convert UserDto data in to byte[] array format
    @Override
    public byte[] serialize(String key, UserEvent data) {
            ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(data);
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
