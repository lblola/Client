package org.example.models.product;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ProductService {
    private ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient client = new OkHttpClient();

    public Product[] getAllProducts() {
        CompletableFuture<Product[]> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/product")
                .get().build();
        return getProducts(future, request);
    }

    public Product[] getAllProductsByClient(int id) {
        CompletableFuture<Product[]> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/client/" + id + "/orders")
                .get().build();
        return getProducts(future, request);
    }


    private Product[] getProducts(CompletableFuture<Product[]> future, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                System.out.println(json);

                future.complete(objectMapper.readValue(json,Product[].class));
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<Void> deleteProduct(int clientId, int productId) throws JsonProcessingException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/client/" + clientId + "/" + productId)
                .header("Content-Type", "application/json")
                .delete().build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(!response.isSuccessful()){
                    System.out.println(call);
                    System.out.println(response);
                    future.completeExceptionally(new RuntimeException("request failed"));
                }else {
                    future.complete(null);
                }
            }
        });
        return future;
    }

    public CompletableFuture<Void> addProduct(int clientId, int productId) throws JsonProcessingException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/client/" + clientId + "/" + productId)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(objectMapper.writeValueAsString(new Product()).getBytes())).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if(!response.isSuccessful()){
                    System.out.println(call);
                    System.out.println(response);
                    future.completeExceptionally(new RuntimeException("request failed"));
                }else {
                    future.complete(null);
                }
            }
        });
        return future;
    }

    public Product getProduct(int id) {
        CompletableFuture<Product> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/product/" + id)
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
                future.complete(objectMapper.readValue(json, Product.class));
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
