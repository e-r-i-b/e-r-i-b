package com.rssl.phizic.business.loanclaim.questionnaire;

/**
 * @author Gulov
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Статус ответа на вопрос
 */
public enum Status
{
	/**
	 * Опубликован
	 */
	PUBLISHED(0),

	/**
	 * Не опубликован
	 */
	UNPUBLISHED(1);

	private final int value;

	private Status(int value)
	{
		this.value = value;
	}

	public int toValue()
	{
		return value;
	}

	public static Status fromValue(int value)
	{
		switch (value)
		{
			case 0:
				return PUBLISHED;
			case 1:
				return UNPUBLISHED;
			default:
				throw new IllegalArgumentException("Неизвестный тип статуса вопроса [" + value + "]");
		}
	}

	@Override
	public String toString()
	{
		return value == 0 ? "Опубликован" : "Не опубликован";
	}
}
