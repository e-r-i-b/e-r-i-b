package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAlias;
import com.rssl.phizic.business.ermb.provider.ServiceProviderSmsAliasService;
import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;

import java.util.LinkedList;
import java.util.List;

/**
 * Парсер для смс-команды "Оплата услуг (поставщики)"
 * @author Rtischeva
 * @ created 18.09.13
 * @ $Author$
 * @ $Revision$
 */
public class ServicePaymentCommandParser extends CommandParserBase
{
	private final ServiceProviderSmsAliasService serviceProviderSmsAliasService = new ServiceProviderSmsAliasService();

	///////////////////////////////////////////////////////////////////////////

	public Command parseCommand()
	{
		// Оплата = получатель
		//        | получатель разделитель список_реквизитов
		// TODO:  | получатель разделитель список_реквизитов разделитель карта

		Command command = parseServicePaymentCommandV1();

		if (command == null)
			command = parseServicePaymentCommandV2();

		return command;
	}

	private Command parseServicePaymentCommandV1()
	{
		// Оплата = получатель

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		ServiceProviderSmsAlias recipient = parseRecipient();
		if (recipient != null && parseEOF())
		{
			// SUCCESS. Указан поставщик
			completeLexeme(tx);
			return commandFactory.createServicePaymentCommand(recipient, null);
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseServicePaymentCommandV2()
	{
		// Оплата = получатель разделитель список_реквизитов

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		ServiceProviderSmsAlias recipient = parseRecipient();
		if (recipient != null && parseDelim())
		{
			List<String> requisites = parseRequisiteList();
			if (requisites != null && parseEOF())
			{
				// SUCCESS. Указан поставщик и его реквизиты
				completeLexeme(tx);
				return commandFactory.createServicePaymentCommand(recipient, requisites);
			}

			// FAIL-B. После реквизитов указана какая-то ересь
			addError(messageBuilder.buildCommandFormatError());
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. Встречена другая СМС-команда
		cancelLexeme(tx);
		return null;
	}

	private ServiceProviderSmsAlias parseRecipient()
	{
		ParseTransaction tx = beginLexeme(Lexeme.RECIPIENT);

		String recipient = parseAlias();
		if (recipient != null)
		{
			ServiceProviderSmsAlias serviceProviderSmsAlias = findServiceProviderSmsAlias(recipient);
			if (serviceProviderSmsAlias != null)
			{
				completeLexeme(tx);
				return serviceProviderSmsAlias;
			}
		}

		cancelLexeme(tx);
		return null;
	}

	private List<String> parseRequisiteList()
	{
		// Список_реквизитов = <пусто> | реквизит | реквизит разделитель список_реквизитов

		List<String> list = new LinkedList<String>();

		while (true)
		{
			String requisite = parseRequisite();
			if (requisite == null)
				break;

			list.add(requisite);

			if (!parseDelim())
				break;
		}

		return list;
	}

	private String parseRequisite()
	{
		// реквизит = строка

		return parseString();
	}

	private ServiceProviderSmsAlias findServiceProviderSmsAlias(String name)
	{
		try
		{
			return serviceProviderSmsAliasService.findSmsAliasByName(name);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

}
