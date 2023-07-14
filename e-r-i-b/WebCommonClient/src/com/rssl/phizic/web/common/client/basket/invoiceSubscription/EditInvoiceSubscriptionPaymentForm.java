package com.rssl.phizic.web.common.client.basket.invoiceSubscription;

import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.web.actions.payments.forms.EditPaymentForm;

/**
 * Форма редактирование заявки на подписку получения инвойсов
 * @author niculichev
 * @ created 01.06.14
 * @ $Author$
 * @ $Revision$
 */
public class EditInvoiceSubscriptionPaymentForm extends EditPaymentForm
{
	private AccountingEntity accountingEntity; // объект учета, в разрезе которого происходит редактирование
	private String redirectUrl;

	public String getRedirectUrl()
	{
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl)
	{
		this.redirectUrl = redirectUrl;
	}

	public AccountingEntity getAccountingEntity()
	{
		return accountingEntity;
	}

	public void setAccountingEntity(AccountingEntity accountingEntity)
	{
		this.accountingEntity = accountingEntity;
	}
}
