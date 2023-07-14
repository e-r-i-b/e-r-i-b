package com.rssl.phizic.security;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.AbstractEntity;

import java.util.Date;
import java.io.Serializable;

/**
 * @author Erkin
 * @ created 08.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Данные подтверждения для сохранения в базе
 */
@PlainOldJavaObject
public class ConfirmBean extends AbstractEntity implements ConfirmToken, Serializable, Cloneable
{
	/**
	 * Должен быть равен SmsConfig.getSmsMaxLength()
	 */
	static final int CONFIRM_CODE_MAX_LENGTH = 1024;

	static final int CONFIRMABLE_TASK_BODY_MAX_SIZE = 1024;

	/**
	 * Логин-id пользователя, от лица которого производится подтверждение
	 */
	private long loginId;

	/**
	 * Основной код подтверждения
	 */
	private String primaryConfirmCode;

	/**
	 * Дополнительный код подтверждения
	 */
	private String secondaryConfirmCode;

	/**
	 * Срок окончания действия кода подтверждения
	 */
	private Date expireTime;

	/**
	 * Дата, после которой код становится просроченным
	 * До этой даты невозможна генерация этого кода заново
	 * Просроченные коды удаляются джобом
	 */
	private Date overdueTime;

	/**
	 * Класс подтверждаемой операции
	 */
	private Class<? extends ConfirmableTask> confirmableTaskClass;
	
	/**
	 * Данные подтверждаемой операции
	 */
	private String confirmableTaskBody;

	/**
	 * Телефон, на который был отправлен код подтверждения
	 */
	private String phone;

	/**
	 * Время создания бина, или получения из базы (не персистентный)
	 */
	private Date createin = new Date();

	///////////////////////////////////////////////////////////////////////////

	public long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}

	public String getPrimaryConfirmCode()
	{
		return primaryConfirmCode;
	}

	public void setPrimaryConfirmCode(String primaryConfirmCode)
	{
		this.primaryConfirmCode = primaryConfirmCode;
	}

	public String getSecondaryConfirmCode()
	{
		return secondaryConfirmCode;
	}

	public void setSecondaryConfirmCode(String secondaryConfirmCode)
	{
		this.secondaryConfirmCode = secondaryConfirmCode;
	}

	public Date getExpireTime()
	{
		return expireTime;
	}

	public void setExpireTime(Date expireTime)
	{
		this.expireTime = expireTime;
	}

	public Class<? extends ConfirmableTask> getConfirmableTaskClass()
	{
		return confirmableTaskClass;
	}

	public void setConfirmableTaskClass(Class<? extends ConfirmableTask> confirmableTaskClass)
	{
		this.confirmableTaskClass = confirmableTaskClass;
	}

	public String getConfirmableTaskBody()
	{
		return confirmableTaskBody;
	}

	public void setConfirmableTaskBody(String confirmableTaskBody)
	{
		this.confirmableTaskBody = confirmableTaskBody;
	}

	public boolean isExpired()
	{
		return createin.after(expireTime);
	}

	public Date getOverdueTime()
	{
		return overdueTime;
	}

	public void setOverdueTime(Date overdueTime)
	{
		this.overdueTime = overdueTime;
	}

	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	@Override
	protected ConfirmBean clone()
	{
		try
		{
			return (ConfirmBean) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			// Один из базовых классов выбросил CloneNotSupportedException
			throw new InternalErrorException("Не удалось склонировать confirm-бин " + this, e);
		}
	}

	@Override
	public String toString()
	{
		return loginId + "|" + "***" + "|" + confirmableTaskClass + "|" + confirmableTaskBody;
	}
}
