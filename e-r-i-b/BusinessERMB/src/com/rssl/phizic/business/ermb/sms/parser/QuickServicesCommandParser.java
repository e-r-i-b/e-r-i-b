package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * ѕарсер команды "быстрые сервисы" (отключение быстрых сервисов)
 * @author Rtischeva
 * @ created 15.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class QuickServicesCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// откл_быстрый_платеж = ФЌќЋ№Ф | ФЌ”Ћ№Ф | ФNULLФ

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordQickServices() && parseEOF())
		{
			// SUCCESS. ”казано ключевое слово
			completeLexeme(tx);
			return commandFactory.createQuickServicesCommand();
		}

		// FAIL-A. ¬стречена друга€ —ћ—-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordQickServices()
	{
		return parseCommandKeyword(getKeywords());
	}
}
