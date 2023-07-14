package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

import java.math.BigDecimal;

/**
 * Парсер смс-команды "Оплата кредита"
 * @author Rtischeva
 * @ created 09.10.13
 * @ $Author$
 * @ $Revision$
 */
public class LoanPaymentCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// СМС-команда оплата кредита = КРЕДИТ
		//                              КРЕДИТ разделитель договор
		//                            | КРЕДИТ разделитель договор разделитель сумма
		//                            | КРЕДИТ разделитель договор разделитель сумма разделитель карта
		// КРЕДИТ - название команды
		// договор - алиас кредитного договора
		// сумма- сумма погашения в валюте кредита
		// карта - алиас банковской карты

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
		// СМС-команда оплата кредита = КРЕДИТ
		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordLoanPayment() && parseEOF())
		{
			// SUCCESS. Указано ключевое слово
			completeLexeme(tx);
			return commandFactory.createLoanPaymentCommand();
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseLoanPaymentCommandV2()
	{
		// СМС-команда оплата кредита = КРЕДИТ разделитель договор

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordLoanPayment() && parseDelim())
		{
			String loan = parseProduct();
			if (loan != null && parseEOF())
			{
				// SUCCESS. Указано ключевое слово и договор
				completeLexeme(tx);
				return commandFactory.createLoanPaymentCommand(loan, null, null);
			}

			// FAIL-B. Не указан или некорректно указан договор
			addError(messageBuilder.buildUnknownLoanProductMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseLoanPaymentCommandV3()
	{
		// СМС-команда оплата кредита = КРЕДИТ разделитель договор разделитель сумма

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordLoanPayment() && parseDelim())
		{
			String loan = parseProduct();
			if (loan != null && parseDelim())
			{
				BigDecimal amount = parseAmount();
				if (amount != null && parseEOF())
				{
					// SUCCESS. Указано ключевое слово, договор и сумма
					completeLexeme(tx);
					return commandFactory.createLoanPaymentCommand(loan, amount, null);
				}

				// FAIL-C. Не указана или некорректно указана сумма
				addError(messageBuilder.buildPaymentCommandIncorrectAmountMessage());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указан или некорректно указан договор
			addError(messageBuilder.buildUnknownLoanProductMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseLoanPaymentCommandV4()
	{
		// СМС-команда оплата кредита = КРЕДИТ разделитель договор разделитель сумма разделитель карта

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
						// SUCCESS. Указано ключевое слово, договор, сумма и карта
						completeLexeme(tx);
						return commandFactory.createLoanPaymentCommand(loan, amount, card);
					}

					// FAIL-D. Не указана или некорректно указана карта
					addError(messageBuilder.buildIncorrectLoanAliasMessage());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Не указана или некорректно указана сумма
				addError(messageBuilder.buildPaymentCommandIncorrectAmountMessage());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указан или неправильно указан договор
			addError(messageBuilder.buildUnknownLoanProductMessage());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordLoanPayment()
	{
		return parseCommandKeyword(getKeywords());
	}
}
