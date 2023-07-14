package com.rssl.phizic.business.payments.forms.meta.handlers.basket.subscription;

import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;

/**
 * @author osminin
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 *
 * �������, ����������� ����������� ������������� ������ �� ������������ ����������
 */
public class CheckDelayInvoiceSubscriptionHandler extends CheckInvoiceSubscriptionOperationHandler
{
	@Override
	protected String getMessage()
	{
		return "������ �� ������������ ���������� ������ �������� ������ ��� �������� ������. ����������, �������� ����� ������.";
	}

	@Override
	protected boolean check(InvoiceSubscriptionState state)
	{
		return InvoiceSubscriptionState.ACTIVE == state;
	}
}
