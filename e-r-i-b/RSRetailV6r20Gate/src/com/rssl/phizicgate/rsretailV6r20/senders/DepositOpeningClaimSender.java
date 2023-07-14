package com.rssl.phizicgate.rsretailV6r20.senders;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.deposit.DepositOpeningClaim;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.common.types.Currency;
import org.w3c.dom.Element;

import java.math.BigDecimal;

public class DepositOpeningClaimSender extends AbstractDocumentSender
{
	public DepositOpeningClaimSender(GateFactory factory)
	{
		super(factory);
	}

	public GateMessage createGateMessage(GateDocument gateDocument) throws GateException, GateLogicException
	{
		if (!(gateDocument instanceof DepositOpeningClaim))
			throw new GateException("�������� ��� �������, ������ ���� - DepositOpeningClaim.");
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		return service.createRequest("openDeposit_q");
	}

	void fillGateMessage(GateMessage message, GateDocument gateDocument) throws GateLogicException, GateException
	{
		super.fillGateMessage(message, gateDocument);
		if (!(gateDocument instanceof DepositOpeningClaim))
			throw new GateException("�������� ��� �������, ������ ���� - DepositOpeningClaim.");

		DepositOpeningClaim claim = (DepositOpeningClaim) gateDocument;

		if (claim.getPeriod()!=null)
		{
			message.addParameter("chargeOffAccount",claim.getChargeOffAccount());
		                                   
			message.addParameter("type", "65");//TODO �������� �� ����������� BUG009845: �� �������� ������ �� �������� ������.

			BigDecimal value = claim.getChargeOffAmount().getDecimal();
			if( value.compareTo(new BigDecimal(0)) != 0 )
			{
				Element chargeOffAmount = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "chargeOffAmount");
				XmlHelper.appendSimpleElement(chargeOffAmount, "value", value.toString());
			}
		}

		String accountType = claim.getDepositConditionsId();
		if (claim.getPeriod() == null) //��������� ����
		{
			Currency currency = claim.getChargeOffAmount().getCurrency();
			accountType += currency.getCode();
			Object parameter = getParameter(accountType);
			if (parameter == null)
			{
				throw new GateException("����������� ��� ������������ �����: " + claim.getDepositConditionsId());
			}
			accountType = (String) parameter;
		}
		message.addParameter("depositConditionsId", accountType);

		String isoCode = claim.getChargeOffAmount().getCurrency().getCode();
		if ("RUB".equals(isoCode))
			isoCode = "RUR";
		message.addParameter("receiverCurrency", isoCode);//todo ��� ����� ������ ������������ ������????? ���� - �� �������� � V51
		message.addParameter("officeExternalId", claim.getOfficeExternalId());
		message.addParameter("clientCode", claim.getClientInfo().getExternalOwnerId());
		if (claim.getCommission() != null)
			message.addParameter("commission", claim.getCommission().getDecimal().toString());
		if(claim.getGround() != null)
		{
			message.addParameter("ground",claim.getGround());
		}
	}
}