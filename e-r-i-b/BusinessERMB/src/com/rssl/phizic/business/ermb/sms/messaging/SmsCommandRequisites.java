package com.rssl.phizic.business.ermb.sms.messaging;

import com.rssl.phizic.auth.AuthEngine;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.business.ermb.sms.delayed.DelayedCommandService;
import com.rssl.phizic.business.ermb.sms.parser.SmsReader;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.session.PersonSession;
import com.rssl.phizic.synchronization.types.SMSRq;
import com.rssl.phizic.text.MessageComposer;
import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * @author Gulov
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реквизиты и действия исполнителя команды, которые используются при выполнении СМС-команды
 */
public class SmsCommandRequisites implements ExecutorRequisites
{
	private static final DelayedCommandService delayedCommandService = new DelayedCommandService();

	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private static final MessageBuilder messageBuilder = new MessageBuilder();

	/**
	 * СМС-запрос (never null)
	 */
	private final SMSRq xmlRequest;

	/**
	 * Модуль (never null)
	 */
	private final Module module;

	/**
	 * Сессия клиента
	 */
	private PersonSession session;

	/**
	 * Конструктор
	 * @param xmlRequest - СМС-запрос
	 * @param module - модуль
	 */
	public SmsCommandRequisites(SMSRq xmlRequest, Module module)
	{
		this.xmlRequest = xmlRequest;
		this.module = module;
	}

	public PersonSession authenticate()
	{
		AuthEngine authEngine = module.getAuthEngine();
		session = authEngine.authenticateByPhone(xmlRequest.getPhone());
		return session;
	}

	public Command createCommand()
	{
		return parseSms(xmlRequest.getText(), session.getPerson());
	}

	public Module getModule()
	{
		return module;
	}

	public String getPhone()
	{
		return PhoneNumberFormat.MOBILE_INTERANTIONAL.format(xmlRequest.getPhone());
	}

	public void handleInactiveExternalSystemException(Command command, MessageComposer messageComposer)
	{
		Long personId = session.getPerson().getId();
		// сохраняем команду в отложенные
		delayedCommandService.save(personId, command);

		SmsSenderUtils.notifyClientMessage(getPhone(), messageComposer.buildInactiveExternalSystemErrorMessage(command));
	}

	public void doAfterExecute(Command command)
	{
	}

	private Command parseSms(String smsText, Person person)
	{
		SmsReader smsReader = new SmsReader();
		smsReader.setModule(getModule());
		smsReader.setPerson(person);
		smsReader.setPhone(getPhone());

		Command command = smsReader.read(smsText);
		if (command == null)
			throw new UserErrorException(messageBuilder.buildUnknownCommandMessage());

		return command;
	}
}
