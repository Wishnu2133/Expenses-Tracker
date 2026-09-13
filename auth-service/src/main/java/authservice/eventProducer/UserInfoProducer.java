package authservice.eventProducer;

import authservice.models.UserEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoProducer {

    private final KafkaTemplate<String,UserEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.name}")
    private String TOPIC_NAME;

    UserInfoProducer(KafkaTemplate<String , UserEvent> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(UserEvent userEvent){

        try {
            // Produce a message to publish in particular Topic with Info(payload)
            // setHeader : by using Header Broker store message base on particular name
            Message<UserEvent> message = MessageBuilder.withPayload(userEvent)
                    .setHeader(KafkaHeaders.TOPIC,TOPIC_NAME).build();
            kafkaTemplate.send(message);
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
