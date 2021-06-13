package com.training.periodical.util.validator;

/**
 * Link of chain validation
 * Checks for not empty data
 * @see ChainValidator
 *
 */
public class CheckNotEmpty extends ChainValidator {
    private String data;

    public CheckNotEmpty(String data) {
        this.data = data;
    }

    @Override
    public boolean isValid(){
        if(data.equals("")) {
            return false;
        } else if (next != null){
            return next.isValid();
        }
        return true;
    }
}
