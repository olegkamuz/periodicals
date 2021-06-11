package com.training.periodical.util.validator;

public class CheckNotAll extends ChainValidator{
    private final String data;

    public CheckNotAll(String data) {
        this.data = data;
    }


    @Override
    public boolean isValid(){
        if(data.equals("all")) {
            return false;
        } else if (next != null){
            return next.isValid();
        }
        return true;
    }

}
