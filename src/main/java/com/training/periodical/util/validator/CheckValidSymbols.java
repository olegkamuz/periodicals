package com.training.periodical.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckValidSymbols extends ChainValidator{
    private final String data;

    public CheckValidSymbols(String data) {
        this.data = data;
    }

    @Override
    public boolean isValid(){
        if(!isValidSymbols(data)) {
            return false;
        } else if (next != null){
            return next.isValid();
        }
        return true;
    }

    private boolean isValidSymbols(String input){
        String pattern = "^[A-Za-zА-ЯЁа-яё0-9]+(?:[ _-][A-Za-z0-9]+)*$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(input);
        while (m.find()) {
            input = m.replaceAll("");
        }
        return input.length() == 0;
    }

}
