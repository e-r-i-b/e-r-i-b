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
 * Генератор внешнего ключа
 */

public final class ExternalIdGenerator
{
	/**
	 * сгенерировать внешний ключ документа
	 * @param cardBlockRq сообщение
	 * @return внешний ключ документа
	 */
	public static String generateExternalId(CardBlockRq cardBlockRq)
	{
		return generate(cardBlockRq.getRqUID(), cardBlockRq.getRqTm());
	}

	/**
	 * сгенерировать внешний ключ документа
	 * @param cardBlockRq сообщение
	 * @return внешний ключ документа
	 */
	public static String generateExternalId(CardBlockRs cardBlockRq)
	{
		return generate(cardBlockRq.getRqUID(), cardBlockRq.getRqTm());
	}

	/**
	 * сгенерировать внешний ключ документа
	 * @param svcAddRq сообщение
	 * @return внешний ключ документа
	 */
	public static String generateExternalId(SvcAddRq svcAddRq)
	{
		return generate(svcAddRq.getRqUID(), svcAddRq.getRqTm());
	}

	/**
	 * сгенерировать внешний ключ документа
	 * @param svcAddRs сообщение
	 * @return внешний ключ документа
	 */
	public static String generateExternalId(SvcAddRs svcAddRs)
	{
		return generate(svcAddRs.getRqUID(), svcAddRs.getRqTm());
	}

	/**
	 * сгенерировать внешний ключ документа
	 * @param request сообщение
	 * @return внешний ключ документа
	 */
	public static String generateExternalId(SvcAcctDelRq request)
	{
		return generate(request.getRqUID(), request.getRqTm());
	}

	/**
	 * сгенерировать внешний ключ документа
	 * @param response сообщение
	 * @return внешний ключ документа
	 */
	public static String generateExternalId(SvcAcctDelRs response)
	{
		return generate(response.getRqUID(), response.getRqTm());
	}

	/**
	 * сгенерировать внешний ключ документа
	 * @param setAccountStateRq сообщение
	 * @return внешний ключ документа
	 */
	public static String generateExternalId(SetAccountStateRq setAccountStateRq)
	{
		return generate(setAccountStateRq.getRqUID(), setAccountStateRq.getRqTm());
	}

	/**
	 * сгенерировать внешний ключ документа
	 * @param setAccountStateRs сообщение
	 * @return внешний ключ документа
	 */
	public static String generateExternalId(SetAccountStateRs setAccountStateRs)
	{
		return generate(setAccountStateRs.getRqUID(), setAccountStateRs.getRqTm());
	}

	/**
	 * сгенерировать внешний ключ документа
	 * @param xferAddRq сообщение
	 * @return внешний ключ документа
	 */
	public static String generateExternalId(XferAddRq xferAddRq)
	{
		return generate(xferAddRq.getRqUID(), xferAddRq.getRqTm());
	}

	/**
	 * сгенерировать внешний ключ документа
	 * @param xferAddRs сообщение
	 * @return внешний ключ документа
	 */
	public static String generateExternalId(XferAddRs xferAddRs)
	{
		return generate(xferAddRs.getRqUID(), xferAddRs.getRqTm());
	}

	/**
	 * сгенерировать внешний ключ
	 * @param response сообщение
	 * @return ключ
	 */
	public static String generateExternalId(AcceptBillBasketExecuteRs response)
	{
		return response.getRqUID();
	}

	/**
	 * сгенерировать внешний ключ
	 * @param response сообщение
	 * @return ключ
	 */
	public static String generateExternalId(RequestPrivateEarlyRepaymentRs response)
	{
		return response.getRqUID();
	}

	/**
	 * сгенерировать внешний ключ
	 * @param parts части ключа
	 * @return ключ
	 */
	private static String generate(String... parts)
	{
		return StringUtils.join(parts, "^") + "|esb-mq-" + ESBSegment.federal.name();
	}
}
