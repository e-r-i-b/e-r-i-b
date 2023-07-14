package com.rssl.phizic.esb.ejb.federal.documents;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.esb.ejb.MessageProcessor;
import com.rssl.phizic.esb.ejb.updater.DocumentStateUpdater;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Collections;
import javax.jms.Message;

/**
 * @author akrenev
 * @ created 01.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * базовый процессор обработки документов
 */

abstract class ProcessorBase<T> implements MessageProcessor
{
	private static final Log log = LogFactory.getLog(LogModule.Gate.toString());
	private static final int UNKNOWN_STATE = -105;

	protected abstract String getExternalId(T message, Message source);

	protected abstract StatusType getStatus(T message);

	protected abstract String getType(T message);

	protected abstract String getId(T message);

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
		DocumentStateUpdater.getInstance().updateDocument(externalId, getCommand(status));
	}

	private DocumentCommand getCommand(StatusType status)
	{
		if(status.getStatusCode() == 0)
			return new DocumentCommand(DocumentEvent.EXECUTE);
		return new DocumentCommand(DocumentEvent.REFUSE, Collections.<String, Object>singletonMap(DocumentCommand.ERROR_TEXT, status.getStatusDesc()));
	}
}
