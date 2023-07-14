package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;

/**
 * @author Gulov
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class TemplateCommandParser extends CommandParserBase
{
	public Command parseCommand()
	{
		// Ўаблоны = ФЎјЅЋќЌџФ | ФЎјЅЋќЌФ | Фѕќ–”„≈Ќ»≈Ф | Фѕќ–”„≈Ќ»яФ | ФSHABLONФ | ФSHABLONYФ | ФPORUTCHENIEФ | ФPORUTCHENIYAФ
		//         | получательФ?Ф

		Command command = parseTemplatListCommandV1();

		if (command == null)
			command = parseTemplatListCommandV2();

		return command;
	}

	private Command parseTemplatListCommandV1()
	{
		// Ўаблоны = ФЎјЅЋќЌџФ | ФЎјЅЋќЌФ | Фѕќ–”„≈Ќ»≈Ф | Фѕќ–”„≈Ќ»яФ | ФSHABLONФ | ФSHABLONYФ | ФPORUTCHENIEФ | ФPORUTCHENIYAФ

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		if (parseKeywordTemplate() && parseEOF())
		{
			// SUCCESS. ”казано ключевое слово
			completeLexeme(tx);
			return commandFactory.createTemplateCommand(null);
		}

		// FAIL-A. ¬стречена друга€ —ћ—-команда
		cancelLexeme(tx);
		return null;
	}

	private Command parseTemplatListCommandV2()
	{
		// Ўаблоны = получательФ?Ф

		ParseTransaction tx = beginLexeme(Lexeme.COMMAND);

		String recipient = parseRecipient();
		if (recipient != null && parseSample("?"))
		{
			if (parseEOF())
			{
				// SUCCESS. ”казан получатель
			    completeLexeme(tx);
				return commandFactory.createTemplateCommand(recipient);
			}

			// FAIL-B. ѕосле получател€ идЄт кака€-то ересь
			cancelLexeme(tx);
			return null;
		}

		// FAIL-A. ¬стречена друга€ —ћ—-команда
		cancelLexeme(tx);
		return null;
	}

	private String parseRecipient()
	{
		// ѕолучатель = алиас
		return parseAlias();
	}

	private boolean parseKeywordTemplate()
	{
		return parseCommandKeyword(getKeywords());
	}
}
