package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.TemplateHelper;

/**
 * @author khudyakov
 * @ created 10.02.14
 * @ $Author$
 * @ $Revision$
 */
public class DeniedEditTemplateHandler extends TemplateHandlerBase<TemplateDocument>
{
	private static final String PROVIDER_UNSUPPORTED_EDIT_TEMPLATE = "Билинговый поставщик c id = %d не поддерживает редактирование платежа";

	public void process(TemplateDocument template, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			ServiceProviderShort provider = TemplateHelper.getTemplateProviderShort(template);
			if (provider != null && !provider.isEditPaymentSupported())
			{
				throw new DocumentLogicException(PROVIDER_UNSUPPORTED_EDIT_TEMPLATE);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
