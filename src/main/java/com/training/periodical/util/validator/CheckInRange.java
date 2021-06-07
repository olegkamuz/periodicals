package com.training.periodical.util.validator;


public class CheckInRange extends ChainValidator {
    private String data;
    private final Integer range_to;
    public CheckInRange(String data, int range_to) {
        this.data = data;
        this.range_to = range_to;
    }

    @Override
    public boolean isValid() throws ValidatorException{
        if (inRange()) {
            return true;
        } else if (next != null) {
            return next.isValid();
        }
        return false;
    }

    private boolean inRange() throws ValidatorException{
        if (range_to == null) {
            throw new ValidatorException("exception in isValidate method at" +
                    CheckInRange.class.getSimpleName() + " ranges not set");
        }
        int intData = Integer.parseInt(data);
        if (intData < 0 || intData > range_to) return false;
        return true;
    }
}
