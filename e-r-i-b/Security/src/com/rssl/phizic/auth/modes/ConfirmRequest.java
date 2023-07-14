package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.io.Serializable;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: moshenko $
 * @ $Revision: 43580 $
 */

public interface ConfirmRequest extends Serializable
{
	/**
	 * @return тип стратегии
	 */
	ConfirmStrategyType getStrategyType();

	/**
	 * @return запрос с ошибкой
	 */
	boolean isError();

	/**
	 * @return ошибка при вводе пароля 	
	 */
	boolean isErrorFieldPassword();

	/**
	 * @param error - в поле пароль произошла ошибка.
	 */
	void setErrorFieldPassword(boolean error);
	
	/**
	 * @return выполнены ли действия необходимые перед подтверждением
	 */
	boolean isPreConfirm();

	/**
	 * @param isPreConfirm - выполнены ли действия необходимые перед подтверждением
	 */
	void setPreConfirm(boolean isPreConfirm);

	/**
	 * @return сообщение об ошибке
	 */
	String getErrorMessage();

	/**
	 * @param errorMessage - сообщение об ошибке
	 */
	void setErrorMessage(String errorMessage);

	/**
	 * @return список информационных сообщений
	 */
	List<String> getMessages();

	/**
	 * @param message - информационное сообщение
	 */
	void addMessage(String message);

	/**
	 * @return дополнительная информация
	 */
	List<String> getAdditionInfo();

	/**
	 * очистить списки сообщений
	 */
	public void resetMessages();
}