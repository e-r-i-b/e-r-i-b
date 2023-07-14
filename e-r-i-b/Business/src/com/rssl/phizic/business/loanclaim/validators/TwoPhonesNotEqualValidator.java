package com.rssl.phizic.business.loanclaim.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;

import java.util.Map;

/**
 * Валидатор проверяет совпадение двух номеров телефона: если телефоны совпадают, возвращает false
 * @author Rtischeva
 * @ created 04.02.15
 * @ $Author$
 * @ $Revision$
 */
public class TwoPhonesNotEqualValidator extends MultiFieldsValidatorBase
{
	public static final String PARAMETER_COUNTRY_FIELD_NAME_1 = "countryFieldName1";
	public static final String PARAMETER_TELECOM_FIELD_NAME_1 = "telecomFieldName1";
	public static final String PARAMETER_NUNBER_FIELD_NAME_1 = "numberFieldName1";

	public static final String PARAMETER_COUNTRY_FIELD_NAME_2 = "countryFieldName2";
	public static final String PARAMETER_TELECOM_FIELD_NAME_2 = "telecomFieldName2";
	public static final String PARAMETER_NUNBER_FIELD_NAME_2 = "numberFieldName2";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String countryField1 = getParameter(PARAMETER_COUNTRY_FIELD_NAME_1);
		String telecomField1 = getParameter(PARAMETER_TELECOM_FIELD_NAME_1);
		String numberField1 = getParameter(PARAMETER_NUNBER_FIELD_NAME_1);

		String country1 = (String) values.get(countryField1);
		String telecom1= (String) values.get(telecomField1);
		String number1 = (String) values.get(numberField1);

		String countryField2 = getParameter(PARAMETER_COUNTRY_FIELD_NAME_2);
		String telecomField2 = getParameter(PARAMETER_TELECOM_FIELD_NAME_2);
		String numberField2 = getParameter(PARAMETER_NUNBER_FIELD_NAME_2);

		String country2 = (String) values.get(countryField2);
		String telecom2= (String) values.get(telecomField2);
		String number2 = (String) values.get(numberField2);

		if (country1.equals(country2) && telecom1.equals(telecom2) && number1.equals(number2))
			return false;

		return true;
	}
}
