package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal;

import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author akrenev
 * @ created 27.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �������� �����
 */

public final class ExternalIdGenerator
{
	/**
	 * ������������� ������� ���� ���������
	 * @param cardBlockRq ���������
	 * @return ������� ���� ���������
	 */
	public static String generateExternalId(CardBlockRq cardBlockRq)
	{
		return generate(cardBlockRq.getRqUID(), cardBlockRq.getRqTm());
	}

	/**
	 * ������������� ������� ���� ���������
	 * @param cardBlockRq ���������
	 * @return ������� ���� ���������
	 */
	public static String generateExternalId(CardBlockRs cardBlockRq)
	{
		return generate(cardBlockRq.getRqUID(), cardBlockRq.getRqTm());
	}

	/**
	 * ������������� ������� ���� ���������
	 * @param svcAddRq ���������
	 * @return ������� ���� ���������
	 */
	public static String generateExternalId(SvcAddRq svcAddRq)
	{
		return generate(svcAddRq.getRqUID(), svcAddRq.getRqTm());
	}

	/**
	 * ������������� ������� ���� ���������
	 * @param svcAddRs ���������
	 * @return ������� ���� ���������
	 */
	public static String generateExternalId(SvcAddRs svcAddRs)
	{
		return generate(svcAddRs.getRqUID(), svcAddRs.getRqTm());
	}

	/**
	 * ������������� ������� ���� ���������
	 * @param request ���������
	 * @return ������� ���� ���������
	 */
	public static String generateExternalId(SvcAcctDelRq request)
	{
		return generate(request.getRqUID(), request.getRqTm());
	}

	/**
	 * ������������� ������� ���� ���������
	 * @param response ���������
	 * @return ������� ���� ���������
	 */
	public static String generateExternalId(SvcAcctDelRs response)
	{
		return generate(response.getRqUID(), response.getRqTm());
	}

	/**
	 * ������������� ������� ���� ���������
	 * @param setAccountStateRq ���������
	 * @return ������� ���� ���������
	 */
	public static String generateExternalId(SetAccountStateRq setAccountStateRq)
	{
		return generate(setAccountStateRq.getRqUID(), setAccountStateRq.getRqTm());
	}

	/**
	 * ������������� ������� ���� ���������
	 * @param setAccountStateRs ���������
	 * @return ������� ���� ���������
	 */
	public static String generateExternalId(SetAccountStateRs setAccountStateRs)
	{
		return generate(setAccountStateRs.getRqUID(), setAccountStateRs.getRqTm());
	}

	/**
	 * ������������� ������� ���� ���������
	 * @param xferAddRq ���������
	 * @return ������� ���� ���������
	 */
	public static String generateExternalId(XferAddRq xferAddRq)
	{
		return generate(xferAddRq.getRqUID(), xferAddRq.getRqTm());
	}

	/**
	 * ������������� ������� ���� ���������
	 * @param xferAddRs ���������
	 * @return ������� ���� ���������
	 */
	public static String generateExternalId(XferAddRs xferAddRs)
	{
		return generate(xferAddRs.getRqUID(), xferAddRs.getRqTm());
	}

	/**
	 * ������������� ������� ����
	 * @param response ���������
	 * @return ����
	 */
	public static String generateExternalId(AcceptBillBasketExecuteRs response)
	{
		return response.getRqUID();
	}

	/**
	 * ������������� ������� ����
	 * @param response ���������
	 * @return ����
	 */
	public static String generateExternalId(RequestPrivateEarlyRepaymentRs response)
	{
		return response.getRqUID();
	}

	/**
	 * ������������� ������� ����
	 * @param parts ����� �����
	 * @return ����
	 */
	private static String generate(String... parts)
	{
		return StringUtils.join(parts, "^") + "|esb-mq-" + ESBSegment.federal.name();
	}
}
