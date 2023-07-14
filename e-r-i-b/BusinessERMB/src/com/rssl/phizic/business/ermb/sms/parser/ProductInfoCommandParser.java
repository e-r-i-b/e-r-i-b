package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * Парсер смс-команды "Информация" 
 * @author Rtischeva
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class ProductInfoCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// информация = команда_информация
		//            | команда_информация разделитель продукт

		Command command = parseProductInfoCommandV1();

		if (command == null)
			command = parseProductInfoCommandV2();

		return command;
	}

	private Command parseProductInfoCommandV1()
	{
		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordProductInfo() && parseEOF())
		{
			// SUCCESS. Указано ключевое слово
			completeLexeme(tx);
			return commandFactory.createProductInfoCommand(null);
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseProductInfoCommandV2()
	{
		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordProductInfo() && parseDelim())
		{
			String product = parseProduct();
			if (product != null && parseEOF())
			{
				// SUCCESS. Указано ключевое слово и продукт
				completeLexeme(tx);
				return commandFactory.createProductInfoCommand(product);
			}

			// FAIL-B. Не указан или некорректно указан продукт либо за продуктом идёт что-то ещё
			addError(messageBuilder.buildUnknownCommandMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordProductInfo()
	{
		return parseCommandKeyword(getKeywords());
	}
}
