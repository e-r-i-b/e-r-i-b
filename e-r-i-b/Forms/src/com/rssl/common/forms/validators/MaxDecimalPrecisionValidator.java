package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.NumericUtil;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

/**
 * Проверка максимально допустимой точности числа (количество значащих знаков после запятой, если
 * maxPrecision >= 0, и степени 10, если maxPrecision < 0, то есть если maxPrecision == -2, максимальная
 * точность - сотни )
 * @author Pankin
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class MaxDecimalPrecisionValidator extends FieldValidatorBase
{
	public static final String PARAMETER_MAX_PRECISION = "precision";

	private int maxPrecision;

	public void setParameter(String name, String value)
	{
		if ( name.equalsIgnoreCase(PARAMETER_MAX_PRECISION) )
		{
			maxPrecision = Integer.parseInt(value);
		}
	}

    public String getParameter(String name)
    {
	    if ( name.equalsIgnoreCase(PARAMETER_MAX_PRECISION) )
	    {
		    return String.valueOf(maxPrecision);
	    }

        return null;
    }

	public boolean validate(String value) throws TemporalDocumentException
	{
		if (isValueEmpty(value))
			return true;

		BigDecimal bigDecimalValue = null;
		try
		{
			bigDecimalValue = NumericUtil.parseBigDecimal(StringUtils.deleteWhitespace(value));
		}
		catch (Exception ignored)
		{
			return false;
		}

		return maxPrecision >= bigDecimalValue.stripTrailingZeros().scale();
	}
}
