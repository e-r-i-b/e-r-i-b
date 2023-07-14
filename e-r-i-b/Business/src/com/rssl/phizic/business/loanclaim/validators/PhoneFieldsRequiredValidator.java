package com.rssl.phizic.business.loanclaim.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * ��������� ������������� ����� ��� ������ ��������
 * @author Rtischeva
 * @ created 07.04.14
 * @ $Author$
 * @ $Revision$
 */
public class PhoneFieldsRequiredValidator extends MultiFieldsValidatorBase
{
	public static final String PARAMETER_COUNTRY_FIELD_NAME = "countryFieldName";
	public static final String PARAMETER_TELECOM_FIELD_NAME = "telecomFieldName";
	public static final String PARAMETER_NUNBER_FIELD_NAME = "numberFieldName";


	public boolean validate(Map values) throws TemporalDocumentException
	{
		String countryField = getParameter(PARAMETER_COUNTRY_FIELD_NAME);
		String telecomField = getParameter(PARAMETER_TELECOM_FIELD_NAME);
		String numberField = getParameter(PARAMETER_NUNBER_FIELD_NAME);

		String country = (String) values.get(countryField);
		String telecom = (String) values.get(telecomField);
		String number = (String) values.get(numberField);

		// ���������� false, ����:
		// 1. �� �������� ���, �� �������� �����
		// 2. ��� �� �������� �����, �� �������� ���
		// 3. ��� �������� ��� � ����� � �� �������� �������
		// 4. ��� ������� �� ����� 7, �� �� �������� ��� ��� �����
		// 5. ��� ��� ���� ������

		if (StringHelper.isEmpty(telecom) && StringHelper.isEmpty(number) && (StringHelper.isEmpty(country) || "7".equals(country)))
			return true;

		boolean rc =  StringHelper.isEmpty(telecom) && StringHelper.isNotEmpty(number);
		rc = rc || StringHelper.isNotEmpty(telecom) && StringHelper.isEmpty(number);
		rc = rc || (StringHelper.isNotEmpty(number)&& (StringHelper.isNotEmpty(telecom) && StringHelper.isEmpty(country)));
		rc = rc || (!"7".equals(country) && (StringHelper.isEmpty(telecom) || StringHelper.isEmpty(number)));
		return !rc;
	}
}
