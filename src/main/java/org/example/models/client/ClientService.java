package org.example.models.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ClientService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient client = new OkHttpClient();
    private String name;
    private String password;

    public ClientService(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public ClientService() {
    }

    public Boolean getResultAuth() throws ExecutionException, InterruptedException {
        ClientAuth clientAuth = new ClientAuth(name,
                password);
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Request request = null;
        try {
            request = new Request.Builder()
                    .url("http://localhost:8080/client/auth")
                    .header("Content-Type", "application/json")
                    .post(RequestBody.create(objectMapper.writeValueAsString(new ClientAuth(clientAuth.getName(), clientAuth.getPassword())).getBytes())).build();

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


    public Client getClient(String name) {
        CompletableFuture<Client> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/client/find/" + name)
                .get().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                System.out.println(json);
                future.complete(objectMapper.readValue(json,Client.class));
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<Void> createClient(Client user) throws JsonProcessingException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/client")
                .header("Content-Type", "application/json")
                .post(RequestBody.create(objectMapper.writeValueAsString(new Client(user.getName(),
                        user.getAddress(), user.getPassword())).getBytes())).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(!response.isSuccessful()){
                    future.completeExceptionally(new RuntimeException("request failed"));
                }else{
                    future.complete(null);
                }
            }
        });
        return future;
    }

}
