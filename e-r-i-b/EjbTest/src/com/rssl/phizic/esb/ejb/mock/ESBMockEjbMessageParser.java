package com.rssl.phizic.esb.ejb.mock;

import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Rtischeva
 * @ created 13.12.14
 * @ $Author$
 * @ $Revision$
 *
 * парсер сообщений в шину
 */
public class ESBMockEjbMessageParser implements ESBMockMessageParser<ESBMessage>
{
	private static final Log log = LogFactory.getLog(LogModule.Web.toString());
	private final ESBSegment segment;

	/**
	 * конструктор
	 * @param segment сегмент
	 */
	public ESBMockEjbMessageParser(ESBSegment segment)
	{
		this.segment = segment;
	}

	public ESBMessage parseMessage(TextMessage textMessage, javax.jms.Message source)
	{
		try
		{
			log.trace("Получено текстовое JMS-сообщение: " + textMessage);
			String text = textMessage.getText();
			return buildXmlMessage(segment.getMessageParser().parseMessage((javax.jms.TextMessage) source), text, source);
		}
		catch (Exception e)
		{
			log.error("Ошибка разбора сообщения.", e);
			return null;
		}
	}

	private ESBMessage buildXmlMessage(Object data, String text, javax.jms.Message source) throws Exception
	{
		//noinspection unchecked
		return new ESBMessage(text, data, source);
	}
}
