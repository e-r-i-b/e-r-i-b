package com.rssl.phizic.gate.login;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Гейтовый интерфейс логина
 */

public interface Login
{
	/**
	 * @return идентификатор
	 */
	public Long getId();

	/**
	 * задать логин
	 * @param userId логин
	 */
	public void setUserId(String userId);

	/**
	 * @return идентификатор
	 */
	public String getUserId();

	/**
	 * задать пароль
	 * @param password пароль
	 */
	public void setPassword(String password);

	/**
	 * @return пароль
	 */
	public String getPassword();

	/**
	 * @return список блокировок
	 */
	public List<LoginBlock> getBlocks();

	/**
	 * @return дата последней синхронизации данных
	 */
	public Calendar getLastSynchronizationDate();
}
