package com.yuzuki.luminousnet.controller.allnet;

import com.yuzuki.luminousnet.utils.LuminousNetUtils;
import com.yuzuki.luminousnet.utils.ZLibUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/sys/servlet")
public class AllNetController
{
    @PostMapping(value = "/PowerOn", produces = "text/plain")
    public String powerOn(InputStream dataStream)
    {
        String data = ZLibUtils.decode(dataStream);
        String returnString = "";
        Map<String, Object> params = parseData(data);
        String gameID = (String) params.get("game_id");
        String ver = (String) params.get("ver");
        String serial = (String) params.get("serial");
        switch (gameID)
        {
            case "SDHD":
                if (serial.equals("A63E01C2751"))
                {
                    System.out.println(LuminousNetUtils.LogHeader() + "PowerOn Accepted: SDHD " + ver + " - " + serial);
                    returnString += "stat=1";
                    returnString += "&uri=http://192.168.1.223/ChusanServlet/&host=192.168.1.223";
                    returnString += "&place_id=01A0&name=GiGO%E6%9D%B1%E4%BA%AC%E9%83%BD%E7%99%BD%E5%BA%AD%E5%BA%97";
                    returnString += "&nickname=%E5%8B%BA%E5%AD%90";
                    returnString += "&region0=1";
                    returnString += "&region_name0=%E5%8C%97%E4%BA%AC%E5%B8%82";
                    returnString += "&region_name1=%E6%88%BF%E5%B1%B1%E5%8C%BA";
                    returnString += "&region_name2=1";
                    returnString += "&region_name3=1";
                    returnString += "&country=CHN";
                    returnString += "&allnet_id=2";
                    returnString += "&client_timezone=+0900";
                    returnString += "&utc_time=" + Instant.now().toString().substring(0, 19) + "Z";
                    returnString += "&setting=1";
                    returnString += "&res_ver=3";
                    returnString += "&token=" + (String) params.get("token");
                }
                else
                {
                    System.out.println(LuminousNetUtils.LogHeader() + "PowerOn Rejected: SDHD " + ver + " - " + serial);
                    return "stat=-1\n";
                }
                break;
        }
        return returnString;
    }

    @PostMapping(value = "/DownloadOrder", produces = "text/plain")
    public String downloadOrder(InputStream dataStream)
    {
        String data = ZLibUtils.decode(dataStream);
        String send = "stat=1&serial=A63E01C2751&uri=null\n";
        return send;
    }

    public Map<String, Object> parseData(String data)
    {
        Map<String, Object> map = new HashMap<>();
        String[] params = data.split("&");
        for (int i = 0; i < params.length; i++)
        {
            map.put(params[i].split("=")[0], params[i].split("=")[1]);
        }
        return map;
    }
}
