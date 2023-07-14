package com.rssl.phizic.security;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.rssl.phizic.auth.sms.SmsPasswordService;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonManagerBase;
import org.apache.commons.lang.time.DateUtils;

import java.util.Date;

import static com.rssl.phizic.security.ConfirmBean.CONFIRMABLE_TASK_BODY_MAX_SIZE;

/**
 * @author Erkin
 * @ created 06.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовая реализация персонального менеджера подтверждений
 */
public class PersonConfirmManagerImpl extends PersonManagerBase implements PersonConfirmManager
{
	protected static final int GENERATE_CODE_MAX_ATTEMPTS = 10;

	protected static final ConfirmBeanService confirmBeanService = new ConfirmBeanService();

	private static final Gson gson = ConfirmGsonSingleton.getGson();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param module - модуль
	 * @param person - клиент
	 */
	public PersonConfirmManagerImpl(Module module, Person person)
	{
		super(module, person);
	}

	public void askForConfirm(ConfirmableTask confirmableTask)
	{
		askForConfirm(confirmableTask, getPersonSmsMessanger().getDefaultPhone());
	}

	public void askForConfirm(ConfirmableTask confirmableTask, String phoneNumber)
	{
		// 1. Генерируем код подтверждения
		ConfirmCodeMessage confirmCodeMessage = createConfirmCode(confirmableTask, phoneNumber);

		// 2. Передаём клиенту код подтверждения
		getPersonSmsMessanger().sendSms(confirmCodeMessage, phoneNumber);

		// 3. Просим клиента ввести код подтверждения
		getPersonInteractManager().askForConfirmCode();
	}

	protected ConfirmCodeMessage createConfirmCode(ConfirmableTask confirmableTask, String phoneNumber)
	{
		ConfirmBean confirmBean = createConfirmBean(confirmableTask);
		confirmBean.setPhone(phoneNumber);

		String confirmCode = null;
		for (int i=0; i < GENERATE_CODE_MAX_ATTEMPTS; i++)
		{
			confirmCode = generateConfirmCode();
			confirmBean.setPrimaryConfirmCode(confirmCode);
			confirmBean.setSecondaryConfirmCode(confirmCode);

			if (confirmBeanService.addConfirmBean(confirmBean))
				break;

			confirmCode = null;
			confirmBean.setPrimaryConfirmCode(null);
		}

		if (confirmCode == null)
		{
			// Если сюда пришли, значит, все попытки генерации пароля закончились ConstraintViolationException
			throw new InternalErrorException("Не удалось сгенерировать код подтверждения для " + confirmBean);
		}

		return buildConfirmCodeMessage(confirmCode, confirmableTask);
	}

	protected ConfirmBean createConfirmBean(ConfirmableTask confirmableTask)
	{
		Date now = new Date();
		Person person = getPerson();

		String confirmableTaskBody = gson.toJson(confirmableTask);
		if (confirmableTaskBody.length() > CONFIRMABLE_TASK_BODY_MAX_SIZE)
			throw new InternalErrorException("Превышен макс.размер confirmableTaskBody: " + confirmableTaskBody);
		ErmbConfig config = ConfigFactory.getConfig(ErmbConfig.class);
		Date expireTime = DateUtils.addSeconds(now, config.getConfirmBeanSecondsActive());
		Date overdueTime = DateUtils.addSeconds(now, config.getConfirmBeanTimeToLife());

		ConfirmBean bean = new ConfirmBean();
		bean.setLoginId(person.getLogin().getId());
		bean.setConfirmableTaskClass(confirmableTask.getClass());
		bean.setConfirmableTaskBody(confirmableTaskBody);
		bean.setExpireTime(expireTime);
		bean.setOverdueTime(overdueTime);

		return bean;
	}

	protected String generateConfirmCode()
	{
		try
		{
			return SmsPasswordService.generatePassword();
		}
		catch (SecurityDbException e)
		{
			throw new InternalErrorException("Не удалось сгенерировать код подтверждения", e);
		}
	}

	protected ConfirmCodeMessage buildConfirmCodeMessage(String confirmCode, ConfirmableTask confirmableTask)
	{
		return getMessageComposer().buildConfirmCodeMessage(confirmCode, confirmableTask);
	}

	///////////////////////////////////////////////////////////////////////////
	
	public ConfirmToken captureConfirm(String confirmCode, String phone, boolean primary)
	{
		ConfirmBean confirmBean = confirmBeanService.captureConfirmBean(getPerson(), confirmCode, phone, primary);
		if (confirmBean == null)
			return null;
		// Если confirnm-бин устарел, считаем, что код подтверждения не найден
		if (confirmBean.isExpired())
			return null;
		return confirmBean;
	}

	public boolean similarConfirmCodeExists(String confirmCode, String phone)
	{
		return confirmBeanService.similarConfirmBeanExists(getPerson(), confirmCode, phone);
	}

	///////////////////////////////////////////////////////////////////////////

	public void grantConfirm(ConfirmToken confirmToken)
	{
		ConfirmBean confirmBean = (ConfirmBean) confirmToken;
		ConfirmableTask confirmableTask = restoreConfirmableTask(confirmBean);

		if (confirmableTask == null)
			return;

		confirmableTask.setModule(getModule());
		confirmableTask.setPerson(getPerson());
		confirmableTask.confirmGranted();
	}

	private ConfirmableTask restoreConfirmableTask(ConfirmBean confirmBean)
	{
		try
		{
			Class<? extends ConfirmableTask> taskClass = confirmBean.getConfirmableTaskClass();
			//Для кодов подтверждений пришедших из МБК taskClass, и taskBody не передаются
			if (taskClass == null)
				return null;

			String taskBody = confirmBean.getConfirmableTaskBody();
			return gson.fromJson(taskBody, taskClass);
		}
		catch (JsonSyntaxException e)
		{
			throw new InternalErrorException("Не удалось прочитать confirm-бин: " + confirmBean, e);
		}
	}
}
