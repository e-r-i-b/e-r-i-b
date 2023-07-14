package com.rssl.phizicgate.iqwave.loyalty;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.loyalty.LoyaltyOffer;
import com.rssl.phizic.gate.loyalty.LoyaltyProgram;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.gate.loyalty.LoyaltyProgramService;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizicgate.iqwave.messaging.Constants;
import org.w3c.dom.Document;

import java.util.List;

/**
 * @author gladishev
 * @ created 06.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramServiceImpl extends AbstractService implements LoyaltyProgramService
{
	private static final LoyaltyProgramResponseSerializer responseSerializer = new LoyaltyProgramResponseSerializer();

	public LoyaltyProgramServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public LoyaltyProgram getClientLoyaltyProgram(String externalId) throws GateException, GateLogicException
	{
		return getLoyaltyProgramByExternalId(externalId);
	}

	public List<LoyaltyProgramOperation> getLoyaltyOperationInfo(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.LOYALTY_GET_STATEMENT_REPORT_REQUEST);

		String externalId = loyaltyProgram.getExternalId();
		message.addParameter(Constants.PAN_TAG_NAME, externalId);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		return responseSerializer.fillLoyaltyProgramOperationList(response.getDocumentElement(), externalId);
	}

	public List<LoyaltyOffer> getLoyaltyOffers(LoyaltyProgram loyaltyProgram) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.LOYALTY_GET_OFFERS_REQUEST);

		String externalId = loyaltyProgram.getExternalId();
		message.addParameter(Constants.PAN_TAG_NAME, externalId);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		return responseSerializer.fillLoyaltyOfferList(response.getDocumentElement(), externalId);
	}

	private LoyaltyProgram getLoyaltyProgramByExternalId(String externalId) throws GateException, GateLogicException
	{
		WebBankServiceFacade serviceFacade = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		GateMessage message = serviceFacade.createRequest(Constants.LOYALTY_GET_BALANCE_REQUEST);

		message.addParameter(Constants.PAN_TAG_NAME, externalId);
		Document response = serviceFacade.sendOnlineMessage(message, null);

		return responseSerializer.fillLoyaltyProgram(response.getDocumentElement(), externalId);
	}
}
