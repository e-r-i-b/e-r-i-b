package com.rssl.phizic.business.dictionaries.pfp.products.types;

/**
 * @author akrenev
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ��������
 */

public class DiagramParameters
{
	private Boolean useZero;
	private DiagramAxis axisX;
	private DiagramAxis axisY;

	/**
	 * @return true -- ���������� 0 �� �������
	 */
	public Boolean getUseZero()
	{
		return useZero;
	}

	/**
	 *  ������ ���� ���������� 0 �� �������
	 * @param useZero true -- ���������� 0 �� �������
	 */
	public void setUseZero(Boolean useZero)
	{
		this.useZero = useZero;
	}

	/**
	 * @return ��������� ��� �������
	 */
	public DiagramAxis getAxisX()
	{
		return axisX;
	}

	/**
	 * ������ ��������� ��� �������
	 * @param axisX ���������
	 */
	public void setAxisX(DiagramAxis axisX)
	{
		this.axisX = axisX;
	}

	/**
	 * @return ��������� ��� �������
	 */
	public DiagramAxis getAxisY()
	{
		return axisY;
	}

	/**
	 * ������ ��������� ��� �������
	 * @param axisY ���������
	 */
	public void setAxisY(DiagramAxis axisY)
	{
		this.axisY = axisY;
	}
}
