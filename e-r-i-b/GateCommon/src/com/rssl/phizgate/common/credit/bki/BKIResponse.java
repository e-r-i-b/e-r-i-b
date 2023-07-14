package com.rssl.phizgate.common.credit.bki;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.gate.bki.BKIRequestType;

import java.io.Serializable;

/**
 * ��������� CreditProxyListener -> ����
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
	 * @param responseXml ����� �� ���
	 * @param requestType ��� ������� � ���
	 * @param personId ������, �� �������� ������� ������
	 * @param paymentId ������, �� �������� ������� ������
	 */
	public BKIResponse(String responseXml, BKIRequestType requestType, long personId, Long paymentId)
	{
		this.responseXml = responseXml;
		this.requestType = requestType;
		this.personId = personId;
		this.paymentId = paymentId;
	}

	/**
	 * @return ����� �� ���
	 */
	public String getResponseXml()
	{
		return responseXml;
	}

	/**
	 * @return ��� ������� � ���
	 */
	public BKIRequestType getRequestType()
	{
		return requestType;
	}

	/**
	 * @return ������, �� �������� ������� ������
	 */
	public long getPersonId()
	{
		return personId;
	}

	/**
	 * @return ������, �� �������� ������� ������ (null - CHECK ������)
	 */
	public Long getPaymentId()
	{
		return paymentId;
	}
}
