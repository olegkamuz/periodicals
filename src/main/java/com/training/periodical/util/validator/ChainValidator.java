package com.training.periodical.util.validator;

/**
 * Abstract class to create chain validation
 * trade-off for every link
 * chain of responsibility pattern used
 *
 */
public abstract class ChainValidator {
    public ChainValidator next;

    public ChainValidator chainWith(ChainValidator next) {
        this.next = next;
        return next;
    }

    public abstract boolean isValid() ;

}
