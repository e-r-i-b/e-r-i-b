package com.rssl.phizic.security;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.messaging.OperationType;

/**
 * @author eMakarov
 * @ created 01.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface CallBackHandler
{
	public void setPassword(String str);

	public String getPassword();

	public void setLogin(Login login);

	public Login getLogin();

	public void setConfirmableObject(ConfirmableObject confirmableObject);

	public ConfirmableObject getConfimableObject();

	/**
	 * @return сообщение пользователю
	 */
	public String proceed() throws Exception;

	public void setAdditionalCheck();

	/**
	 * Установить тип операции
	 * @param operationType тип операции
	 */
	public void setOperationType(OperationType operationType);

	/**
	 * @param useRecipientMobilePhoneOnly - флажок "для отправки использовать только телефон в анкете клиента"
	 */
	void setUseRecipientMobilePhoneOnly(boolean useRecipientMobilePhoneOnly);

	/**
	 * @param needWarningMessage отправлять ли сообщение с предупреждением безопасности
	 */
	public void setNeedWarningMessage(boolean needWarningMessage);

	/**
	 * @param useAlternativeRegistrations использовать альтернативный способ получения регистраций
	 */
	public void setUseAlternativeRegistrations(boolean useAlternativeRegistrations);
}
