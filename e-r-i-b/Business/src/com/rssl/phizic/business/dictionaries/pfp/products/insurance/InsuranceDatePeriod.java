package com.rssl.phizic.business.dictionaries.pfp.products.insurance;

import com.rssl.common.forms.validators.IntegerIntervalsHelper;
import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Money;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 * Информация о периоде страхового продукта
 */
public class InsuranceDatePeriod
{
	private static final IntegerIntervalsHelper INTERVALS_HELPER = new IntegerIntervalsHelper();

	private Long id;               //идентификатор
	private Boolean defaultPeriod; //тип периода
	private PeriodType type;       //тип периода
	private BigDecimal minSum;     //минимальная сумма
	private BigDecimal maxSum;     //максимальная сумма
	private String period;         // период

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Boolean getDefaultPeriod()
	{
		return defaultPeriod;
	}

	public void setDefaultPeriod(Boolean defaultPeriod)
	{
		this.defaultPeriod = defaultPeriod;
	}

	public PeriodType getType()
	{
		return type;
	}

	public void setType(PeriodType type)
	{
		this.type = type;
	}

	public BigDecimal getMinSum()
	{
		return minSum;
	}

	public Money getMinSumInNationalCurrency()
	{
		try
		{
			if(minSum != null)
				return new Money(minSum, MoneyUtil.getNationalCurrency());
		}
		catch (BusinessException ignore){}
		return null;
	}

	public void setMinSum(BigDecimal minSum)
	{
		this.minSum = minSum;
	}

	public BigDecimal getMaxSum()
	{
		return maxSum;
	}

	public Money getMaxSumInNationalCurrency()
	{
		try
		{
			if(maxSum != null)
				return new Money(maxSum, MoneyUtil.getNationalCurrency());
		}
		catch (BusinessException ignore){}
		return null;
	}

	public void setMaxSum(BigDecimal maxSum)
	{
		this.maxSum = maxSum;
	}

	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	/**
	 * @return Возвращает распарсенный список периодов
	 */
	public List<Integer> getPeriodList()
	{
		try
		{
			return INTERVALS_HELPER.parse(period);
		}
		catch (ParseException ignore)
		{
			return Collections.emptyList();
		}
	}
}
