package com.rssl.phizic.gate.dictionaries.officies;

import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.Map;

/**
 * Код подразделения банка
 * @author Kidyaev
 * @ created 01.11.2006
 * @ $Author$
 * @ $Revision$
 */
public interface Code extends Serializable
{
	/**
	 * Сравнивает этот код офиса с переданным в параметре.
	 * Реализация должна сравнивать все поля, описанные в потомках.
	 * @param o объект для сравнения
	 * @return <code>true</code> если коды эквивалентны,
     *         <code>false</code> иначе.
	 */
	boolean equals(Object o);

	/**
	 * Возвращает хэш-код для этого кода
	 * @return хэш-код для этого кода
	 */
	int hashCode();

	/**
	 * Возвращает все поля кода офиса Map<название, значение>
	 * @return список полей и их значения
	 */
	Map<String, String> getFields();

	/**
	 * Возвращает уник. идентификатор кода
	 * @return id кода
	 */

	String getId();

	/**
	 * Преобразует код к xml виду
	 * &lt;parentTag&gt;
	 *     &lt;названиеПоля&gt;значениеПоля&lt;/названиеПоля&gt;
	 * &lt;/parentTag&gt;
	 * @param parentTag - родительский тег
	 */
	void toXml(Element parentTag);
}
