package com.mx.meli.data.proxy.config;

import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class LocaleConfig {

    @PostConstruct
    public void init() {

	    TimeZone.setDefault(TimeZone.getTimeZone("GMT-06:00"));
	    log.info("Date in UTC ----- " + new Date().toString());
    }
}