package com.training.periodical.util.encoder;


/**
 * Exception for Encoder utility
 *
 */
public class EncoderException extends Exception {

    private static final long serialVersionUID = -9030975630019330970L;

    public EncoderException() {
        super();
    }
    public EncoderException(String message) {
        super(message);
    }
    public EncoderException(String message, Throwable cause) {
        super(message, cause);
    }
    public EncoderException(Throwable cause) {
        super(cause);
    }
}
