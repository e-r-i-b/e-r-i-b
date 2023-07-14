package com.rssl.phizgate.common.credit.bki;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.gate.bki.BKIRequestType;

import java.io.Serializable;

/**
 * Сообщение CreditProxyListener -> блок
 * @author Puzikov
 * @ created 10.10.14
 * @ $Author$
 * @ $Revision$
 */
@Immutable
public class BKIResponse implements Serializable
{
	private final String responseXml;
	private final BKIRequestType requestType;
	private final long personId;
	private final Long paymentId;

	/**
	 * @param responseXml ответ из БКИ
	 * @param requestType тип запроса в БКИ
	 * @param personId клиент, по которому делался запрос
	 * @param paymentId платеж, по которому оплачен запрос
	 */
	public BKIResponse(String responseXml, BKIRequestType requestType, long personId, Long paymentId)
	{
		this.responseXml = responseXml;
		this.requestType = requestType;
		this.personId = personId;
		this.paymentId = paymentId;
	}

	/**
	 * @return ответ из БКИ
	 */
	public String getResponseXml()
	{
		return responseXml;
	}

	/**
	 * @return тип запроса в БКИ
	 */
	public BKIRequestType getRequestType()
	{
		return requestType;
	}

	/**
	 * @return клиент, по которому делался запрос
	 */
	public long getPersonId()
	{
		return personId;
	}

	/**
	 * @return платеж, по которому оплачен запрос (null - CHECK запрос)
	 */
	public Long getPaymentId()
	{
		return paymentId;
	}
}
