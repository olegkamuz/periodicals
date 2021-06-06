package com.training.periodical.util.validator;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Validate input data in validation check order specified
 *
 * for range check left boundary need to be set first
 */
public class Validator implements Serializable {
    private static final long serialVersionUID = 763482292333045953L;
    private static Integer range_int_from = 0;
    public static Integer range_int_to;

    public enum Check {
        NOT_NULL, NOT_EMPTY, IS_CAST_TO_INT, IN_INT_RANGE_INCLUSIVE_INCLUSIVE, URL_DECODE, ALL
    }

    public static boolean isValid(String data, Check... parameters) throws ValidatorException {
        for (Check parameter : parameters) {
            switch (parameter) {
                case NOT_NULL:
                    if (data == null) return false;
                    break;
                case NOT_EMPTY:
                    if (data.equals("")) return false;
                    break;
                case IS_CAST_TO_INT:
                    try {
                        Integer.parseInt(data);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                    break;
                case IN_INT_RANGE_INCLUSIVE_INCLUSIVE:
                    if (range_int_to == null) {
                        throw new ValidatorException("exception in isValidate method at" +
                                Validator.class.getSimpleName() + " ranges not set");
                    }
                    int intData = Integer.parseInt(data);
                    if (intData < range_int_from || intData > range_int_to) return false;
                    break;
                case URL_DECODE:
                    try {
                        URLDecoder.decode(data, StandardCharsets.UTF_8.toString());
                    } catch (UnsupportedEncodingException e) {
                        return false;
                    }
                    break;
                case ALL:
                    if(data.equals("all")) return false;
                    break;
            }
        }
        return true;
    }
}
