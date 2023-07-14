package com.rssl.phizic.operations.finances;

import java.util.Calendar;

/**
 * @author gololobov
 * @ created 12.09.2011
 * @ $Author$
 * @ $Revision$
 */
/*
 * Описывает элемент графика "Движения средств по категории" в анализе личных финансов
 */
public class CategoriesDynamicMoveDescription
{
	//Порядковый номер периода
	private Long id;
	//Первый день периода
	private Calendar firstDay;
	//Последний день периода
	private Calendar lastDay;
	//Сумма по всем операциям за период
	private Double nationalSumm;
	//Итоговая сумма
	private Double totalSumm;

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

	public Double getNationalSumm()
	{
		return nationalSumm;
	}

	public void setNationalSumm(Double nationalSumm)
	{
		this.nationalSumm = nationalSumm;
	}

	public Double getTotalSumm()
	{
		return totalSumm;
	}

	public void setTotalSumm(Double totalSumm)
	{
		this.totalSumm = totalSumm;
	}
}
