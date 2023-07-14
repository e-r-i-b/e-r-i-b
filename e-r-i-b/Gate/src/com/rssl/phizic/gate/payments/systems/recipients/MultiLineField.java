package com.rssl.phizic.gate.payments.systems.recipients;

/**
 *  Поля для ввода в несколько строк.
 * @author Gainanov
 * @ created 25.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface MultiLineField extends Field
{
	/**
	 * Получить кол-во строк.
	 *
	 * @return кол-во строк
	 */
	int getLinesNumber();
}
