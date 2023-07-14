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
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.*;

/**
 * @author Krenev
 * @ created 16.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class DefaultBusinessDocumentSender extends BusinessDocumentHandlerBase
{
	/**
	 * ���������� ��������
	 * @param document ��������
	 * @param stateMachineEvent
	 * @throws com.rssl.common.forms.DocumentLogicException ����������� �������� ��������, ����� ��������� ������
	 */
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof ConvertableToGateDocument))
			throw new DocumentException("��������� ��������� ConvertableToGateDocument");

		if (document instanceof SynchronizableDocument)
			((SynchronizableDocument) document).setSendNodeNumber(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());

		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		try
		{
			documentService.send(((ConvertableToGateDocument)document).asGateDocument());
		}
		catch (TemporalGateException e)
		{
			throw new TemporalDocumentException(e);
		}
		catch (RedirectGateLogicException e)
		{
			throw new RedirectDocumentLogicException(e);
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
			throw new DocumentLogicException(e, e.getErrCode());
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
