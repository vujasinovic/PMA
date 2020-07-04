package com.example.transportivo.provider;

import android.util.Log;

import com.example.transportivo.globals.CommentCallableFunctions;
import com.example.transportivo.globals.FirebaseCallable;
import com.example.transportivo.globals.NotificationCallableFunctions;
import com.example.transportivo.globals.NotificationTokenCallableFunctions;
import com.example.transportivo.globals.OffersCallableFunctions;
import com.example.transportivo.model.Comment;
import com.example.transportivo.model.Notification;
import com.example.transportivo.model.NotificationToken;
import com.example.transportivo.model.Offer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.Task;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.functions.HttpsCallableResult;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FirebaseClient<T> {
    private static final String ID = "id";
    private static final String DATA = "data";
    private static final String REGION = "europe-west3";

    private FirebaseFunctions firebaseFunctions;
    private ObjectMapper objectMapper;

    public FirebaseClient() {
        this.objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        firebaseFunctions = FirebaseFunctions.getInstance(REGION);
    }

    public void getAll(Class<T> entityClass, Consumer<T[]> entities) {
        getAll(entityClass, new HashMap<>(), entities);
    }

    public void getAll(Class<T> entityClass, Map<String, Object> query, Consumer<T[]> entities) {
        FirebaseCallable callableFunctions = getFirebaseCallableFunctions(entityClass);

        firebaseFunctions.getHttpsCallable(callableFunctions.getAll())
                .call(query)
                .addOnCompleteListener(command -> entities.accept(onCompleteGetAll(command, entityClass)));
    }

    public void create(T entity, Consumer<T> result) {
        FirebaseCallable callableFunction = getFirebaseCallableFunctions((Class<T>) entity.getClass());

        Map data = objectMapper.convertValue(entity, Map.class);
        data.remove(ID);

        firebaseFunctions.getHttpsCallable(callableFunction.create())
                .call(data)
                .addOnCompleteListener(command -> result.accept(onCompleteCreate(command, (Class<T>) entity.getClass())));
    }

    public void update(T entity, Consumer<T> result) {
        FirebaseCallable callableFunctions = getFirebaseCallableFunctions((Class<T>) entity.getClass());

        Map data = objectMapper.convertValue(entity, Map.class);

        firebaseFunctions.getHttpsCallable(callableFunctions.update())
                .call(data)
                .addOnCompleteListener(command -> result.accept(onCompleteCreate(command, (Class<T>) entity.getClass())));
    }

    public void getOne(Class<T> entityClass, String id, Consumer<T> result) {
        FirebaseCallable callableFunctions = getFirebaseCallableFunctions(entityClass);

        Map<String, String> idMap = new HashMap<>();
        idMap.put(ID, id);

        firebaseFunctions.getHttpsCallable(callableFunctions.getOne())
                .call(idMap).addOnCompleteListener(command -> result.accept(onCompleteGetOne(command, entityClass)));
    }

    private T onCompleteGetOne(Task<HttpsCallableResult> command, Class<T> entityClass) {
        T entity = null;

        try {
            Map<String, Object> data = (Map<String, Object>) command.getResult().getData();
            entity = objectMapper.convertValue(data, entityClass);

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        return entity;
    }

    private T onCompleteCreate(Task<HttpsCallableResult> command, Class<T> entityClass) {
        T entity = null;

        try {
            Map<String, Object> data = (Map<String, Object>) command.getResult().getData();
            entity = objectMapper.convertValue(data.get(DATA), entityClass);

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        return entity;
    }

    private T[] onCompleteGetAll(Task<HttpsCallableResult> command, Class<T> entityClass) {
        T[] entities = (T[]) Array.newInstance(entityClass, 0);

        try {
            entities = (T[]) objectMapper.convertValue(command.getResult().getData(), entities.getClass());

            return entities;
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        return entities;
    }

    private FirebaseCallable getFirebaseCallableFunctions(Class<T> entityClass) {
        FirebaseCallable firebaseCallable = null;

        if (entityClass.equals(Offer.class)) {
            firebaseCallable = new OffersCallableFunctions();
        } else if (entityClass.equals(Notification.class)) {
            firebaseCallable = new NotificationCallableFunctions();
        } else if (entityClass.equals(Comment.class)) {
            firebaseCallable = new CommentCallableFunctions();
        } else if (entityClass.equals(NotificationToken.class)) {
            firebaseCallable = new NotificationTokenCallableFunctions();
        }

        return firebaseCallable;
    }
}
