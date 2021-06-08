package com.training.periodical.util.validator;

public class CheckNotNull extends ChainValidator {
    private String data;

    public CheckNotNull(String data) {
        this.data = data;
    }

    @Override
    public boolean isValid(){
        if(data == null) {
            return false;
        } else if (next != null){
            return next.isValid();
        }
        return true;
    }
}
