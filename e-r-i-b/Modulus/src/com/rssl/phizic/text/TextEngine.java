package com.rssl.phizic.text;

import com.rssl.phizic.engine.Engine;

/**
 * @author Erkin
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Движок тестовок
 */
public interface TextEngine extends Engine
{
	/**
	 * Создаёт персонального композитора сообщений
	 * @return новый композитор сообщений
	 */
	MessageComposer createMessageComposer();
}
