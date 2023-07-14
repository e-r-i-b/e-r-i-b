package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;
import java.math.BigDecimal;

/**
 * Проверка что на сумма на счете не меньше чем проверяемая
 * Проверка работает с допущением что сумма на счете и проверяемая сумма в одной валюте
 * @author Kidyaev
 * @ created 05.12.2005
 * @ $Author: Kidyaev $
 * @ $Revision: 1548 $
 */

public class OnlyOneAmountFieldRequiredValidator extends MultiFieldsValidatorBase
{
    public static final String FIELD_1 = "field_1";
    public static final String FIELD_2 = "field_2";

    public boolean validate(Map values) throws TemporalDocumentException
    {
        BigDecimal amount_1 = (BigDecimal) retrieveFieldValue(FIELD_1,values);
        BigDecimal amount_2 = (BigDecimal) retrieveFieldValue(FIELD_2,values);

        return (amount_1 != null && amount_2 == null) || (amount_1 == null && amount_2 != null);
    }
}
