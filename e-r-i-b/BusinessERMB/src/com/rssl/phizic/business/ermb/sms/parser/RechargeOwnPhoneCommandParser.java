package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.common.forms.validators.passwords.SmsPasswordConfig;
import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.config.ConfigFactory;

import java.math.BigDecimal;

/**
 * Парсер команды оплаты своего телефона
 * @author Rtischeva
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */
public class RechargeOwnPhoneCommandParser extends CommandParserBase
{
	//Длина суммы списания меньше длины кода подтверждения
	private final int amountMaxLength = ConfigFactory.getConfig(SmsPasswordConfig.class).getSmsPasswordLength() - 1;

	public Command parseCommand()
	{
		// Свой_телефон = сумма | сумма разделитель карта
		//Если сообщение содержит число меньше 5 символов (длина кода подтверждения), то считается, что это оплата мобильного телефона

		Command command = parseRechargeCommandV1();

		if (command == null)
			command = parseRechargeCommandV2();

		return command;
	}

	private Command parseRechargeCommandV1()
	{
		// Свой_телефон = сумма

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		BigDecimal amount = parseAmount(amountMaxLength);
		if (amount != null && parseEOF())
		{
			// SUCCESS. Указана сумма
			completeLexeme(tx);
			return commandFactory.createRechargePhoneCommand(amount, null);
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseRechargeCommandV2()
	{
		// Свой_телефон = сумма разделитель карта

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		BigDecimal amount = parseAmount();
		if (amount != null && parseDelim())
		{
			String card = parseProduct();
			if (card != null)
			{
				if (parseEOF())
				{
					// SUCCESS. Указана сумма и карта
					completeLexeme(tx);
					return commandFactory.createRechargePhoneCommand(amount, card);
				}

				// FAIL-C. После карты идёт что-то ещё
				addError(messageBuilder.buildCommandFormatError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указана или некорректно указана карта
			addError(messageBuilder.buildRechargePhoneBadCardError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}
}
