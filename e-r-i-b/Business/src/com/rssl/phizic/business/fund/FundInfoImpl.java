package com.rssl.phizic.business.fund;

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

	/**
	 * ctor
	 * @param request запрос
	 * @param initiatorGuid ФИО ДУЛ ДР ТБ инициатора
	 * @param senderGuid ФИО ДУЛ ДР ТБ отправителя
	 * @param externalResponseId внешний идентификатор ответа в блоке инициатора
	 * @param initiatorPhones телефоны инициатора
	 */
	public FundInfoImpl(Request request, GUID initiatorGuid, GUID senderGuid, String externalResponseId, String initiatorPhones)
	{
		this.request = request;
		this.initiatorGuid = initiatorGuid;
		this.senderGuid = senderGuid;
		this.externalResponseId = externalResponseId;
		this.initiatorPhones = initiatorPhones;
	}

	public Request getRequest()
	{
		return request;
	}

	/**
	 * @param request запрос на сбор средств
	 */
	public void setRequest(Request request)
	{
		this.request = request;
	}

	public GUID getInitiatorGuid()
	{
		return initiatorGuid;
	}

	/**
	 * @param initiatorGuid ФИО ДУЛ ДР ТБ инициатора
	 */
	public void setInitiatorGuid(GUID initiatorGuid)
	{
		this.initiatorGuid = initiatorGuid;
	}

	public GUID getSenderGuid()
	{
		return senderGuid;
	}

	/**
	 * @param senderGuid ФИО ДУЛ ДР ТБ отправителя
	 */
	public void setSenderGuid(GUID senderGuid)
	{
		this.senderGuid = senderGuid;
	}

	public String getExternalResponseId()
	{
		return externalResponseId;
	}

	/**
	 * @param externalResponseId внешний идентификатор ответа в блоке инициатора
	 */
	public void setExternalResponseId(String externalResponseId)
	{
		this.externalResponseId = externalResponseId;
	}

	public String getInitiatorPhones()
	{
		return initiatorPhones;
	}

	/**
	 * @param initiatorPhones телефоны инициатора
	 */
	public void setInitiatorPhones(String initiatorPhones)
	{
		this.initiatorPhones = initiatorPhones;
	}
}
