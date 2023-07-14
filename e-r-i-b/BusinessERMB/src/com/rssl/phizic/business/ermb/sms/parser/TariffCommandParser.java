package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * Парсер смс-команды "Тариф"
 * @author Rtischeva
 * @ created 09.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class TariffCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// Тариф = команда_тариф
		//       | команда_тариф разделитель название_тарифа

		Command command = parseShowTariffCommand();

		if (command == null)
			command = parseChangeTariffCommand();

		return command;
	}

	private Command parseShowTariffCommand()
	{
		// Тариф = команда_тариф

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTariff() && parseEOF())
		{
			// SUCCESS. Прочитано ключевое слово => возвращаем команду "показать тарифы"
			completeLexeme(tx);
			return commandFactory.createShowTariffCommand();
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseChangeTariffCommand()
	{
		// Тариф = команда_тариф разделитель название_тарифа

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTariff() && parseDelim())
		{
			String newTariff = parseTariff();
			if (newTariff != null && parseEOF())
			{
				// SUCCESS. Прочитано ключевое слово и тариф => возвращаем команду "сменить тариф"
				completeLexeme(tx);
				return commandFactory.createChangeTariffCommand(newTariff);
			}

			// FAIL-B. Не указан или некорректно указан тариф
			cancelLexeme(tx);
			addError(messageBuilder.buildIncorrectTariffNameMessage());
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordTariff()
	{
		return parseCommandKeyword(getKeywords());
	}
}
