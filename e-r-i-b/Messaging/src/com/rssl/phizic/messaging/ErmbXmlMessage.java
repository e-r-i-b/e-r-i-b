package com.rssl.phizic.messaging;

/**
 * @author Erkin
 * @ created 24.03.2014
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.gate.mobilebank.P2PRequest;
import com.rssl.phizic.synchronization.types.*;

public class ErmbXmlMessage extends XmlMessage
{
	private SMSRq smsRq;

	private UpdateResourceRq updateResourceRq;

	private CheckImsiResponse checkImsiResponse;

	private UpdateClientRq updateClientRq;

	private ConfirmProfilesRq confirmProfilesRq;

	private P2PRequest p2PRequest;

	private ServiceFeeResultRq serviceFeeResultRq;

	public ErmbXmlMessage(SMSRq smsRq, String message)
	{
		super(message, smsRq.getClass(), smsRq.getRqUID().toString(), smsRq.getRqTime());
		this.smsRq = smsRq;
	}

	public ErmbXmlMessage(UpdateResourceRq updateResourceRq, String message)
	{
		super(message, updateResourceRq.getClass(), updateResourceRq.getRqUID().toString(), updateResourceRq.getRqTime());
		this.updateResourceRq = updateResourceRq;
	}

	public ErmbXmlMessage(CheckImsiResponse checkImsiResponse, String message)
	{
		super(message, checkImsiResponse.getClass(), checkImsiResponse.getRqUID().toString(), checkImsiResponse.getRqTime());
		this.checkImsiResponse = checkImsiResponse;
	}

	public ErmbXmlMessage(UpdateClientRq updateClientRq, String message)
	{
		super(message, updateClientRq.getClass(), updateClientRq.getRqUID().toString(), updateClientRq.getRqTime());
		this.updateClientRq = updateClientRq;
	}

	public ErmbXmlMessage(ConfirmProfilesRq confirmProfilesRq, String message)
	{
		super(message, confirmProfilesRq.getClass(), confirmProfilesRq.getRqUID().toString(), confirmProfilesRq.getRqTime());
		this.confirmProfilesRq = confirmProfilesRq;
	}

	public ErmbXmlMessage(P2PRequest p2pRequest, String message)
	{
		super(message, p2pRequest.getClass(), String.valueOf(p2pRequest.id), null);
		this.p2PRequest = p2pRequest;
	}

	public ErmbXmlMessage(ServiceFeeResultRq serviceFeeResultRq, String message)
	{
		super(message, serviceFeeResultRq.getClass(), serviceFeeResultRq.getRqUID().toString(), serviceFeeResultRq.getRqTime());
		this.serviceFeeResultRq = serviceFeeResultRq;
	}

	public SMSRq getSmsRq()
	{
		return smsRq;
	}

	public UpdateResourceRq getUpdateResourceRq()
	{
		return updateResourceRq;
	}

	public CheckImsiResponse getCheckImsiResponse()
	{
		return checkImsiResponse;
	}

	public UpdateClientRq getUpdateClientRq()
	{
		return updateClientRq;
	}

	public ConfirmProfilesRq getConfirmProfilesRq()
	{
		return confirmProfilesRq;
	}

	public P2PRequest getP2PRequest()
	{
		return p2PRequest;
	}

	public ServiceFeeResultRq getServiceFeeResultRq()
	{
		return serviceFeeResultRq;
	}
}
