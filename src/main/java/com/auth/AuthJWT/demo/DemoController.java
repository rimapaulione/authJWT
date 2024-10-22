
package com.auth.AuthJWT.demo;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class DemoController {

    @GetMapping("/auth/demo-controller")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World!");
    }

}