package org.agoncal.sample.openrewrite.rabbitmqjson;

import static org.agoncal.sample.openrewrite.rabbitmqjson.ApplicationRabbitMQJson.BAR_QUEUE;
import static org.agoncal.sample.openrewrite.rabbitmqjson.ApplicationRabbitMQJson.FOO_QUEUE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class MessageController {

    @Autowired
    private Producer producer;

    @GetMapping("/sendMessagesJson")
    public ResponseEntity<String> sendMessages(@RequestParam String queue, @RequestParam String message) {
        if (queue.equalsIgnoreCase(FOO_QUEUE)) {
            producer.sendMessage(FOO_QUEUE, message);
        } else if (queue.equalsIgnoreCase(BAR_QUEUE)) {
            producer.sendMessage(BAR_QUEUE, message);
        } else {
            return ResponseEntity.badRequest().body("Invalid queue name");
        }
        return ResponseEntity.ok("Message '" + message + "' sent to '" + queue + "' queue");
    }
}