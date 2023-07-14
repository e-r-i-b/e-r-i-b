package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;

import static com.rssl.phizic.business.ermb.sms.parser.Lexeme.PHONE;

/**
 * Парсер смс-команды подключения автоплатежа
 * @author Rtischeva
 * @ created 21.05.14
 * @ $Author$
 * @ $Revision$
 */
public class CreateAutoPaymentCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		//Автоплатеж	= команда_автоплатеж разделитель телефон разделитель сумма разделитель порог
		//              | команда_автоплатеж разделитель телефон разделитель сумма разделитель порог разделитель карта
		//              | команда_автоплатеж разделитель телефон разделитель лимит
		//              | команда_автоплатеж разделитель телефон разделитель лимит разделитель карта

		Command command = parseCreateAutoPaymentCommandV1();

		if (command == null)
			command = parseCreateAutoPaymentCommandV2();

		if (command == null)
			command = parseCreateAutoPaymentCommandV3();

		if (command == null)
			command = parseCreateAutoPaymentCommandV4();

		return command;

	}

	private Command parseCreateAutoPaymentCommandV1()
	{
		//Автоплатеж = команда_автоплатеж разделитель телефон разделитель сумма разделитель порог

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCreateAutoPayment() && parseDelim())
		{
			ParseTransaction phoneTx = beginLexeme(PHONE);
			PhoneNumber phone = parsePhone(phoneTx);
			if (phone != null && parseDelim())
			{
				if (isClientPhone(phone))
				{
					BigDecimal amount = parseAmount();
					if (amount != null && parseDelim())
					{
						BigDecimal threshold = parseAmount();
						if (threshold != null)
						{
							if (parseEOF())
							{
								// SUCCESS. Указано ключевое слово, телефон, сумма и порог либо ключевое слово, телефон, лимит и карта. Поэтому возвращаем команду со всеми параметрами, разбираться будем дальше
								completeLexeme(tx);
								return commandFactory.createCreateAutoPaymentCommand(phone, amount, threshold, amount, String.valueOf(threshold));
							}

							// FAIL-F. После порога идёт что-то ещё
							addError(messageBuilder.buildAutoPayBadThresholdMessage(phone));
							cancelLexeme(tx);
							return null;
						}

						// FAIL-E. Не указан или некорректно указан порог
						addError(messageBuilder.buildAutoPayBadThresholdMessage(phone));
						cancelLexeme(tx);
						return null;
					}
					// FAIL-D. Не указана или некорректно указана сумма
					addError(messageBuilder.buildAutoPayBadAmountMessage(phone));
					cancelLexeme(tx);
					return null;
				}
				// FAIL-C. Указан не свой номер телефона
				addError(messageBuilder.buildAutoPayIncorrectYourPhoneMessage(phone));
				cancelLexeme(tx);
				return null;
			}

			cancelLexeme(phoneTx);
			// FAIL-B. Не указан или некорректно указан телефон
			addError(messageBuilder.buildAutoPayIncorrectPhoneMessage(parseString()));
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseCreateAutoPaymentCommandV2()
	{
		//Автоплатеж = команда_автоплатеж разделитель телефон разделитель сумма разделитель порог разделитель карта

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCreateAutoPayment() && parseDelim())
		{
			ParseTransaction phoneTx = beginLexeme(PHONE);
			PhoneNumber phone = parsePhone(phoneTx);
			if (phone != null && parseDelim())
			{
				if (isClientPhone(phone))
				{
					BigDecimal amount = parseAmount();
					if (amount != null && parseDelim())
					{
						BigDecimal threshold = parseAmount();
						if (threshold != null && parseDelim())
						{
							String card = parseProduct();
							if (card != null)
							{
								if (parseEOF())
								{
									// SUCCESS. Указано ключевое слово, телефон, сумма, порог и карта
									completeLexeme(tx);
									return commandFactory.createCreateAutoPaymentCommand(phone, amount, threshold, null, card);
								}

								// FAIL-G. После карты идёт что-то ещё
								addError(messageBuilder.buildAutoPayIncorrectCardMessage(phone));
								cancelLexeme(tx);
								return null;
							}

							// FAIL-F. Вместо номера карты что-то непонятное
							addError(messageBuilder.buildAutoPayIncorrectCardMessage(phone));
							cancelLexeme(tx);
							return null;
						}

						// FAIL-E. Не указан или некорректно указан порог
						addError(messageBuilder.buildAutoPayBadThresholdMessage(phone));
						cancelLexeme(tx);
						return null;
					}
					// FAIL-D. Не указана или некорректно указана сумма
					addError(messageBuilder.buildAutoPayBadAmountMessage(phone));
					cancelLexeme(tx);
					return null;
				}
				// FAIL-C. Указан не свой номер телефона
				addError(messageBuilder.buildAutoPayIncorrectYourPhoneMessage(phone));
				cancelLexeme(tx);
				return null;
			}
			cancelLexeme(phoneTx);
			// FAIL-B. Не указан или некорректно указан телефон
			addError(messageBuilder.buildAutoPayIncorrectPhoneMessage(parseString()));
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseCreateAutoPaymentCommandV3()
	{
		//Автоплатеж =  команда_автоплатеж разделитель телефон разделитель лимит

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCreateAutoPayment() && parseDelim())
		{
			ParseTransaction phoneTx = beginLexeme(PHONE);
			PhoneNumber phone = parsePhone(phoneTx);
			if (phone != null && parseDelim())
			{
				if (isClientPhone(phone))
				{
					BigDecimal limit = parseAmount();
					if (limit != null)
					{
						if (parseEOF())
						{
							// SUCCESS. Указано ключевое слово, телефон и лимит
							completeLexeme(tx);
							return commandFactory.createCreateAutoPaymentCommand(phone, null, null, limit, null);
						}

						// FAIL-E. После лимита идёт что-то ещё
						addError(messageBuilder.buildAutoPayBadLimitMessage(phone));
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. Не указан или некорректно указан лимит
					addError(messageBuilder.buildAutoPayBadLimitMessage(phone));
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Указан не свой номер телефона
				addError(messageBuilder.buildAutoPayIncorrectYourPhoneMessage(phone));
				cancelLexeme(tx);
				return null;
			}
			cancelLexeme(phoneTx);
			// FAIL-B. Не указан или некорректно указан телефон
			addError(messageBuilder.buildAutoPayIncorrectPhoneMessage(parseString()));
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseCreateAutoPaymentCommandV4()
	{
		//Автоплатеж =  команда_автоплатеж разделитель телефон разделитель лимит разделитель карта

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordCreateAutoPayment() && parseDelim())
		{
			ParseTransaction phoneTx = beginLexeme(PHONE);
			PhoneNumber phone = parsePhone(phoneTx);
			if (phone != null && parseDelim())
			{
				if (isClientPhone(phone))
				{
					BigDecimal limit = parseAmount();
					if (limit != null && parseDelim())
					{
						String card = parseProduct();
						if (card != null)
						{
							if (parseEOF())
							{
								// SUCCESS. Указано ключевое слово, телефон, лимит и карта
								completeLexeme(tx);
								return commandFactory.createCreateAutoPaymentCommand(phone, null, null, limit, card);
							}

							// FAIL-F. После карты идёт что-то ещё
							addError(messageBuilder.buildAutoPayIncorrectCardMessage(phone));
							cancelLexeme(tx);
							return null;
						}

						// FAIL-E. Вместо номера карты что-то непонятное
						addError(messageBuilder.buildAutoPayIncorrectCardMessage(phone));
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. После лимита идёт непонятно что
					addError(messageBuilder.buildAutoPayBadLimitMessage(phone));
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Указан не свой номер телефона
				addError(messageBuilder.buildAutoPayIncorrectYourPhoneMessage(phone));
				cancelLexeme(tx);
				return null;
			}
			cancelLexeme(phoneTx);
			// FAIL-B. Не указан или некорректно указан телефон
			addError(messageBuilder.buildAutoPayIncorrectPhoneMessage(parseString()));
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordCreateAutoPayment()
	{
		return parseCommandKeyword(getKeywords());
	}

	private boolean isClientPhone(PhoneNumber phone)
	{
		ErmbProfile profile = getPerson().getErmbProfile();
		boolean isClientPhone = false;
		for (String string :profile.getPhoneNumbers())
		{
			PhoneNumber number = PhoneNumber.fromString(string);
			if (phone.equals(number))
				isClientPhone = true;
		}
		return isClientPhone;
	}
}
