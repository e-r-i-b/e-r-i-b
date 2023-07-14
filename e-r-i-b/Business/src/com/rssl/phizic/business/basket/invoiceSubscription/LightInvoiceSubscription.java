package com.rssl.phizic.business.basket.invoiceSubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.basket.Constants;
import com.rssl.phizic.common.types.basket.InvoiceSubscriptionState;

/**
 * @author osminin
 * @ created 16.06.14
 * @ $Author$
 * @ $Revision$
 *
 * Лайт версия сущности "Подписка"
 * Используется, если нужно получить данные по подписке, кроме реквизитов, тк реквизиты хранятся как CLOB
 */
public class LightInvoiceSubscription
{
	private long id;
	private String name;
	private Long recipientId;
	private String baseState;
	private String autoSubExternalId;

	/**
	 * @return идентфикатор
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id идентфикатор
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	/**
	 * @return наименование услуги
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name наименование услуги
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return статус услуги
	 */
	public InvoiceSubscriptionState getState()
	{
		if (baseState.contains(Constants.STATE_DELIMITER))
		{
			return InvoiceSubscriptionState.WAIT;
		}
		return InvoiceSubscriptionState.valueOf(baseState);
	}

	/**
	 * @return идентификатор поставщика
	 */
	public Long getRecipientId()
	{
		return recipientId;
	}

	/**
	 * @param recipientId идентификатор поставщика
	 */
	public void setRecipientId(Long recipientId)
	{
		this.recipientId = recipientId;
	}

	/**
	 * @return строковый статус
	 */
	public String getBaseState()
	{
		return baseState;
	}

	/**
	 * @param baseState строковый статус
	 */
	public void setBaseState(String baseState)
	{
		this.baseState = baseState;
	}

	/**
	 * @return статус, в который переходит подписка
	 * @throws BusinessException
	 */
	public InvoiceSubscriptionState getNextState() throws BusinessException
	{
		return InvoiceSubscription.getNextState(baseState);
	}

	/**
	 * @return внешний идентификатор связазнной автоподписик
	 */
	public String getAutoSubExternalId()
	{
		return autoSubExternalId;
	}

	/**
	 * Установить внешний идентификатор связазнной автоподписик
	 * @param autoSubExternalId внешний идентификатор
	 */
	public void setAutoSubExternalId(String autoSubExternalId)
	{
		this.autoSubExternalId = autoSubExternalId;
	}
}
