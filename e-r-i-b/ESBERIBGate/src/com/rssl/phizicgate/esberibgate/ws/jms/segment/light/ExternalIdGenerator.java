package com.rssl.phizicgate.esberibgate.ws.jms.segment.light;

import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.light.generated.*;
import org.apache.commons.lang.StringUtils;

/**
 * @author akrenev
 * @ created 10.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Генератор внешнего ключа
 */

public final class ExternalIdGenerator
{
	/**
	 * сформировать внешний ключ
	 * @param request запрос
	 * @return внешний ключ
	 */
	public static String generateExternalId(CardToCardRequest request)
	{
		HeadRequestType.MessUID messUID = request.getHead().getMessUID();
		return generate(messUID.getMessageId(), messUID.getMessageDate());
	}

	/**
	 * сформировать внешний ключ
	 * @param response ответ
	 * @return внешний ключ
	 */
	public static String generateExternalId(CardToCardResponse response)
	{
		HeadResponseType.ParentId parentId = response.getHead().getParentId();
		return generate(parentId.getMessageId(), parentId.getMessageDate());
	}

	/**
	 * сформировать внешний ключ
	 * @param request запрос
	 * @return внешний ключ
	 */
	public static String generateExternalId(CardToCardInfoRequest request)
	{
		HeadRequestType.MessUID messUID = request.getHead().getMessUID();
		return generate(messUID.getMessageId(), messUID.getMessageDate());
	}

	/**
	 * сформировать внешний ключ
	 * @param request запрос
	 * @return внешний ключ
	 */
	public static String generateExternalId(SimplePaymentRequest request)
	{
		HeadRequestType.MessUID messUID = request.getHead().getMessUID();
		return generate(messUID.getMessageId(), messUID.getMessageDate());
	}

	/**
	 * сформировать внешний ключ
	 * @param response ответ
	 * @return внешний ключ
	 */
	public static String generateExternalId(SimplePaymentResponse response)
	{
		HeadResponseType.ParentId parentId = response.getHead().getParentId();
		return generate(parentId.getMessageId(), parentId.getMessageDate());
	}

	/**
	 * сгенерировать внешний ключ
	 * @param parts части ключа
	 * @return ключ
	 */
	private static String generate(String... parts)
	{
		return StringUtils.join(parts, "^") + "|esb-mq-" + ESBSegment.light.name();
	}
}
