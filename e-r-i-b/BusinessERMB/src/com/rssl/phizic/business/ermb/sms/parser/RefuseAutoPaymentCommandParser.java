package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * ������ ���-������� ���������� �����������
 * @author Rtischeva
 * @ created 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class RefuseAutoPaymentCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		//����_����������	= �������_����_���������� ����������� �������
		//                  | �������_����_���������� ����������� ������� ����������� �����

		Command command = parseRefuseAutoPaymentCommandV1();

		if (command == null)
			command = parseRefuseAutoPaymentCommandV2();

		return command;
	}

	private Command parseRefuseAutoPaymentCommandV1()
	{
	   //����_����������	= �������_����_���������� ����������� �������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordRefuseAutoPayment())
		{
			if (parseDelim())
			{
				PhoneNumber phone = parsePhone();
				if (phone != null)
				{
					if (parseEOF())
					{
						// SUCCESS. ������� �������� ����� � �������
						completeLexeme(tx);
						return commandFactory.createRefuseAutoPaymentCommand(phone, null);
					}

					// FAIL-C. ����� �������� ��� ���-�� ���
					addError(messageBuilder.buildRefuseAutoPayBadArgsMessage());
					cancelLexeme(tx);
					return null;
				}
			}
			// FAIL-B. ������������ ����� ��������
			addError(messageBuilder.buildRefuseAutoPayBadArgsMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseRefuseAutoPaymentCommandV2()
	{
		//����_����������	= �������_����_���������� ����������� ������� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordRefuseAutoPayment() && parseDelim())
		{
			PhoneNumber phone = parsePhone();
			if (phone != null && parseDelim())
			{
				String card = parseProduct();
				if (card != null)
				{
					if (parseEOF())
					{
						// SUCCESS. ������� �������� �����, ������� � �����
						completeLexeme(tx);
						return commandFactory.createRefuseAutoPaymentCommand(phone, card);
					}
					// FAIL-D. ����� ����� ��� ���-�� ���
					addError(messageBuilder.buildRefuseAutoPayBadArgsMessage());
					cancelLexeme(tx);
					return null;
				}
				// FAIL-C. ������ ����� ������� ��������� ���
				addError(messageBuilder.buildRefuseAutoPayBadArgsMessage());
				cancelLexeme(tx);
				return null;
			}
			// FAIL-B. ������������ ����� ��������
			addError(messageBuilder.buildRefuseAutoPayBadArgsMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;

	}

	private boolean parseKeywordRefuseAutoPayment()
	{
		return parseCommandKeyword(getKeywords());
	}
}
