package com.rssl.phizic.business.documents.metadata.checkers;

import com.rssl.phizic.business.documents.payments.BusinessDocument;

import java.io.Serializable;

/**
 * @author Krenev
 * @ created 15.08.2007
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentChecker extends Serializable
{
	/**
	 * Установить значение параметра
	 * @param name имя
	 * @param value значение
	 */
	void setParameter(String name, String value);

	/**
	 * Получить значение
	 * @param name имя параметра
	 * @return значение
	 */
	String getParameter(String name);

	/**
	 * Проверить документ
	 * @param doc документ
	 * @return результат проверки
	 */
	boolean check(BusinessDocument doc);
}
