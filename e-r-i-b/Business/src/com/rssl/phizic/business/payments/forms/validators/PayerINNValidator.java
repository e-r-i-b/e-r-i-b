package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * ��������� �������� ����������� �� ����� ��� ����������� � ��� ������������ � ����� ����
 * @author Mescheryakova
 * @ created 15.03.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ���������� ��� ����������(payerInn) � ���� ��� � ������(personInn)
 * �� ���� � ������ ��� ���, �� ��������� ������ ���� �������������.
 */
public class PayerINNValidator extends MultiFieldsValidatorBase
{
	private static final String PAYER_INN = "payerInn";
	private static final String PERSON_INN = "personInn";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String payerINN = (String) retrieveFieldValue(PAYER_INN, values);
		String personINN = (String) retrieveFieldValue(PERSON_INN, values);

		if (StringHelper.isEmpty(personINN))
			return true; // ���� � ��� � ���� �� ����� ���, �� �� ���������
		return personINN.equals(payerINN);
	}
}
