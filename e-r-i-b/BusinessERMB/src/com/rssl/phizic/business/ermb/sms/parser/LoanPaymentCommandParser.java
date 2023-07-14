package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

import java.math.BigDecimal;

/**
 * ������ ���-������� "������ �������"
 * @author Rtischeva
 * @ created 09.10.13
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// ���-������� ������ ������� = ������
		//                              ������ ����������� �������
		//                            | ������ ����������� ������� ����������� �����
		//                            | ������ ����������� ������� ����������� ����� ����������� �����
		// ������ - �������� �������
		// ������� - ����� ���������� ��������
		// �����- ����� ��������� � ������ �������
		// ����� - ����� ���������� �����

		Command command = parseLoanPaymentCommandV1();

		if (command == null)
			command = parseLoanPaymentCommandV2();

		if (command == null)
			command = parseLoanPaymentCommandV3();

		if (command == null)
			command = parseLoanPaymentCommandV4();

		return command;
	}

	private Command parseLoanPaymentCommandV1()
	{
		// ���-������� ������ ������� = ������
		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordLoanPayment() && parseEOF())
		{
			// SUCCESS. ������� �������� �����
			completeLexeme(tx);
			return commandFactory.createLoanPaymentCommand();
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseLoanPaymentCommandV2()
	{
		// ���-������� ������ ������� = ������ ����������� �������

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordLoanPayment() && parseDelim())
		{
			String loan = parseProduct();
			if (loan != null && parseEOF())
			{
				// SUCCESS. ������� �������� ����� � �������
				completeLexeme(tx);
				return commandFactory.createLoanPaymentCommand(loan, null, null);
			}

			// FAIL-B. �� ������ ��� ����������� ������ �������
			addError(messageBuilder.buildUnknownLoanProductMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseLoanPaymentCommandV3()
	{
		// ���-������� ������ ������� = ������ ����������� ������� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordLoanPayment() && parseDelim())
		{
			String loan = parseProduct();
			if (loan != null && parseDelim())
			{
				BigDecimal amount = parseAmount();
				if (amount != null && parseEOF())
				{
					// SUCCESS. ������� �������� �����, ������� � �����
					completeLexeme(tx);
					return commandFactory.createLoanPaymentCommand(loan, amount, null);
				}

				// FAIL-C. �� ������� ��� ����������� ������� �����
				addError(messageBuilder.buildPaymentCommandIncorrectAmountMessage());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������ ��� ����������� ������ �������
			addError(messageBuilder.buildUnknownLoanProductMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseLoanPaymentCommandV4()
	{
		// ���-������� ������ ������� = ������ ����������� ������� ����������� ����� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordLoanPayment() && parseDelim())
		{
			String loan = parseProduct();
			if (loan != null && parseDelim())
			{
				BigDecimal amount = parseAmount();
				if (amount != null && parseDelim())
				{
					String card = parseProduct();
					if (card != null && parseEOF())
					{
						// SUCCESS. ������� �������� �����, �������, ����� � �����
						completeLexeme(tx);
						return commandFactory.createLoanPaymentCommand(loan, amount, card);
					}

					// FAIL-D. �� ������� ��� ����������� ������� �����
					addError(messageBuilder.buildIncorrectLoanAliasMessage());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. �� ������� ��� ����������� ������� �����
				addError(messageBuilder.buildPaymentCommandIncorrectAmountMessage());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������ ��� ����������� ������ �������
			addError(messageBuilder.buildUnknownLoanProductMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordLoanPayment()
	{
		return parseCommandKeyword(getKeywords());
	}
}
