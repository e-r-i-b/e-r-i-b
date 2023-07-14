package com.rssl.phizic.web.client.basket.invoiceSubscription;

import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.web.actions.payments.forms.CreateAutoSubscriptionPaymentForm;

/**
 * @author niculichev
 * @ created 04.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateInvoiceSubscriptionForm extends CreateAutoSubscriptionPaymentForm
{
	private Long autoId; //������������� ������������� ����������� ��������
	private Long accountingEntityId; // ������������� ������� �����
	private AccountingEntity accountingEntity; // ������ �����, � ������� �������� ���������� �������� ��������
	private Long serviceId; //������������� ������, ��� ������ �����

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
}
