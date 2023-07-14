package com.rssl.phizic.business.finances.processed;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 10.07.14
 * @ $Author$
 * @ $Revision$
 *
 * Логины владельцев заявок на получение карточных операций из ИПС, обрабатываемых в текущий момент.
 * Сущность необходима для обеспечения невозможности выполнять заявки одного клиента в разных потоках(или на разных СП).
 */
public class ProcessedCardOperationClaimLogin
{
	private Long loginId;
	private Calendar processingDate;

	/**
	 * Пустой конструктор, для нужд Hibernate
	 */
	public ProcessedCardOperationClaimLogin()
	{
	}

	/**
	 * Конструктор
	 * @param loginId - идентификатор логина
	 */
	public ProcessedCardOperationClaimLogin(Long loginId)
	{
		this.loginId = loginId;
		this.processingDate = Calendar.getInstance();
	}

	/**
	 * @return идентификатор логина
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	/**
	 * Установить идентификатор логина
	 * @param loginId идентификатор логина
	 */
	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return дата начала обработки заявок клиента
	 */
	public Calendar getProcessingDate()
	{
		return processingDate;
	}

	/**
	 * Установить дату начала обработки заявок клиента
	 * @param processingDate дата начала обработки заявок клиента
	 */
	public void setProcessingDate(Calendar processingDate)
	{
		this.processingDate = processingDate;
	}
}
