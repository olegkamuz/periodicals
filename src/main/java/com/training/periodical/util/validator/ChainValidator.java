package com.training.periodical.util.validator;

public abstract class ChainValidator {
    public ChainValidator next;

    public ChainValidator chainWith(ChainValidator next) {
        this.next = next;
        return next;
    }

    public abstract boolean isValid() ;

    public boolean checkNext() {
        if(next == null){
            return true;
        }
        return next.isValid();
    }
}
