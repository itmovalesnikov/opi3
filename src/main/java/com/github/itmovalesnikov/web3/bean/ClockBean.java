package com.github.itmovalesnikov.web3.bean;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Named("clockBean")
@RequestScoped
public class ClockBean {
    public String getCurrentTime() {
        return LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }
}