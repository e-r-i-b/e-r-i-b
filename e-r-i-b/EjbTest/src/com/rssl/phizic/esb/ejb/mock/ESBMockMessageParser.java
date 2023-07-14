package com.rssl.phizic.esb.ejb.mock;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 27.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * Заглушечный парсер сообщенией в шину
 */

public interface ESBMockMessageParser<M>
{
	/**
	 * распарсить сообщение
	 * @param textMessage текст сообщения
	 * @param source исходное сообщение
	 * @return распарсенное сообщение
	 */
	M parseMessage(com.rssl.phizic.common.types.TextMessage textMessage, Message source);
}
