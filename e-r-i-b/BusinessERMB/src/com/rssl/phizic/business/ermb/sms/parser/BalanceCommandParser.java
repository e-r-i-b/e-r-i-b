package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * @author Erkin
 * @ created 26.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Парсер для СМС-команды БАЛАНС
 */
public class BalanceCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// Баланс = команда_баланс
		//        | команда_баланс разделитель продукт

		Command command = parseBalanceCommandV1();

		if (command == null)
			command = parseBalanceCommandV2();

		return command;
	}

	private Command parseBalanceCommandV1()
	{
		// Баланс = команда_баланс

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordBalance() && parseEOF())
		{
			// SUCCESS. Указано ключевое слово
			completeLexeme(tx);
			return commandFactory.createBalanceCommand(null);
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseBalanceCommandV2()
	{
		// Баланс = команда_баланс разделитель продукт

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordBalance() && parseDelim())
		{
			String product = parseProduct();
			if (product != null)
			{
				if (parseEOF())
				{
					// SUCCESS. Указано ключевое слово и продукт
					completeLexeme(tx);
					return commandFactory.createBalanceCommand(product);
				}

				// FAIL-C. За продуктом идёт что-то ещё
				addError(messageBuilder.buildCommandFormatError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Некорректно указан продукт
			addError(messageBuilder.buildCommandFormatError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordBalance()
	{
		return parseCommandKeyword(getKeywords());
	}
}
