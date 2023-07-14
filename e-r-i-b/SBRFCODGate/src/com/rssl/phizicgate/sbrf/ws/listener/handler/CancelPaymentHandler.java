package com.rssl.phizicgate.sbrf.ws.listener.handler;

import com.rssl.phizicgate.sbrf.ws.listener.OfflineRequestHandler;
import com.rssl.phizicgate.sbrf.ws.listener.InternalMessageInfoContainer;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 11.01.2007
 * Time: 11:34:36
 * To change this template use File | Settings | File Templates.
 */
public class CancelPaymentHandler implements OfflineRequestHandler
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	public CancelPaymentHandler()
	{
	}

	public boolean handleMessage(InternalMessageInfoContainer messageInfoContainer, Object object) throws GateException, GateLogicException
	{
		String errorCode = messageInfoContainer.getErrorCode();

		if( errorCode == null)
			throw new GateException("Ошибка при обработке платежа, не найден код ошибки id:" + messageInfoContainer.getLink());

		UpdateDocumentService updateDocumentService = GateSingleton.getFactory().service(UpdateDocumentService.class);

		SynchronizableDocument document = (SynchronizableDocument)object;

		updateDocumentService.update(document, getUpdateCommand(messageInfoContainer));

		return true;
	}

	public DocumentCommand getUpdateCommand(InternalMessageInfoContainer messageInfoContainer)
	{
		String outputText = messageInfoContainer.getErrorText();
		String errorCode = messageInfoContainer.getErrorCode();
		String messageLink = messageInfoContainer.getLink();

		Map<String, Object> additionalFields = new HashMap<String, Object>();
		DocumentEvent documentEvent = DocumentEvent.ERROR;

		/*
		   в случае ERROR_VALIDATE_CODE записываем ошибку в лог, и помечаем плтеж как отклоненный
		   так как произошла ошибка в системе, иначе передаем текст ошибки клиенту
		   */
		if( errorCode.equals( DefaultErrorMessageHandler.ERROR_VALIDATE_CODE) )
		{
			String exceptionId = Long.toString(new Date().getTime());
			log.error(new GateException(exceptionId + ": Ошибка при обработке платежа id:"+ messageLink + ", сообщение:" + outputText));
			outputText = "Ошибка обработки документа. Код ошибки " + exceptionId
					+ ". Обратитесь в банк.";
			documentEvent = DocumentEvent.REFUSE;
		}

		if( errorCode.equals(DefaultErrorMessageHandler.ERROR_CLIENT_CODE) )
		{
			String exceptionId = Long.toString(new Date().getTime());
			log.error(new GateException(exceptionId + ": Ошибка при обработке платежа id:"+ messageLink + ", сообщение:" + outputText));
			documentEvent = DocumentEvent.REFUSE;
		}

		additionalFields.put(DocumentCommand.ERROR_TEXT, outputText);
		return new DocumentCommand(documentEvent, additionalFields);
	}
}