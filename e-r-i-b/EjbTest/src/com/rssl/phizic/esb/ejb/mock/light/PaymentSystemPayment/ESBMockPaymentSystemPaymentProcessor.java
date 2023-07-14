package com.rssl.phizic.esb.ejb.mock.light.PaymentSystemPayment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.light.LightESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadRequestType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.HeadResponseType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.SimplePaymentRequest;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.SimplePaymentResponse;
import com.rssl.phizicgate.manager.config.AdaptersConfig;

/**
 * @author akrenev
 * @ created 18.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушка, имитирующая работу шины при запросе типовой оплаты
 */

public class ESBMockPaymentSystemPaymentProcessor extends LightESBMockProcessorBase<SimplePaymentRequest, SimplePaymentResponse>
{
	private static final String FROM_ABONENT = "SVFE";
	private static final String TARGET_SYSTEM = null;

	/**
	 * конструктор
	 * @param module - модуль
	 */
	public ESBMockPaymentSystemPaymentProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected boolean needSendResult(ESBMessage<SimplePaymentRequest> xmlRequest, SimplePaymentResponse message)
	{
		return getRandomBoolean();
	}

	@Override
	protected boolean needSendOnline(ESBMessage<SimplePaymentRequest> xmlRequest, SimplePaymentResponse message)
	{
		return getRandomBoolean();
	}

	private HeadResponseType getHeader(HeadRequestType requestHead)
	{
		return getHeader(requestHead, FROM_ABONENT, TARGET_SYSTEM, getOkStatus());
	}

	@Override
	protected void process(ESBMessage<SimplePaymentRequest> xmlRequest)
	{
		SimplePaymentRequest request = xmlRequest.getObject();

		SimplePaymentResponse response = new SimplePaymentResponse();

		HeadResponseType head = getHeader(request.getHead());
		response.setHead(head);
		response.setBody(getBody(request, head.getError()));

		send(xmlRequest, response, "CardPaymentSystemPayment");
	}

	private SimplePaymentResponse.Body getBody(SimplePaymentRequest request, HeadResponseType.Error status)
	{
		if (!isOkStatus(status))
			return null;

		SimplePaymentResponse.Body body = new SimplePaymentResponse.Body();
		body.setAuthorizeCode(getAuthCode());

		String providerId = request.getBody().getRoute().getDigCode();
		ServiceProviderService service = new ServiceProviderService();

		try
		{
			ServiceProviderBase provider = service.findBySynchKey(providerId + "@" + providerId + "|" + ConfigFactory.getConfig(AdaptersConfig.class).getCardTransfersAdapter().getUUID());

			body.setOperationIdentifier(String.valueOf(provider.getSynchKey()));
			body.setRecAcc(provider.getAccount());
			body.setRecBic(provider.getBIC());
			body.setRecCorrAcc(provider.getCorrAccount());
			body.setRecInn(provider.getINN());
			body.setRecCompName(provider.getBankName());
		}
		catch (BusinessException e)
		{
			log.error("[LIGHT_ESB-MQ MOCK] Не смогли получить поставщика для типовой оплаты по коду " + providerId, e);
		}
		return body;
	}
}
