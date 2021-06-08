package com.training.periodical.util.validator;


public class CheckInRange extends ChainValidator {
    private String data;
    private final Integer range_to;
    public CheckInRange(String data, int range_to) {
        this.data = data;
        this.range_to = range_to;
    }

    @Override
    public boolean isValid() {
        if (!inRange()) {
            return false;
        } else if (next != null) {
            return next.isValid();
        }
        return true;
    }

    private boolean inRange(){
        int intData = Integer.parseInt(data);
        if (intData < 0 || intData > range_to) return false;
        return true;
    }
}
