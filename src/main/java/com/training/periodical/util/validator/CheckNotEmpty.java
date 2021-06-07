package com.training.periodical.util.validator;

public class CheckNotEmpty extends ChainValidator {
    private String data;

    public CheckNotEmpty(String data) {
        this.data = data;
    }

    @Override
    public boolean isValid() throws ValidatorException{
        if(data.equals("")) {
            return false;
        } else if (next != null){
            return next.isValid();
        }
        return true;
    }
}
