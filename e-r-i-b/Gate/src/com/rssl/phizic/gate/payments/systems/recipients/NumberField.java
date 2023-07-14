package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * поле для ввода числовых значений
 * реализации должны возвращать тип number
 * @author krenev
 * @ created 15.11.2010
 * @ $Author$
 * @ $Revision$
 */
public interface NumberField extends Field
{
	/**
	 * точность числового значения
	 * (количество знаков после запятой)
	 * @return точность.
	 */
	Integer getNumberPrecision();
}
