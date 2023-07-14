package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.NonThreadSafe;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;

/**
 * @author Erkin
 * @ created 26.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Парсер для СМС-команды
 */
@Statefull
@NonThreadSafe
public interface CommandParser
{
	/**
	 * @return имя парсера
	 * Используется в логировании
	 */
	String getParserName();

	/**
	 * @param module - модуль
	 */
	@MandatoryParameter
	void setModule(Module module);

	/**
	 * @param person - клиент
	 */
	@MandatoryParameter
	void setPerson(Person person);

	/*
	 * @param phone - телефон, с которого пришла команда
	 */
	@MandatoryParameter
	void setPhone(String phone);

	/**
	 * @param keywords - словарь с ключевыми словами СМС-команды
	 */
	@MandatoryParameter
	void setKeywords(Dictionary keywords);

	@MandatoryParameter
	void setScanner(Scanner scanner);

	/**
	 * @param text - текст СМС (до начала обработки)
	 */
	@MandatoryParameter
	void setText(String text);

	@MandatoryParameter
	void setErrorCollector(ParseErrorCollector parseErrorCollector);

	/**
	 * @return СМС-команда или null, если парсер не смог прочитать СМС-команду
	 */
	Command parseCommand();
}
