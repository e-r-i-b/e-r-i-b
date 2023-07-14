package com.rssl.phizic.utils.counter;

import java.util.Set;

/**
 * Стратегия именования
 * @author Puzikov
 * @ created 19.05.15
 * @ $Author$
 * @ $Revision$
 */

public interface NamingStrategy
{
	/**
	 * Преобразовать имя в валидное в контексте именования
	 * @param name новое имя
	 * @return вадидное имя
	 */
	String transform(String name);

	/**
	 * Получить стандартизованное имя на основе остальных имен набора
	 * @param existingNames старые имена
	 * @param standardName новое имя
	 * @return новое стандартизованное имя
	 */
	String unify(Set<String> existingNames, String standardName);
}
