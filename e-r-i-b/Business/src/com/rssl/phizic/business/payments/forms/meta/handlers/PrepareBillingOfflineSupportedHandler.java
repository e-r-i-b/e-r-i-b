package com.rssl.phizic.business.payments.forms.meta.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.forms.meta.PrepareDocumentHandler;
import com.rssl.phizic.business.payments.forms.meta.conditions.OfflineDelayedCondition;


import java.util.Calendar;

/**
 * @author komarov
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 * ’эндлер дл€ подготовки биллинговых платежей, учитывающий возможную оплату при недоступных внешних системах
 */
public class PrepareBillingOfflineSupportedHandler extends PrepareDocumentHandler
{
	private static final OfflineDelayedCondition OFFLINE_DELAYED_CONDITION = new OfflineDelayedCondition();

	protected boolean isOfflineDelayedSystem(BusinessDocument document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			ServiceProviderShort provider = DocumentHelper.getDocumentProvider(document);
			                                                                           //ѕо свободным реквизитам //ƒоступен при неработающей внешней системе
			return OFFLINE_DELAYED_CONDITION.accepted(document, stateMachineEvent) && !(provider == null || provider.isOfflineAvailable());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new DocumentLogicException(e);
		}
	}

}
