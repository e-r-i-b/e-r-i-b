package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.TemporalDocumentException;

import java.util.Map;

/**
 * ��������� ��������� ����� ��� �������.
 * ���� ������ ��������/����������, �� ���������� true.
 * ���� ��� �� ���, �� ������ ��������� ������ ����� �������� � ����� ����������.
 *
 * @author bogdanov
 * @ created 11.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class NotBorrowerCurrencyValidator extends CompareValidator
{
	private final IsBorrowerValidator isBorrowerValidator = new IsBorrowerValidator();

	@Override
	public void setBinding(String validatorField, String formField)
	{
		super.setBinding(validatorField, formField);
		isBorrowerValidator.setBinding(validatorField, formField);
	}

	@Override
	public boolean validate(Map values) throws TemporalDocumentException
	{
		if (isBorrowerValidator.validate(values))
			return true;

		setParameter(CompareValidator.OPERATOR, "eq");
		return super.validate(values);    
	}
}
