package org.example.models.employee;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EmployeeService {

    private ObjectMapper objectMapper = new ObjectMapper();
    private OkHttpClient client = new OkHttpClient();

    public Employee[] getAllEmployees(){
        CompletableFuture<Employee[]> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/employee")
                .get().build();
        return getEmployees(future, request);
    }

    private Employee[] getEmployees(CompletableFuture<Employee[]> future, Request request) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String json = response.body().string();
                System.out.println(json);

                future.complete(objectMapper.readValue(json,Employee[].class));
            }
        });
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public CompletableFuture<Void> addEmployee(Employee employee) throws JsonProcessingException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/employee")
                .header("Content-Type", "application/json")
                .post(RequestBody.create(objectMapper.writeValueAsString(new Employee(employee.getName(),
                        employee.getSalary())).getBytes())).build();
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

    public CompletableFuture<Void> deleteEmployee(String name) throws JsonProcessingException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/employee/" + name)
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

    public CompletableFuture<Void> patchEmployee(int id, Employee employee) throws JsonProcessingException {
        CompletableFuture<Void> future = new CompletableFuture<>();
        Request request = new Request.Builder()
                .url("http://localhost:8080/employee/" + id)
                .header("Content-Type", "application/json")
                .patch(RequestBody.create(objectMapper.writeValueAsString(new Employee(employee.getName(),
                        employee.getSalary())).getBytes())).build();
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
}
