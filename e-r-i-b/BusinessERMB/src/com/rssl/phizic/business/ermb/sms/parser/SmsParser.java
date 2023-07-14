package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.business.ermb.sms.config.CommandDefinition;
import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.config.ConfigFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Erkin
 * @ created 21.11.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Синтаксический Анализатор СМС-запросов ЕРМБ
 */
class SmsParser extends ParserBase
{
	private Collection<CommandParser> commandParsers;

	private BestParseErrorCollector errorCollector = new BestParseErrorCollector();

	///////////////////////////////////////////////////////////////////////////

	void init()
	{
		SmsConfig smsConfig = ConfigFactory.getConfig(SmsConfig.class);
		List<CommandDefinition> commandDefinitions = new ArrayList<CommandDefinition>(smsConfig.getEnabledCommandDefinitions());
		//парсеры должны быть отсортированы по приоритету разбора
		Collections.sort(commandDefinitions, CommandDefinition.PARSE_ORDER_COMPARATOR);

		commandParsers = new ArrayList<CommandParser>(commandDefinitions.size());
		for (CommandDefinition commandDefinition : commandDefinitions)
		{
			CommandParser parser = createCommandParser(commandDefinition);
			commandParsers.add(parser);
		}
	}

	private CommandParser createCommandParser(CommandDefinition definition)
	{
		CommandParser parser = definition.createParser();
		parser.setModule(getModule());
		parser.setPerson(getPerson());
		parser.setPhone(getPhone());
		parser.setKeywords(definition.getKeywords());
		parser.setScanner(getScanner());
		parser.setText(getText());
		parser.setErrorCollector(errorCollector);
		return parser;
	}

	///////////////////////////////////////////////////////////////////////////

	Command parse()
	{
		// СМС-запрос состоит из комманды и всё

		// 1. Пропускаем СМС через парсеры команд
		for (CommandParser commandParser : commandParsers)
		{
			Command command = commandParser.parseCommand();

			// 1.A Парсер разобрал СМС без ошибок => завершаем разбор успехом
			if (command != null)
			{
				if (log.isDebugEnabled())
					log.debug(commandParser.getParserName() +  " прочитал сообщение");
				return command;
			}

			// 1.B Парсер "ничего не понял" либо "прочитал с ошибками" => переходим к следующей команде
			if (log.isDebugEnabled())
				log.debug(commandParser.getParserName() +  " не сумел прочесть сообщение");
		}

		// 2. Ни один из парсеров не сумел разобрать СМС
		// Возвращаем ошибку, при которой было прочитано больше всего текста
		String bestParseError = errorCollector.getBestError();
		if (bestParseError != null)
			throw new UserErrorException(new TextMessage(bestParseError));

		// 3. Возвращаем ошибку "Неизвестная СМС-команда"
		throw new UserErrorException(messageBuilder.buildUnknownCommandMessage());
	}
}
