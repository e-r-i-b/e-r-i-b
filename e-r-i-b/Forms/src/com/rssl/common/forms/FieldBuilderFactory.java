package com.rssl.common.forms;

/**
 * @author Erkin
 * @ created 19.03.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Фабрика field-builder-ов
 */
public interface FieldBuilderFactory
{
	/**
	 * Создаёт новый field-builder
	 * @return новый field-builder
	 */
	FieldBuilder createFieldBuilder();
}
