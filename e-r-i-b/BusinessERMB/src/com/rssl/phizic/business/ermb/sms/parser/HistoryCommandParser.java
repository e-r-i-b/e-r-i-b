package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * Парсер для СМС-команды ИСТОРИЯ
 */
public class HistoryCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// История = команда_история
		//         | команда_история разделитель продукт

		Command command = parseHistoryCommandV1();

		if (command == null)
			command = parseHistoryCommandV2();

		return command;
	}

	private Command parseHistoryCommandV1()
	{
		// История = команда_история

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordHistory() && parseEOF())
		{
			// SUCCESS. Указано ключевое слово
			completeLexeme(tx);
			return commandFactory.createHistoryCommand(null);
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseHistoryCommandV2()
	{
		// История = команда_история разделитель продукт

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordHistory() && parseDelim())
		{
			String product = parseProduct();
			if (product != null)
			{
				if (parseEOF())
				{
					// SUCCESS. Указано ключевое слово и продукт
					completeLexeme(tx);
					return commandFactory.createHistoryCommand(product);
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

	private boolean parseKeywordHistory()
	{
		return parseCommandKeyword(getKeywords());
	}
}
