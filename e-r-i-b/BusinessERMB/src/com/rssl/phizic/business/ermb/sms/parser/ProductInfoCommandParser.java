package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * ������ ���-������� "����������" 
 * @author Rtischeva
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class ProductInfoCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ���������� = �������_����������
		//            | �������_���������� ����������� �������

		Command command = parseProductInfoCommandV1();

		if (command == null)
			command = parseProductInfoCommandV2();

		return command;
	}

	private Command parseProductInfoCommandV1()
	{
		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordProductInfo() && parseEOF())
		{
			// SUCCESS. ������� �������� �����
			completeLexeme(tx);
			return commandFactory.createProductInfoCommand(null);
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseProductInfoCommandV2()
	{
		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordProductInfo() && parseDelim())
		{
			String product = parseProduct();
			if (product != null && parseEOF())
			{
				// SUCCESS. ������� �������� ����� � �������
				completeLexeme(tx);
				return commandFactory.createProductInfoCommand(product);
			}

			// FAIL-B. �� ������ ��� ����������� ������ ������� ���� �� ��������� ��� ���-�� ���
			addError(messageBuilder.buildUnknownCommandMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordProductInfo()
	{
		return parseCommandKeyword(getKeywords());
	}
}
