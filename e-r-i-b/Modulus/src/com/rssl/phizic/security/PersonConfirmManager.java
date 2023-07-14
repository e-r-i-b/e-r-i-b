package com.rssl.phizic.security;

/**
 * @author Erkin
 * @ created 25.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Персональный менеджер подтверждений
 */
public interface PersonConfirmManager
{
	/**
	 * Запросить подтверждение для указанной задачи
	 * @param confirmableTask - задача, которую нужно подтвердить
	 */
	void askForConfirm(ConfirmableTask confirmableTask);

	/**
	 * Запросить подтверждение для указанной задачи
	 * @param confirmableTask - задача, которую нужно подтвердить
	 * @param phoneNumber - номер телефона для подтверждения
	 */
	void askForConfirm(ConfirmableTask confirmableTask, String phoneNumber);

	/**
	 * Найти и заблокировать токен подтверждения
	 * @param confirmCode - код подтверждения
	 * @param phone телефон, на который отсылался код подтверждения
	 * @param primary true - основной код (набор цифр), false - дополнительный (полный текст смс)
	 * @return токен подтверждения или null, если токен не найден
	 */
	ConfirmToken captureConfirm(String confirmCode, String phone, boolean primary);

	/**
	 * Проверить, существует ли код подтверждения, который "похож" на присланный (получается из присланного добавлением одного символа)
	 * @param confirmCode - код подтверждения
	 * @param phone телефон, на который отсылался код подтверждения
	 * @return
	 */
	boolean similarConfirmCodeExists(String confirmCode, String phone);

	/**
	 * Выдать подтверждение по указанному токену
	 * @param confirmToken - токен подтверждения (never null)
	 */
	void grantConfirm(ConfirmToken confirmToken);
}
