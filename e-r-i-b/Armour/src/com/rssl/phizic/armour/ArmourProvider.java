package com.rssl.phizic.armour;

/**
 * @author Krenev
 * @ created 05.06.2008
 * @ $Author$
 * @ $Revision$
 */
public interface ArmourProvider
{
	/**
	 * получение кол-ва пользователей в лицензии
	 * @return кол-во пользователей в лицензии
	 * @throws ArmourException ошибки при работе
	 */
	long getUsersAmount() throws ArmourException;

	/**
	 * Проверить, что текущее кол-во пользователей не превышает кол-ва в лицензии
	 * @param currentAmount текущее кол-во.
	 * @return true - не превышает.
	 * @throws ArmourException ошибки при работе
	 */
	boolean isUserAmountNotExceed(long currentAmount) throws ArmourException;

	/**
	 * проверка, доступна ли лицензия
	 * @return true - доступна
	 * @throws ArmourException ошибки при работе
	 */
	boolean isLicenseExist() throws ArmourException;

	/**
	 * освобождение ресурсов
	 */
	public void release();
}
