package com.rssl.phizic.captcha;

/**
 * ��������� ����������� �������� ������.
 *
 * @author bogdanov
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */

class CaptchaLetterConfig
{
	static final String SCALE_AMPLITUDE_KEY = "com.rssl.captcha.code.letter.scaleAmplitude";
	static final String MOVE_AMPLITUDE_FACTOR_KEY = "com.rssl.captcha.code.letter.moveAmplitudeFactor";
	static final String ROTATE_AMPLITUDE_KEY = "com.rssl.captcha.code.letter.rotateAmplitude";
	static final String PEN_WIDTH_KEY = "com.rssl.captcha.code.letter.penWidth";
	static final String WIDTH_KEY = "com.rssl.captcha.code.letter.width";

	/**
	 * ��������� ���������������.
	 */
	private double scaleAmplitude;
	/**
	 * ��������� ��������.
	 */
	private double moveAmplitude;
	/**
	 * ��������� ��������.
	 */
	private double rotateAmplitude;
	/**
	 * ������� ���� ��� �����.
	 */
	private double penWidth;

	/**
	 * ����������� ������ �������.
	 */
	private double width;

	public double getMoveAmplitude()
	{
		return moveAmplitude;
	}

	public void setMoveAmplitude(double moveAmplitude)
	{
		this.moveAmplitude = moveAmplitude;
	}

	public double getPenWidth()
	{
		return penWidth;
	}

	public void setPenWidth(double penWidth)
	{
		this.penWidth = penWidth;
	}

	public double getRotateAmplitude()
	{
		return rotateAmplitude;
	}

	public void setRotateAmplitude(double rotateAmplitude)
	{
		this.rotateAmplitude = rotateAmplitude;
	}

	public double getScaleAmplitude()
	{
		return scaleAmplitude;
	}

	public void setScaleAmplitude(double scaleAmplitude)
	{
		this.scaleAmplitude = scaleAmplitude;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}
}
