package com.rssl.phizic.security.password;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 21:17:33
 *
 * Одноразовый пароль
 */
public class OneTimePassword
{
	private final String hash;
	private final Calendar expireDate;
	private int   wrongAttempts;
	private final String entityType;
	private final Long entityId;

	/**
	 * конструктор
	 * @param expireDate дата окончания действия пароля
	 * @param hash       хеш пароля
	 * @param entityType тип подтверждаемой сущности
	 * @param entityId   идентификатор подтверждаемой сущности
	 */
	public OneTimePassword(Calendar expireDate, String hash, String entityType, Long entityId)
	{
		this.expireDate = expireDate;
		this.hash    = hash;
		this.wrongAttempts = 0;
		this.entityType = entityType;
		this.entityId = entityId;
	}

	/**
	 * @return дата окончания действия пароля
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	/**
	 * @return хеш пароля
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @return количество ошибок
	 */
	public int getWrongAttempts()
	{
		return wrongAttempts;
	}

	/**
	 * увеличить количество ошибок
	 */
	public void incWrongAttempts()
	{
		wrongAttempts++;
	}

	/**
	 * @return тип подтверждаемой сущности
	 */
	public String getEntityType()
	{
		return entityType;
	}

	/**
	 * @return идентификатор подтверждаемой сущности
	 */
	public Long getEntityId()
	{
		return entityId;
	}
}
