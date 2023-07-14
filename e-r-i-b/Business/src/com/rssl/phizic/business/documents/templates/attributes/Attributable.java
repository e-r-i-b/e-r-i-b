package com.rssl.phizic.business.documents.templates.attributes;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;

/**
 * Интерфейс шаблона работы с дополнительными полями
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public interface Attributable extends StateObject
{
	/**
	 * Создать атрибут
	 * @param name имя
	 * @param type тип
	 * @param value значение
	 * @return атрибут
	 */
	ExtendedAttribute createAttribute(String name, Type type, Object value);

	/**
	 * Получить атрибут по имени
	 * @param name имя атрибута
	 * @return атрибут
	 */
	ExtendedAttribute getAttribute(String name);

	/**
	 * Добавить атрибут
	 * @param attribute атрибут
	 */
	void addAttribute(ExtendedAttribute attribute);

	/**
	 * Удалить атрибут
	 * @param name имя атрибута
	 */
	void removeAttribute(String name);
}
