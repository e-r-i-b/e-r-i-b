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
 * Парсер для СМС-команды "Подтверждение операции"
 * ВАЖНО! В процессе разбора СМС используется поиск 
 */
public class ConfirmCommandParser extends CommandParserBase
{
	private final int confirmCodeLength = ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordLength();

	///////////////////////////////////////////////////////////////////////////

	public Command parseCommand()
	{
		// Подтверждение = # код_подтверждения | код_подтверждения | исходящее_смс_с_кодом_подтверждения
		// код_подтверждения = (1*(цифра))
		//если клиент отправляет число из 5 (настройка) символов, то система необходимо интерпретировать данное сообщение как подтверждение действия с соответствующим кодом

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		// A. # код_подтверждения
		if (parseSample("#"))
		{
			String confirmCode = parseConfirmCode();
			if (confirmCode != null)
			{
				if (parseEOF())
				{
					// Прочитана решётка с кодом подтверждения
					// (проверка валидности кода подтверждения в команде)
					completeLexeme(tx);
					return commandFactory.createConfirmCommand(confirmCode);
				}
			}
			throw new UserErrorException(messageBuilder.buildIncorrectConfirmCodeMessage());
		}

		else
		{
			// B. код_подтверждения
			String confirmCode = parseConfirmCode();
			if (confirmCode != null)
			{
				if (parseEOF())
				{
					// Прочитан код подтверждения
					// (проверка валидности кода подтверждения в команде)
					completeLexeme(tx);
					return commandFactory.createConfirmCommand(confirmCode);
				}
			}
		}

		// C. исходящее_смс_с_кодом_подтверждения
		String confirmCodeMessage = getText();
		ConfirmToken confirmToken = findSecondaryConfirmToken(confirmCodeMessage);
		if (confirmToken != null)
		{
			// СМС полностью совпадает с текстом подтверждения
			completeLexeme(tx);
			return commandFactory.createConfirmCommand(confirmToken);
		}

		// Встречена другая СМС-команда
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
			throw new UnsupportedOperationException("ConfirmEngine не поддерживается в модуле " + getModule().getName());
		PersonConfirmManager confirmManager = confirmEngine.createPersonConfirmManager(getPerson());
		return confirmManager.captureConfirm(confirmCode, getPhone(), false);
	}
}
