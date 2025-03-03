package com.yuzuki.luminousnet.controller.chusan;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;

@RestController()
@RequestMapping("/ChusanServlet/MatchingServer")
public class MatchingContoller
{
    @PostMapping(value = "/Ping", produces = "text/plain")
    public String ping(InputStream dataStream)
    {
        String send = "{\"returnCode\":1}";
        return send;
    }
}
