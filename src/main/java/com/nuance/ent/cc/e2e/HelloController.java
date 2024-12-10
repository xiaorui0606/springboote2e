package com.nuance.ent.cc.e2e;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Hello controller.
 */
@RestController
public class HelloController {
    /**
     * Hello string.
     *
     * @return the string
     */
    @GetMapping("/")
    public String hello() {
        return "Ok";
    }
}
