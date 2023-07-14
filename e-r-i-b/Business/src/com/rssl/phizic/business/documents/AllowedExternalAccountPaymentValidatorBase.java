package com.rssl.phizic.business.documents;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 18.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������� ��������� �������� ����������� �������� �� ����� �������� ����������
 *
 * ������� ���������, �������� �� ������� � ������,
 * ���� ������� ��������, �������, �������� �� ������ ��������, ���� ���, �� ������� ��������
 * ���� ������ ������, �� ��������� ������� ������ � ��� ������, ���� ������ �������� �� �����
 */
public abstract class AllowedExternalAccountPaymentValidatorBase extends FieldValidatorBase
{
	private static final String ERROR_MESSAGE = "��������� ������, �������� �������� ������ � ���������� �����.";

	public AllowedExternalAccountPaymentValidatorBase()
	{
		super();
		setMessage(ERROR_MESSAGE);
	}

	public boolean validate(String value)
	{
		return isExternalEccountPaymentAllowed() || StringHelper.isEmpty(value)|| !value.startsWith("account");
	}

	protected abstract boolean isExternalEccountPaymentAllowed();
}
