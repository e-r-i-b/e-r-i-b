package com.rssl.phizic.business.fund.sender;

import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.gate.fund.Response;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - ответ отправителя на сбор средств в блоке отправителя
 */
public class FundSenderResponse implements Response
{
	private String firstName;
	private String surName;
	private String patrName;
	private Calendar birthDate;
	private String tb;
	private String passport;
	private String externalId;
	private String externalRequestId;
	private FundResponseState state;
	private BigDecimal sum;
	private String message;
	private Long paymentId;
	private String initiatorFirstName;
	private String initiatorSurName;
	private String initiatorPatrName;
	private Calendar initiatorBirthDate;
	private String initiatorTb;
	private String initiatorPassport;
	private String initiatorPhones;
	private String requestMessage;
	private FundRequestState requestState;
	private BigDecimal requiredSum;
	private BigDecimal reccomendSum;
	private String toResource;
	private Calendar closedDate;
	private Calendar expectedClosedDate;
	private Calendar createdDate;
	private FundRequestState viewRequestState;
	private Calendar viewClosedDate;

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
	 * @return дата рождения отправителя денег
	 */
	public Calendar getBirthDate()
	{
		return birthDate;
	}

	/**
	 * @param birthDate дата рождения отправителя денег
	 */
	public void setBirthDate(Calendar birthDate)
	{
		this.birthDate = birthDate;
	}

	/**
	 * @return тербанк отправителя денег
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb тербанк отправителя денег
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ДУЛ отправителя денег
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * @param passport ДУЛ отправителя денег
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * @return внешний идентификатор ответа
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId внешний идентификатор ответа
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return внешний идентификатор запроса
	 */
	public String getExternalRequestId()
	{
		return externalRequestId;
	}

	/**
	 * @param externalRequestId внешний идентификатор запроса
	 */
	public void setExternalRequestId(String externalRequestId)
	{
		this.externalRequestId = externalRequestId;
	}

	/**
	 * @return статус обработки запроса на сбор средств
	 */
	public FundResponseState getState()
	{
		return state;
	}

	/**
	 * @param state статус обработки запроса на сбор средств
	 */
	public void setState(FundResponseState state)
	{
		this.state = state;
	}

	/**
	 * @return сумма перевода
	 */
	public BigDecimal getSum()
	{
		return sum;
	}

	/**
	 * @param sum сумма перевода
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
	 * @return идентификатор перевода денег
	 */
	public Long getPaymentId()
	{
		return paymentId;
	}

	/**
	 * @param paymentId идентификатор перевода денег
	 */
	public void setPaymentId(Long paymentId)
	{
		this.paymentId = paymentId;
	}

	/**
	 * @return имя инициатора запроса
	 */
	public String getInitiatorFirstName()
	{
		return initiatorFirstName;
	}

	/**
	 * @param initiatorFirstName имя инициатора запроса
	 */
	public void setInitiatorFirstName(String initiatorFirstName)
	{
		this.initiatorFirstName = initiatorFirstName;
	}

	/**
	 * @return фамилия инициатора запроса
	 */
	public String getInitiatorSurName()
	{
		return initiatorSurName;
	}

	/**
	 * @param initiatorSurName фамилия инициатора запроса
	 */
	public void setInitiatorSurName(String initiatorSurName)
	{
		this.initiatorSurName = initiatorSurName;
	}

	/**
	 * @return отчество инициатора запроса
	 */
	public String getInitiatorPatrName()
	{
		return initiatorPatrName;
	}

	/**
	 * @param initiatorPatrName отчество инициатора запроса
	 */
	public void setInitiatorPatrName(String initiatorPatrName)
	{
		this.initiatorPatrName = initiatorPatrName;
	}

	/**
	 * @return дата рождения инициатора
	 */
	public Calendar getInitiatorBirthDate()
	{
		return initiatorBirthDate;
	}

	/**
	 * @param initiatorBirthDate дата рождения инициатора
	 */
	public void setInitiatorBirthDate(Calendar initiatorBirthDate)
	{
		this.initiatorBirthDate = initiatorBirthDate;
	}

	/**
	 * @return номер тербанка инициатора
	 */
	public String getInitiatorTb()
	{
		return initiatorTb;
	}

	/**
	 * @param initiatorTb номер тербанка инициатора
	 */
	public void setInitiatorTb(String initiatorTb)
	{
		this.initiatorTb = initiatorTb;
	}

	/**
	 * @return ДУЛ инициатора
	 */
	public String getInitiatorPassport()
	{
		return initiatorPassport;
	}

	/**
	 * @param initiatorPassport ДУЛ инициатора
	 */
	public void setInitiatorPassport(String initiatorPassport)
	{
		this.initiatorPassport = initiatorPassport;
	}

	/**
	 * @return телефоны инициатора запроса через разделитель ","
	 */
	public String getInitiatorPhones()
	{
		return initiatorPhones;
	}

	/**
	 * Получение массива, содержащего номера телефонов инициатора запроса
	 * @return  Массив из номеров телефонов
	 */
	public String[] getListInitiatorPhones()
	{
		return initiatorPhones.split(",");
	}

	/**
	 * @param initiatorPhones телефоны инициатора запроса через разделитель ","
	 */
	public void setInitiatorPhones(String initiatorPhones)
	{
		this.initiatorPhones = initiatorPhones;
	}

	/**
	 * @return сообщений отправителям
	 */
	public String getRequestMessage()
	{
		return requestMessage;
	}

	/**
	 * @param requestMessage сообщений отправителям
	 */
	public void setRequestMessage(String requestMessage)
	{
		this.requestMessage = requestMessage;
	}

	/**
	 * @return статус запроса на сбор средств
	 */
	public FundRequestState getRequestState()
	{
		return requestState;
	}

	/**
	 * @param requestState статус запроса на сбор средств
	 */
	public void setRequestState(FundRequestState requestState)
	{
		this.requestState = requestState;
	}

	/**
	 * @return необходимая общая сумма
	 */
	public BigDecimal getRequiredSum()
	{
		return requiredSum;
	}

	/**
	 * @param requiredSum необходимая общая сумма
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
	 * @return ресурс зачисления
	 */
	public String getToResource()
	{
		return toResource;
	}

	/**
	 * @param toResource ресурс зачисления
	 */
	public void setToResource(String toResource)
	{
		this.toResource = toResource;
	}

	/**
	 * @return дата закрытия запроса на сбор средств
	 */
	public Calendar getClosedDate()
	{
		return closedDate;
	}

	/**
	 * @param closedDate дата закрытия запроса на сбор средств
	 */
	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	/**
	 * @return плановая дата закрытия запроса на сбор средств
	 */
	public Calendar getExpectedClosedDate()
	{
		return expectedClosedDate;
	}

	/**
	 * @param expectedClosedDate плановая дата закрытия запроса на сбор средств
	 */
	public void setExpectedClosedDate(Calendar expectedClosedDate)
	{
		this.expectedClosedDate = expectedClosedDate;
	}

	/**
	 * @return дата создания запроса на сбор средств
	 */
	public Calendar getCreatedDate()
	{
		return createdDate;
	}

	/**
	 * @param createdDate дата создания запроса на сбор средств
	 */
	public void setCreatedDate(Calendar createdDate)
	{
		this.createdDate = createdDate;
	}

	/**
	 * @return выводимый клиенту статус запроса на сбор средств
	 */
	public FundRequestState getViewRequestState()
	{
		return viewRequestState;
	}

	/**
	 * @param viewRequestState выводимый клиенту статус запроса на сбор средств
	 */
	public void setViewRequestState(FundRequestState viewRequestState)
	{
		this.viewRequestState = viewRequestState;
	}

	/**
	 * @return выводимая клиенту дата закрытия запроса на сбор средств
	 */
	public Calendar getViewClosedDate()
	{
		return viewClosedDate;
	}

	/**
	 * @param viewClosedDate выводимая клиенту дата закрытия запроса на сбор средств
	 */
	public void setViewClosedDate(Calendar viewClosedDate)
	{
		this.viewClosedDate = viewClosedDate;
	}

	/**
	 * Получение полного имени инициатора запроса для отображения по умолчанию
	 * @return Полное имя в формате: Имя Отчество Ф, если отчества нет, то Имя Ф
	 */
	public  String getInitiatorFIO(){
		return initiatorFirstName + " " + (StringHelper.isNotEmpty(initiatorPatrName)? initiatorPatrName + " " : "") + initiatorSurName.charAt(0);
	}

	/**
	 * Получение полного имени отправителя денег для отображения по умолчанию
	 * @return Полное имя в формате: Имя Отчество Ф, если отчества нет, то Имя Ф
	 */
	public  String getFIO(){
		return firstName + " " + (StringHelper.isNotEmpty(patrName)? patrName + " " : "") + surName.charAt(0);
	}
}
