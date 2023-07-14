package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * ������ ���-������� "�����"
 * @author Rtischeva
 * @ created 09.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class TariffCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ����� = �������_�����
		//       | �������_����� ����������� ��������_������

		Command command = parseShowTariffCommand();

		if (command == null)
			command = parseChangeTariffCommand();

		return command;
	}

	private Command parseShowTariffCommand()
	{
		// ����� = �������_�����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTariff() && parseEOF())
		{
			// SUCCESS. ��������� �������� ����� => ���������� ������� "�������� ������"
			completeLexeme(tx);
			return commandFactory.createShowTariffCommand();
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseChangeTariffCommand()
	{
		// ����� = �������_����� ����������� ��������_������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTariff() && parseDelim())
		{
			String newTariff = parseTariff();
			if (newTariff != null && parseEOF())
			{
				// SUCCESS. ��������� �������� ����� � ����� => ���������� ������� "������� �����"
				completeLexeme(tx);
				return commandFactory.createChangeTariffCommand(newTariff);
			}

			// FAIL-B. �� ������ ��� ����������� ������ �����
			cancelLexeme(tx);
			addError(messageBuilder.buildIncorrectTariffNameMessage());
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordTariff()
	{
		return parseCommandKeyword(getKeywords());
	}
}
