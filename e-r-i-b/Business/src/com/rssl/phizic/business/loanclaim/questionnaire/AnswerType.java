package com.rssl.phizic.business.loanclaim.questionnaire;

/**
 * @author Gulov
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Тип ответа на вопрос
 */
public enum AnswerType
{
	/**
	 * Да/Нет
	 */
	YES_NO(0),

	/**
	 * Поле ввода
	 */
	INPUT_FIELD(1);

	private final int value;

	private AnswerType(int value)
	{
		this.value = value;
	}

	public int toValue()
	{
		return value;
	}

	public static AnswerType fromValue(int value)
	{
		switch (value)
		{
			case 0:
				return YES_NO;
			case 1:
				return INPUT_FIELD;
			default:
				throw new IllegalArgumentException("Неизвестный тип ответа [" + value + "]");
		}
	}
}
