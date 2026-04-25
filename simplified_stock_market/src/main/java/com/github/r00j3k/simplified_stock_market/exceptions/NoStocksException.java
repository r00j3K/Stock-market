package com.github.r00j3k.simplified_stock_market.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoStocksException extends RuntimeException{
    public NoStocksException(String message){
        super(message);
    }
}
