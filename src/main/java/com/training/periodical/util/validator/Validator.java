package com.training.periodical.util.validator;

/**
 * Validate input data in validation check order specified
 * <p>
 * for range check both boundaries need to be set first
 */
public class Validator {
    public static Integer range_int_from;
    public static Integer range_int_to;

    public enum CheckType {
        NOT_NULL, NOT_EMPTY, IS_CAST_TO_INT, IN_INT_RANGE_INCLUSIVE_INCLUSIVE;
    }

    public static boolean isValid(String data, CheckType... parameters) throws ValidatorException {
        for (CheckType parameter : parameters) {
            switch (parameter) {
                case NOT_NULL:
                    if (data == null) return false;
                case NOT_EMPTY:
                    if (data.equals("")) return false;
                case IS_CAST_TO_INT:
                    try {
                        Integer.parseInt(data);
                    } catch (NumberFormatException e) {
                        return false;
                    }
                case IN_INT_RANGE_INCLUSIVE_INCLUSIVE:
                    if (range_int_from == null || range_int_to == null) {
                        throw new ValidatorException("exception in isValidate method at" +
                                Validator.class.getSimpleName() + " ranges not set");
                    }
                    int intData = Integer.parseInt(data);
                    if (intData < range_int_from || intData > range_int_to) return false;
            }
        }
        return true;
    }
}
