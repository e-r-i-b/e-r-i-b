package com.rssl.phizic.web.persons;

import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author muhin
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 *
 * Форма просмотра детальной информации по задолженности в АРМ Сотрудника
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
	 * Возвращает да, если плажёт является автоматически созданым и непотверждённым
	 * @return Да, если плажёт является автоматически созданым и непотверждённым, нет в противном случае
	 */
	public boolean isAutoCreatedNotConfirm()
	{
		return autoCreatedNotConfirm;
	}

	/**
	 * Устанавливает флаг, что платёж является автоматически созданым и непотверждённым
	 */
	public void markAutoCreatedNotConfirm()
	{
		this.autoCreatedNotConfirm = true;
	}

	/**
	 * @return задолженность
	 */
	public Invoice getInvoice()
	{
		return invoice;
	}

	/**
	 * @param invoice задолженность
	 */
	public void setInvoice(Invoice invoice)
	{
		this.invoice = invoice;
	}

	/**
	 * @return наименование банка
	 */
	public String getBankName()
	{
		return bankName;
	}

	/**
	 * @param bankName наименование банка
	 */
	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	/**
	 * @return счет банка
	 */
	public String getBankAccount()
	{
		return bankAccount;
	}

	/**
	 * @param bankAccount счет банка
	 */
	public void setBankAccount(String bankAccount)
	{
		this.bankAccount = bankAccount;
	}

	/**
	 * @return наименование услуги
	 */
	public String getSubscriptionName()
	{
		return subscriptionName;
	}

	/**
	 * @param subscriptionName наименование услуги
	 */
	public void setSubscriptionName(String subscriptionName)
	{
		this.subscriptionName = subscriptionName;
	}

	/**
	 * @return доступны ли операции по ивойсу
	 */
	public boolean isOperationAvailable()
	{
		return isOperationAvailable;
	}

	/**
	 * @param operationAvailable доступны ли операции по ивойсу
	 */
	public void setOperationAvailable(boolean operationAvailable)
	{
		isOperationAvailable = operationAvailable;
	}

	/**
	 * @return true - требуется подтверджение
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
