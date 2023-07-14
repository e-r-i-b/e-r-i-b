package com.rssl.auth.security;

import java.util.Calendar;

/**
 * Класс описывающий время входа пользователя. Преднаначен для класса SessionSecurityManagerImp
 * @author usachev
 * @ created 17.02.15
 * @ $Author$
 * @ $Revision$
 */
public class StatusDescriptionUserSecurityManager
{
	private Calendar lastAccessTime;
	private boolean isTrustUser;
	/**
	 * Получение времени последнего входа пользователя как доверенного
	 * @return Время последнего входа
	 */
	public Calendar getLastAccessTime()
	{
		return lastAccessTime;
	}

	/**
	 * Установка времени последнего входа пользователя как доверенного
	 * @param lastAccessTime  Время
	 */
	public void setLastAccessTime(Calendar lastAccessTime)
	{
		this.lastAccessTime = lastAccessTime;
	}


	/**
	 * Есть ли время последнего входа, как доверенным пользователем
	 * @return Да, если есть время. Нет, в противном случае.
	 */
	public boolean hasLastTimeVisit(){
		return lastAccessTime != null;
	}

	/**
	 * Проверка, доверяем ли мы пользователю или нет
	 * @return Да, если доверяем. Нет в противном случае
	 */
	public boolean isTrustUser()
	{
		return isTrustUser;
	}

	/**
	 * Установка флага доверенности к пользователю.
	 * @param trustUser Да, если доверяем. Нет в противном случае.
	 */
	public void setTrustUser(boolean trustUser)
	{
		isTrustUser = trustUser;
	}
}
