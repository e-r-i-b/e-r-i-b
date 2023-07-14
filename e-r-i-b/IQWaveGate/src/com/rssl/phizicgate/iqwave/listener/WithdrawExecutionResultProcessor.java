package com.rssl.phizicgate.iqwave.listener;

import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * обработчик ответов на результат исполнения отмены оплаты заказа/возврата товара
 * @author gladishev
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class WithdrawExecutionResultProcessor extends ExecutionResultProcessorBase
{
	public void fillPaymentData(SynchronizableDocument synchronizableDocument, Document bodyContent) throws GateException
	{
		if (synchronizableDocument.getType() != WithdrawDocument.class)
		{
			throw new GateException("Документ " + synchronizableDocument.getExternalId() + " имеет неверный тип");
		}

		WithdrawDocument payment = (WithdrawDocument) synchronizableDocument;
		Element element = bodyContent.getDocumentElement();

		payment.setAuthorizeCode(XmlHelper.getSimpleElementValue(element, Constants.AUTHORIZE_CODE_FIELD_NAME));

		// сеттим идентификатор операции SVFE
		String operationIdentifier = XmlHelper.getSimpleElementValue(element, Constants.OPERATIOIN_IDENTIFIER);
		if (!StringHelper.isEmpty(operationIdentifier))
			payment.setIdFromPaymentSystem(operationIdentifier);
	}
}
