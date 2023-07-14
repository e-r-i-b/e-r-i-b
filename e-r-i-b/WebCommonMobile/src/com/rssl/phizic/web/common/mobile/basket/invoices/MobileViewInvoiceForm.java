package com.rssl.phizic.web.common.mobile.basket.invoices;

import com.rssl.phizic.web.common.client.basket.invoice.ViewInvoiceForm;

/**
 * ����� ��������� ��������� ����������, ������ �� ����� ������� � ��������
 * @ author: Gololobov
 * @ created: 26.10.14
 * @ $Author$
 * @ $Revision$
 */
public class MobileViewInvoiceForm extends ViewInvoiceForm
{
	private boolean anotherStrategyAvailable;
	private String chooseDelayDateInvoice;
	private Integer fromResource;

	public boolean isAnotherStrategyAvailable()
	{
		return anotherStrategyAvailable;
	}

	public void setAnotherStrategyAvailable(boolean anotherStrategyAvailable)
	{
		this.anotherStrategyAvailable = anotherStrategyAvailable;
	}

	public String getChooseDelayDateInvoice()
	{
		return chooseDelayDateInvoice;
	}

	public void setChooseDelayDateInvoice(String chooseDelayDateInvoice)
	{
		this.chooseDelayDateInvoice = chooseDelayDateInvoice;
	}

	/**
	 * @return ������������� ����� ��������
	 */
	public Integer getFromResource()
	{
		return fromResource;
	}

	/**
	 *
	 * @param fromResource ������������� ����� ��������
	 */
	public void setFromResource(Integer fromResource)
	{
		this.fromResource = fromResource;
	}
}
