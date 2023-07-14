package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.utils.PhoneNumber;

/**
 * Парсер смс-команды отключения автоплатежа
 * @author Rtischeva
 * @ created 24.06.14
 * @ $Author$
 * @ $Revision$
 */
public class RefuseAutoPaymentCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		//откл_автоплатеж	= команда_откл_автоплатеж разделитель телефон
		//                  | команда_откл_автоплатеж разделитель телефон разделитель карта

		Command command = parseRefuseAutoPaymentCommandV1();

		if (command == null)
			command = parseRefuseAutoPaymentCommandV2();

		return command;
	}

	private Command parseRefuseAutoPaymentCommandV1()
	{
	   //откл_автоплатеж	= команда_откл_автоплатеж разделитель телефон

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
						// SUCCESS. Указано ключевое слово и телефон
						completeLexeme(tx);
						return commandFactory.createRefuseAutoPaymentCommand(phone, null);
					}

					// FAIL-C. После телефона идёт что-то ещё
					addError(messageBuilder.buildRefuseAutoPayBadArgsMessage());
					cancelLexeme(tx);
					return null;
				}
			}
			// FAIL-B. Некорректный номер телефона
			addError(messageBuilder.buildRefuseAutoPayBadArgsMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseRefuseAutoPaymentCommandV2()
	{
		//откл_автоплатеж	= команда_откл_автоплатеж разделитель телефон разделитель карта

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
						// SUCCESS. Указано ключевое слово, телефон и карта
						completeLexeme(tx);
						return commandFactory.createRefuseAutoPaymentCommand(phone, card);
					}
					// FAIL-D. После карты идёт что-то ещё
					addError(messageBuilder.buildRefuseAutoPayBadArgsMessage());
					cancelLexeme(tx);
					return null;
				}
				// FAIL-C. Вместо карты указано непонятно что
				addError(messageBuilder.buildRefuseAutoPayBadArgsMessage());
				cancelLexeme(tx);
				return null;
			}
			// FAIL-B. Некорректный номер телефона
			addError(messageBuilder.buildRefuseAutoPayBadArgsMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;

	}

	private boolean parseKeywordRefuseAutoPayment()
	{
		return parseCommandKeyword(getKeywords());
	}
}
