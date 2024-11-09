package com.example.test.client;

import com.example.test.dto.ResponseObject;
import feign.Body;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "employee-service", url = "http://localhost:8081/api/v1/employee")
public interface EmployeeServiceClient {
    @GetMapping("")
    ResponseObject getAllEmployees();


    @PostMapping
    @Headers("Content-Type: application/json")
    ResponseObject createEmployee(@RequestBody String employeeRequest);

}
