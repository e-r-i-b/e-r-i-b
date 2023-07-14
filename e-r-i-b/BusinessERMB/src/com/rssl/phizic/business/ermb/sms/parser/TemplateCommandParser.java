package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * @author Gulov
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class TemplateCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ������� = �������۔ | ������͔ | ���������Ŕ | ���������ߔ | �SHABLON� | �SHABLONY� | �PORUTCHENIE� | �PORUTCHENIYA�
		//         | �����������?�

		Command command = parseTemplatListCommandV1();

		if (command == null)
			command = parseTemplatListCommandV2();

		return command;
	}

	private Command parseTemplatListCommandV1()
	{
		// ������� = �������۔ | ������͔ | ���������Ŕ | ���������ߔ | �SHABLON� | �SHABLONY� | �PORUTCHENIE� | �PORUTCHENIYA�

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTemplate() && parseEOF())
		{
			// SUCCESS. ������� �������� �����
			completeLexeme(tx);
			return commandFactory.createTemplateCommand(null);
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseTemplatListCommandV2()
	{
		// ������� = �����������?�

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		String recipient = parseRecipient();
		if (recipient != null && parseSample("?"))
		{
			if (parseEOF())
			{
				// SUCCESS. ������ ����������
			    completeLexeme(tx);
				return commandFactory.createTemplateCommand(recipient);
			}

			// FAIL-B. ����� ���������� ��� �����-�� �����
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private String parseRecipient()
	{
		// ���������� = �����
		return parseAlias();
	}

	private boolean parseKeywordTemplate()
	{
		return parseCommandKeyword(getKeywords());
	}
}
