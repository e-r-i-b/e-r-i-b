package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * Если строковое представление значения поля (field_1) равно переданному параметру (expectedValue),
 * то зависимое поле (field_2) должно иметь значение
 * @author Kidyaev
 * @ created 17.08.2006
 * @ $Author: Kidyaev $
 * @ $Revision: 1830 $
 */

public class DependentFieldRequiredValidator extends MultiFieldsValidatorBase
{
    private static final String FIELD_1        = "field_1";
    private static final String FIELD_2        = "field_2";
    private static final String EXPECTED_VALUE = "expectedValue";

    public boolean validate(Map values) throws TemporalDocumentException
    {
        String field_1_value = getFieldValue(values, FIELD_1);
        String field_2_value = getFieldValue(values, FIELD_2);
        String expectedValue = getExpectedValue();

        //noinspection SimplifiableConditionalExpression
        return field_1_value.equals(expectedValue) ? !isValueEmpty(field_2_value) : true;
    }

    private String getExpectedValue()
    {
        String value = getParameter(EXPECTED_VALUE);
        return value == null ? "" : value;
    }

    private String getFieldValue(Map values, String fieldName)
    {
        Object value = retrieveFieldValue(fieldName, values);
        return value == null ? "" : value.toString();
    }

    private boolean isValueEmpty(String value)
    {
        return value == null || value.equals("");
    }
}
