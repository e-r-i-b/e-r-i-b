package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.esb.ejb.MessageProcessor;
import com.rssl.phizic.esb.ejb.updater.DocumentStateUpdater;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import javax.jms.Message;

/**
 * процессор с возможностью записать данные в присалнный документ
 * @author basharin
 * @ created 07.07.15
 * @ $Author$
 * @ $Revision$
 */

public abstract class ProcessorUpdateDocument<T> implements MessageProcessor
{
	private static final Log log = LogFactory.getLog(LogModule.Gate.toString());
	private static final int UNKNOWN_STATE = -105;

	protected abstract String getExternalId(T message, Message source);

	protected abstract StatusType getStatus(T message);

	protected abstract String getType(T message);

	protected abstract String getId(T message);

	protected abstract void updateDocument(SynchronizableDocument document, T message) throws GateLogicException, GateException;

	public final String getMessageType(Object message)
	{
		return getType(castMessage(message));
	}

	public final String getMessageId(Object message)
	{
		return getId(castMessage(message));
	}

	private T castMessage(Object message)
	{
		//noinspection unchecked
		return (T) message;
	}

	public final void processMessage(Object message, Message source) throws GateLogicException, GateException
	{
		//noinspection unchecked
		T response = castMessage(message);
		StatusType status = getStatus(response);
		String externalId = getExternalId(response, source);
		if (status.getStatusCode() == UNKNOWN_STATE)
		{
			log.error("Для документа \"" + externalId + "\" пришел неизвестный статус (-105).");
			return;
		}
		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		SynchronizableDocument document = updateDocumentService.find(externalId);
		updateDocument(document, response);
		DocumentStateUpdater.getInstance().updateDocument(externalId, getCommand(status));
	}

	private DocumentCommand getCommand(StatusType status)
	{
		if(status.getStatusCode() == 0)
			return new DocumentCommand(DocumentEvent.EXECUTE);
		return new DocumentCommand(DocumentEvent.REFUSE, Collections.<String, Object>singletonMap(DocumentCommand.ERROR_TEXT, status.getStatusDesc()));
	}
}
