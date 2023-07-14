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
 * ������ ��� ������ � �������������� ������ ��������, � ��������� ����
 */
public class ErmbConfirmPhoneHolderHelper
{
	private final static ErmbClientPhoneService phoneService = new ErmbClientPhoneService();
	private final MessageComposer messageComposer = new MessageComposer();
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ��������� ��� ������������� �� ����
	 * @param phoneNumber ����� �������� �� ������� ��������
	 * @param translitMode ����� �������������� ���������
	 */
	public void sendConfirmCode(String phoneNumber, TranslitMode translitMode) throws BusinessException
	{
		try
		{
			//1.���� �� ����������� �� ��� �������� �� ���������� ������
			ErmbTmpPhone tmpPhone = phoneService.findTempPhoneByNumber(phoneNumber);
			if (tmpPhone == null)
			{
				//������� ��� ��� �����(ConfirmableObject)
				tmpPhone = new ErmbTmpPhone(phoneNumber);
				simpleService.addOrUpdate(tmpPhone);
			}
			//2. ���������� ������
			String password = createSmsPassword(tmpPhone);
			//3. ���������� ��� �� ���������� �����
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
	 * ��������� ��� ������������� �� ����
	 * @param phoneNumber ����� �������� �� ������� ��������
	 * @param translitMode ����� �������������� ���������
	 * @param expiredDate ����� ����� ������ ���� null (����� ����� ����� �������� �� �������� ���)
	 */
	public void sendConfirmCodeOfflineDoc(String phoneNumber, TranslitMode translitMode,Calendar expiredDate) throws BusinessException
	{
		try
		{
			//2. ���������� ������
			String password = SmsPasswordService.createSmsPassword(expiredDate, phoneNumber);
			//3. ���������� ��� �� ���������� �����
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
	 * ������� �������, ���������������  ��� ����� ���������
	 * @param swapPhoneNumber ����� ������� �������� ��������
	 * @param confirmCode ��� ����� ����� null ���� doCheck = false
	 */
	public void removeSwapClientPhone(String swapPhoneNumber, String confirmCode) throws ErmbPhoneNotFoundException, BusinessException, SecurityLogicException
	{
		try
		{
			ErmbClientPhone phone = phoneService.findPhoneByNumber(swapPhoneNumber);
			if (phone == null)
				throw new ErmbPhoneNotFoundException("�� ������� ����� ������� ���� ��� ����� ���������");

			if (!SmsPasswordService.checkPasswordOfflineDoc(confirmCode, swapPhoneNumber))
				//confirmCode �� �����
				throw new SecurityLogicException("������� ������ ��� �������������");

			phoneService.removeWhithProfileCheck(phone);
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param confirmCode ��� �������������
	 * @param swapPhoneNumber ����� �� ��� �������� ������ ��������� 
	 * @return ���� �������� ������ ������� true
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
	 * @param confirmCode ��� �������������
	 * @param phoneNumber ����� �� ��� �������� ������ ���������
	 * @return ���� �������� ������ ������� true
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
			//������ �� ������ ��������.
			if (!password.getEntityId().equals(phone.getId()))
			{
				return null;
			}
			//������ �� ������� ���� ��������.
			if (!password.getEntityType().equals(phone.getClass().getName()))
			{
				return null;
			}
			//������ �� ���� ��������. � ������� �� ��?
			if (password.getExpireDate().after(Calendar.getInstance()))
			{
				//�������
				return password;
			}
			//��� ���������, ������� ���
			store.remove(getStoreKey());
			return null;
		}
	}

	/**
	 * �������������� ������.
	 * @param phone ������ ��� �������� ���������� ������.
	 */
	protected void invalidatePassword(ErmbTmpPhone phone)
	{
		if (phone == null)
		{
			throw new IllegalArgumentException("�������������� ������ �� ����� ���� null");
		}
		Store store = StoreManager.getCurrentStore();
		synchronized (store.getSyncObject())
		{
			OneTimePassword password = (OneTimePassword)store.restore(getStoreKey());
			if (password == null)
			{
				return;
			}
			//������ �� ������ ��������.
			if (!password.getEntityId().equals(phone.getId()))
			{
				return;
			}
			//������ �� ������� ���� ��������.
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
