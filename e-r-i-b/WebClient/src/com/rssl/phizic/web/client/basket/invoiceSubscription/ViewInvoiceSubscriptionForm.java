package com.rssl.phizic.web.client.basket.invoiceSubscription;

import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author osminin
 * @ created 14.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Форма просмотра подписки (услуги)
 */
public class ViewInvoiceSubscriptionForm extends EditFormBase
{
	private InvoiceSubscription invoiceSubscription;
	private String accountingEntityName;
	private Image providerIcon;

	/**
	 * @return подписка
	 */
	public InvoiceSubscription getInvoiceSubscription()
	{
		return invoiceSubscription;
	}

	/**
	 * @param invoiceSubscription подписка
	 */
	public void setInvoiceSubscription(InvoiceSubscription invoiceSubscription)
	{
		this.invoiceSubscription = invoiceSubscription;
	}

	/**
	 * @return наименование объекта учета
	 */
	public String getAccountingEntityName()
	{
		return accountingEntityName;
	}

	/**
	 * @param accountingEntityName наименование объекта учета
	 */
	public void setAccountingEntityName(String accountingEntityName)
	{
		this.accountingEntityName = accountingEntityName;
	}

	/**
	 * @return иконка поставщика услуг
	 */
	public Image getProviderIcon()
	{
		return providerIcon;
	}

	/**
	 * @param providerIcon иконка поставщика услуг
	 */
	public void setProviderIcon(Image providerIcon)
	{
		this.providerIcon = providerIcon;
	}
}
