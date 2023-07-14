package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;

/**
 * Базовый класс данных ограничений операций. Назначение: представление тройки
 * <логин, услуга, операция>, к которой привязываются уже конкретные
 * ограничения, например на счета, на карты, см. {@link ListAccountRestrictionData} и
 * {@link  ListCardRestrictionData} соответственно.
 *
 * @author Roshka
 * @ created 17.04.2006
 * @ $Author$
 * @ $Revision$
 */

public abstract class RestrictionData <T extends Restriction>
{
	private Long id;
	private Long loginId;
	private Long serviceId;
	private Long operationId;

	public Long getId()
	{
		return id;
	}

	@SuppressWarnings({"UNUSED_SYMBOL"})
	private void setId(Long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Long getOperationId()
	{
		return operationId;
	}

	public void setOperationId(Long operationId)
	{
		this.operationId = operationId;
	}

	public Long getServiceId()
	{
		return serviceId;
	}

	public void setServiceId(Long serviceId)
	{
		this.serviceId = serviceId;
	}

	/**
	 * Построить ограничение - {@link Restriction} Пользователи метода - операции.
	 * @return
	 * @throws BusinessException
	 */
	public abstract T buildRestriction() throws BusinessException;
}