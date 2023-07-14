package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * Парсер смс-команды "Курс" 
 * @author Rtischeva
 * @ created 29.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// курс = команда_курс

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCurrencyRate() && parseEOF())
		{
			// SUCCESS. Указано ключевое слово
			completeLexeme(tx);
			return commandFactory.createCurrencyRateCommand();
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordCurrencyRate()
	{
		return parseCommandKeyword(getKeywords());
	}
}
