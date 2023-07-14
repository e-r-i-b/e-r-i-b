package com.rssl.phizic.business.informers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.billing.BillingService;
import com.rssl.phizic.business.dictionaries.billing.TemplateState;
import com.rssl.phizic.business.documents.OperationMessagesConfig;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.filters.StateTemplateFilter;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Collections;
import java.util.List;

/**
 * Информатор клиента внешних операций недоговорным поставщикам услуг
 *
 * @author khudyakov
 * @ created 14.08.14
 * @ $Author$
 * @ $Revision$
 */
public class ExternalServiceProviderTemplateStateInformer implements DocumentStateInformer
{
	private static final BillingService billingService = new BillingService();

	private TemplateDocument template;


	public ExternalServiceProviderTemplateStateInformer(TemplateDocument template)
	{
		this.template = template;
	}

	public List<String> inform() throws BusinessException
	{
		if (template.getBillingCode() == null)
		{
			return Collections.emptyList();
		}

		Billing billing = billingService.getByCode(template.getBillingCode());
		if (billing == null)
		{
			return Collections.emptyList();
		}

		TemplateState templateState = billing.getTemplateState();
		if (TemplateState.PLANING_FOR_DEACTIVATE == templateState)
		{
			return Collections.singletonList(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.TEMPLATE_PLANING_FOR_DEACTIVATE));
		}

		if (TemplateState.INACTIVE == templateState)
		{
			return Collections.singletonList(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));
		}

		return Collections.emptyList();
	}

	public boolean isActive()
	{
		if (template == null)
		{
			return false;
		}

		return FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER == template.getFormType() && new StateTemplateFilter(StateCode.SAVED_TEMPLATE, StateCode.WAIT_CONFIRM_TEMPLATE, StateCode.TEMPLATE).accept(template);
	}
}
