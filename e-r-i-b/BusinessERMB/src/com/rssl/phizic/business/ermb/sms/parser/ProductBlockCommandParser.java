package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * @author Gulov
 * @ created 10.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class ProductBlockCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// Команда_блокировка = команда_блокировка разделитель продукт
		//                    | команда_блокировка разделитель продукт разделитель код_блокировки

		Command command = parseProductBlockCommandV1();

		if (command == null)
			command = parseProductBlockCommandV2();

		return command;
	}

	private Command parseProductBlockCommandV1()
	{
		// Команда_блокировка = команда_блокировка разделитель продукт

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordProductBlock() && parseDelim())
		{
			String product = parseProduct();
			if (product != null)
			{
				if (parseEOF())
				{
					// SUCCESS. Указано ключевое слово и продукт
					completeLexeme(tx);
					return commandFactory.createProductBlockCommand(product, null);
				}

				// FAIL-C. За продуктом идёт что-то ещё
				addError(messageBuilder.buildCommandFormatError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указана или некорректно указан продукт
			addError(messageBuilder.buildProductBlockBadProductError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseProductBlockCommandV2()
	{
		// Команда_блокировка = команда_блокировка разделитель продукт разделитель код_блокировки

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordProductBlock() && parseDelim())
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
						return commandFactory.createProductBlockCommand(product, blockCode);
					}

					// FAIL-D. За кодом блокировки идёт что-то ещё
					addError(messageBuilder.buildCommandFormatError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Не указан или некорректно указан код блокировки
				// (возвращаем ошибку "Некорректная длина параметра или запроса",
				// т.к. для карты и счёта текстовки ошибок по некорректному коду отличаются)
				addError(messageBuilder.buildCommandFormatError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указана или некорректно указан продукт
			addError(messageBuilder.buildProductBlockBadProductError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordProductBlock()
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
