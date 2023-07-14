package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.common.forms.validators.passwords.SmsPasswordConfig;
import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.config.ConfigFactory;

import java.math.BigDecimal;

/**
 * ������ ������� ������ ������ ��������
 * @author Rtischeva
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */
public class RechargeOwnPhoneCommandParser extends CommandParserBase
{
	//����� ����� �������� ������ ����� ���� �������������
	private final int amountMaxLength = ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordLength() - 1;

	public Command parseCommand()
	{
		// ����_������� = ����� | ����� ����������� �����
		//���� ��������� �������� ����� ������ 5 �������� (����� ���� �������������), �� ���������, ��� ��� ������ ���������� ��������

		Command command = parseRechargeCommandV1();

		if (command == null)
			command = parseRechargeCommandV2();

		return command;
	}

	private Command parseRechargeCommandV1()
	{
		// ����_������� = �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		BigDecimal amount = parseAmount(amountMaxLength);
		if (amount != null && parseEOF())
		{
			// SUCCESS. ������� �����
			completeLexeme(tx);
			return commandFactory.createRechargePhoneCommand(amount, null);
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private Command parseRechargeCommandV2()
	{
		// ����_������� = ����� ����������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		BigDecimal amount = parseAmount();
		if (amount != null && parseDelim())
		{
			String card = parseProduct();
			if (card != null)
			{
				if (parseEOF())
				{
					// SUCCESS. ������� ����� � �����
					completeLexeme(tx);
					return commandFactory.createRechargePhoneCommand(amount, card);
				}

				// FAIL-C. ����� ����� ��� ���-�� ���
				addError(messageBuilder.buildCommandFormatError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. �� ������� ��� ����������� ������� �����
			addError(messageBuilder.buildRechargePhoneBadCardError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}
}
