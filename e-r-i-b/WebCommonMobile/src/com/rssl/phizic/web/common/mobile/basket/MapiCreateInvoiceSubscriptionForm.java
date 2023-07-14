package com.rssl.phizic.web.common.mobile.basket;

import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.web.actions.payments.forms.EditServicePaymentForm;

/**
 * @author Balovtsev
 * @since 30.10.14.
 */
public class MapiCreateInvoiceSubscriptionForm extends EditServicePaymentForm
{
	private AccountingEntity accountingEntity;
	private Long             accountingEntityId;
	private Long             serviceId;
	private Long             autoId;

	public Long getAutoId()
	{
		return autoId;
	}

	public void setAutoId(Long autoId)
	{
		this.autoId = autoId;
	}

	public Long getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(Long serviceId)
	{
		this.serviceId = serviceId;
	}

	public Long getAccountingEntityId()
	{
		return accountingEntityId;
	}

	public void setAccountingEntityId(Long accountingEntityId)
	{
		this.accountingEntityId = accountingEntityId;
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
