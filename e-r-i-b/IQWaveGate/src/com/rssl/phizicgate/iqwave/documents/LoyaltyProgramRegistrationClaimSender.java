package com.rssl.phizicgate.iqwave.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.LoyaltyProgramRegistrationClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.iqwave.loyalty.LoyaltyProgramRequestHelper;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

/**
 * Сендер заявки на регистрацию в программе лояльности
 * @author gladishev
 * @ created 10.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramRegistrationClaimSender extends IQWaveAbstractDocumentSender
{
	/**
	 * ctor
	 * @param factory - гейтовая фабрика
	 */
	public LoyaltyProgramRegistrationClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public void send(GateDocument document) throws GateException, GateLogicException
	{
		if (document.getType() != LoyaltyProgramRegistrationClaim.class)
		{
			throw new GateException("Неверный тип платежа - ожидается LoyaltyProgramRegistrationClaim");
		}

		LoyaltyProgramRegistrationClaim claim = (LoyaltyProgramRegistrationClaim) document;
		if (StringHelper.isEmpty(claim.getCardNumber()))
		{
			throw new GateException("Не указаны данные карты для регистрации");
		}

		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.LOYALTY_REGISTER_REQUEST);

		message.addParameter(Constants.PAN_TAG_NAME, claim.getCardNumber());
		message.addParameter(Constants.LOY_TEL_TAG_NAME, claim.getPhoneNumber());
		message.addParameter(Constants.ACCEPT_TAG_NAME, '1');

		Document response = serviceFacade.sendOnlineMessage(message, null);
		if (StringHelper.isEmpty(LoyaltyProgramRequestHelper.validateMessage(response.getDocumentElement(), claim.getCardNumber())))
			throw new GateException("Операция временно недоступна. Повторите попытку позже.");

		claim.setExternalId(getExternalId(response));
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
	}

	protected String getConfirmRequestName(GateDocument document)
	{
		throw new UnsupportedOperationException();
	}
}
