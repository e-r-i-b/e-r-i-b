package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * Парсер для смс-команды: регистрация/проверка баланса в программе лояльности «Спасибо от Сбербанка»
 * @author Puzikov
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 */

public class SberThanksCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// спасибо = команда_спасибо

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordThanks() && parseEOF())
		{
			// SUCCESS. Прочитано ключевое слово
			completeLexeme(tx);
			return commandFactory.createLoyaltyCommand();
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordThanks()
	{
		return parseCommandKeyword(getKeywords());
	}
}