package com.rssl.phizicgate.manager.services.objects;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentStatus;
import com.rssl.phizic.gate.longoffer.ExecutionEventType;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.gate.longoffer.TotalAmountPeriod;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author hudyakov
 * @ created 28.02.2011
 * @ $Author$
 * @ $Revision$
 */
public abstract class AutoPaymentBase implements AutoPayment
{
	AutoPayment delegate;

	public AutoPaymentBase(AutoPayment payment)
	{
		delegate = payment;
	}

	/**
	 * @return Номер карты
	 */
	public String getCardNumber()
	{
		return delegate.getCardNumber();
	}

	/**
	 * @return Код сервиса (маршрута)
	 */
	public String getCodeService()
	{
		return delegate.getCodeService();
	}

	public void setCodeService(String codeService)
	{
		delegate.setCodeService(codeService);
	}

	/**
	 * @return Пороговый лимит
	 * (поле обязательно для порогового автоплатежа)
	 */
	public Money getFloorLimit()
	{
		return delegate.getFloorLimit();
	}

	/**
	 * @return Мнемоническое имя автоплатежа
	 */
	public String getFriendlyName()
	{
		return delegate.getFriendlyName();
	}

	/**
	 * @return состояние автоплатжа
	 */
	public AutoPaymentStatus getReportStatus()
	{
		return delegate.getReportStatus();
	}

	/**
	 * @return Дата оформления заявки на автоплатеж
	 */
	public Calendar getDateAccepted()
	{
		return delegate.getDateAccepted();
	}

	/**
	 * @return номер лицевого счета/телефона
	 */
	public String getRequisite()
	{
		return delegate.getRequisite();
	}

	/**
	 * @return имя получателя
	 */
	public String getReceiverName()
	{
		return delegate.getReceiverName();
	}

	/**
	 * @return номер длительного поручения
	 */
	public String getNumber()
	{
		return delegate.getNumber();
	}

	/**
	 * @return офис, в котором оформлено поручение.
	 */
	public Office getOffice() throws GateException
	{
		return delegate.getOffice();
	}

	/**
	 * @return дата начала действия поручения
	 */
	public Calendar getStartDate()
	{
		return delegate.getStartDate();
	}

	/**
	 * @return дата окончания действия поручения
	 */
	public Calendar getEndDate()
	{
		return delegate.getEndDate();
	}

	/**
	 * @return фактический тип платежей, исполняющихся в рамках поручения.
	 */
	public Class<? extends GateDocument> getType()
	{
		return delegate.getType();
	}

	/**
	 * @return тип события исполнения регулярного платежа: по капитализации, ежемесячно....
	 */
	public ExecutionEventType getExecutionEventType()
	{
		return delegate.getExecutionEventType();
	}

	/**
	 * @return тип суммы исполения регулярного платежа
	 */
	public SumType getSumType()
	{
		return delegate.getSumType();
	}

	/**
	 * @return сумма исполнения. не может быть заполенно одновременно с getPercent
	 */
	public Money getAmount()
	{
		return delegate.getAmount();
	}

	/**
	 * @return процент от суммы исполнения. не может быть заполенно одновременно с getAmount
	 */
	public BigDecimal getPercent()
	{
		return delegate.getPercent();
	}

	/**
	 * @return День месяца, в который производится списание по поручению
	 */
	public Long getPayDay()
	{
		return delegate.getPayDay();
	}

	/**
	 * 0 – наивысший приоритет
	 * @return Приоритет исполнения поручения
	 */
	public Long getPriority()
	{
		return delegate.getPriority();
	}

	/**
	 * @return Номер счета
	 */
	public String getAccountNumber()
	{
		return delegate.getAccountNumber();
	}

	/**
	 * @return Лимит на общую сумму списания за определнный срок(указанный в getTotalAmountPeriod())
	 */
	public Money getTotalAmountLimit()
	{
		return delegate.getTotalAmountLimit();
	}

	/**
	 * @return Период, за который считается общая сумма списания, для ограничения по лимиту
	 */
	public TotalAmountPeriod getTotalAmountPeriod()
	{
		return delegate.getTotalAmountPeriod();
	}
}
