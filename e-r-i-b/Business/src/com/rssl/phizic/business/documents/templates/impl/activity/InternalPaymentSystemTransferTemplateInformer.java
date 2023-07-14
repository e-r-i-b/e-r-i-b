package com.rssl.phizic.business.documents.templates.impl.activity;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderHelper;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderState;
import com.rssl.phizic.business.dictionaries.providers.wrappers.ServiceProviderShortcut;
import com.rssl.phizic.business.documents.templates.ActivityInfo;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.filters.ActiveTemplateFilter;
import com.rssl.phizic.business.documents.templates.service.filters.ReadyToPaymentTemplateFilter;

/**
 * Получение информации по автивности шаблона договорному постаавщику услуг
 *
 * @author khudyakov
 * @ created 24.11.14
 * @ $Author$
 * @ $Revision$
 */
public class InternalPaymentSystemTransferTemplateInformer extends PaymentSystemTransferTemplateInformer
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	private ServiceProviderShortcut shortcut;

	public ActivityInfo inform() throws BusinessException
	{
		if (shortcut == null)
		{
			return new FailTransferTemplateInformer().inform();
		}

		return super.inform();
	}

	public InternalPaymentSystemTransferTemplateInformer(TemplateDocument template, ServiceProviderShortcut shortcut) throws BusinessException
	{
		this.shortcut   = shortcut;
		this.active     = new ActiveTemplateFilter().accept(template);
		this.readyToPay = new ReadyToPaymentTemplateFilter().accept(template);
	}

	public InternalPaymentSystemTransferTemplateInformer(TemplateDocument template) throws BusinessException
	{
		shortcut    = serviceProviderService.getShortcutById(template.getReceiverInternalId());
		active      = new ActiveTemplateFilter().accept(template);
		readyToPay  = new ReadyToPaymentTemplateFilter().accept(template);
	}

	protected boolean getAvailableEdit()
	{
		return super.getAvailableEdit() && ServiceProviderState.ACTIVE == shortcut.getState() && shortcut.isEditPaymentSupported();
	}

	protected boolean getAvailableConfirm()
	{
		return super.getAvailableConfirm() && ServiceProviderState.ACTIVE == shortcut.getState();
	}

	protected boolean getAvailablePay()
	{
		return super.getAvailablePay() && ServiceProviderHelper.isReadyToPay(shortcut);
	}

	protected boolean getAvailableAutoPay() throws BusinessException
	{
		return super.getAvailableAutoPay() && ServiceProviderHelper.isAutoPaymentAllowed(shortcut);
	}

}
