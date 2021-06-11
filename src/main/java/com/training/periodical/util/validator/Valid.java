package com.training.periodical.util.validator;

import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Valid {
    public static boolean notNull(Object... inputArr) {
        for(Object input: inputArr) {
            if (input == null) {
                return false;
            }
        }
        return true;
    }

    public static boolean notEmpty(String... inputArr) {
        for(String input: inputArr) {
            ChainValidator notEmpty = new CheckNotEmpty(input);
            if (!notEmpty.isValid()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidSymbols(String... inputArr) {
        for(String input: inputArr) {
            ChainValidator validSymbols =  new CheckValidSymbols(input);

            if (!validSymbols.isValid()) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidLocale(String... inputArr) {
        for(String input: inputArr) {
            ChainValidator validLocale =  new CheckValidLocale(input);

            if (!validLocale.isValid()) {
                return false;
            }
        }
        return true;
    }

    public static String escapeInput(String input) {
        return StringEscapeUtils.escapeHtml4(input);
    }


    public static boolean isMagazineIdEmpty(HttpServletRequest request) {
        Object magIds = request.getSession().getAttribute("magazineId");
        if (magIds instanceof List) {
            if (((List) magIds).size() > 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAttributeNull(HttpServletRequest request, String attributeName) {
        return request.getSession().getAttribute(attributeName) != null;
    }

    public static boolean notNullNotEmpty(String... data) {
        for (String s : data) {
            ChainValidator notNull = new CheckNotNull(s);
            ChainValidator notEmpty = new CheckNotEmpty(s);
            notNull.chainWith(notEmpty);
            if (!notNull.isValid()) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNullNotEmptyUrlDecodeAll(String... data) {

        for (String s : data) {
            ChainValidator notNull = new CheckNotNull(s);
            ChainValidator notEmpty = new CheckNotEmpty(s);
            ChainValidator urlDecode = new CheckUrlDecode(s);
            ChainValidator all = new CheckNotAll(s);

            notNull.chainWith(notEmpty);
            notEmpty.chainWith(urlDecode);
            urlDecode.chainWith(all);

            if (!notNull.isValid()) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNullNotEmptyCastToIntInRange(int range_to, String... data) {
        for (String s : data) {
            ChainValidator notNull = new CheckNotNull(s);
            ChainValidator notEmpty = new CheckNotEmpty(s);
            ChainValidator isCastToInt = new CheckIsCastToInt(s);
            ChainValidator inRange = new CheckInRange(s, range_to);

            notNull.chainWith(notEmpty);
            notEmpty.chainWith(isCastToInt);
            isCastToInt.chainWith(inRange);

            if (!notNull.isValid()) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNullNotEmptyValidSymbols(String... data) {
        for (String s : data) {
            ChainValidator notNull = new CheckNotNull(s);
            ChainValidator notEmpty = new CheckNotEmpty(s);
            ChainValidator validSymbols = new CheckValidSymbols(s);

            notNull.chainWith(notEmpty);
            notEmpty.chainWith(validSymbols);

            if (!notNull.isValid()) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNullNotEmptyValidSymbolsNotAll(String... data) {
        for (String s : data) {
            ChainValidator notNull = new CheckNotNull(s);
            ChainValidator notEmpty = new CheckNotEmpty(s);
            ChainValidator validSymbols = new CheckValidSymbols(s);
            ChainValidator notAll = new CheckNotAll(s);

            notNull.chainWith(notEmpty);
            notEmpty.chainWith(validSymbols);
            validSymbols.chainWith(notAll);

            if (!notNull.isValid()) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNullNotEmptyValidLocale(String... data) {
        for (String s : data) {
            ChainValidator notNull = new CheckNotNull(s);
            ChainValidator notEmpty = new CheckNotEmpty(s);
            ChainValidator validLocale = new CheckValidLocale(s);

            notNull.chainWith(notEmpty);
            notEmpty.chainWith(validLocale);

            if (!notNull.isValid()) {
                return false;
            }
        }
        return true;
    }
}
