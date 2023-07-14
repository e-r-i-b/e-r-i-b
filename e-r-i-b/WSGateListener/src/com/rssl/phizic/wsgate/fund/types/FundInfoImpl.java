package com.rssl.phizic.wsgate.fund.types;

import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.fund.FundInfo;
import com.rssl.phizic.gate.fund.Request;

/**
 * @author osminin
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */
public class FundInfoImpl implements FundInfo
{
	private Request request;
	private GUID initiatorGuid;
	private GUID senderGuid;
	private String externalResponseId;
	private String initiatorPhones;

	public Request getRequest()
	{
		return request;
	}

	public void setRequest(Request request)
	{
		this.request = request;
	}

	public GUID getInitiatorGuid()
	{
		return initiatorGuid;
	}

	public void setInitiatorGuid(GUID initiatorGuid)
	{
		this.initiatorGuid = initiatorGuid;
	}

	public GUID getSenderGuid()
	{
		return senderGuid;
	}

	public void setSenderGuid(GUID senderGuid)
	{
		this.senderGuid = senderGuid;
	}

	public String getExternalResponseId()
	{
		return externalResponseId;
	}

	public void setExternalResponseId(String externalResponseId)
	{
		this.externalResponseId = externalResponseId;
	}

	public String getInitiatorPhones()
	{
		return initiatorPhones;
	}

	public void setInitiatorPhones(String initiatorPhones)
	{
		this.initiatorPhones = initiatorPhones;
	}
}
