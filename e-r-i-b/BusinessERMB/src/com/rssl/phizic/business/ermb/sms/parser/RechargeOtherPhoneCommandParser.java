package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;

/**
 * ������ ������� ������ ������ ��������
 * @author Rtischeva
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */
public class RechargeOtherPhoneCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// �����_������� = �������_������� ����������� ������� ����������� �����
		//               | �������_������� ����������� ������� ����������� ����� ����������� �����
		//               | ������� ����������� �����
		//               | ������� ����������� ����� ����������� �����

		Command command = parseRechargeCommandV1();

		if (command == null)
			command = parseRechargeCommandV2();

		if (command == null)
			command = parseRechargeCommandV3();

		if (command == null)
			command = parseRechargeCommandV4();

		return command;
	}

	private Command parseRechargeCommandV1()
	{
		// �����_������� = �������_������� ����������� ������� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordRechargePhone() && parseDelim())
		{
			PhoneNumber phone = parsePhone();
			if (phone != null && parseDelim())
			{
				BigDecimal amount = parseAmount();
				if (amount != null)
				{
					if (parseEOF())
					{
						// SUCCESS. ������� �������� �����, ������� � �����
						completeLexeme(tx);
						return commandFactory.createRechargePhoneCommand(phone, amount, null);
					}

					// FAIL-D. ����� ����� ��� ���-�� ���
					addError(messageBuilder.buildRechargePhoneBadAmountError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. �� ������� ��� ����������� ������� �����
				addError(messageBuilder.buildRechargePhoneBadAmountError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������ ��� ����������� ������ �������
			addError(messageBuilder.buildRechargePhoneBadPhoneError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseRechargeCommandV2()
	{
		// �����_������� = �������_������� ����������� ������� ����������� ����� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordRechargePhone() && parseDelim())
		{
			PhoneNumber phone = parsePhone();
			if (phone != null && parseDelim())
			{
				BigDecimal amount = parseAmount();
				if (amount != null && parseDelim())
				{
					String card = parseProduct();
					if (card != null)
					{
						if (parseEOF())
						{
							// SUCCESS. ������� �������� �����, �������, ����� � �����
							completeLexeme(tx);
							return commandFactory.createRechargePhoneCommand(phone, amount, card);
						}

						// FAIL-E. ����� ����� ��� ���-�� ���
						addError(messageBuilder.buildCommandFormatError());
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. �� ������� ��� ����������� ������� �����
					addError(messageBuilder.buildRechargePhoneBadCardError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. �� ������� ��� ����������� ������� �����
				addError(messageBuilder.buildRechargePhoneBadAmountError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������ ��� ����������� ������ �������
			addError(messageBuilder.buildRechargePhoneBadPhoneError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseRechargeCommandV3()
	{
		// �����_������� = ������� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		PhoneNumber phone = parsePhone();
		if (phone != null && parseDelim())
		{
			BigDecimal amount = parseAmount();
			if (amount != null)
			{
				if (parseEOF())
				{
					// SUCCESS. ������ ������� � �����
					completeLexeme(tx);
					return commandFactory.createRechargePhoneCommand(phone, amount, null);
				}

				// FAIL-C. ����� ����� ��� ���-�� ���
				addError(messageBuilder.buildCommandFormatError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������� ��� ����������� ������� �����
			addError(messageBuilder.buildRechargePhoneBadAmountError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseRechargeCommandV4()
	{
		// �����_������� = ������� ����������� ����� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		PhoneNumber phone = parsePhone();
		if (phone != null && parseDelim())
		{
			BigDecimal amount = parseAmount();
			if (amount != null && parseDelim())
			{
				String card = parseProduct();
				if (card != null)
				{
					if (parseEOF())
					{
						// SUCCESS. ������ �������, ����� � �����
						completeLexeme(tx);
						return commandFactory.createRechargePhoneCommand(phone, amount, card);
					}

					// FAIL-D. ����� ����� ��� ���-�� ���
					addError(messageBuilder.buildCommandFormatError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. ����������� ������� �����
				addError(messageBuilder.buildRechargePhoneBadCardError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������� ��� ����������� ������� �����
			addError(messageBuilder.buildRechargePhoneBadAmountError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordRechargePhone()
	{
		return parseCommandKeyword(getKeywords());
	}
}
