package com.training.periodical.util.validator;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class Valid {
    public static boolean notNull(String data) {
        ChainValidator notNull = new CheckNotNull(data);
        return notNull.isValid();
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



    public static boolean notNullNotEmpty(String data) {
        ChainValidator notNull = new CheckNotNull(data);
        ChainValidator notEmpty = new CheckNotEmpty(data);
        notNull.chainWith(notEmpty);
        return notNull.isValid();
    }

    public static boolean notNullNotEmptyUrlDecodeAll(String data) {
        ChainValidator notNull = new CheckNotNull(data);
        ChainValidator notEmpty = new CheckNotEmpty(data);
        ChainValidator urlDecode = new CheckUrlDecode(data);
        ChainValidator all = new CheckAll(data);

        notNull.chainWith(notEmpty);
        notEmpty.chainWith(urlDecode);
        urlDecode.chainWith(all);

        return notNull.isValid();
    }

    public static boolean notNullNotEmptyCastToIntInRange(String data, int range_to) {
        ChainValidator notNull = new CheckNotNull(data);
        ChainValidator notEmpty = new CheckNotEmpty(data);
        ChainValidator isCastToInt = new CheckIsCastToInt(data);
        ChainValidator inRange = new CheckInRange(data, range_to);

        notNull.chainWith(notEmpty);
        notEmpty.chainWith(isCastToInt);
        isCastToInt.chainWith(inRange);

        return notNull.isValid();
    }
}
