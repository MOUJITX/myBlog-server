package com.moujitx.myblog.server.controller;

import com.moujitx.myblog.server.entity.Visitor;
import com.moujitx.myblog.server.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/visitor")
public class VisitorController {

    @Autowired
    VisitorService visitorService;

    public Boolean insert(String ipv4, String agent, String token) {
        Visitor visitor = new Visitor();
        visitor.setIpv4(ipv4);
        visitor.setAgent(agent);
        visitor.setToken(token);

        visitorService.insert(visitor);
        return true;
    }
}
