package com.rssl.phizic.utils.xml;

import org.xml.sax.helpers.AttributesImpl;

/**
 * @author Erkin
 * @ created 18.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Расширение списка атрибутов DOM-элемента
 * Включает:
 * - возможность формирования списка "на ходу" - через серию вызовов add.
 * Например:
 *      writer.startElement("tagName", new XMLElementAttributes()
 *	            .add("attribute1", "value1")
 *              .add("attribute2", "value2")

 */
public class XMLElementAttributes extends AttributesImpl
{
	/**
	 * Конструктор для пустого списка атрибутов
	 */
	public XMLElementAttributes() {}

	/**
	 * Конструктор для списка из одного атрибута
	 * @param name - имя атрибута
	 * @param value - значение атрибута
	 */
	public XMLElementAttributes(String name, String value)
	{
		add(name, value);
	}

	/**
	 * Добавить атрибут
	 * @param name - имя атрибута
	 * @param value - значение атрибута
	 * @return this
	 */
	public XMLElementAttributes add(String name, String value)
	{
		addAttribute("", "", name, "CDATA", value);
		return this;
	}
}
