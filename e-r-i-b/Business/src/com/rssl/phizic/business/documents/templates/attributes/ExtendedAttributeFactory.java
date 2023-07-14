package com.rssl.phizic.business.documents.templates.attributes;

import com.rssl.phizic.gate.documents.attribute.Type;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;

/**
 * Фабрика доп. атрибута
 *
 * @author khudyakov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 */
public interface ExtendedAttributeFactory
{
	/**
	 * Создать доп. атрибут
	 * @param name имя
	 * @param type тип
	 * @param value значение
	 * @return атрибут
	 */
	ExtendedAttribute create(String name, Type type, Object value);

	/**
	 * Создать доп. атрибут
	 * @param id идентификатор
	 * @param name имя
	 * @param type тип
	 * @param value значение
	 * @return атрибут
	 */
	ExtendedAttribute create(Long id, String name, Type type, Object value);
}
