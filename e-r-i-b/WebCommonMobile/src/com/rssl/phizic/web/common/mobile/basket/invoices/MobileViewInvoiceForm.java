package com.rssl.phizic.web.common.mobile.basket.invoices;

import com.rssl.phizic.web.common.client.basket.invoice.ViewInvoiceForm;

/**
 * Форма просмотра детальной информации, оплаты по счету корзины и удаления
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
	 * @return идентификатор карты списания
	 */
	public Integer getFromResource()
	{
		return fromResource;
	}

	/**
	 *
	 * @param fromResource идентификатор карты списания
	 */
	public void setFromResource(Integer fromResource)
	{
		this.fromResource = fromResource;
	}
}
