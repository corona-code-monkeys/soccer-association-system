package com.SAS.soccer_association_system;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ExampleController {

    @GetMapping("/test/{input}")
    public String greeting( @PathVariable(value = "input") String input) {
        return "Your path variable input is: " + input;
    }

    @GetMapping("/test")
    public String test2(@RequestParam(value = "input", defaultValue = "default_input")String input) {
        return "Your request param input is: " + input;
    }

}
