package com.training.periodical.util.validator;

public class CheckAll extends ChainValidator{
    private String data;

    public CheckAll(String data) {
        this.data = data;
    }


    @Override
    public boolean isValid() throws ValidatorException{
        if(!data.equals("all")) {
            return true;
        } else if (next != null){
            return next.isValid();
        }
        return false;
    }
}
