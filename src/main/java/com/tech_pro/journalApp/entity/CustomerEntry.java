package com.tech_pro.journalApp.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Document
@Data
public class CustomerEntry {
    @Id
    private ObjectId id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime dateOfBirth;
    private LocalDateTime date;
    private boolean active;
}
