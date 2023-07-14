package com.rssl.phizicgate.sbrf.senders;

import com.rssl.phizgate.common.payments.documents.AbstractDocumentSenderBase;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.SpecificGateConfig;
import com.rssl.phizic.gate.documents.AbstractAccountTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.WithdrawDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.MessageHead;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.sbrf.utils.DocumentOfflineHandlerHelper;
import com.rssl.phizicgate.sbrf.ws.Constants;
import org.w3c.dom.Document;

import java.util.Calendar;

/**
 * @author Egorova
 * @ created 22.04.2008
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractDocumentSender extends AbstractDocumentSenderBase
{
	protected AbstractDocumentSender(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * заполнить сообщение гейту данными из документа
	 *
	 * @param gateMessage сообщение
	 * @param gateDocument документ
	 */
	abstract void fillGateMessage(GateMessage gateMessage, GateDocument gateDocument) throws GateLogicException, GateException;

	/**
	 * послать документ
	 * @param gateDocument документ
	 * @throws com.rssl.phizic.gate.exceptions.GateException
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException
	 */
	public void send(GateDocument gateDocument) throws GateException, GateLogicException
	{
		WebBankServiceFacade service = getFactory().service(WebBankServiceFacade.class);

		GateMessage gateMessage = service.createRequest(getRequestName(gateDocument));

		fillGateMessage(gateMessage, gateDocument);
		MessageHead messageHead = new MessageHeadImpl(String.valueOf(gateDocument.getId()), Calendar.getInstance(), null, null, null, null);

		Document responce = service.sendOfflineMessage(gateMessage, messageHead);

		//для документов требующих обратной связи устанавливаем идентификатор.
		if (gateDocument instanceof SynchronizableDocument)
		{
			SynchronizableDocument syncMessage = (SynchronizableDocument) gateDocument;
			//в ЦОД нет идентификаторов платежей, но каждому платежу соотвествует только одно сообщение
			//поэтому делаем идентификатор сообщения внещним идентификатором платежа
			// Склеиваем дату создания документа с идентификатором платежа, отпадет необходимость искать ее при запросе, например, на отзыв
			syncMessage.setExternalId(StringHelper.getEmptyIfNull(getMessageIdFromResponce(responce))
					+ Constants.EXTERNAL_ID_DELIMITER + StringHelper.getEmptyIfNull(getDateFromResponce(responce)));

			addOfflineDocumentInfo(syncMessage);
		}
	}

	public void repeatSend(GateDocument gateDocument) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException("Повторная отправка не поддерживается");
	}

	private String getMessageIdFromResponce(Document responce)
	{
		if (responce != null)
			//todo смотри BUG016540: (None)Доработать MessagingServiceSupport для передачи все информации.
			return XmlHelper.getSimpleElementValue(responce.getDocumentElement(), "messageId");
		else
			return null;
	}

	private String getDateFromResponce(Document responce)
	{
		return responce != null ? XmlHelper.getSimpleElementValue(responce.getDocumentElement(), "messageDate") : null;
	}

	abstract protected String getRequestName(GateDocument gateDocument) throws GateException, GateLogicException;

	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		//не реализовано
		return;
	}

	public void rollback(WithdrawDocument document) throws GateException, GateLogicException
	{
		//Возможность отзыва платежа не реализована
		return;
	}

	public void confirm(GateDocument document) throws GateException, GateLogicException
	{
		//Возможность подтверждения платежа не реализована
		return;
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		//Возможность валидации платежа не реализована. Ничего не делаем.
		return;
	}

	/**
	 * BUG024178: если клиент не SBOL передаем clientId  = 0
	 * */
	protected String getClientId(String clientId) throws GateException, GateLogicException
	{
		//TODO: после тестирования BUG024530 в банке убрать (всегда возвращать 0)
//		BackRefClientService backRefClientService = getFactory().service(BackRefClientService.class);
//		if (clientId != null)
//		{
//			String creationType = backRefClientService.getClientCreationType(clientId);
//			if (!creationType.equals(SBOL_CREATION_TYPE)) return "0";
//		}
//		return clientId;
		return "0";
	}

	/**
	 * если в назначение платежа стоит "длинное" тире, то банковские системы не могут обработать этот платеж
	 */
	protected String processGround(AbstractAccountTransfer payment)
	{
		String ground = payment.getGround();
		if(ground == null)
			return null;
		return StringHelper.replaceQuotes(ground.replace('–','-'));
	}

	protected void fillPaymentData(GateDocument document, String additInfo) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractAccountTransfer))
			throw new GateException("Неверный тип документа. Ожидается AbstractAccountTransfer");

		DocumentOfflineHandlerHelper.getSuccessOfflineRequestHandler(document.getType()).fillPaymentData((AbstractAccountTransfer) document, additInfo);
	}
}
