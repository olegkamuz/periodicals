package com.training.periodical.util;

import com.training.periodical.util.validator.ChainValidator;
import com.training.periodical.util.validator.CheckAll;
import com.training.periodical.util.validator.CheckInRange;
import com.training.periodical.util.validator.CheckIsCastToInt;
import com.training.periodical.util.validator.CheckNotEmpty;
import com.training.periodical.util.validator.CheckNotNull;
import com.training.periodical.util.validator.CheckUrlDecode;
import com.training.periodical.util.validator.ValidatorException;


public class Valid {
    public static boolean notNullNotEmpty(String data) throws ValidatorException {
        ChainValidator notNull = new CheckNotNull(data);
        notNull.chainWith(new CheckNotEmpty(data));
        return notNull.isValid();
    }
    public static boolean notNullNotEmptyUrlDecodeAll(String data) throws ValidatorException {
        ChainValidator notNull = new CheckNotNull(data);
        notNull.chainWith(new CheckNotEmpty(data));
        notNull.chainWith(new CheckUrlDecode(data));
        notNull.chainWith(new CheckAll(data));
        return notNull.isValid();
    }

    public static boolean notNullNotEmptyCastToIntInRange(String data, int range_to) throws ValidatorException {
        ChainValidator notNull = new CheckNotNull(data);
        notNull.chainWith(new CheckNotEmpty(data));
        notNull.chainWith(new CheckIsCastToInt(data));
        notNull.chainWith(new CheckInRange(data, range_to));
        return notNull.isValid();
    }
}
