package com.training.periodical.util.validator;

/**
 * Link of chain validation
 * Checks for 'all' string in data
 * @see ChainValidator
 *
 */
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
