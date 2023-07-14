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
 * ����� ��������� �������� (������)
 */
public class ViewInvoiceSubscriptionForm extends EditFormBase
{
	private InvoiceSubscription invoiceSubscription;
	private String accountingEntityName;
	private Image providerIcon;

	/**
	 * @return ��������
	 */
	public InvoiceSubscription getInvoiceSubscription()
	{
		return invoiceSubscription;
	}

	/**
	 * @param invoiceSubscription ��������
	 */
	public void setInvoiceSubscription(InvoiceSubscription invoiceSubscription)
	{
		this.invoiceSubscription = invoiceSubscription;
	}

	/**
	 * @return ������������ ������� �����
	 */
	public String getAccountingEntityName()
	{
		return accountingEntityName;
	}

	/**
	 * @param accountingEntityName ������������ ������� �����
	 */
	public void setAccountingEntityName(String accountingEntityName)
	{
		this.accountingEntityName = accountingEntityName;
	}

	/**
	 * @return ������ ���������� �����
	 */
	public Image getProviderIcon()
	{
		return providerIcon;
	}

	/**
	 * @param providerIcon ������ ���������� �����
	 */
	public void setProviderIcon(Image providerIcon)
	{
		this.providerIcon = providerIcon;
	}
}
