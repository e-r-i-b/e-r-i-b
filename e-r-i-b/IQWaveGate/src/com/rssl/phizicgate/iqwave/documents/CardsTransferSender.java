package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.payments.CardsTransfer;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 * Запрос на исполнение:
 * Элемент <Body> содержит следующие дочерние элементы:
 * Элемент	        Тип	        Комментарий	                    Кратность
 * <DebitCard>	    CardInf     Информация по карте списания	[1]
 * <CurrCode>	    IsoCode     Валюта операции	                [1]
 * <CreditCard>	    CardNumber  Номер карты зачисления	        [1]
 * <Summa>	        Money       Cумма платежа	                [1]
 *
 *  Ответ об исполнении:
 * Элемент <Body> содержит следующие дочерние элементы:
 *
 * Элемент	            Тип	    Комментарий	        Кратность
 * <AuthorizeCode>	AuthCode    Код авторизации	    [1]
 */
public class CardsTransferSender extends IQWaveAbstractDocumentSender
{
	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public CardsTransferSender(GateFactory factory)
	{
		super(factory);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if (!CardsTransfer.class.isAssignableFrom(document.getType()))
		{
			throw new GateException("Неверный тип платежа - ожидается наследник CardsTransfer");
		}

		CardsTransfer payment = (CardsTransfer) document;
		Money amount = payment.getChargeOffAmount() == null ? payment.getDestinationAmount() : payment.getChargeOffAmount();

		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.CARDS_TRANSFER_REQUEST);
		//DebitCard Информаци по карте списания
		RequestHelper.appendCardInfForCardsTransfer(message, payment.getChargeOffCard());
		//CurrCode Валюта операции 
		message.addParameter("CurrCode", amount.getCurrency().getCode());
		//CreditCard Номер карты зачисления
		message.addParameter("CreditCard", payment.getReceiverCard());
		//Summa Сумма платежа
		RequestHelper.appendSumma(message, amount, "Summa");

		Document response = serviceFacade.sendOnlineMessage(message, null);

		payment.setExternalId(getExternalId(response));

		String authorizeCode = XmlHelper.getSimpleElementValue(response.getDocumentElement(), Constants.AUTHORIZE_CODE_FIELD_NAME);
		payment.setAuthorizeCode(authorizeCode);
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
	}

	protected String getConfirmRequestName(GateDocument document)
	{
		throw new UnsupportedOperationException();
	}
}
