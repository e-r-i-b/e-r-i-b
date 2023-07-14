package com.rssl.phizic.operations.finances;

import java.util.Calendar;

/**
 * @author gololobov
 * @ created 14.09.2011
 * @ $Author$
 * @ $Revision$
 */

/*
 * Описывает элемент графика "Объем наличных и безналичных операций" в анализе личных финансов
 */
public class CashAndCashlessVolumeDescription
{
	//Порядковый номер периода
	private Long id;
	//Первый день периода
	private Calendar firstDay;
	//Последний день периода
	private Calendar lastDay;
	//Сумма за наличный рассчет в национальной валюте за период
	private Double cashSumm;
	//Сумма за безналичный рассчет в национальной валюте за период
	private Double cashlessSumm;
	//Итоговая сумма за наличный рассчет
	private Double totalCashSumm;
	//Итоговая сумма за безналичный рассчет
	private Double totalCashlessSumm;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getFirstDay()
	{
		return firstDay;
	}

	public void setFirstDay(Calendar firstDay)
	{
		this.firstDay = firstDay;
	}

	public Calendar getLastDay()
	{
		return lastDay;
	}

	public void setLastDay(Calendar lastDay)
	{
		this.lastDay = lastDay;
	}

	public Double getCashSumm()
	{
		return cashSumm;
	}

	public void setCashSumm(Double cashSumm)
	{
		this.cashSumm = cashSumm;
	}

	public Double getCashlessSumm()
	{
		return cashlessSumm;
	}

	public void setCashlessSumm(Double cashlessSumm)
	{
		this.cashlessSumm = cashlessSumm;
	}

	public Double getTotalCashSumm()
	{
		return totalCashSumm;
	}

	public void setTotalCashSumm(Double totalCashSumm)
	{
		this.totalCashSumm = totalCashSumm;
	}

	public Double getTotalCashlessSumm()
	{
		return totalCashlessSumm;
	}

	public void setTotalCashlessSumm(Double totalCashlessSumm)
	{
		this.totalCashlessSumm = totalCashlessSumm;
	}
}
