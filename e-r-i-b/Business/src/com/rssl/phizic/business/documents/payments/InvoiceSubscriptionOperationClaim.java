package com.rssl.phizic.business.documents.payments;

/**
 * @author osminin
 * @ created 03.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ������� ����� ��� ������ � �������� �� ���������
 */
public abstract class InvoiceSubscriptionOperationClaim extends JurPayment
{
	private static final String SUBSCRIPTION_NAME_FIELD     = "subscription-name";
	private static final String GROUP_NAME_FIELD            = "group-name";
	private static final String INVOICE_ACCOUNT_NAME_FIELD  = "invoice-account-name";
	private static final String INVOICE_INFO_FIELD          = "invoice-info";

	/**
	 * @return ������������ ��������
	 */
	public String getSubscriptionName()
	{
		return getNullSaveAttributeStringValue(SUBSCRIPTION_NAME_FIELD);
	}

	/**
	 * @param subscriptionName ������������ ��������
	 */
	public void setSubscriptionName(String subscriptionName)
	{
		setNullSaveAttributeStringValue(SUBSCRIPTION_NAME_FIELD, subscriptionName);
	}

	/**
	 * @return ������������ ������ �����
	 */
	public String getGroupName()
	{
		return getNullSaveAttributeStringValue(GROUP_NAME_FIELD);
	}

	/**
	 * @param groupName ������������ ������ �����
	 */
	public void setGroupName(String groupName)
	{
		setNullSaveAttributeStringValue(GROUP_NAME_FIELD, groupName);
	}

	/**
	 * @return �������� ������������� �����
	 */
	public String getInvoiceAccountName()
	{
		return getNullSaveAttributeStringValue(INVOICE_ACCOUNT_NAME_FIELD);
	}

	/**
	 * @param invoiceAccountName �������� ������������� �����
	 */
	public void setInvoiceAccountName(String invoiceAccountName)
	{
		setNullSaveAttributeStringValue(INVOICE_ACCOUNT_NAME_FIELD, invoiceAccountName);
	}

	/**
	 * @return ���������� � ������������ ����� �� ������ ������
	 */
	public String getInvoiceInfo()
	{
		return getNullSaveAttributeStringValue(INVOICE_INFO_FIELD);
	}

	/**
	 * @param invoiceInfo ���������� � ������������ ����� �� ������ ������
	 */
	public void setInvoiceInfo(String invoiceInfo)
	{
		setNullSaveAttributeStringValue(INVOICE_INFO_FIELD, invoiceInfo);
	}
}
