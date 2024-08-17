package com.natukikana.luminousnet.controller.chusan;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.natukikana.luminousnet.utils.ZLibUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/ChusanServlet/ChuniServlet")
public class ChusanServletController
{
    @PostMapping(value = "/GetGameSettingApi")
    public byte[] getGameSettingApi(InputStream dataStream)
    {
        JSONObject jsonObject = new JSONObject();

        JSONObject gameSetting = new JSONObject();
        gameSetting.put("romVersion", "2.22.00");
        gameSetting.put("dataVersion", "2.20.07");
        gameSetting.put("isMaintenance", false);
        gameSetting.put("requestInterval", 0);
        gameSetting.put("rebootStartTime", "2024-08-17 21:54:57");
        gameSetting.put("rebootEndTime", "2024-08-17 22:54:57");
        gameSetting.put("isBackgroundDistribute", "false");
        gameSetting.put("maxCountCharacter", 300);
        gameSetting.put("maxCountItem", 300);
        gameSetting.put("maxCountMusic", 300);
        gameSetting.put("matchStartTime", "2024-08-17 23:54:57");
        gameSetting.put("matchEndTime", "2024-08-18 03:54:57");
        gameSetting.put("matchTimeLimit", 10);
        gameSetting.put("matchErrorLimit", 10);
        gameSetting.put("matchingUri", ("http:\\/\\/chu3-servlet.sega.ink\\/MysteriaNet\\/ChusanServlet\\/"));
        gameSetting.put("udpHolePunchUri", "http:\\/\\/chu3-servlet.sega.ink\\/MysteriaNet\\/ChusanServlet\\/");
        gameSetting.put("reflectorUri", "http:\\/\\/chu3-servlet.sega.ink\\/MysteriaNet\\/ChusanServlet\\/");
        jsonObject.put("gameSetting", gameSetting);
        jsonObject.put("isDumpUpload", "true");
        jsonObject.put("isAou", "false");
        return ZLibUtils.encode(jsonObject.toString().getBytes());
    }
}