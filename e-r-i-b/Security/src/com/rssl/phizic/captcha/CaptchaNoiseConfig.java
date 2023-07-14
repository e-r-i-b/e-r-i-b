package com.rssl.phizic.captcha;

/**
 * ��������� ���� ��� ������.
 *
 * @author bogdanov
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */

class CaptchaNoiseConfig
{
	static final String PEN_WIDTH_KEY = "com.rssl.captcha.noise.penWidth";
	static final String Y_START_KEY = "com.rssl.captcha.noise.yStart";
	static final String X_START_KEY = "com.rssl.captcha.noise.xStart";
	static final String SEGMENT_COUNT_KEY = "com.rssl.captcha.noise.segmentCount";
	static final String MAX_SEGMENT_HEIGHT_KEY = "com.rssl.captcha.noise.segmentMaxHeight";

	/**
	 * ������ ����.
	 */
	private double penWidth;

	/**
	 * ������ ��������� ��������� �� y.
	 */
	private double[] yStarts;
	/**
	 * ������ ��������� ��������� �� x.
	 */
	private double[] xStarts;
	/**
	 * ���������� ���������.
	 */
	private int segmentCount;
	/**
	 * ������������ ������ ��������.
	 */
	private double maxSegmentHeight;

	public double getMaxSegmentHeight()
	{
		return maxSegmentHeight;
	}

	public void setMaxSegmentHeight(double maxSegmentHeight)
	{
		this.maxSegmentHeight = maxSegmentHeight;
	}

	public double getPenWidth()
	{
		return penWidth;
	}

	public void setPenWidth(double penWidth)
	{
		this.penWidth = penWidth;
	}

	public int getSegmentCount()
	{
		return segmentCount;
	}

	public void setSegmentCount(int segmentCount)
	{
		this.segmentCount = segmentCount;
	}

	public double[] getXStarts()
	{
		return xStarts;
	}

	public void setXStarts(double[] xStarts)
	{
		if (xStarts.length == 1 && xStarts[0] < 0)
			this.xStarts = new double[0];

		this.xStarts = xStarts;
	}

	public double[] getYStarts()
	{
		if (yStarts.length == 1 && yStarts[0] < 0)
			this.yStarts = new double[0];

		return yStarts;
	}

	public void setYStarts(double[] yStarts)
	{
		this.yStarts = yStarts;
	}
}
