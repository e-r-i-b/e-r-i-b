package com.rssl.phizic.auth.modes;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.auth.modes.ConfirmResponse;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 03.01.2007
 * @ $Author: emakarov $
 * @ $Revision: 9379 $
 */

public interface ConfirmResponseReader
{
	/**
	 * @param valuesSource источник полей для ответа
	 */
	void setValuesSource(FieldValuesSource valuesSource);

	/**
	 * @return true == удалось прочитать
	 */
	boolean read();

	/**
	 * @return ответ если read() == true
	 */
	ConfirmResponse getResponse();

	/**
	 * @return ошибки если read() == false
	 */
	List<String> getErrors();

}
