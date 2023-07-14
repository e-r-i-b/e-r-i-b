package com.rssl.phizic.gate.payments.systems.recipients;

import java.util.List;

/**
 * Поле c фиксированным множеством допустимых значений.
 * getType реализации должен возвращать list либо set.
 * @author Gainanov
 * @ created 25.07.2008
 * @ $Author$
 * @ $Revision$
 * TODO rename
 */
public interface ListField extends Field
{
	/**
	 * Значение, которые должны быть представлены в списке.
	 *
	 * @return список значений для выбора.
	 */
	List<ListValue> getValues();
}
