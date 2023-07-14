package com.rssl.phizic.business.dictionaries.kbk;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class KBK implements DictionaryRecord
{
	private Long id;                    // идентификатор
	private String code;                // код
	private String description;         // описание
	private String appointment;         // типовое назначение платежа
	private PaymentType paymentType;    // тип оплаты
	private Money minCommission;        // минимальная сумма коммиссии
	private Money maxCommission;        // максимальная сумма коммиссии
	private BigDecimal rate;            // процентная ставка
	private String shortName;           // короткое название

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getAppointment()
	{
		return appointment;
	}

	public void setAppointment(String appointment)
	{
		this.appointment = appointment;
	}

	public PaymentType getPaymentType()
	{
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType)
	{
		this.paymentType = paymentType;
	}

	public Money getMinCommission()
	{
		return minCommission;
	}

	public void setMinCommission(Money minCommission)
	{
		this.minCommission = minCommission;
	}

	public Money getMaxCommission()
	{
		return maxCommission;
	}

	public void setMaxCommission(Money maxCommission)
	{
		this.maxCommission = maxCommission;
	}

	public BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}

	public Comparable getSynchKey()
	{
		return code;
	}

	public void updateFrom(DictionaryRecord that)
	{
		KBK source = (KBK) that;
		this.description = source.description;
	}

	public String getShortName()
	{
		return shortName;
	}

	public void setShortName(String shortName)
	{
		this.shortName = shortName;
	}
}
