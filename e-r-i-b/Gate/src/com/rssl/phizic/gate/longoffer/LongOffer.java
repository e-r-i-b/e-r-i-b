package com.rssl.phizic.gate.longoffer;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;

import java.math.BigDecimal;
import java.util.Calendar;
import java.io.Serializable;

/**
 * @author krenev
 * @ created 20.08.2010
 * @ $Author$
 * @ $Revision$
 * Информация о длительном поручении
 */
public interface LongOffer extends Serializable
{
	/**
	 * @return внешний идентификатор поручения
	 */
	String getExternalId();

	/**
	 * @return номер длительного поручения
	 */
	String getNumber();

	/**
	 * @return офис, в котором оформлено поручение.
	 */
	Office getOffice() throws GateException;

	/**
	 * @return дата начала действия поручения
	 */
	Calendar getStartDate();

	/**
	 * @return дата окончания действия поручения
	 */
	Calendar getEndDate();

	/**
	 * @return фактический тип платежей, исполняющихся в рамках поручения.
	 */
	Class<? extends GateDocument> getType();

	/**
	 * @return тип события исполнения регулярного платежа: по капитализации, ежемесячно....
	 */
	ExecutionEventType getExecutionEventType();

	/**
	 * @return тип суммы исполения регулярного платежа
	 */
	SumType getSumType();

	/**
	 * @return сумма исполнения. не может быть заполенно одновременно с getPercent
	 */
	Money getAmount();

	/**
	 * @return процент от суммы исполнения. не может быть заполенно одновременно с getAmount
	 */
	BigDecimal getPercent();

	/**
	 * @return День месяца, в который производится списание по поручению
	 */
	Long getPayDay();

	/**
	 * 0 – наивысший приоритет
	 * @return Приоритет исполнения поручения
	 */
	Long getPriority();

	/**
	 * @return имя получателя
	 */
	String getReceiverName();

	/**
	 * @return Мнемоническое имя автоплатежа
	 */
	String getFriendlyName();
	
	/**
	 * @return Пороговый лимит
	 * (поле обязательно для порогового автоплатежа)
	 */
	Money getFloorLimit();

	/**
	 * @return Лимит на общую сумму списания за определнный срок(указанный в getTotalAmountPeriod())
	 */
	Money getTotalAmountLimit();

	/**
	 * @return Период, за который считается общая сумма списания, для ограничения по лимиту
	 */
	TotalAmountPeriod getTotalAmountPeriod();

	/**
	 * @return Номер карты
	 */
	public String getCardNumber();

	/**
	 * @return Номер счета
	 */
	public String getAccountNumber();
}
