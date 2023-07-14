package com.rssl.phizic.gate.documents.attribute;

/**
 * @author khudyakov
 * @ created 14.04.14
 * @ $Author$
 * @ $Revision$
 */
public interface ExtendedAttribute
{
	/**
	 * @return имя атрибута
	 */
	String getName();

	/**
	 * @return тип атрибута
	 */
	Type getType();

	/**
	 * @return значение атрибута
	 */
	Object getValue();

	/**
	 * Установить значение атрибута
	 * @param value значение
	 */
	void setValue(Object value);

	/**
	 * @return строковое значение
	 */
	String getStringValue();

	/**
	 * @return true - значение поля изменилось
	 */
	boolean isChanged();

	/**
	 * Установить флаг смены значения поля
	 * @param changed true - значение поля изменилось
	 */
	void setChanged(boolean changed);
}
