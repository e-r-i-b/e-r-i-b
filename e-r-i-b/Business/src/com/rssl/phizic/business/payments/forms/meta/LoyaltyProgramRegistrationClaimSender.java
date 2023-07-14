package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.*;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.payments.ConvertableToGateDocument;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.LoyaltyProgramRegistrationClaim;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.*;

/**
 * Обработка заявки на регистрацию в программе лояльности
 * @author lukina
 * @ created 20.06.2014
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramRegistrationClaimSender extends BusinessDocumentHandlerBase
{
	private static final String URL_RO_REDIRECT = "urlToRedirect";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof LoyaltyProgramRegistrationClaim))
			throw new DocumentException("Ожидается LoyaltyProgramRegistrationClaim");

		if (document instanceof SynchronizableDocument)
			((SynchronizableDocument) document).setSendNodeNumber(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());

		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		try
		{
			documentService.send(((ConvertableToGateDocument)document).asGateDocument());
		}
		catch (TemporalGateException e)
		{
			throw new ForceRedirectDocumentLogicException(e, getParameter(URL_RO_REDIRECT));
		}
		catch (GateWrapperSendTimeOutException e)
		{
			throw new DocumentSendTimeOutException(e);
		}
		catch (GateSendTimeOutException e)
		{
			throw new DocumentSendTimeOutException(e);
		}
		catch (GateTimeOutException e)
		{
			throw new DocumentTimeOutException(e);
		}
		catch (OfflineExternalServiceException e)
		{
			throw new DocumentOfflineException(e);
		}
		catch (GateLogicException e)
		{
			throw new DocumentLogicException(e.getMessage(), e);
		}
		catch (GateException e)
		{
			throw new ForceRedirectDocumentLogicException(e, getParameter(URL_RO_REDIRECT));
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
