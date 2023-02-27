package com.arash.edu.bujournal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping({"", "/v1"})
@RestController
public class GroupController {

    @RequestMapping(method = RequestMethod.GET, path = "/app/stats")
    public String getAppStats() {
        return "Alpha";
    }
}
