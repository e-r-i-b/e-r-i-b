package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * @author Gulov
 * @ created 10.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class ProductBlockCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// �������_���������� = �������_���������� ����������� �������
		//                    | �������_���������� ����������� ������� ����������� ���_����������

		Command command = parseProductBlockCommandV1();

		if (command == null)
			command = parseProductBlockCommandV2();

		return command;
	}

	private Command parseProductBlockCommandV1()
	{
		// �������_���������� = �������_���������� ����������� �������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordProductBlock() && parseDelim())
		{
			String product = parseProduct();
			if (product != null)
			{
				if (parseEOF())
				{
					// SUCCESS. ������� �������� ����� � �������
					completeLexeme(tx);
					return commandFactory.createProductBlockCommand(product, null);
				}

				// FAIL-C. �� ��������� ��� ���-�� ���
				addError(messageBuilder.buildCommandFormatError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������� ��� ����������� ������ �������
			addError(messageBuilder.buildProductBlockBadProductError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseProductBlockCommandV2()
	{
		// �������_���������� = �������_���������� ����������� ������� ����������� ���_����������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordProductBlock() && parseDelim())
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
						return commandFactory.createProductBlockCommand(product, blockCode);
					}

					// FAIL-D. �� ����� ���������� ��� ���-�� ���
					addError(messageBuilder.buildCommandFormatError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. �� ������ ��� ����������� ������ ��� ����������
				// (���������� ������ "������������ ����� ��������� ��� �������",
				// �.�. ��� ����� � ����� ��������� ������ �� ������������� ���� ����������)
				addError(messageBuilder.buildCommandFormatError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������� ��� ����������� ������ �������
			addError(messageBuilder.buildProductBlockBadProductError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordProductBlock()
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
