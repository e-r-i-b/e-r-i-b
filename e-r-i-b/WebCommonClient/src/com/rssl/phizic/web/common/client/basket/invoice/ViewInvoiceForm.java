package com.rssl.phizic.web.common.client.basket.invoice;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.business.forms.types.CardType;
import com.rssl.phizic.business.forms.types.UserResourceType;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author osminin
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ‘орма просмотра задолженности
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
	private List<CardLink> chargeOffResources;
	private boolean accessRecoverAutoSub;

	/**
	 * ¬озвращает да, если плажЄт €вл€етс€ автоматически созданым и непотверждЄнным
	 * @return ƒа, если плажЄт €вл€етс€ автоматически созданым и непотверждЄнным, нет в противном случае
	 */
	public boolean isAutoCreatedNotConfirm()
	{
		return autoCreatedNotConfirm;
	}

	/**
	 * ”станавливает флаг, что платЄж €вл€етс€ автоматически созданым и непотверждЄнным
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
	 * @return true - требуетс€ подтверджение
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

	/**
	 * @return ƒоступные источниики списани€ дл€ оплаты
	 */
	public List<CardLink> getChargeOffResources()
	{
		return chargeOffResources;
	}

	public void setChargeOffResources(List<CardLink> chargeOffResources)
	{
		this.chargeOffResources = chargeOffResources;
	}

	/**
	 * @return true - доступно восстановление автоподписки
	 * (т.е. автопоиск создан автоматически, посредством приостановки авподписки)
	 */
	public boolean isAccessRecoverAutoSub()
	{
		return accessRecoverAutoSub;
	}

	public void setAccessRecoverAutoSub(boolean accessRecoverAutoSub)
	{
		this.accessRecoverAutoSub = accessRecoverAutoSub;
	}
}
