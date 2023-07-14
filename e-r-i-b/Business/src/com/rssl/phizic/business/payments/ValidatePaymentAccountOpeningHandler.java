package com.rssl.phizic.business.payments;

import com.rssl.phizic.business.documents.*;

/**
 * @author sergunin
 * @ created 11.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ����� �� ����������������� ��� �������� ���������
 * ��� �������� ������ (������� ���� ��������)
 */
public class ValidatePaymentAccountOpeningHandler extends ValidatePaymentAccountHandler
{
	private static final String ERROR_MESSAGE = "�������� �� ���������� ����� ��� ����� ��������������. ����������, ������� ������ ���� ��� ����� ��������.";

	@Override
	protected String getDisabledErrorMessage(ResourceType resourceType) {
		return ERROR_MESSAGE;
	}

	@Override
	protected String getClosedErrorMessage(String accountOrCard) {
		return ERROR_MESSAGE;
	}
}
