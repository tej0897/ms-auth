package com.tej0897.msauth.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.tej0897.msauth.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    private static final String COLLECTION_NAME = "users";

    public String saveUser(User user) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(user.getId().toString());
        ApiFuture<WriteResult> writeResult = docRef.set(user);
        return writeResult.get().getUpdateTime().toString();
    }

    public User getUserByUsername(String username) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection(COLLECTION_NAME).whereEqualTo("username", username).limit(1).get();

        List<QueryDocumentSnapshot> documents = query.get().getDocuments();
        return documents.isEmpty() ? null : documents.getFirst().toObject(User.class);
    }

    public boolean existsByUsername(String username) throws ExecutionException, InterruptedException {
        return getUserByUsername(username) != null;
    }

    public boolean existsByEmail(String email) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection(COLLECTION_NAME)
                .whereEqualTo("email", email)
                .get();
        return !query.get().getDocuments().isEmpty();
    }
}
