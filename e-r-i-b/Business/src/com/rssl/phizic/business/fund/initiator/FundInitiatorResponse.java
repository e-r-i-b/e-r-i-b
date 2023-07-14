package com.rssl.phizic.business.fund.initiator;

import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - ответы за запросы сбора средств в блоке инициатора
 */
public class FundInitiatorResponse
{
	private Long id;
	private String externalId;
	private long requestId;
	private String phone;
	private FundResponseState state;
	private BigDecimal sum;
	private String message;
	private Calendar createdDate;
	private boolean accumulated;
	private String firstName;
	private String surName;
	private String patrName;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Состоит из номера блока инициатора, разделителя '@' и внутреннего идентификатора
	 * @return  внешний идентификатор
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId внешний идентификатор
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return идентификатор запроса на сбор средств
	 */
	public long getRequestId()
	{
		return requestId;
	}

	/**
	 * @param requestId идентификатор запроса на сбор средств
	 */
	public void setRequestId(long requestId)
	{
		this.requestId = requestId;
	}

	/**
	 * @return номер телефона
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone номер телефона
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return сумма, которую отправил клиент на счет инициатора
	 */
	public BigDecimal getSum()
	{
		return sum;
	}

	/**
	 * @param sum сумма, которую отправил клиент на счет инициатора
	 */
	public void setSum(BigDecimal sum)
	{
		this.sum = sum;
	}

	/**
	 * @return сообщение отправителя денег
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message сообщение отправителя денег
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return статус обработки запроса
	 */
	public FundResponseState getState()
	{
		return state;
	}

	/**
	 * @param state статус обработки запроса
	 */
	public void setState(FundResponseState state)
	{
		this.state = state;
	}

	/**
	 * @return дата создания
	 */
	public Calendar getCreatedDate()
	{
		return createdDate;
	}

	/**
	 * @param createdDate дата создания
	 */
	public void setCreatedDate(Calendar createdDate)
	{
		this.createdDate = createdDate;
	}

	/**
	 * @return набралась ли этим ответом общая необходимая сумма
	 */
	public boolean isAccumulated()
	{
		return accumulated;
	}

	/**
	 * @param accumulated набралась ли этим ответом общая необходимая сумма
	 */
	public void setAccumulated(boolean accumulated)
	{
		this.accumulated = accumulated;
	}

	/**
	 * @return имя отправителя денег
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName имя отправителя денег
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return фамилия отправителя денег
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * @param surName фамилия отправителя денег
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	/**
	 * @return отчество отправителя денег
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @param patrName отчество отправителя денег
	 */
	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	/**
	 * Получение полного имени пользователя для отображения по умолчанию
	 * @return Полное имя в формате: Имя Отчество Ф
	 */
	public  String getDefaultFIO()
	{
		StringBuilder defaultFIO = new StringBuilder(30);
		if (StringHelper.isNotEmpty(firstName))
		{
			defaultFIO.append(firstName);
		}
		if (StringHelper.isNotEmpty(patrName))
		{
			defaultFIO.append(" ");
			defaultFIO.append(patrName);
		}
		if (StringHelper.isNotEmpty(surName))
		{
			defaultFIO.append(" ");
			defaultFIO.append(surName.charAt(0));
		}
		return defaultFIO.toString();
	}
}
