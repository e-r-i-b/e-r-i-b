package com.rssl.phizic.logging.operations;

import java.util.LinkedHashMap;

/**
 * @author Evgrafov
 * @ created 15.03.2006
 * @ $Author: lukina $
 * @ $Revision: 25612 $
 */

public interface LogDataReader
{
	/**
	 * Результат выполнения операции
	 */
	enum ResultType
	{
		SUCCESS,
		SYSTEM_ERROR,
		CLIENT_ERROR
	}

	/**
	 * "Путь(запрос)", по которыму инициирована операция
	 * Например для веба - URL, для SMS-банкинга - смс команда. 
	 * @return
	 */
	String getOperationPath();

	/**
	 * @deprecated убрать при исправлении CHG016701 	Дата последнего входа
	 * @return
	 */
	@Deprecated String getOperationKey();

	/**
	 * @return описание логируемых данных
	 */
	String getDescription();

	/**
	 * @return ключ логируемых данных
	 */
	String getKey();

	/**
	 *
	 * @return прочитать парамтеры
	 * @throws Exception
	 */
	LinkedHashMap readParameters() throws Exception;

	/**
	 * @return тип результата операции
	 */
	ResultType getResultType();
}
