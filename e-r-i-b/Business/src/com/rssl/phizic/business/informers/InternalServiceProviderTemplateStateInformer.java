package com.rssl.phizic.business.informers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.dictionaries.providers.wrappers.ServiceProviderShortcut;
import com.rssl.phizic.business.documents.OperationMessagesConfig;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.filters.StateTemplateFilter;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Collections;
import java.util.List;

/**
 * Информатор клиента внешних операций договорным, недоговорным поставщикам услуг
 *
 * @author khudyakov
 * @ created 14.08.14
 * @ $Author$
 * @ $Revision$
 */
public class InternalServiceProviderTemplateStateInformer implements DocumentStateInformer
{
	private static final ServiceProviderService serviceProviderService  = new ServiceProviderService();

	private TemplateDocument template;


	public InternalServiceProviderTemplateStateInformer(TemplateDocument template)
	{
		this.template = template;
	}

	public List<String> inform() throws BusinessException
	{
		ServiceProviderShortcut shortcut = serviceProviderService.getShortcutById(template.getReceiverInternalId());
		if (shortcut == null)
		{
			return Collections.singletonList(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.CHANGED_PROVIDER_MESSAGE));
		}

		ServiceProviderState state = shortcut.getState();
		if (ServiceProviderState.NOT_ACTIVE == state)
		{
			return Collections.singletonList(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));
		}

		if (ServiceProviderState.ACTIVE != state && ServiceProviderState.MIGRATION != state)
		{
			return Collections.singletonList(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.CHANGED_PROVIDER_MESSAGE));
		}

		if (!shortcut.isWebPaymentAvailability())
		{
			return Collections.singletonList(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.NOT_ACTIVE_PROVIDER_MESSAGE));
		}

		if (ServiceProviderState.ARCHIVE != state && shortcut.isPlaningForDeactivate())
		{
			return Collections.singletonList(ConfigFactory.getConfig(OperationMessagesConfig.class).getOperationMessage(OperationMessagesConfig.TEMPLATE_PLANING_FOR_DEACTIVATE));
		}

		return Collections.emptyList();
	}

	public boolean isActive()
	{
		if (template == null)
		{
			return false;
		}

		return FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER == template.getFormType() && new StateTemplateFilter(StateCode.SAVED_TEMPLATE, StateCode.WAIT_CONFIRM_TEMPLATE, StateCode.TEMPLATE).accept(template);
	}
}
