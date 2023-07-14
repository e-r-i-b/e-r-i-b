package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * ������ ��� ���-�������: �����������/�������� ������� � ��������� ���������� �������� �� ���������
 * @author Puzikov
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 */

public class SberThanksCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ������� = �������_�������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordThanks() && parseEOF())
		{
			// SUCCESS. ��������� �������� �����
			completeLexeme(tx);
			return commandFactory.createLoyaltyCommand();
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordThanks()
	{
		return parseCommandKeyword(getKeywords());
	}
}