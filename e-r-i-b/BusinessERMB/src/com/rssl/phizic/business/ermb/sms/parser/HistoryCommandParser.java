package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * ������ ��� ���-������� �������
 */
public class HistoryCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ������� = �������_�������
		//         | �������_������� ����������� �������

		Command command = parseHistoryCommandV1();

		if (command == null)
			command = parseHistoryCommandV2();

		return command;
	}

	private Command parseHistoryCommandV1()
	{
		// ������� = �������_�������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordHistory() && parseEOF())
		{
			// SUCCESS. ������� �������� �����
			completeLexeme(tx);
			return commandFactory.createHistoryCommand(null);
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseHistoryCommandV2()
	{
		// ������� = �������_������� ����������� �������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordHistory() && parseDelim())
		{
			String product = parseProduct();
			if (product != null)
			{
				if (parseEOF())
				{
					// SUCCESS. ������� �������� ����� � �������
					completeLexeme(tx);
					return commandFactory.createHistoryCommand(product);
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

	private boolean parseKeywordHistory()
	{
		return parseCommandKeyword(getKeywords());
	}
}
