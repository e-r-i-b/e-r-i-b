package com.rssl.phizic.web.common.client.basket.invoiceSubscription;

import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.web.actions.payments.forms.EditPaymentForm;

/**
 * ����� �������������� ������ �� �������� ��������� ��������
 * @author niculichev
 * @ created 01.06.14
 * @ $Author$
 * @ $Revision$
 */
public class EditInvoiceSubscriptionPaymentForm extends EditPaymentForm
{
	private AccountingEntity accountingEntity; // ������ �����, � ������� �������� ���������� ��������������
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
