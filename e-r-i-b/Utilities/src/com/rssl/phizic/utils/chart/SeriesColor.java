package com.rssl.phizic.utils.chart;

import java.awt.*;

/**
 * ����� ��� ����� (��� ������������� � ������������� ��������)
 * @author lepihina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 */
public class SeriesColor
{
	private Color positiveColor;
    private Color negativeColor;

	/**
	 * @param positiveColor - ���� ��� ������������� ��������
	 * @param negativeColor - ���� ��� ������������� ��������
	 */
	public SeriesColor(Color positiveColor, Color negativeColor)
    {
	    this.positiveColor = positiveColor;
	    this.negativeColor = negativeColor;
    }

	/**
	 * @return ���� ��� ������������� ��������
	 */
	public Color getPositiveColor()
	{
		return positiveColor;
	}

	/**
	 * @return ���� ��� ������������� ��������
	 */
	public Color getNegativeColor()
	{
		return negativeColor;
	}
}
