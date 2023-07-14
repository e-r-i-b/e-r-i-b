package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * ������ ������� "������� �������" (���������� ������� ��������)
 * @author Rtischeva
 * @ created 15.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class QuickServicesCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ����_�������_������ = ����ܔ | ����ܔ | �NULL�

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordQickServices() && parseEOF())
		{
			// SUCCESS. ������� �������� �����
			completeLexeme(tx);
			return commandFactory.createQuickServicesCommand();
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordQickServices()
	{
		return parseCommandKeyword(getKeywords());
	}
}
