package com.rssl.phizic.business.fund.initiator;

import com.rssl.phizic.common.types.fund.ClosedReasonType;
import com.rssl.phizic.common.types.fund.Constants;
import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.gate.fund.Request;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - запрос на сбор средств
 */
public class FundRequest implements Request
{
	private Long id;
	private String externalId;
	private long loginId;
	private FundRequestState state;
	private BigDecimal requiredSum;
	private BigDecimal reccomendSum;
	private String message;
	private String resource;
	private Calendar expectedClosedDate;
	private Calendar closedDate;
	private ClosedReasonType closedReason;
	private Calendar createdDate;
	private int sendersCount;
	private FundRequestState viewState;
	private Calendar viewClosedDate;
	private ClosedReasonType viewClosedReason;
	private String initiatorPhones;

	/**
	 * @return идентификатор запроса
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор запроса
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Состоит из номера блока инициатора, разделителя '@' и внутреннего идентификатора
	 * @return внешний идентификатор запроса
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId внешний идентификатор запроса
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return логин
	 */
	public long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId логин
	 */
	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return статус запроса
	 */
	public FundRequestState getState()
	{
		return state;
	}

	/**
	 * @param state статус запроса
	 */
	public void setState(FundRequestState state)
	{
		this.state = state;
	}

	/**
	 * @return ожидаемая сумма
	 */
	public BigDecimal getRequiredSum()
	{
		return requiredSum;
	}

	/**
	 * @param requiredSum ожидаемая сумма
	 */
	public void setRequiredSum(BigDecimal requiredSum)
	{
		this.requiredSum = requiredSum;
	}

	/**
	 * @return рекомендованная сумма
	 */
	public BigDecimal getReccomendSum()
	{
		return reccomendSum;
	}

	/**
	 * @param reccomendSum рекомендованная сумма
	 */
	public void setReccomendSum(BigDecimal reccomendSum)
	{
		this.reccomendSum = reccomendSum;
	}

	/**
	 * @return сообщение отправителям
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message сообщение отправителям
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return ресурс списания
	 */
	public String getResource()
	{
		return resource;
	}

	/**
	 * @param resource ресурс списания
	 */
	public void setResource(String resource)
	{
		this.resource = resource;
	}

	/**
	 * @return ожидаемая дата закрытия запроса
	 */
	public Calendar getExpectedClosedDate()
	{
		return expectedClosedDate;
	}

	/**
	 * @param expectedClosedDate ожидаемая дата закрытия запроса
	 */
	public void setExpectedClosedDate(Calendar expectedClosedDate)
	{
		this.expectedClosedDate = expectedClosedDate;
	}

	/**
	 * @return дата закрытия
	 */
	public Calendar getClosedDate()
	{
		return closedDate;
	}

	/**
	 * @param closedDate дата закрытия
	 */
	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	/**
	 * @return причина закрытия запроса
	 */
	public ClosedReasonType getClosedReason()
	{
		return closedReason;
	}

	/**
	 * @param closedReason причина закрытия запроса
	 */
	public void setClosedReason(ClosedReasonType closedReason)
	{
		this.closedReason = closedReason;
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
	 * @return количество участников
	 */
	public int getSendersCount()
	{
		return sendersCount;
	}

	/**
	 * @param sendersCount количество участников
	 */
	public void setSendersCount(int sendersCount)
	{
		this.sendersCount = sendersCount;
	}

	/**
	 * @return выводимый клиенту статус запроса
	 */
	public FundRequestState getViewState()
	{
		return viewState;
	}

	/**
	 * @param viewState выводимый клиенту статус запроса
	 */
	public void setViewState(FundRequestState viewState)
	{
		this.viewState = viewState;
	}

	/**
	 * @return выводимая клиенту дата закрытия
	 */
	public Calendar getViewClosedDate()
	{
		return viewClosedDate;
	}

	/**
	 * @param viewClosedDate выводимая клиенту дата закрытия
	 */
	public void setViewClosedDate(Calendar viewClosedDate)
	{
		this.viewClosedDate = viewClosedDate;
	}

	/**
	 * @return выводимая клиенту причина закрытия
	 */
	public ClosedReasonType getViewClosedReason()
	{
		return viewClosedReason;
	}

	/**
	 * @param viewClosedReason выводимая клиенту причина закрытия
	 */
	public void setViewClosedReason(ClosedReasonType viewClosedReason)
	{
		this.viewClosedReason = viewClosedReason;
	}

	/**
	 * Получение номеров телефонов инициатора, разделённых через запятую
	 * @return Строка, содержащая номера телефонов через запятую
	 */
	public String getInitiatorPhones()
	{
		return initiatorPhones;
	}

	/**
	 * Инициализация номеров телефов инициатора. ВАЖНО! Номера должны быть разделены запятыми
	 * @param initiatorPhones номера телефонов, разделённых запятыми.
	 */
	public void setInitiatorPhones(String initiatorPhones)
	{
		this.initiatorPhones = initiatorPhones;
	}

	/**
	 * @return Список телефонов инициатора
	 */
	public List<String> getInitiatorPhonesAsList()
	{
		if (StringHelper.isEmpty(initiatorPhones))
		{
			return Collections.emptyList();
		}
		return Arrays.asList(initiatorPhones.split(Constants.INITIATOR_PHONES_DELIMITER));
	}
}
