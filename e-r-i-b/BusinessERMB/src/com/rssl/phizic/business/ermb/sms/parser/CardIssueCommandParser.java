package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * @author Gulov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ���-������� ����������
 */
public class CardIssueCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ���������� = �������_���������� ����������� ������� ����������� ���_����������
		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCardIssue() && parseDelim())
		{
			String product = parseProduct();
			if (product != null && parseDelim())
			{
				Integer blockCode = parseBlockCode();
				if (blockCode != null)
				{
					if (parseEOF())
					{
						// SUCCESS. ������� �������� �����, ������� � ��� ����������
						completeLexeme(tx);
						return commandFactory.createCardIssueCommand(product, blockCode);
					}

					// FAIL-D. �� ����� ���������� ��� ���-�� ���
					addError(messageBuilder.buildCardIssueBadArgsError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. �� ������ ��� ����������� ������ ��� ����������
				// (���������� ������ "������������ ����� ��������� ��� �������",
				// �.�. ��� ����� � ����� ��������� ������ �� ������������� ���� ����������)
				addError(messageBuilder.buildCardIssueBadArgsError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������� ��� ����������� ������ �������
			addError(messageBuilder.buildCommandFormatError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordCardIssue()
	{
		return parseCommandKeyword(getKeywords());
	}

	private Integer parseBlockCode()
	{
		String number = parseNumber(1, 1);
		if (number == null)
			return null;
		return Integer.parseInt(number);
	}
}
