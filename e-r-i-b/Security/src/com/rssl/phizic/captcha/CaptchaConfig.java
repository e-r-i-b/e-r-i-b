package com.rssl.phizic.captcha;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * Настройки каптчи.
 *
 * @author bogdanov
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class CaptchaConfig extends Config
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB);

	private static final String WORKING_KEY = "com.rssl.captcha.working";
	private boolean working;

	private CaptchaCodeConfig codeConfig = new CaptchaCodeConfig();
	private CaptchaCodeVisualConfig visualConfig = new CaptchaCodeVisualConfig();
	private CaptchaImageConfig imageConfig = new CaptchaImageConfig(log);
	private CaptchaLetterConfig letterConfig = new CaptchaLetterConfig();
	private CaptchaNoiseConfig noiseConfig = new CaptchaNoiseConfig();

	/**
	 * Создает настройку каптчи.
	 */
	public CaptchaConfig(PropertyReader propertyReader)
	{
		super(propertyReader);
	}

	CaptchaCodeConfig getCodeConfig()
	{
		return codeConfig;
	}

	CaptchaImageConfig getImageConfig()
	{
		return imageConfig;
	}

	CaptchaLetterConfig getLetterConfig()
	{
		return letterConfig;
	}

	CaptchaNoiseConfig getNoiseConfig()
	{
		return noiseConfig;
	}

	CaptchaCodeVisualConfig getVisualConfig()
	{
		return visualConfig;
	}

	public void doRefresh() throws ConfigurationException
	{
		working = Boolean.parseBoolean(getProperty(WORKING_KEY));

		codeConfig.setMinLength(getIntProperty(CaptchaCodeConfig.MIN_LENGTH_KEY));
		codeConfig.setMaxLength(getIntProperty(CaptchaCodeConfig.MAX_LENGTH_KEY));
		String chars = getProperty(CaptchaCodeConfig.CHARS_KEY);
		codeConfig.setChars(chars.toCharArray());
		codeConfig.setWidth(getIntProperty(CaptchaCodeConfig.WIDTH_KEY));
		codeConfig.setHeight(getIntProperty(CaptchaCodeConfig.HEIGHT_KEY));
		codeConfig.setLeft(getIntProperty(CaptchaCodeConfig.LEFT_KEY));
		codeConfig.setTop(getIntProperty(CaptchaCodeConfig.TOP_KEY));

		visualConfig.setColor(getHexProperty(CaptchaCodeVisualConfig.COLOR_KEY));
		String fonts = getProperty(CaptchaCodeVisualConfig.FONTS_KEY);
		String[] fnts = fonts.split("\\|");
		visualConfig.setFontNames(fnts);

		imageConfig.setColor(getHexProperty(CaptchaImageConfig.COLOR_KEY));
		imageConfig.setBackgroundURL(getProperty(CaptchaImageConfig.BACKGROUND_KEY));
		imageConfig.setForegroundURL(getProperty(CaptchaImageConfig.FOREGROUND_KEY));
		imageConfig.setHeight(getIntProperty(CaptchaImageConfig.HEIGHT_KEY));
		imageConfig.setWidth(getIntProperty(CaptchaImageConfig.WIDTH_KEY));
		imageConfig.setMaxSegmentHeight(getDoubleProperty(CaptchaImageConfig.MAX_SEGMENT_HEIGHT_KEY));
		imageConfig.setSegmentCount(getIntProperty(CaptchaImageConfig.SEGMENT_COUNT_KEY));
		imageConfig.setNeedBlur(Boolean.parseBoolean(getProperty(CaptchaImageConfig.BLUR_KEY)));

		letterConfig.setMoveAmplitude(getDoubleProperty(CaptchaLetterConfig.MOVE_AMPLITUDE_FACTOR_KEY));
		letterConfig.setPenWidth(getDoubleProperty(CaptchaLetterConfig.PEN_WIDTH_KEY));
		letterConfig.setRotateAmplitude(getDoubleProperty(CaptchaLetterConfig.ROTATE_AMPLITUDE_KEY));
		letterConfig.setScaleAmplitude(getDoubleProperty(CaptchaLetterConfig.SCALE_AMPLITUDE_KEY));
		letterConfig.setWidth(getDoubleProperty(CaptchaLetterConfig.WIDTH_KEY));

		noiseConfig.setMaxSegmentHeight(getDoubleProperty(CaptchaNoiseConfig.MAX_SEGMENT_HEIGHT_KEY));
		noiseConfig.setPenWidth(getDoubleProperty(CaptchaNoiseConfig.PEN_WIDTH_KEY));
		noiseConfig.setSegmentCount(getIntProperty(CaptchaNoiseConfig.SEGMENT_COUNT_KEY));
		noiseConfig.setXStarts(getDoublesProperty(CaptchaNoiseConfig.X_START_KEY));
		noiseConfig.setYStarts(getDoublesProperty(CaptchaNoiseConfig.Y_START_KEY));

		Captcha.clearCache();
	}

	private int getHexProperty(String key)
	{
		String p = getProperty(key);
		String[] s = p.split("x");
		int sign = Integer.parseInt(s[1].substring(0, 1), 16);
		int value = Integer.parseInt(s[1].substring(1), 16);
		return ((sign << 28) | value);
	}

	protected double getDoubleProperty(String key)
	{
		String p = getProperty(key);
		return Double.parseDouble(p);
	}

	private double[] getDoublesProperty(String key)
	{
		String p = getProperty(key);
		String[] dbls = p.split("\\|");
		double[] doubles = new double[dbls.length];
		for (int i = 0; i < dbls.length; i++)
			doubles[i] = Double.parseDouble(dbls[i]);
		return doubles;
	}

	/**
	 * Работает проверка кода каптчи или нет.
	 *
	 * @return работает или нет проверка кода каптчи.
	 */
	public boolean isWorking()
	{
		return working;
	}
}