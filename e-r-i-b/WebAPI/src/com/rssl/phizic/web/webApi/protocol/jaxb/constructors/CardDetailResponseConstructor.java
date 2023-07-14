package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.operations.card.GetCardInfoOperation;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.helpers.CommonElementsHelper;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.StatusCode;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.IdRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.CardDetailResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productdetail.CardDetailTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productdetail.CreditTypeTag;

/**
 * Заполняет ответ на запрос детальной информации по картам
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

public class CardDetailResponseConstructor extends JAXBResponseConstructor<Request, CardDetailResponse>
{
	@Override protected CardDetailResponse makeResponse(Request request) throws Exception
	{
		IdRequest rqst = (IdRequest) request;
		CardDetailResponse response = new CardDetailResponse();
		Long cardId;
		try
		{
			cardId = Long.valueOf(rqst.getBody().getId());
		}
		catch (NumberFormatException e)
		{
			response.setStatus(new Status(StatusCode.ACCESS_DENIED, "неправильный ИД карты: " + rqst.getBody().getId()));
			return response;
		}
		GetCardInfoOperation cardInfoOperation = createOperation(GetCardInfoOperation.class);
		cardInfoOperation.initialize(cardId);
		CardLink cardLink = cardInfoOperation.getEntity();
		Card card = cardLink.getCard();
		CardDetailTag cardDetailTag = new CardDetailTag();
		CommonElementsHelper.fillCardTag(cardLink, cardDetailTag);
		Client cardClient = cardLink.getCardClient();
		cardDetailTag.setHolderName(PersonHelper.getFormattedPersonName(cardClient.getFirstName(), cardClient.getSurName(), cardClient.getPatrName()));
		if (card.getAvailableCashLimit() != null)
			cardDetailTag.setAvailableCashLimit(new MoneyTag(card.getAvailableCashLimit()));
		if (card.getPurchaseLimit() != null)
			cardDetailTag.setPurchaseLimit(new MoneyTag(card.getPurchaseLimit()));
		if (card.getCardType().equals(CardType.credit))
		{
			CreditTypeTag creditTypeTag = new CreditTypeTag();
			if (card.getOverdraftLimit() != null)
				creditTypeTag.setLimit(new MoneyTag(card.getOverdraftLimit()));
			if (card.getOverdraftOwnSum() != null)
				creditTypeTag.setOwnSum(new MoneyTag(card.getOverdraftOwnSum()));
			if (card.getOverdraftMinimalPayment() != null)
				creditTypeTag.setMinPayment(new MoneyTag(card.getOverdraftMinimalPayment()));
			if (card.getOverdraftMinimalPaymentDate() != null)
				creditTypeTag.setMinPaymentDate(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(card.getOverdraftMinimalPaymentDate()));
		}
		response.setCard(cardDetailTag);
		return response;
	}
}
