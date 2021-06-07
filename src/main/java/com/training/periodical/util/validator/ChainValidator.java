package com.training.periodical.util.validator;

public abstract class ChainValidator {
    public ChainValidator next;

    public ChainValidator chainWith(ChainValidator next) {
        this.next = next;
        return next;
    }

    public abstract boolean isValid() throws ValidatorException;

    public boolean checkNext() throws ValidatorException{
        if(next == null){
            return true;
        }
        return next.isValid();
    }
}
