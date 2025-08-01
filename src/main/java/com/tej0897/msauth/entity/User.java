package com.tej0897.msauth.entity;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @DocumentId
    private String id;
    private String username;
    private String email;
    private String passwordHash;

    @ServerTimestamp
    private Date createdAt;

    @ServerTimestamp
    private Date updatedAt;
}
