package com.rssl.phizic.operations.ext.sbrf.strategy;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.auth.sms.NoActiveSmsPasswordException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.loanclaim.ukoClaim.ConfirmPhoneCallback;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.logging.confirm.OperationConfirmLogConfig;
import com.rssl.phizic.messaging.IKFLMessagingLogicException;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Gulov
 * Date: 03.03.2015
 * Time: 13:26:03
 */

/**
 * Стратегия потверждения заявки на кредит для гостевого входа смс паролем
 * Перед появлением всплывающего окна подтверждения, генерится пароль и сохраняется в заявке.
 * Клиент отправляет смс на номер 900 сообщение вида - БКИ-12345.
 * Джоб CardOfferRefusesJob обрабаытвает это сообщение и отправляет клиенту сообщение с паролем.
 * Клиент вводит в окно пароль и подтерждает заявку. Пароль, который ввел клиент сравнивается с тем, который сохранился в заявке.
 */
public class GuestLoanClaimConfirmStrategy extends SmsPasswordConfirmStrategy
{
	private static final BusinessDocumentService documentService = new BusinessDocumentService();

	public PreConfirmObject preConfirmActions(final CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException
	{
		final ConfirmableObject confirmableObject = callBackHandler.getConfimableObject();
		String returnMessage = null;
		Map<String,Object> res = new HashMap<String,Object>();
		try
		{
			//Подтверждение обратного звонка для гостя с неподключенным МБ
			if (confirmableObject instanceof ConfirmPhoneCallback)
				super.preConfirmActions(callBackHandler);
			//Подтверждение расширенной заявки для гостя
			else if (confirmableObject instanceof ExtendedLoanClaim)
			{
				// чтобы в случае ошибки в RSAlarm пароль не сохранился в базе
				if (mobileBankExists())
				{
					returnMessage = HibernateExecutor.getInstance().execute(new HibernateAction<String>()
					{
						public String run(Session session) throws Exception
						{
							return createExtLoanCliamPassword(callBackHandler);
						}
					});
					res.put(CLIENT_SEND_MESSAGE_KEY, returnMessage);
				}
				else
				{
					CallBackHandlerSmsImpl callBackHandlerSms = (CallBackHandlerSmsImpl) callBackHandler;
					ExtendedLoanClaim claim = (ExtendedLoanClaim) confirmableObject;
					callBackHandlerSms.setPhoneNumber(claim.getOwnerGuestPhone());
					callBackHandlerSms.setOperationType(OperationType.CALLBACK_BCI_CONFIRM_OPERATION);
					super.preConfirmActions(callBackHandler);
					res.put(NAME_FIELD_LIFE_TIME, getLifeTimePassword());
					res.put(CLIENT_SEND_MESSAGE_KEY, returnMessage);
				}
			};
		}
		catch (SmsPasswordExistException ex)
		{
			throw new SecurityLogicException("Повторная отправка сообщения возможна через " + ex.getLeftTime() + " сек.", ex);
		}
		catch (IKFLMessagingLogicException e)
		{
			//инвалидируем сохраненный пароль, чтобы клиент не угадывал.
			invalidatePassword(callBackHandler.getConfimableObject());
			throw new SecurityLogicException(e.getMessage(), e);
		}
		catch (InactiveExternalSystemException e)
		{
			//инвалидируем сохраненный пароль, чтобы клиент не угадывал.
			invalidatePassword(callBackHandler.getConfimableObject());
			throw new SecurityLogicException(e.getMessage(), e);
		}
		catch (Exception ex)
		{
			log.error(ex.getMessage(),ex);
			//инвалидируем сохраненный пароль, чтобы клиент не угадывал.
			invalidatePassword(callBackHandler.getConfimableObject());
			throw new SecurityLogicException("Не удалось отправить пароль. Вам необходимо обратиться в Банк или повторить попытку позднее", ex);
		}
		return new PreConfirmObject(res);
	}

	private String createExtLoanCliamPassword(final CallBackHandler callBackHandler) throws SecurityDbException,SecurityLogicException,BusinessException
	{
		ConfirmableObject confirmableObject = callBackHandler.getConfimableObject();
		try
		{
			String passString = createPassword(confirmableObject);
			ExtendedLoanClaim claim = (ExtendedLoanClaim) confirmableObject;
			claim.setConfirmPassword(passString);
			claim.setBkiConfirmDate(Calendar.getInstance());
			claim.setActionSign(ExtendedLoanClaim.CONFIRM_GUEST_ACTION);
			new DbDocumentTarget().save(claim);
			return "";
		}
		catch (SmsPasswordExistException ignore)
		{
			return "";
		}
	}

	public ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException
	{
		if (!mobileBankExists())
			return super.validate(login, request, response);
		if (!login.isMobileBankConnected())
			return super.validate(login, request, response);

		if (request == null)
			throw new SecurityException("Не установлен запрос на подтверждение");

		if (response == null)
			throw new SecurityException("Не установлен ответ на подтверждение");

		if (!(getPasswordRequestClass().isInstance(request)))
			throw new SecurityException("некорректный тип ConfirmRequest, ожидался SmsPasswordConfirmRequest");

		OneTimePasswordConfirmResponse res = (OneTimePasswordConfirmResponse) response;
		OneTimePasswordConfirmRequest req = (OneTimePasswordConfirmRequest) request;
		req.setErrorFieldPassword(false);
		req.resetMessages();

		String oneTimePassword = restorePassword(req.getConfirmableObject().getId());
		String confirmCode = res.getPassword();

		if (oneTimePassword == null)
		{
			req.setRequredNewPassword(true);
			ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmFailed(getConfirmType(), confirmCode);
			throw new NoActiveSmsPasswordException("Срок действия пароля истек, либо Вы неправильно ввели пароль.");
		}

		synchronized (oneTimePassword)
		{
			if (!oneTimePassword.equals(res.getPassword()))
				throw new SecurityLogicException("Неверно введен пароль.");
			invalidatePassword(req.getConfirmableObject());
		}
		ConfigFactory.getConfig(OperationConfirmLogConfig.class).getWriter().confirmSuccess(getConfirmType(), confirmCode);
		return new ConfirmStrategyResult(false);
	}

	private String restorePassword(Long id) throws SecurityException
	{
		ExtendedLoanClaim claim = null;
		try
		{
			claim = (ExtendedLoanClaim) documentService.findById(id);
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
		return claim.getConfirmPassword();
	}

	protected void invalidatePassword(ConfirmableObject confirmableObject)
	{
		if (!mobileBankExists())
		{
			super.invalidatePassword(confirmableObject);
			return;
		}
		ExtendedLoanClaim claim = null;
		try
		{
			if (confirmableObject instanceof ExtendedLoanClaim)
			{
				claim = (ExtendedLoanClaim) confirmableObject;
				claim.setConfirmPassword("");
				new DbDocumentTarget().save(claim);
			}
			else if (confirmableObject instanceof ConfirmPhoneCallback)
				super.invalidatePassword(confirmableObject);
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
	}

	private boolean mobileBankExists()
	{
		return PersonContext.getPersonDataProvider().getPersonData().isMB();
	}
}