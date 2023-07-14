package com.rssl.phizic.security;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.payment.BlockingCardTaskImpl;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 23.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Реализация менеджера подтверждений для СМС-канала ЕРМБ
 *  
 * Генерирует не один, а два confirm-бина:
 * Первый в поле "код подтверждения" содержит собственно код подтверждения
 * Второй в поле "код подтверждения" содержит текст СМС-сообщения с кодом подтверждения
 * Последний нужен в связи с условием:
 * - сообщение с кодом подтверждения, высланное клиенту, также является кодом подтверждения
 */
public class SmsPersonConfirmManager extends PersonConfirmManagerImpl
{
	private final OneTimePasswordGeneratorSmsChannel passwordGenerator = new OneTimePasswordGeneratorSmsChannel();

	/**
	 * ctor
	 * @param module - модуль
	 * @param person - клиент
	 */
	public SmsPersonConfirmManager(Module module, Person person)
	{
		super(module, person);
	}

	@Override
	protected ConfirmCodeMessage createConfirmCode(final ConfirmableTask confirmableTask, String phoneNumber)
	{
		ConfirmBean confirmBean = createConfirmBean(confirmableTask);
		confirmBean.setPhone(phoneNumber);

		ConfirmCodeMessage confirmCodeMessage = null;
		for (int i=0; i < GENERATE_CODE_MAX_ATTEMPTS; i++)
		{
			String confirmCode = passwordGenerator.generate();
			confirmCodeMessage = buildConfirmCodeMessage(confirmCode, confirmableTask);
			String confirmText = confirmCodeMessage.getText();
			//Текст смс обязательно должен содержать код подтверждения, т.к. на это завязан механизм подтверждения в смс-канале.
			//Связано с требованием:
			//"от клиента могут приходить подтверждения операций – SMS-сообщения, полностью повторяющие сообщения, отправленные из банка или содержащие код подтверждения"
			if (confirmText == null || !confirmText.contains(confirmCode))
				throw new InternalErrorException("Смс с кодом подтверждения не содержит самого кода подтверждения");

			confirmBean.setPrimaryConfirmCode(confirmCode);
			confirmBean.setSecondaryConfirmCode(confirmText);

			if (confirmBeanService.addConfirmBean(confirmBean))
				break;

			confirmBean.setPrimaryConfirmCode(null);
			confirmBean.setSecondaryConfirmCode(null);
			confirmCodeMessage = null;
		}

		if (confirmCodeMessage == null)
		{
			// Если сюда пришли, значит, все попытки генерации пароля закончились ConstraintViolationException
			throw new InternalErrorException("Не удалось сгенерировать код подтверждения для " + confirmBean);
		}

		return confirmCodeMessage;
	}

	public ConfirmToken captureConfirm(String confirmCode, String phone, boolean primary)
	{
		ConfirmBean confirmBean = confirmBeanService.captureConfirmBean(getPerson(), confirmCode, phone, primary);
		if (confirmBean == null)
			return null;

		boolean isBlockingCard = StringHelper.equals(confirmBean.getConfirmableTaskClass().getName(), BlockingCardTaskImpl.class.getName());
		// Если confirm-бин устарел, считаем, что код подтверждения не найден, кроме блокировки карт, их проверяем выше
		if (confirmBean.isExpired() && !isBlockingCard)
			return null;

		return confirmBean;
	}
}
