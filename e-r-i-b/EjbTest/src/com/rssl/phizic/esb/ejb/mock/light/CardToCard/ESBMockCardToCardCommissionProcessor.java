package com.rssl.phizic.esb.ejb.mock.light.CardToCard;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.light.LightESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardInfoRequest;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardInfoResponse;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadRequestType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadResponseType;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 18.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушка, имитирующая работу шины при запросе комиссии для перевода карта-карта
 */

public class ESBMockCardToCardCommissionProcessor extends LightESBMockProcessorBase<CardToCardInfoRequest, CardToCardInfoResponse>
{
	private static final String SB_CARD     = "00";
	private static final String MASTERCARD  = "01";
	private static final String VISA        = "02";

	private static final String FROM_ABONENT_WAY4  = "WAY4";
	private static final String TARGET_SYSTEM_WAY4 = "WAY4";
	private static final String FROM_ABONENT_SVFE  = "SVFE";
	private static final String TARGET_SYSTEM_SVFE = "SVFE";

	/**
	 * конструктор
	 * @param module - модуль
	 */
	public ESBMockCardToCardCommissionProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected boolean needSendResult(ESBMessage<CardToCardInfoRequest> xmlRequest, CardToCardInfoResponse message)
	{
		return getRandomBoolean();
	}

	@Override
	protected boolean needSendOnline(ESBMessage<CardToCardInfoRequest> xmlRequest, CardToCardInfoResponse message)
	{
		return true;
	}

	private HeadResponseType getHeader(HeadRequestType requestHead, String properties)
	{
		if (isExternalPayment(properties) || getRandomBoolean())
			return getHeader(requestHead, FROM_ABONENT_SVFE, TARGET_SYSTEM_SVFE, getOkStatus());

		return getHeader(requestHead, FROM_ABONENT_WAY4, TARGET_SYSTEM_WAY4, getOkStatus());
	}

	private Money getCommission(CardToCardInfoRequest.Body body)
	{
		double paymentSum = new BigDecimal(body.getSumma()).doubleValue();
		//noinspection MagicNumber
		double commissionValue = paymentSum * 0.05; //берем 5 процентов от суммы платежа
		return new Money(new BigDecimal(commissionValue), parseCurrency(body.getCurrCode()));
	}

	@Override
	protected void process(ESBMessage<CardToCardInfoRequest> xmlRequest)
	{
		CardToCardInfoRequest request = xmlRequest.getObject();
		String properties = request.getBody().getProperties();

		CardToCardInfoResponse response = new CardToCardInfoResponse();

		HeadResponseType header = getHeader(request.getHead(), properties);
		response.setHead(header);
		response.setBody(getBody(request, header.getError()));

		send(xmlRequest, response, getServiceName(properties));
	}

	private CardToCardInfoResponse.Body getBody(CardToCardInfoRequest request, HeadResponseType.Error error)
	{
		if (!isOkStatus(error))
			return null;

		CardToCardInfoResponse.Body body = new CardToCardInfoResponse.Body();
		body.setAuthorizeCode(getAuthCode());
		body.setComission(getMoney(getCommission(request.getBody())));
		return body;
	}

	private boolean isExternalPayment(String properties)
	{
		return MASTERCARD.equals(properties) || VISA.equals(properties);
	}

	private String getServiceName(String properties)
	{
		return isExternalPayment(properties) ? "CardToCardIntraBankTransferCommission": "CardToCardOurBankTransferCommission";
	}
}
