/**
 * PfrDoneServiceSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.ws.esberiblistener.pfr;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.pfr.PFRStatement;
import com.rssl.phizic.business.pfr.PFRStatementService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.pfr.PFRStatementClaim;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.pfr.PFRService;
import com.rssl.phizic.gate.pfr.StatementStatus;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRqType;
import com.rssl.phizic.ws.esberiblistener.pfr.generated.PfrDoneRsType;
import com.rssl.phizicgate.esberibgate.pfr.PFRServiceImpl;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.Map;

/**
 * Реализация обработчика входящих сообщений по ПФР от шины
 *
 * @author gladishev
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */

public class EsbEribPFRBackServiceImpl extends EsbEribPFRBackServiceBase<PFRStatementClaim>
{
	/**
	 * Список дополнительных полей документа
	 */
	private static final Map<String, Object> additionalFields = Collections.EMPTY_MAP;

	/**
	 * Сервис по обновления документов
	 */
	private static final UpdateDocumentService updateDocumentService =
		GateSingleton.getFactory().service(UpdateDocumentService.class);

	/**
	 * Сервис для обновления выписки
	 */
	private static final PFRStatementService pfrStatementService = new PFRStatementService();

	@Override
	protected PfrDoneRsType updateDocument(PFRStatementClaim document, PfrDoneRqType parameters)
	{
		if (document.isReady() != StatementStatus.NOT_YET_AVAILABLE)
		{
			log.warn("Заявка с operationId = " + parameters.getResult().getOperationId() +
					"должна иметь статус 'Выписка ещё не доступна. Может стать доступна позднее'" );
			return createResponse(-1L);
		}

		PFRService pfrService = new PFRServiceImpl(GateSingleton.getFactory());

		try
		{
			String xmlText = pfrService.getStatement(document);

			updateDocument(document, DocumentEvent.EXECUTE);
			updatePFRStatement(document, xmlText);
			return createResponse(0L);
		}
		catch (RemoteException e)
		{
			logException(e);
			return createResponse(-1L);
		}
		catch (Exception e)
		{
			logException(e);
			return createResponse(-1L);
		}
	}

	private void updatePFRStatement(SynchronizableDocument document, String xmlText)
	{
		PFRStatement statement = new PFRStatement();

		statement.setClaimId(document.getId());
		statement.setStatementXml(xmlText);

		try
		{
			pfrStatementService.add(statement);
		}
		catch (BusinessException e)
		{
			logException(e);
		}
	}

	private void updateDocument(SynchronizableDocument document, DocumentEvent event)
	{
		DocumentCommand command = new DocumentCommand(event, additionalFields);

		try
		{
			updateDocumentService.update(document, command);
		}
		catch (GateException e)
		{
			logException(e);
		}
		catch (GateLogicException e)
		{
			logException(e);
		}
	}

	protected PFRStatementClaim findDocument(String operationId)
	{
		try
		{
			SynchronizableDocument result = updateDocumentService.find(operationId);

			if (!(result instanceof PFRStatementClaim))
				return null;

			return (PFRStatementClaim) result;
		}
		catch (GateException e)
		{
			logException(e);
		}
		catch (GateLogicException e)
		{
			logException(e);
		}
		return null;
	}
}
