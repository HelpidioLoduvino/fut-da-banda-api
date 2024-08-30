package com.example.futdabandaapi.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class UploadPath {

    @Value("${club.upload.dir}")
    private String clubUploadDir;

    @Value("${player.upload.dir}")
    private String playerUploadDir;

    @Value("${field.upload.dir}")
    private String fieldUploadDir;

}
