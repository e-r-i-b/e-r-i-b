package com.rssl.phizic.business.dictionaries.pfp.products.types;

/**
 * @author akrenev
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Параметры диаграмы
 */

public class DiagramParameters
{
	private Boolean useZero;
	private DiagramAxis axisX;
	private DiagramAxis axisY;

	/**
	 * @return true -- Отображать 0 на графике
	 */
	public Boolean getUseZero()
	{
		return useZero;
	}

	/**
	 *  задать флаг тображения 0 на графике
	 * @param useZero true -- Отображать 0 на графике
	 */
	public void setUseZero(Boolean useZero)
	{
		this.useZero = useZero;
	}

	/**
	 * @return параметры оси абсцисс
	 */
	public DiagramAxis getAxisX()
	{
		return axisX;
	}

	/**
	 * задать параметры оси абсцисс
	 * @param axisX параметры
	 */
	public void setAxisX(DiagramAxis axisX)
	{
		this.axisX = axisX;
	}

	/**
	 * @return параметры оси ординат
	 */
	public DiagramAxis getAxisY()
	{
		return axisY;
	}

	/**
	 * задать параметры оси ординат
	 * @param axisY параметры
	 */
	public void setAxisY(DiagramAxis axisY)
	{
		this.axisY = axisY;
	}
}
