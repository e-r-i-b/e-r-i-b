package com.rssl.phizic.documents;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.common.types.Money;

/**
 * Интерфейс перевода
 *
 * @author khudyakov
 * @ created 17.11.14
 * @ $Author$
 * @ $Revision$
 */
public interface AbstractPaymentTransfer extends StateObject
{
	/**
	 * Возвращает фио клиента из PersonContext-а в формате "Имя Отчество Ф."
	 * Если контекст пуст, то возвращается пустая строка
	 * @return фио клиента в формате "Имя Отчество Ф."
	 */
	String getFormattedPersonName() throws DocumentException;

	/**
	 * @return сумма платежа
	 */
	Money getExactAmount();
}
