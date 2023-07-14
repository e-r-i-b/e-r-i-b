package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * @author Erkin
 * @ created 26.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ���-������� ������
 */
public class BalanceCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ������ = �������_������
		//        | �������_������ ����������� �������

		Command command = parseBalanceCommandV1();

		if (command == null)
			command = parseBalanceCommandV2();

		return command;
	}

	private Command parseBalanceCommandV1()
	{
		// ������ = �������_������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordBalance() && parseEOF())
		{
			// SUCCESS. ������� �������� �����
			completeLexeme(tx);
			return commandFactory.createBalanceCommand(null);
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseBalanceCommandV2()
	{
		// ������ = �������_������ ����������� �������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordBalance() && parseDelim())
		{
			String product = parseProduct();
			if (product != null)
			{
				if (parseEOF())
				{
					// SUCCESS. ������� �������� ����� � �������
					completeLexeme(tx);
					return commandFactory.createBalanceCommand(product);
				}

				// FAIL-C. �� ��������� ��� ���-�� ���
				addError(messageBuilder.buildCommandFormatError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. ����������� ������ �������
			addError(messageBuilder.buildCommandFormatError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordBalance()
	{
		return parseCommandKeyword(getKeywords());
	}
}
