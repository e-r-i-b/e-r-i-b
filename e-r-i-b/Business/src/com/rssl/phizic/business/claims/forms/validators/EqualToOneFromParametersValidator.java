package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.phizic.business.payments.forms.validators.AccountAndCardValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * @author emakarov
 * @ created 16.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class EqualToOneFromParametersValidator extends AccountAndCardValidatorBase
{
	protected final static String FIELD_PARAMETER_NAME = "parameterName";

	/**
	 * Default ctor
	 */
	public EqualToOneFromParametersValidator()
	{
	}

	/**
	 * ��������� �������� ����� �� ���������� ���� ����� �� ���� ����������� ������ ���� THREAD SAFE!!!!!!!!!
	 *
	 * @param values �������� ��� ��������. Key - ��� ���� (� �����), Value - �������� ����.
	 */
	public boolean validate(Map values) throws TemporalDocumentException
	{
		String[] names = getParametersNames();
		String parameterName = String.valueOf(retrieveFieldValue(FIELD_PARAMETER_NAME, values));
		for (String name : names)
		{
			if (parameterName.equals(getParameter(name)))
				return true;
		}
		return false;
	}
}
