package com.rssl.phizic.business.services.groups.destenaion;

import com.rssl.phizic.business.services.groups.ServicesGroupInformation;

/**
 * Интерфейс для получения групп сервисов
 * @author komarov
 * @ created 08.05.2015
 * @ $Author$
 * @ $Revision$
 */
public interface GroupDestenation<T>
{
	/**
	 * добавляет элемент
	 * @param information ServicesGroupInformation
	 */
	void add(ServicesGroupInformation information);

	/**
	 * Возвращает результат
	 * @return результат
	 */
	T getResult();
}
