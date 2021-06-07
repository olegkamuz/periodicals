package com.training.periodical.util.validator;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class CheckUrlDecode extends ChainValidator {
    private String data;

    public CheckUrlDecode(String data) {
        this.data = data;
    }

    @Override
    public boolean isValid() throws ValidatorException{
        if(isUrlDecode()) {
            return true;
        } else if (next != null){
            return next.isValid();
        }
        return false;
    }

    private boolean isUrlDecode(){
        try {
            URLDecoder.decode(data, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        return true;
    }
}
