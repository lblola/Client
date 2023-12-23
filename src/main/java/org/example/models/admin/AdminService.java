package org.example.models.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.example.models.client.ClientAuth;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AdminService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient client = new OkHttpClient();
    private String name;
    private String password;

    public AdminService(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public AdminService() {
    }

    public Boolean getResultAuth() throws ExecutionException, InterruptedException {
        Admin admin = new Admin(name,
                password);
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Request request = null;
        try {
            request = new Request.Builder()
                    .url("http://localhost:8080/admin/auth")
                    .header("Content-Type", "application/json")
                    .post(RequestBody.create(objectMapper.writeValueAsString(new Admin(admin.getName(),
                                    admin.getPassword())).getBytes())).build();

        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                boolean resultAuth = false;
                String json = response.body().string();
                if (!response.isSuccessful()) {
                    future.completeExceptionally(new RuntimeException("request failed"));
                } else {
                    resultAuth = future.complete(objectMapper.readValue(json, Boolean.class));
                }
            }
        });
        return future.get();
    }
}
