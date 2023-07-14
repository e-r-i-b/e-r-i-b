package com.rssl.phizic.business.payments.forms.meta.ima;

import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.BusinessException;

/**
 * User: Balovtsev
 * Date: 26.09.2012
 * Time: 13:01:47
 */
public class UpdateIMAccountLinksHandler extends BusinessDocumentHandlerBase
{
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider();
		PersonData personData = personDataProvider == null ? null : personDataProvider.getPersonData();

		if(personData != null)
		{
			personData.setNeedReloadIMAccounts();

			try
			{
				GateSingleton.getFactory().service(CacheService.class).clearClientProductsCache(personData.getPerson().asClient(), IMAccountLink.class);
			}
			catch (GateException e)
			{
				throw new DocumentException(e);
			}
			catch (GateLogicException e)
			{
				throw new DocumentLogicException(e);
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}
		}
	}
}
