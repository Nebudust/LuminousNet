package com.natukikana.luminousnet;

import com.natukikana.luminousnet.utils.LuminousNetUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LuminousNetApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(LuminousNetApplication.class, args);
        System.out.println(LuminousNetUtils.LogHeader() + "Aime Server: OK");
        System.out.println(LuminousNetUtils.LogHeader() + "Luminous Net Started");
    }
}
