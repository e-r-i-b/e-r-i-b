package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;

/**
 * Парсер команды оплаты чужого телефона
 * @author Rtischeva
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */
public class RechargeOtherPhoneCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// Чужой_телефон = команда_телефон разделитель телефон разделитель сумма
		//               | команда_телефон разделитель телефон разделитель сумма разделитель карта
		//               | телефон разделитель сумма
		//               | телефон разделитель сумма разделитель карта

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
		// Чужой_телефон = команда_телефон разделитель телефон разделитель сумма

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
						// SUCCESS. Указано ключевое слово, телефон и сумма
						completeLexeme(tx);
						return commandFactory.createRechargePhoneCommand(phone, amount, null);
					}

					// FAIL-D. После суммы идёт что-то ещё
					addError(messageBuilder.buildRechargePhoneBadAmountError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Не указана или некорректно указана сумма
				addError(messageBuilder.buildRechargePhoneBadAmountError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указан или некорректно указан телефон
			addError(messageBuilder.buildRechargePhoneBadPhoneError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseRechargeCommandV2()
	{
		// Чужой_телефон = команда_телефон разделитель телефон разделитель сумма разделитель карта

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
							// SUCCESS. Указано ключевое слово, телефон, сумма и карта
							completeLexeme(tx);
							return commandFactory.createRechargePhoneCommand(phone, amount, card);
						}

						// FAIL-E. После карты идёт что-то ещё
						addError(messageBuilder.buildCommandFormatError());
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. Не указана или некорректно указана карта
					addError(messageBuilder.buildRechargePhoneBadCardError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Не указана или некорректно указана сумма
				addError(messageBuilder.buildRechargePhoneBadAmountError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указан или некорректно указан телефон
			addError(messageBuilder.buildRechargePhoneBadPhoneError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseRechargeCommandV3()
	{
		// Чужой_телефон = телефон разделитель сумма

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		PhoneNumber phone = parsePhone();
		if (phone != null && parseDelim())
		{
			BigDecimal amount = parseAmount();
			if (amount != null)
			{
				if (parseEOF())
				{
					// SUCCESS. Указан телефон и сумма
					completeLexeme(tx);
					return commandFactory.createRechargePhoneCommand(phone, amount, null);
				}

				// FAIL-C. После суммы идёт что-то ещё
				addError(messageBuilder.buildCommandFormatError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указана или некорректно указана сумма
			addError(messageBuilder.buildRechargePhoneBadAmountError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseRechargeCommandV4()
	{
		// Чужой_телефон = телефон разделитель сумма разделитель карта

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
						// SUCCESS. Указан телефон, сумма и карта
						completeLexeme(tx);
						return commandFactory.createRechargePhoneCommand(phone, amount, card);
					}

					// FAIL-D. После карты идёт что-то ещё
					addError(messageBuilder.buildCommandFormatError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Некорректно указана карта
				addError(messageBuilder.buildRechargePhoneBadCardError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указана или некорректно указана сумма
			addError(messageBuilder.buildRechargePhoneBadAmountError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordRechargePhone()
	{
		return parseCommandKeyword(getKeywords());
	}
}
