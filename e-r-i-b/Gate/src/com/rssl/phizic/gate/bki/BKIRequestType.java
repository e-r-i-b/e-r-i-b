package com.rssl.phizic.gate.bki;

/**
 * @author Erkin
 * @ created 24.11.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тип запроса в БКИ
 */
public enum BKIRequestType
{
	/**
	 * Сокращенный запрос: проверка наличия КИ клиента в ОКБ
	 */
	BKICheckCreditHistory("51"),

	/**
	 * Полный запрос: выгрузка отчёта КИ из ОКБ
	 */
	BKIGetCreditHistory("52"),

	;

	/**
	 * Код в БКИ
	 */
	public final String code;

	private BKIRequestType(String code)
	{
		this.code = code;
	}

	/**
	 * Получить тип по коду
	 * @param code - код
	 * @return тип (never null)
	 * @throws IllegalArgumentException если указан неизвестный код
	 */
	public static BKIRequestType fromCode(String code)
	{
		for (BKIRequestType type : values())
		{
			if (type.code.equals(code))
				return type;
		}

		throw new IllegalArgumentException("Неожиданный тип запроса в БКИ: " + code);
	}
}
