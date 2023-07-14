package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * @author Gulov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Парсер смс-команды ПЕРЕВЫПУСК
 */
public class CardIssueCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// перевыпуск = команда_перевыпуск разделитель продукт разделитель код_блокировки
		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCardIssue() && parseDelim())
		{
			String product = parseProduct();
			if (product != null && parseDelim())
			{
				Integer blockCode = parseBlockCode();
				if (blockCode != null)
				{
					if (parseEOF())
					{
						// SUCCESS. Указано ключевое слово, продукт и код блокировки
						completeLexeme(tx);
						return commandFactory.createCardIssueCommand(product, blockCode);
					}

					// FAIL-D. За кодом блокировки идёт что-то ещё
					addError(messageBuilder.buildCardIssueBadArgsError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Не указан или некорректно указан код блокировки
				// (возвращаем ошибку "Некорректная длина параметра или запроса",
				// т.к. для карты и счёта текстовки ошибок по некорректному коду отличаются)
				addError(messageBuilder.buildCardIssueBadArgsError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указана или некорректно указан продукт
			addError(messageBuilder.buildCommandFormatError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordCardIssue()
	{
		return parseCommandKeyword(getKeywords());
	}

	private Integer parseBlockCode()
	{
		String number = parseNumber(1, 1);
		if (number == null)
			return null;
		return Integer.parseInt(number);
	}
}
