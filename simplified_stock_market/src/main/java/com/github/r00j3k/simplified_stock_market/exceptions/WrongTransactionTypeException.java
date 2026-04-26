package com.github.r00j3k.simplified_stock_market.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongTransactionTypeException extends RuntimeException{
    public WrongTransactionTypeException(String message){
        super(message); 
    }
}
