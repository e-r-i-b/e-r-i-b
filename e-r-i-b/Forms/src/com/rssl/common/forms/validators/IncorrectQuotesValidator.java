package com.rssl.common.forms.validators;

/**
 * проверка на наличие не правильных кавычек (УФ) ANSI 147(93), 148(94)
 * @author basharin
 * @ created 23.04.2013
 * @ $Author$
 * @ $Revision$
 */

public class IncorrectQuotesValidator extends RegexpFieldValidator
{
	private static final String regexp = "[^УФ]*";
	private static final String message = "¬ пол€х документа использованы запрещенные символы: У Ф. ѕожалуйста, укажите кавычки вида: С Т";

	public IncorrectQuotesValidator()
	{
		super(regexp, message);
	}
}