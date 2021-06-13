package com.training.periodical.util.validator;

/**
 * Link of chain validation
 * Checks for casting to Integer possibility
 * @see ChainValidator
 *
 */
public class CheckIsCastToInt extends ChainValidator {
    private String data;
    public CheckIsCastToInt(String data) {
        this.data = data;
    }

    @Override
    public boolean isValid(){
        if (!isCast()) {
            return false;
        } else if (next != null) {
            return next.isValid();
        }
        return true;
    }

    private boolean isCast(){
        try {
            Integer.parseInt(data);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}

