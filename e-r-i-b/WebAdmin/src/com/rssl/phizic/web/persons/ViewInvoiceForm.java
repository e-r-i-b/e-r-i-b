package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author muhin
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��������� ��������� ���������� �� ������������� � ��� ����������
 */
public class ViewInvoiceForm extends EditFormBase
{
	private Invoice invoice;
	private String bankName;
	private String bankAccount;
	private String subscriptionName;
	private boolean isOperationAvailable;
	private boolean needConfirm;
	private boolean confirmSubscription;
	private boolean autoCreatedNotConfirm;

	/**
	 * ���������� ��, ���� ����� �������� ������������� �������� � ��������������
	 * @return ��, ���� ����� �������� ������������� �������� � ��������������, ��� � ��������� ������
	 */
	public boolean isAutoCreatedNotConfirm()
	{
		return autoCreatedNotConfirm;
	}

	/**
	 * ������������� ����, ��� ����� �������� ������������� �������� � ��������������
	 */
	public void markAutoCreatedNotConfirm()
	{
		this.autoCreatedNotConfirm = true;
	}

	/**
	 * @return �������������
	 */
	public Invoice getInvoice()
	{
		return invoice;
	}

	/**
	 * @param invoice �������������
	 */
	public void setInvoice(Invoice invoice)
	{
		this.invoice = invoice;
	}

	/**
	 * @return ������������ �����
	 */
	public String getBankName()
	{
		return bankName;
	}

	/**
	 * @param bankName ������������ �����
	 */
	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	/**
	 * @return ���� �����
	 */
	public String getBankAccount()
	{
		return bankAccount;
	}

	/**
	 * @param bankAccount ���� �����
	 */
	public void setBankAccount(String bankAccount)
	{
		this.bankAccount = bankAccount;
	}

	/**
	 * @return ������������ ������
	 */
	public String getSubscriptionName()
	{
		return subscriptionName;
	}

	/**
	 * @param subscriptionName ������������ ������
	 */
	public void setSubscriptionName(String subscriptionName)
	{
		this.subscriptionName = subscriptionName;
	}

	/**
	 * @return �������� �� �������� �� ������
	 */
	public boolean isOperationAvailable()
	{
		return isOperationAvailable;
	}

	/**
	 * @param operationAvailable �������� �� �������� �� ������
	 */
	public void setOperationAvailable(boolean operationAvailable)
	{
		isOperationAvailable = operationAvailable;
	}

	/**
	 * @return true - ��������� �������������
	 */
	public boolean isNeedConfirm()
	{
		return needConfirm;
	}

	public void setNeedConfirm(boolean needConfirm)
	{
		this.needConfirm = needConfirm;
	}

	public String getChooseDelayDateInvoice()
	{
		return (String) getField("chooseDelayDateInvoice");
	}

	public boolean isConfirmSubscription()
	{
		return confirmSubscription;
	}

	public void setConfirmSubscription(boolean confirmSubscription)
	{
		this.confirmSubscription = confirmSubscription;
	}
}
