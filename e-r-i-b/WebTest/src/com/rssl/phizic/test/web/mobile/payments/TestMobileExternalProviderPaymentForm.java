package com.rssl.phizic.test.web.mobile.payments;

/**
 * ������ ������ ��������-�������� � ������ ����� �����������
 * @author Dorzhinov
 * @ created 14.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class TestMobileExternalProviderPaymentForm extends TestMobileDocumentForm
{
	private String orderUuid;

	public String getOrderUuid()
	{
		return orderUuid;
	}

	public void setOrderUuid(String orderUuid)
	{
		this.orderUuid = orderUuid;
	}
}
