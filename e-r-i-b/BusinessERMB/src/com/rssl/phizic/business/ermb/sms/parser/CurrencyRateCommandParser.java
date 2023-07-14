package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * ������ ���-������� "����" 
 * @author Rtischeva
 * @ created 29.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ���� = �������_����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCurrencyRate() && parseEOF())
		{
			// SUCCESS. ������� �������� �����
			completeLexeme(tx);
			return commandFactory.createCurrencyRateCommand();
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordCurrencyRate()
	{
		return parseCommandKeyword(getKeywords());
	}
}
