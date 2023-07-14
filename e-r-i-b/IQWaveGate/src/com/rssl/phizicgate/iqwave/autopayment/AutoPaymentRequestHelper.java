package com.rssl.phizicgate.iqwave.autopayment;

import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import com.rssl.phizicgate.iqwave.utils.AutoPaymentCompositeId;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author osminin
 * @ created 31.01.2011
 * @ $Author$
 * @ $Revision$
 * Хелпер для заполнения запросов
 */
public class AutoPaymentRequestHelper
{
	/**
	 * Заполнить сообщение AutoPayListRequest
	 * @param cards список карт
	 * @param message сообщение
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void fillAutoPayListRequest(List<Card> cards, GateMessage message) throws GateException, GateLogicException
	{
		for (Card card: cards)
			message.appendParameter(Constants.AUTO_PAY_CARD_NO_TEG, card.getNumber());
	}

	/**
	 * Запролнить базовое сообщение, используется в AutoPayReportRequest, AutoPayStateRequest, AutoPayRegisterRequest,
	 * AutoPayCorrectionRequest, AutoPayCancelRequest
	 * @param externalId идентификатор
	 * @param message сообщение
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public void fillBaseRequest(String externalId, GateMessage message) throws GateException, GateLogicException
	{
		AutoPaymentCompositeId compositeId = new AutoPaymentCompositeId(externalId);
		fillBaseRequest(message, compositeId.getCardNumber(), compositeId.getTelNumber(),  compositeId.getProviderId());
	}

	/**
	 * Заполнить базовое сообщение, используется в AutoPayReportRequest, AutoPayStateRequest, AutoPayRegisterRequest,
	 * AutoPayCorrectionRequest, AutoPayCancelRequest
	 * @param message сообщение
	 * @param cardNumber Номер карты
	 * @param requisite Номер лицевого счета/телефона.
	 * @param routeCode Код сервиса (маршрута) организации получателя
	 */
	public void fillBaseRequest(GateMessage message, String cardNumber, String requisite, String routeCode)
	{
		message.addParameter(Constants.AUTO_PAY_CARD_NO_TEG, cardNumber);
		message.addParameter(Constants.AUTO_PAY_TEL_NO_TEG, requisite);
		Element element = message.getDocument().getDocumentElement();
		Element providerId = XmlHelper.appendSimpleElement(element, Constants.AUTO_PAY_PROVIDER_ID_TEG);
		XmlHelper.appendSimpleElement(providerId, Constants.DIG_CODE_TEG, routeCode);
	}
}
