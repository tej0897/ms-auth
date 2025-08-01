package com.tej0897.msauth.entity;

import com.google.cloud.firestore.annotation.ServerTimestamp;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Builder.Default
    private UUID id = UUID.randomUUID();

    private String username;

    private String email;

    private String passwordHash;

    @ServerTimestamp
    private Date createdAt;

    @ServerTimestamp
    private Date updatedAt;
}
