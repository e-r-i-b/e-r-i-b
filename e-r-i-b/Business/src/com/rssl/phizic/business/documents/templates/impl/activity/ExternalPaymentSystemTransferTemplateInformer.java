package com.rssl.phizic.business.documents.templates.impl.activity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.billing.TemplateState;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.filters.ActiveTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.ReadyToPaymentTemplateFilter;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author tisov
 * @ created 25.05.15
 * @ $Author$
 * @ $Revision$
 * Класс для получения активности по шаблону платежа по свободным реквизитам
 */
public class ExternalPaymentSystemTransferTemplateInformer extends PaymentSystemTransferTemplateInformer
{
	private static final BillingService billingService = new BillingService();

	private TemplateState templateState;
	private boolean noBilling;

	/**
	 * ctor
	 * @param template шаблон
	 * @throws BusinessException
	 */
	public ExternalPaymentSystemTransferTemplateInformer(TemplateDocument template) throws BusinessException
	{
		active      = new ActiveTemplateFilter().accept(template);
		readyToPay  = new ReadyToPaymentTemplateFilter().accept(template);
		String billingCode = template.getBillingCode();
		this.noBilling = true;

		if (!StringHelper.isEmpty(billingCode))
		{
			Billing billing = billingService.getByCode(billingCode);
			if (billing != null)
			{
				this.templateState = billing.getTemplateState();
				this.noBilling = false;
			}
		}

	}

	@Override
	protected boolean getAvailableEdit()
	{
		return super.getAvailableEdit() && isAdditionalConditionCompleted();
	}

	@Override
	protected boolean getAvailablePay()
	{
		return super.getAvailablePay() && isAdditionalConditionCompleted();
	}

	@Override
	protected boolean getAvailableAutoPay() throws BusinessException
	{
		return super.getAvailableAutoPay() && isAdditionalConditionCompleted();
	}

	private boolean isAdditionalConditionCompleted()
	{
		return this.noBilling || TemplateState.INACTIVE != this.templateState;
	}
}
