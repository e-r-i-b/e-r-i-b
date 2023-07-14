package com.rssl.common.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.utils.StringHelper;

/**
 * ��������� ���������� ������������� �����
 * (� ���������� maxValue/minValue �������� ������ ������������/����������� �������� �����,
 *  �� ��������� �������������, ��� ����������� �������� ����� - 0)
 *
 * @author Evgrafov
 * @ created 01.12.2005
 * @ $Author: erkin $
 * @ $Revision: 48664 $
 */

public class MoneyFieldValidator extends NumericRangeValidator
{
	public static final String PARAMETER_REGEXP = "regexp";

	private static final String DEFAULT_REGEXP = "^\\d+((\\.|,)\\d{0,2})?$";

	private final NumericRangeValidator numericRangeValidator;

	private RegexpFieldValidator formatValidator;

	/**
	 * ctor
	 */
	public MoneyFieldValidator()
    {
        numericRangeValidator = new NumericRangeValidator();
	    formatValidator = new RegexpMoneyFieldValidator(DEFAULT_REGEXP);
    }

	@Override
	public void setParameter(String name, String value)
	{
		if (name.equals(PARAMETER_REGEXP))
		    formatValidator = new RegexpMoneyFieldValidator(value);
		else super.setParameter(name, value);
	}

	public boolean validate(String value) throws TemporalDocumentException
    {
	    if(isValueEmpty(value))
	        return true;

		//���� �������� minValue �� �����, �������, ��� �� ����� - 0
		String minValue = getParameter(PARAMETER_MIN_VALUE);
		numericRangeValidator.setParameter(PARAMETER_MIN_VALUE, StringHelper.getZeroIfEmptyOrNull(minValue));

		//������������� �������� ������������ ����� - maxValue
		String maxValue = getParameter(PARAMETER_MAX_VALUE);
		if (StringHelper.isNotEmpty(maxValue))
			numericRangeValidator.setParameter(PARAMETER_MAX_VALUE, maxValue);

	    return formatValidator.validate(value) && numericRangeValidator.validate(value);
    }

}
