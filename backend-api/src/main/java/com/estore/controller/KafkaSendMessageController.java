package com.estore.controller;

import com.estore.kafka.JsonKafkaProducer;
import com.estore.kafka.KafkaProducer;
import com.estore.payload.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/kafka")
public class KafkaSendMessageController {
    private KafkaProducer kafkaProducer;

    private JsonKafkaProducer jsonKafkaProducer;

    public KafkaSendMessageController(JsonKafkaProducer jsonKafkaProducer) {
        this.jsonKafkaProducer = jsonKafkaProducer;
    }

//    public KafkaSendMessageController(KafkaProducer kafkaProducer) {
//        this.kafkaProducer = kafkaProducer;
//    }

//    @GetMapping("/publish")
//    public ResponseEntity<String> publish(@RequestParam("message") String message){
//        kafkaProducer.sendMessage(message);
//
//        return new ResponseEntity<>("Message sent to topic", HttpStatus.OK);
//    }

    @PostMapping("/publish/json")
    public ResponseEntity<String> publishJson(@RequestBody User user) {
        jsonKafkaProducer.sendMessage(user);

        return new ResponseEntity<>("Json message sent to topic", HttpStatus.OK);

    }

}
