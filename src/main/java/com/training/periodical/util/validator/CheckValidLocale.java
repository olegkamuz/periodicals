package com.training.periodical.util.validator;

public class CheckValidLocale extends ChainValidator{
    private final String data;

    public CheckValidLocale(String data) {
        this.data = data;
    }


    @Override
    public boolean isValid(){
        if(!isValidLocale(data)) {
            return false;
        } else if (next != null){
            return next.isValid();
        }
        return true;
    }


    private static boolean isValidLocale(String locale) {
        switch (locale) {
            case ("ru"):
            case ("en"):
                return true;
            default:
                return false;
        }
    }

}
