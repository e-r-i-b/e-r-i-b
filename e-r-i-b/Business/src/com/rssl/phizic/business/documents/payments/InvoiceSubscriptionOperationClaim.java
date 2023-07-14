package com.rssl.phizic.business.documents.payments;

/**
 * @author osminin
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс для работы с заявками по подпискам
 */
public abstract class InvoiceSubscriptionOperationClaim extends JurPayment
{
	private static final String SUBSCRIPTION_NAME_FIELD     = "subscription-name";
	private static final String GROUP_NAME_FIELD            = "group-name";
	private static final String INVOICE_ACCOUNT_NAME_FIELD  = "invoice-account-name";
	private static final String INVOICE_INFO_FIELD          = "invoice-info";

	/**
	 * @return Наименование подписки
	 */
	public String getSubscriptionName()
	{
		return getNullSaveAttributeStringValue(SUBSCRIPTION_NAME_FIELD);
	}

	/**
	 * @param subscriptionName Наименование подписки
	 */
	public void setSubscriptionName(String subscriptionName)
	{
		setNullSaveAttributeStringValue(SUBSCRIPTION_NAME_FIELD, subscriptionName);
	}

	/**
	 * @return Наименование группы услуг
	 */
	public String getGroupName()
	{
		return getNullSaveAttributeStringValue(GROUP_NAME_FIELD);
	}

	/**
	 * @param groupName Наименование группы услуг
	 */
	public void setGroupName(String groupName)
	{
		setNullSaveAttributeStringValue(GROUP_NAME_FIELD, groupName);
	}

	/**
	 * @return Название выставляемого счета
	 */
	public String getInvoiceAccountName()
	{
		return getNullSaveAttributeStringValue(INVOICE_ACCOUNT_NAME_FIELD);
	}

	/**
	 * @param invoiceAccountName Название выставляемого счета
	 */
	public void setInvoiceAccountName(String invoiceAccountName)
	{
		setNullSaveAttributeStringValue(INVOICE_ACCOUNT_NAME_FIELD, invoiceAccountName);
	}

	/**
	 * @return Информация о выставлениии счета по данной услуге
	 */
	public String getInvoiceInfo()
	{
		return getNullSaveAttributeStringValue(INVOICE_INFO_FIELD);
	}

	/**
	 * @param invoiceInfo Информация о выставлениии счета по данной услуге
	 */
	public void setInvoiceInfo(String invoiceInfo)
	{
		setNullSaveAttributeStringValue(INVOICE_INFO_FIELD, invoiceInfo);
	}
}
