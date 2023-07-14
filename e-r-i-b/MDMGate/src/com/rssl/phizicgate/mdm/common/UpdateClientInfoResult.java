package com.rssl.phizicgate.mdm.common;

/**
 * @author akrenev
 * @ created 06.08.2015
 * @ $Author$
 * @ $Revision$
 *
 * Результат обработки оповещения
 */

public class UpdateClientInfoResult
{
	private static final UpdateClientInfoResult OK_RESULT = new UpdateClientInfoResult(0, null);

	private final long code;
	private final String description;

	/**
	 * конструткор
	 * @param code        код
	 * @param description описание
	 */
	private UpdateClientInfoResult(long code, String description)
	{
		this.code = code;
		this.description = description;
	}

	/**
	 * @return результат удачного обновления
	 */
	public static UpdateClientInfoResult getOkResult()
	{
		return OK_RESULT;
	}

	/**
	 * @return код
	 */
	public long getCode()
	{
		return code;
	}

	/**
	 * @return описание
	 */
	public String getDescription()
	{
		return description;
	}
}
