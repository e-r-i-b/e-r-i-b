package com.rssl.phizic.business.loanclaim.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author Gulov
 * @ created 29.04.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Проверяет длину номера телефона в заявке на кредит
 */
public class PhoneFieldLengthValidator extends MultiFieldsValidatorBase
{
	private static final String PARAMETER_COUNTRY_FIELD_NAME = "country";
	private static final String PARAMETER_TELECOM_FIELD_NAME = "telecom";
	private static final String PARAMETER_NUNBER_FIELD_NAME = "number";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String countryField = getParameter(PARAMETER_COUNTRY_FIELD_NAME);
		String telecomField = getParameter(PARAMETER_TELECOM_FIELD_NAME);
		String numberField = getParameter(PARAMETER_NUNBER_FIELD_NAME);

		String country = (String) values.get(countryField);
		String telecom = (String) values.get(telecomField);
		String number = (String) values.get(numberField);

		return ((StringHelper.isNotEmpty(telecom) || StringHelper.isNotEmpty(number)) && (country.length() + telecom.length() + number.length() == 11))
				|| (StringHelper.isEmpty(telecom) && StringHelper.isEmpty(number));
	}
}
