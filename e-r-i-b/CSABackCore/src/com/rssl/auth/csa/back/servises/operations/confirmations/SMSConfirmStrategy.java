package com.rssl.auth.csa.back.servises.operations.confirmations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.exceptions.IllegalOperationStateException;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageInfo;
import com.rssl.auth.csa.back.integration.mobilebank.SendMessageRouter;
import com.rssl.auth.csa.back.messages.MessageInfoImpl;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.operations.CSASmsResourcesOperation;
import com.rssl.auth.csa.back.servises.operations.ConfirmableOperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.mobilebank.GetRegistrationMode;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author akrenev
 * @ created 08.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * стратегия подтверждения смс-паролем
 */

public class SMSConfirmStrategy implements ConfirmStrategy<DisposablePassword, SMSConfirmationInfo>
{
	private DisposablePassword password;
	protected String code;
	protected String cardNumber;
	protected boolean useAlternativeRegistrationsMode;
	protected boolean isCheckIMSI;
	private String ouid;
	private String appName;
	private Class<? extends ConfirmableOperationBase> operationClass;
	private Set<String> excludePhones;

	/**
	 *
	 * @param ouid идентификатор операции (для логов)
	 * @param operationClass класс вызывающей операции (для получения текста смс)
	 * @param cardNumber номер карты (для отправки смс)
	 * @param useAlternativeRegistrationsMode использовать ли альтернативные способы поиска получателя (для отправки смс)
	 * @param checkIMSI использовать ли проверку IMSI (для отправки смс)
	 * @param password объект пароля (для валидации)
	 */
	public SMSConfirmStrategy(String ouid, Class<? extends ConfirmableOperationBase> operationClass, String cardNumber, boolean useAlternativeRegistrationsMode, boolean checkIMSI, DisposablePassword password, String appName)
	{
		this.ouid = ouid;
		this.password = password;
		this.cardNumber = cardNumber;
		this.useAlternativeRegistrationsMode = useAlternativeRegistrationsMode;
		isCheckIMSI = checkIMSI;
		this.operationClass = operationClass;
		this.appName = appName;
	}

	public void initialize() throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		int length = ConfigFactory.getConfig(Config.class).getConfirmCodeLength();
		String allowedChars = ConfigFactory.getConfig(Config.class).getConfirmCodeAllowedChars();
		code = RandomHelper.rand(length, allowedChars);
		password = new DisposablePassword(code);
	}

	public DisposablePassword getConfirmCodeInfo()
	{
		return password;
	}

	public void publishCode() throws Exception
	{
		String text;
		String textToLog;
		String stubText;
		String name = getOperationClass().getName();
		if (name.equals("com.rssl.auth.csa.back.servises.operations.MobileRegistrationOperation") || name.equals("com.rssl.auth.csa.back.servises.operations.SocialRegistrationOperation"))
		{
			Map<String, String> params = new HashMap<String, String>();
			params.put("{applicationName}", appName);
			params.put("{0}", code);
			text = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", params);

			Map<String, String> paramsToLog = new HashMap<String, String>();
			paramsToLog.put("{applicationName}", appName);
			paramsToLog.put("{0}", CSASmsResourcesOperation.PASSWORD_MASK);
			textToLog = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", paramsToLog);

			stubText = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".stubText");
		}
		else
		{
			text = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", code);
			textToLog = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".confirm", CSASmsResourcesOperation.PASSWORD_MASK);
			stubText = CSASmsResourcesOperation.getFormattedSmsResourcesText(name + ".stubText");
		}

		MessageInfoImpl     messageInfo      = new MessageInfoImpl(text, textToLog, stubText);
		GetRegistrationMode registrationMode = useAlternativeRegistrationsMode ? GetRegistrationMode.BOTH : GetRegistrationMode.SOLID;
		SendMessageInfo sendMessageInfo = new SendMessageInfo(cardNumber, messageInfo, isCheckIMSI, registrationMode, excludePhones);

		SendMessageRouter.getInstance().sendMessage(sendMessageInfo);
	}

	public void checkConfirmAllowed()
	{
		if (isFailed() || getConfirmTimeOut() <= 0)
			throw new IllegalOperationStateException("Операция " + ouid + " не может быть подтверждена.\n " +
					"Количество оставшихся попыток: " + getLeftAttempts() + "\n Таймаут ожидания: " + getConfirmTimeOut());
	}

	public boolean check(String code) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		if (code == null)
			throw new IllegalStateException("Код подтверждения не сгенерирован");

		boolean checkResult = password.check(code);
		if (!checkResult)
			password.incErrorCount();
		return checkResult;
	}

	public boolean isFailed()
	{
		return getLeftAttempts() == 0;
	}

	public SMSConfirmationInfo getConfirmationInfo()
	{
		return new SMSConfirmationInfo(getLeftAttempts(), getConfirmTimeOut());
	}

	protected Class<? extends ConfirmableOperationBase> getOperationClass()
	{
		return operationClass;
	}

	private long getLeftAttempts()
	{
		return getConfirmAtemptsCount() - password.getConfirmErrors();
	}

	private int getConfirmAtemptsCount()
	{
		return ConfigFactory.getConfig(Config.class).getConfirmationAttemptsCount();
	}

	/**
	 *  @return Таймаут ожидания кода подтверждения
	 */
	private long getConfirmTimeOut()
	{
		if (password == null)
			throw new IllegalStateException("Код подтверждения не сгенерирован");

		Long result = ConfirmableOperationBase.getCongirmationLifeTime() - DateHelper.diff(ActiveRecord.getCurrentDate(), password.getCreationDate()) / 1000;
		if (result < 1)
			return 0L;
		return result;
	}

	public Set<String> getExcludePhones()
	{
		return excludePhones;
	}

	public void setExcludePhones(Set<String> excludePhones)
	{
		this.excludePhones = excludePhones;
	}
}
