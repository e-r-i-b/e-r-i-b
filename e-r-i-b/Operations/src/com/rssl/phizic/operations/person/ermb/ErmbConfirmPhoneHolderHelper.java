package com.rssl.phizic.operations.person.ermb;

import com.rssl.phizic.auth.sms.SmsPasswordService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.ermb.ErmbClientPhone;
import com.rssl.phizic.business.ermb.ErmbClientPhoneService;
import com.rssl.phizic.business.ermb.ErmbPhoneNotFoundException;
import com.rssl.phizic.business.ermb.ErmbTmpPhone;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.messaging.IKFLMessage;
import com.rssl.phizic.messaging.MessageComposer;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.ext.sbrf.sms.SendSmsByPhoneMethod;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.password.OneTimePassword;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 18.06.2013
 * Time: 15:25:38
 * Хелпер для работы с подтверждением номера телефона, в контексте ЕРМБ
 */
public class ErmbConfirmPhoneHolderHelper
{
	private final static ErmbClientPhoneService phoneService = new ErmbClientPhoneService();
	private final MessageComposer messageComposer = new MessageComposer();
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * Отправить код подтверждения на номе
	 * @param phoneNumber Номер телефона на который высылаем
	 * @param translitMode Режим транслитерации сообщений
	 */
	public void sendConfirmCode(String phoneNumber, TranslitMode translitMode) throws BusinessException
	{
		try
		{
			//1.Ищем не создавалось ли уже сущность по пришедшему номеру
			ErmbTmpPhone tmpPhone = phoneService.findTempPhoneByNumber(phoneNumber);
			if (tmpPhone == null)
			{
				//Создаем тмп ерм номер(ConfirmableObject)
				tmpPhone = new ErmbTmpPhone(phoneNumber);
				simpleService.addOrUpdate(tmpPhone);
			}
			//2. гинерируем пароль
			String password = createSmsPassword(tmpPhone);
			//3. отправляем смс на переданный номер
			IKFLMessage message = messageComposer.buildASFilialConfirmHolderMessage(password);
			SendSmsByPhoneMethod smsMethod = new SendSmsByPhoneMethod(null, phoneNumber, translitMode, message);
			smsMethod.send(message.getText(), message.getTextToLog(), message.getPriority());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
		catch (IKFLMessagingException e)
		{
			throw new BusinessException(e);
		}
		catch (RuntimeException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Отправить код подтверждения на номе
	 * @param phoneNumber Номер телефона на который высылаем
	 * @param translitMode Режим транслитерации сообщений
	 * @param expiredDate Время жизни пароля либо null (тогда время жизни возьмётся из настроек смс)
	 */
	public void sendConfirmCodeOfflineDoc(String phoneNumber, TranslitMode translitMode,Calendar expiredDate) throws BusinessException
	{
		try
		{
			//2. гинерируем пароль
			String password = SmsPasswordService.createSmsPassword(expiredDate, phoneNumber);
			//3. отправляем смс на переданный номер
			IKFLMessage message = messageComposer.buildASFilialConfirmHolderMessage(password);
			SendSmsByPhoneMethod smsMethod = new SendSmsByPhoneMethod(null, phoneNumber, translitMode, message);
			smsMethod.send(message.getText(), message.getTextToLog(), message.getPriority());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
		catch (IKFLMessagingException e)
		{
			throw new BusinessException(e);
		}
		catch (RuntimeException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Удалить телефон, предназначенный  для смены держателя
	 * @param swapPhoneNumber Номер который подлежит удалению
	 * @param confirmCode смс проль можно null если doCheck = false
	 */
	public void removeSwapClientPhone(String swapPhoneNumber, String confirmCode) throws ErmbPhoneNotFoundException, BusinessException, SecurityLogicException
	{
		try
		{
			ErmbClientPhone phone = phoneService.findPhoneByNumber(swapPhoneNumber);
			if (phone == null)
				throw new ErmbPhoneNotFoundException("Не удалось найти телефон ЕРМБ для смены держателя");

			if (!SmsPasswordService.checkPasswordOfflineDoc(confirmCode, swapPhoneNumber))
				//confirmCode не верен
				throw new SecurityLogicException("Неверно введен код подтверждения");

			phoneService.removeWhithProfileCheck(phone);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param confirmCode Код подтверждения
	 * @param swapPhoneNumber Номер на для которого меняем владельца 
	 * @return Если проверка прошла успешно true
	 * @throws BusinessException
	 */
	public boolean testSwapPhoneNumberCodeOfflineDoc(String confirmCode,String swapPhoneNumber) throws  BusinessException
	{
		try
		{
			return SmsPasswordService.checkPasswordOfflineDoc(confirmCode, swapPhoneNumber);
		}
		catch (SecurityDbException e)
		{
			throw new  BusinessException(e);
		}
	}

	/**
	 * @param confirmCode Код подтверждения
	 * @param phoneNumber Номер на для которого меняем владельца
	 * @return Если проверка прошла успешно true
	 * @throws BusinessException
	 */
	public boolean testSwapPhoneNumberCode(String confirmCode, String phoneNumber) throws  BusinessException
	{
		ErmbTmpPhone phone = phoneService.findTempPhoneByNumber(phoneNumber);
		if (phone == null)
			return false;

		OneTimePassword password = getSmsPassword(phone);
		if (password == null)
			return false;

		synchronized (password)
		{
			if (SmsPasswordService.checkPassword(password, confirmCode))
			{
				invalidatePassword(phone);
				return true;
			}

			password.incWrongAttempts();
			if (password.getWrongAttempts() > SmsPasswordService.getWrongAttemptsCount())
				invalidatePassword(phone);

			return false;
		}
	}

	private static String createSmsPassword(final ErmbTmpPhone phone) throws SecurityDbException
	{
		String password = SmsPasswordService.generatePassword();
		OneTimePassword smsPassword = SmsPasswordService.createSmsPassword(password, phone);
		StoreManager.getCurrentStore().save(getStoreKey(), smsPassword);
		return password;
	}

	private static OneTimePassword getSmsPassword(final ErmbTmpPhone phone)
	{
		Store store = StoreManager.getCurrentStore();
		synchronized (store.getSyncObject())
		{
			OneTimePassword password = (OneTimePassword) store.restore(getStoreKey());
			if (password == null)
			{
				return null;
			}
			//Пароль от другой сущности.
			if (!password.getEntityId().equals(phone.getId()))
			{
				return null;
			}
			//Пароль от другого типа сущности.
			if (!password.getEntityType().equals(phone.getClass().getName()))
			{
				return null;
			}
			//Пароль от этой сущности. а активен ли он?
			if (password.getExpireDate().after(Calendar.getInstance()))
			{
				//активен
				return password;
			}
			//раз неактивен, грохнем его
			store.remove(getStoreKey());
			return null;
		}
	}

	/**
	 * инвалидировать пароль.
	 * @param phone объект для которого создавался пароль.
	 */
	protected void invalidatePassword(ErmbTmpPhone phone)
	{
		if (phone == null)
		{
			throw new IllegalArgumentException("Подтверждаемый объект не может быть null");
		}
		Store store = StoreManager.getCurrentStore();
		synchronized (store.getSyncObject())
		{
			OneTimePassword password = (OneTimePassword)store.restore(getStoreKey());
			if (password == null)
			{
				return;
			}
			//Пароль от другой сущности.
			if (!password.getEntityId().equals(phone.getId()))
			{
				return;
			}
			//Пароль от другого типа сущности.
			if (!password.getEntityType().equals(phone.getClass().getName()))
			{
				return;
			}
			store.remove(getStoreKey());
		}
	}

	private static String getStoreKey()
	{
		return ErmbConfirmPhoneHolderHelper.class.getName();
	}
}
