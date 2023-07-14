package com.rssl.phizic.captcha;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.utils.StringHelper;

import java.awt.*;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Настройки изображения для работы с каптчей/
 *
 * @author bogdanov
 * @ created 18.09.2012
 * @ $Author$
 * @ $Revision$
 */

class CaptchaImageConfig
{
	static final String WIDTH_KEY = "com.rssl.captcha.width";
	static final String HEIGHT_KEY = "com.rssl.captcha.height";
	static final String SEGMENT_COUNT_KEY = "com.rssl.captcha.image.segmentCount";
	static final String MAX_SEGMENT_HEIGHT_KEY = "com.rssl.captcha.image.segmetnMaxHeight";
	static final String COLOR_KEY = "com.rssl.captcha.image.color";
	static final String BACKGROUND_KEY = "com.rssl.captcha.image.background";
	static final String FOREGROUND_KEY = "com.rssl.captcha.image.foreground";
	static final String BLUR_KEY = "com.rssl.captcha.image.blur";

	/**
	 * Ширина картинки.
	 */
	private int width;
	/**
	 * Высота картинки.
	 */
	private int height;
	/**
	 * Количество сегментов для деформации картинки.
	 */
	private int segmentCount;
	/**
	 * Цвет фона.
	 */
	private int color;
	/**
	 * Максимальная высота сегмента.
	 */
	private double maxSegmentHeight;

	/**
	 * задний фон.
	 */
	private Image background;
	/**
	 * передний фон.
	 */
	private Image foreground;

	/**
	 * Нужно ли размытие.
	 */
	private boolean needBlur;
	private Log log;

	/**
	 * URL заднего фона.
	 */
	private String backgroundURL;
	/**
	 * URL переднего фона.
	 */
	private String foregroundURL;

	CaptchaImageConfig(Log log)
	{
		this.log = log;
	}

	public synchronized Image getBackground()
	{
		if (StringHelper.isEmpty(backgroundURL))
			return null;

		if (background != null)
			return background;

		try
		{
			background = ImageIO.read(new URL(backgroundURL));
		}
		catch (IOException e)
		{
			log.info("Задний фон не загружен", e);
			backgroundURL = null;
		}
		return background;
	}

	public void setBackgroundURL(String backgroundURL)
	{
		this.backgroundURL = backgroundURL;
		background = null;
	}

	public int getColor()
	{
		return color;
	}

	public void setColor(int color)
	{
		this.color = color;
	}

	public synchronized Image getForeground()
	{
		if (StringHelper.isEmpty(foregroundURL))
			return null;

		if (foreground != null)
			return foreground;

		try
		{
			foreground = ImageIO.read(new URL(foregroundURL));
		}
		catch (IOException e)
		{
			log.info("Передний фон не загружен", e);
			foregroundURL = null;
		}
		return foreground;
	}

	public void setForegroundURL(String foregroundURL)
	{
		this.foregroundURL = foregroundURL;
		foreground = null;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public double getMaxSegmentHeight()
	{
		return maxSegmentHeight;
	}

	public void setMaxSegmentHeight(double maxSegmentHeight)
	{
		this.maxSegmentHeight = maxSegmentHeight;
	}

	public int getSegmentCount()
	{
		return segmentCount;
	}

	public void setSegmentCount(int segmentCount)
	{
		this.segmentCount = segmentCount;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public boolean isNeedBlur()
	{
		return needBlur;
	}

	public void setNeedBlur(boolean needBlur)
	{
		this.needBlur = needBlur;
	}
}