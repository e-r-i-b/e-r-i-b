package com.rssl.phizic.esb.ejb.mock.light.CardToCard;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.light.LightESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardRequest;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.CardToCardResponse;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadRequestType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadResponseType;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;

import java.util.List;

/**
 * @author akrenev
 * @ created 18.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушка, имитирующа€ работу шины при переводе карта-карта
 */

public class ESBMockCardToCardPaymentProcessor extends LightESBMockProcessorBase<CardToCardRequest, CardToCardResponse>
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
	public ESBMockCardToCardPaymentProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected boolean needSendResult(ESBMessage<CardToCardRequest> xmlRequest, CardToCardResponse message)
	{
		return getRandomBoolean();
	}

	@Override
	protected boolean needSendOnline(ESBMessage<CardToCardRequest> xmlRequest, CardToCardResponse message)
	{
		return getRandomBoolean();
	}

	private HeadResponseType getHeader(HeadRequestType requestHead, String properties)
	{
		HeadResponseType.Error status = getRequestIndex() % 10 == 9? getErrorStatus("2940", "ќшибка"): getOkStatus();
		if (isExternalPayment(properties) || getRandomBoolean())
			return getHeader(requestHead, FROM_ABONENT_SVFE, TARGET_SYSTEM_SVFE, status);

		return getHeader(requestHead, FROM_ABONENT_WAY4, TARGET_SYSTEM_WAY4, status);
	}

	@Override
	protected void process(ESBMessage<CardToCardRequest> xmlRequest)
	{
		CardToCardRequest request = xmlRequest.getObject();
		String properties = request.getBody().getProperties();

		CardToCardResponse response = new CardToCardResponse();

		HeadResponseType header = getHeader(request.getHead(), properties);
		response.setHead(header);
		response.setBody(getBody(request, header.getError()));

		send(xmlRequest, response, getServiceName(request));
	}

	private CardToCardResponse.Body getBody(CardToCardRequest request, HeadResponseType.Error status)
	{
		if (!isOkStatus(status))
			return null;

		CardToCardResponse.Body body = new CardToCardResponse.Body();
		body.setAuthorizeCode(getAuthCode());
		return body;
	}

	private String getServiceName(CardToCardRequest request)
	{
		if (isExternalPayment(request.getBody().getProperties()))
			return "CardToCardIntraBankTransfer";

		try
		{
			SimpleService service = new SimpleService();

			DetachedCriteria firstCardLoginIdCriteria = DetachedCriteria.forClass(CardLink.class);
			firstCardLoginIdCriteria.add(Expression.eq("number", request.getBody().getDebitCard().getCardNumber()));
			firstCardLoginIdCriteria.setProjection(Projections.property("loginId"));
			List<Long> firstCardLoginIds = service.find(firstCardLoginIdCriteria);

			DetachedCriteria secondCardLoginIdCriteria = DetachedCriteria.forClass(CardLink.class);
			secondCardLoginIdCriteria.add(Expression.eq("number", request.getBody().getCreditCard()));
			secondCardLoginIdCriteria.setProjection(Projections.property("loginId"));
			List<Long> secondCardLoginIds = service.find(secondCardLoginIdCriteria);

			if (CollectionUtils.containsAny(firstCardLoginIds, secondCardLoginIds))
				return "CardToCardTransfer";
		}
		catch (BusinessException e)
		{
			log.error("[LIGHT_ESB-MQ] Ќе смогли определить принадлежность карт одному клиенту.", e);
		}

		return "CardToCardOurBankTransfer";
	}


	private boolean isExternalPayment(String properties)
	{
		return MASTERCARD.equals(properties) || VISA.equals(properties);
	}

}
