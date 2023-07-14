package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.PhoneNumber;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 13.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Парсер для СМС-команды ПЕРЕВОД
 * Перевод со счёта на "чужую" карту не работает!
 */
public class TransferCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// Перевод = команда_перевод разделитель продукт разделитель продукт разделитель сумма
		//         | команда_перевод разделитель продукт разделитель телефон разделитель сумма
		//         | команда_перевод разделитель телефон разделитель сумма
		//         | команда_перевод разделитель карта разделитель чужая_карта разделитель сумма

		Command command = parseTransferCommandV1();

		if (command == null)
			command = parseTransferCommandV2();

		if (command == null)
			command = parseTransferCommandV3();

		if (command == null)
			command = parseTransferCommandV4();

		return command;
	}

	/**
	 * Прочитать команду ПЕРЕВОД по варианту "алиас карты/счёта -> алиас карты/счёта"
	 * @return команда ПЕРЕВОД (между своими счетами) или null, если вариант не подходит
	 */
	private Command parseTransferCommandV1()
	{
		// Перевод = команда_перевод разделитель продукт разделитель продукт разделитель сумма

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTransfer() && parseDelim())
		{
			String sender = parseProduct();
			if (sender != null && parseDelim())
			{
				String receiver = parseProduct();
				if (receiver != null && parseDelim())
				{
					BigDecimal amount = parseAmount();
					if (amount != null)
					{
						if (parseEOF())
						{
							// SUCCESS. Указано ключевое слово, алиас продукта списания, алиас продукта зачисления и сумма
							completeLexeme(tx);
							return commandFactory.createInternalTransferCommand(sender, receiver, amount);
						}

						// FAIL-E. После суммы есть ещё что-то
						addError(messageBuilder.buildInternalCardTransferBadArgsError(getCardSmsAutoAliasLength()));
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. Не указана или некорректно указана сумма
					addError(messageBuilder.buildCardTransferBadAmountError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Не указана или некорректно указана карта/счёт получателя
				addError(messageBuilder.buildCardOrPhoneTransferBadReceiverError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указана или некорректно указана карта/счёт отправителя
			addError(messageBuilder.buildCardTransferBadSenderError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	/**
	 * Прочитать команду ПЕРЕВОД по варианту "алиас карты -> номер карты"
	 * @return команда ПЕРЕВОД (по карте) или null, если вариант не подходит
	 */
	private Command parseTransferCommandV2()
	{
		// Перевод = команда_перевод разделитель карта разделитель чужая_карта разделитель сумма

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTransfer() && parseDelim())
		{
			String sender = parseProduct();
			if (sender != null && parseDelim())
			{
				String receiver = parseOtherCard();
				if (receiver != null && parseDelim())
				{
					BigDecimal amount = parseAmount();
					if (amount != null)
					{
						if (parseEOF())
						{
							// SUCCESS. Указано ключевое слово, алиас карты списания, номер карты зачисления и сумма
							completeLexeme(tx);
							return commandFactory.createTransferCommand(sender, receiver, amount);
						}

						// FAIL-E. После суммы есть ещё что-то
						addError(messageBuilder.buildCardTransferBadArgsError(getCardSmsAutoAliasLength(), isOurCard(receiver)));
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. Не указана или некорректно указана сумма
					addError(messageBuilder.buildCardTransferBadAmountError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Не указана или некорректно указана карта/счёт получателя
				addError(messageBuilder.buildCardOrPhoneTransferBadReceiverError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указана или некорректно указана карта/счёт отправителя
			addError(messageBuilder.buildCardTransferBadSenderError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	/**
	 * Прочитать команду ПЕРЕВОД по варианту "алиас карты/счёта -> номер телефона"
	 * @return команда ПЕРЕВОД (по телефону) или null, если вариант не подходит
	 */
	private Command parseTransferCommandV3()
	{
		// Перевод = команда_перевод разделитель продукт разделитель телефон разделитель сумма

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTransfer() && parseDelim())
		{
			String sender = parseProduct();
			if (sender != null && parseDelim())
			{
				PhoneNumber receiver = parsePhone();
				if (receiver != null && parseDelim())
				{
					BigDecimal amount = parseAmount();
					if (amount != null)
					{
						if (parseEOF())
						{
							// SUCCESS. Указано ключевое слово, алиас продукта списания, номер телефона и сумма
							completeLexeme(tx);
							return commandFactory.createPhoneTransferCommand(sender, receiver, amount);
						}

						// FAIL-E. После суммы есть ещё что-то
						addError(messageBuilder.buildPhoneTransferBadArgsError());
						cancelLexeme(tx);
						return null;
					}

					// FAIL-D. Не указана или некорректно указана сумма
					addError(messageBuilder.buildPhoneTransferBadAmountError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Не указан или некорректно указан телефон получателя
				addError(messageBuilder.buildCardOrPhoneTransferBadReceiverError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указана или некорректно указана карта/счёт отправителя
			addError(messageBuilder.buildPhoneTransferBadSenderError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	/**
	 * Прочитать команду ПЕРЕВОД по варианту "(активная карта) -> номер телефона"
	 * @return команда ПЕРЕВОД (по телефону) или null, если вариант не подходит
	 */
	private Command parseTransferCommandV4()
	{
		// Перевод = команда_перевод разделитель телефон разделитель сумма

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTransfer() && parseDelim())
		{
			PhoneNumber receiver = parsePhone();
			if (receiver != null && parseDelim())
			{
				BigDecimal amount = parseAmount();
				if (amount != null)
				{
					if (parseEOF())
					{
						// SUCCESS. Указано ключевое слово, номер телефона и сумма
						completeLexeme(tx);
						return commandFactory.createPhoneTransferCommand(receiver, amount);
					}

					// FAIL-D. После суммы есть ещё что-то
					addError(messageBuilder.buildPhoneTransferBadArgsError());
					cancelLexeme(tx);
					return null;
				}

				// FAIL-C. Не указана или некорректно указана сумма
				addError(messageBuilder.buildPhoneTransferBadAmountError());
				cancelLexeme(tx);
				return null;
			}

			// FAIL-B. Не указан или некорректно указан телефон
			addError(messageBuilder.buildCardOrPhoneTransferBadReceiverError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private boolean parseKeywordTransfer()
	{
		return parseCommandKeyword(getKeywords());
	}

	private boolean isOurCard(String cardNumber)
	{
		try
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			Client client = ((ActivePerson)getPerson()).asClient();

			Pair<String, Office> stringOfficePair = new Pair<String, Office>(cardNumber, null);
			// noinspection unchecked
			GroupResult<Pair<String,Office>,Card> gresult = bankrollService.getCardByNumber(client, stringOfficePair);
			return GroupResultHelper.getOneResult(gresult) != null;
		}
		catch (LogicException e)
		{
			throw new InternalErrorException(e);
		}
		catch (SystemException e)
		{
			throw new InternalErrorException(e);
		}
	}
}
