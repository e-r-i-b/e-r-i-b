package com.rssl.phizic.auth.modes;

import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.sms.NoActiveSmsPasswordException;
import com.rssl.phizic.auth.sms.SmsPasswordService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.confirm.ConfirmType;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.IKFLMessagingLogicException;
import com.rssl.phizic.security.*;
import com.rssl.phizic.security.password.OneTimePassword;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ������� ��������� �������������
 * @author basharin
 * @ created 16.10.13
 * @ $Author$
 * @ $Revision$
 */

public abstract class OneTimePasswordConfirmStrategy implements ConfirmStrategy
{
	public static final String CLIENT_SEND_MESSAGE_KEY = "SEND_INFO";
	public static final String NAME_FIELD_LIFE_TIME = "smsPasswordLifeTime";
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected Exception warning;

	public OneTimePasswordConfirmStrategy()
	{
	}

	protected abstract Class<? extends OneTimePasswordConfirmRequest> getPasswordRequestClass();

	protected abstract ConfirmType getConfirmType();

	protected abstract String getPasswordKey();

	public Exception getWarning()
	{
		return warning;
	}

	public void setWarning(Exception warning)
	{
		this.warning = warning;
	}

	/**
	 * @return ��������� ���������� ��������� ������� �������������
	 */
	protected boolean checkPassword(OneTimePassword oneTimePassword, OneTimePasswordConfirmResponse res)
	{
		return !SmsPasswordService.checkPassword(oneTimePassword, new String(res.getPassword()));
	}

	/**
	 * ���������� �������, ������� ����� ��������� � ������ DocumentSignature
	 * @return true=����������
	 */
	public boolean hasSignature()
	{
		return false;
	}

	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	/**
	 * @return ��������� ���������� ��������� ������� �������������
	 */
	public static long getWrongAttemptsCount()
	{
		return SmsPasswordService.getWrongAttemptsCount();
	}

	/**
	 * @return ��������� ������� ����� ������
	 */
	public static long getLifeTimePassword()
	{
		return SmsPasswordService.getLifeTime();
	}

	/**
	 * �������� SignatureCreator
	 */
	public DocumentSignature createSignature(ConfirmRequest request, SignatureConfirmResponse confirmResponse) throws SecurityLogicException, SecurityException
	{
		throw new UnsupportedOperationException();
	}

	public boolean filter(Map<ConfirmStrategyType, List<StrategyCondition>> conditions, String userChoice, ConfirmableObject object)
	{
		ConfirmStrategyType type = getType();
		List<StrategyCondition> currentConditions = conditions.get(type);
		//���� �� ������ ������� ���������� ���������, �� ������ ������ �� ���������
		if (currentConditions == null)
			return true;

		for (StrategyCondition condition : currentConditions)
		{
			//�������� ������������� �������
			if (!condition.checkCondition(object))
			{
				//���� ������� �� �����������, �� �������
				//  �������� ���� �������������� ������� ���������� ���������� �������������,
				//  ���� ����, ���������� �������������� � ���������.
				String message = condition.getWarning();
				setWarning(message == null ? null : new SecurityLogicException(message));
				return false;
			}
		}
		return true;
	}

	public void reset(CommonLogin login, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		invalidatePassword(confirmableObject);
	}

	public PreConfirmObject preConfirmActions(final CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
	{
		final ConfirmableObject confirmableObject = callBackHandler.getConfimableObject();
		String returnMessage = null;
		try
		{
			// ����� � ������ ������ � RSAlarm ������ �� ���������� � ����
			returnMessage = HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					String passString = createPassword(confirmableObject);
					callBackHandler.setPassword(passString);
					return callBackHandler.proceed();
				}
			});
		}
		catch (SmsPasswordExistException ex)
		{
			throw new SecurityLogicException("��������� �������� ��������� �������� ����� " + ex.getLeftTime() + " ���.", ex);
		}
		catch (IKFLMessagingLogicException e)
		{
			//������������ ����������� ������, ����� ������ �� ��������.
			invalidatePassword(callBackHandler.getConfimableObject());
			//���� ��� ��������� �� ������� ���������� � ���
			throw new SecurityLogicException(e.getMessage(), e);
		}
		catch (InactiveExternalSystemException e)
		{
			//������������ ����������� ������, ����� ������ �� ��������.
			invalidatePassword(callBackHandler.getConfimableObject());
			throw new SecurityLogicException(e.getMessage(), e);
		}
		catch (Exception ex)
		{
			log.error(ex.getMessage(),ex);
			//������������ ����������� ������, ����� ������ �� ��������.
			invalidatePassword(callBackHandler.getConfimableObject());
			throw new SecurityLogicException("�� ������� ��������� ������. ��� ���������� ���������� � ���� ��� ��������� ������� �������", ex);
		}
		if (StringHelper.isEmpty(returnMessage))
			return new PreConfirmObject(NAME_FIELD_LIFE_TIME, getLifeTimePassword());
		else
		{
			Map<String,Object> res = new HashMap<String,Object>();
			res.put(NAME_FIELD_LIFE_TIME, getLifeTimePassword());
			res.put(CLIENT_SEND_MESSAGE_KEY, returnMessage);
			return new PreConfirmObject(res);
		}
	}

	/**
	 * ��������� �����
	 * @param login ����� ��� �������� ����������� ��������
	 * @param request ������
	 * @param response �����
	 * @throws com.rssl.phizic.security.SecurityLogicException �������� ����� (������, ������� etc)
	 */
	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException
	{
		if (request == null)
			throw new SecurityException("�� ���������� ������ �� �������������");

		if (response == null)
			throw new SecurityException("�� ���������� ����� �� �������������");

		if (!(getPasswordRequestClass().isInstance(request)))
			throw new SecurityException("������������ ��� ConfirmRequest, �������� SmsPasswordConfirmRequest");

		OneTimePasswordConfirmResponse res = (OneTimePasswordConfirmResponse) response;
		OneTimePasswordConfirmRequest req = (OneTimePasswordConfirmRequest) request;
		req.setErrorFieldPassword(false);
		req.resetMessages();

		OneTimePassword oneTimePassword = restorePassword(req.getConfirmableObject(), getPasswordKey());
		String confirmCode = res.getPassword();

		if (oneTimePassword == null)
		{
			req.setRequredNewPassword(true);
			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmTimeout(getConfirmType(), confirmCode);
			throw new NoActiveSmsPasswordException("���� �������� ������ �����, " +
					"���� �� ��������� ��� ����������� ����� ������. �������� ����� ������, ����� �� ������ " + getNameButton() + ".");
		}

		synchronized (oneTimePassword)
		{
			if (checkPassword(oneTimePassword, res))
			{
				oneTimePassword.incWrongAttempts();
				long lastAttemptsCount = getWrongAttemptsCount() - oneTimePassword.getWrongAttempts();
				if (lastAttemptsCount < 1)
				{
					invalidatePassword(req.getConfirmableObject());
					req.setRequredNewPassword(true);
					ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmFailed(getConfirmType(), confirmCode);
					throw new SecurityLogicException("���� �������� ������ �����, " +
							"���� �� ��������� ��� ����������� ����� ������. �������� ����� ������, ����� �� ������ " + getNameButton() + ".");
				}
				req.setErrorFieldPassword(true);
				ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmFailed(getConfirmType(), confirmCode);
				throw new SmsErrorLogicException("������� ������ ������. �������� �������: " + lastAttemptsCount, lastAttemptsCount);
			}
			invalidatePassword(req.getConfirmableObject());
		}
		ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmSuccess(getConfirmType(), confirmCode);
		return new ConfirmStrategyResult(false);
	}

	protected abstract String getNameButton();

	/**
	 * �������� �������� ������ �� ���������
	 * @param confirmableObject ������, ��� �������� ��� �������� ������.
	 * @return ������ ��� null, ���� ���������� �������� ������.
	 */
	public static OneTimePassword restorePassword(ConfirmableObject confirmableObject, String passwordKey)
	{
		if (confirmableObject == null)
		{
			throw new IllegalArgumentException("�������������� ������ �� ����� ���� null");
		}
		Store store = StoreManager.getCurrentStore();
		synchronized (store.getSyncObject())
		{
			OneTimePassword password = (OneTimePassword) store.restore(passwordKey);
			if (password == null)
			{
				return null;
			}
			//������ �� ������ ��������.
			if (!password.getEntityId().equals(confirmableObject.getId()))
			{
				return null;
			}
			//������ �� ������� ���� ��������.
			if (!password.getEntityType().equals(confirmableObject.getClass().getName()))
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
			store.remove(passwordKey);
			return null;
		}
	}

	/**
	 * ��������� ������ � ���������.
	 * ����������� ������ 1 �������� ������ �� �������
	 * @param oneTimePassword ������.
	 */
	private void storePassword(OneTimePassword oneTimePassword)
	{
		if (oneTimePassword == null)
		{
			throw new IllegalArgumentException("������ �� ����� ���� null");
		}
		Store store = StoreManager.getCurrentStore();
		synchronized (store.getSyncObject())
		{
			store.save(getPasswordKey(), oneTimePassword);
		}
	}

	protected String createPassword(ConfirmableObject confirmableObject) throws SecurityDbException, SecurityLogicException
	{
		OneTimePassword oneTimePassword = restorePassword(confirmableObject, getPasswordKey());
		if (oneTimePassword != null)
		{
			throw new SmsPasswordExistException((oneTimePassword.getExpireDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000);
		}
		try
		{
			String password = SmsPasswordService.generatePassword();
			storePassword(SmsPasswordService.createSmsPassword(password, confirmableObject));
			return password;
		}
		catch (SecurityDbException e)
		{
			log.error(e.getMessage(), e);
			throw new SecurityLogicException("�� ������� ��������� ������. ��� ���������� ���������� � ���� ��� ��������� ������� �������", e);
		}
	}

	/**
	 * �������������� ������.
	 * @param confirmableObject ������ ��� �������� ���������� ������.
	 */
	protected void invalidatePassword(ConfirmableObject confirmableObject)
	{
		if (confirmableObject == null)
		{
			throw new IllegalArgumentException("�������������� ������ �� ����� ���� null");
		}
		Store store = StoreManager.getCurrentStore();
		synchronized (store.getSyncObject())
		{
			OneTimePassword password = (OneTimePassword)store.restore(getPasswordKey());
			if (password == null)
			{
				return;
			}
			//������ �� ������ ��������.
			if (!password.getEntityId().equals(confirmableObject.getId()))
			{
				return;
			}
			//������ �� ������� ���� ��������.
			if (!password.getEntityType().equals(confirmableObject.getClass().getName()))
			{
				return;
			}
			store.remove(getPasswordKey());
		}
	}
}
