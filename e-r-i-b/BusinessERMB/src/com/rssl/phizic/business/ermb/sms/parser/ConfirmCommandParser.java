package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.common.forms.validators.passwords.SmsPasswordConfig;
import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.ConfirmEngine;
import com.rssl.phizic.security.ConfirmToken;
import com.rssl.phizic.security.PersonConfirmManager;

/**
 * @author Erkin
 * @ created 22.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ���-������� "������������� ��������"
 * �����! � �������� ������� ��� ������������ ����� 
 */
public class ConfirmCommandParser extends CommandParserBase
{
	private final int confirmCodeLength = ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordLength();

	///////////////////////////////////////////////////////////////////////////

	public Command parseCommand()
	{
		// ������������� = # ���_������������� | ���_������������� | ���������_���_�_�����_�������������
		// ���_������������� = (1*(�����))
		//���� ������ ���������� ����� �� 5 (���������) ��������, �� ������� ���������� ���������������� ������ ��������� ��� ������������� �������� � ��������������� �����

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		// A. # ���_�������������
		if (parseSample("#"))
		{
			String confirmCode = parseConfirmCode();
			if (confirmCode != null)
			{
				if (parseEOF())
				{
					// ��������� ������� � ����� �������������
					// (�������� ���������� ���� ������������� � �������)
					completeLexeme(tx);
					return commandFactory.createConfirmCommand(confirmCode);
				}
			}
			throw new UserErrorException(messageBuilder.buildIncorrectConfirmCodeMessage());
		}

		else
		{
			// B. ���_�������������
			String confirmCode = parseConfirmCode();
			if (confirmCode != null)
			{
				if (parseEOF())
				{
					// �������� ��� �������������
					// (�������� ���������� ���� ������������� � �������)
					completeLexeme(tx);
					return commandFactory.createConfirmCommand(confirmCode);
				}
			}
		}

		// C. ���������_���_�_�����_�������������
		String confirmCodeMessage = getText();
		ConfirmToken confirmToken = findSecondaryConfirmToken(confirmCodeMessage);
		if (confirmToken != null)
		{
			// ��� ��������� ��������� � ������� �������������
			completeLexeme(tx);
			return commandFactory.createConfirmCommand(confirmToken);
		}

		// ��������� ������ ���-�������
		cancelLexeme(tx);
		return null;
	}

	private String parseConfirmCode()
	{
		return parseNumber(confirmCodeLength, confirmCodeLength);
	}

	private ConfirmToken findSecondaryConfirmToken(String confirmCode)
	{
		ConfirmEngine confirmEngine = getModule().getConfirmEngine();
		if (confirmEngine == null)
			throw new UnsupportedOperationException("ConfirmEngine �� �������������� � ������ " + getModule().getName());
		PersonConfirmManager confirmManager = confirmEngine.createPersonConfirmManager(getPerson());
		return confirmManager.captureConfirm(confirmCode, getPhone(), false);
	}
}
