package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.NumericUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;

/**
 * @author Evgrafov
 * @ created 02.12.2005
 * @ $Author: krenev_a $
 * @ $Revision: 49024 $
 */

public class NumericRangeValidator extends FieldValidatorBase
{
    public static final String PARAMETER_MIN_VALUE = "minValue";
    public static final String PARAMETER_MAX_VALUE = "maxValue";

    private BigDecimal minValue = null;
    private BigDecimal maxValue = null;

	/**
	 * конструктор
	 */
	public NumericRangeValidator(){}

	/**
	 * конструктор
	 * @param minValue минимальное значение
	 * @param maxValue максимальное значение
	 * @param message сообщение клиенту
	 */
	public NumericRangeValidator(BigDecimal minValue, BigDecimal maxValue, String message)
	{
		this.minValue = minValue;
		this.maxValue = maxValue;
		setMessage(message);
	}

    public String getParameter(String name)
    {
        if (name.equals(PARAMETER_MIN_VALUE) && minValue != null)
            return minValue.toString();
        else if (name.equals(PARAMETER_MAX_VALUE) && maxValue != null)
            return maxValue.toString();
        else
            return super.getParameter(name);
    }

    public void setParameter(String name, String value)
    {
        if (name.equals(PARAMETER_MIN_VALUE))
            minValue = new BigDecimal(value);
        else if (name.equals(PARAMETER_MAX_VALUE))
            maxValue = new BigDecimal(value);
        else
            super.setParameter(name, value);
    }

    public boolean validate(String value) throws TemporalDocumentException
    {
        if(isValueEmpty(value))
            return true;

	    String checkedValueWithOutGroupSpace = StringUtils.deleteWhitespace(value);
        try
        {
            BigDecimal decimalValue = NumericUtil.parseBigDecimal(checkedValueWithOutGroupSpace);

            if(minValue != null && minValue.compareTo(decimalValue) > 0)
                return false;

            if (maxValue != null && maxValue.compareTo(decimalValue) < 0)
                return false;

            return true;
        }
        catch (ParseException e)
        {
            return false;
        }
    }
}
