package com.rssl.phizic.csaadmin.business.login;

import com.rssl.phizic.csaadmin.business.access.AccessScheme;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Логин
 */

public class Login
{
	private Long id;
	private String name;
	private String password;
	private AccessScheme accessScheme;
	private Long nodeId;
	private Calendar lastUpdateDate;
	private boolean deleted;
	private long wrongLoginAttempts;
	private Calendar passwordExpireDate;
	private Calendar lastLogonDate;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * логин
	 * @return логин
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать логин
	 * @param name логин
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return пароль
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * задать новый пароль
	 * @param password пароль
	 */
	public void setNewPassword(String password)
	{
		CryptoService cryptoService = SecurityFactory.cryptoService();
		this.password = cryptoService.hash(password);
	}


	/**
	 * @return схема прав
	 */
	public AccessScheme getAccessScheme()
	{
		return accessScheme;
	}

	/**
	 * задать схему прав
	 * @param accessScheme схема прав
	 */
	public void setAccessScheme(AccessScheme accessScheme)
	{
		this.accessScheme = accessScheme;
	}

	/**
	 * @return идентификатор блока
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * задать идентификатор блока
	 * @param nodeId идентификатор блока
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return дата последнего обновления
	 */
	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	/**
	 * Установить дату последнего обновления
	 * @param lastUpdateDate - дата
	 */
	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return признак удаленности логина
	 */
	public boolean isDeleted()
	{
		return deleted;
	}

	/**
	 * задать признак удаленности логина
	 * @param deleted признак удаленности логина
	 */
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

	/**
	 * @return количество неверных попыток входа
	 */
	public long getWrongLoginAttempts()
	{
		return wrongLoginAttempts;
	}

	/**
	 * @param wrongLoginAttempts - количество неверных попыток входа
	 */
	public void setWrongLoginAttempts(long wrongLoginAttempts)
	{
		this.wrongLoginAttempts = wrongLoginAttempts;
	}

	/**
	 * Увеличивает количество неверных попыток входа на 1
	 */
	public void incWrongLoginAttempts()
	{
		this.wrongLoginAttempts++;
	}

	/**
	 * @return дата устаревания пароля
	 */
	public Calendar getPasswordExpireDate()
	{
		return passwordExpireDate;
	}

	/**
	 * @param passwordExpireDate - дата устаревания пароля
	 */
	public void setPasswordExpireDate(Calendar passwordExpireDate)
	{
		this.passwordExpireDate = passwordExpireDate;
	}

	/**
	 * @return необходимо ли сменить пароль (true - необходимо)
	 */
	public boolean needChangePassword()
	{
		return !Calendar.getInstance().before(passwordExpireDate);
	}

	/**
	 * Установка даты последнего входа по логину
	 * @param lastLogonDate дата последнего входа
	 */
	public void setLastLogonDate(Calendar lastLogonDate)
	{
		this.lastLogonDate = lastLogonDate;
	}

	/**
	 * @return дата последнего входа
	 */
	public Calendar getLastLogonDate()
	{
		return lastLogonDate;
	}
}
