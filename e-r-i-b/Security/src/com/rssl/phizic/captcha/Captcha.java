package com.rssl.phizic.captcha;

import com.rssl.phizic.config.ConfigFactory;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.*;
import java.util.*;

/**
 * Каптча для Сбербанк Онлайн.
 *
 * @author bogdanov
 */
public class Captcha
{
	/**
	 * Параметры каптчи.
	 */
	private CaptchaConfig config;
	/**
	 * Картинка каптчи.
	 */
	private BufferedImage captcha;
	/**
	 * Код каптчи.
	 */
	private char[] captchaCode;
	/**
	 * Генератор случайных чисел.
	 */
	private static final Random rnd = new Random();
	/**
	 * КЭШ для хранения буковок.
	 */
	private static final Map<String, Shape> shapeCache = new HashMap<String, Shape>();

	int width;
	int height;
	int left;
	int top;
	private Color fontColor; //Цвет текста
	private Color bgColor;  //Цвет фона

	public Captcha()
	{
		this(null);
	}

	public Captcha(Integer color)
	{
		this(color, null);
	}

	/**
	 * Конструктор для создания капчи с определённым уровнем прозрачности фона
	 * @param color Цвет текста
	 * @param bgcolor Цвет фона в формате ARGB  (первый байт отвечает за прозрачность)
	 */
	public Captcha(Integer color, Integer bgcolor){
		config = ConfigFactory.getConfig(CaptchaConfig.class);

		width = config.getCodeConfig().getWidth();
		height = config.getCodeConfig().getHeight();
		top = config.getCodeConfig().getTop();
		left = config.getCodeConfig().getLeft();

		this.fontColor = new Color(color == null ? config.getVisualConfig().getColor() : color);
		this.bgColor = new Color(bgcolor == null ? config.getImageConfig().getColor() : bgcolor, true);
		captchaCode = generateCode();
		generateCaptcha();
	}

	/**
	 * Генерация кода каптчи.
	 *
	 * @return код каптчи.
	 */
	private char[] generateCode()
	{
		Set<Character> bedChars = new HashSet<Character>();
		int numOfSymbols = rnd.nextInt(config.getCodeConfig().getMaxLength() - config.getCodeConfig().getMinLength() + 1) + config.getCodeConfig().getMinLength();
		char[] chars = config.getCodeConfig().getChars();
		char[] code = new char[numOfSymbols];
		for (int i = 0; i < code.length; i++)
		{
			char ch = chars[rnd.nextInt(chars.length)];
			while (bedChars.contains(ch))
			{
				ch = chars[rnd.nextInt(chars.length)];
			}
			bedChars.clear();
			bedChars.add(ch);
			bedChars.add(new Character((char) (ch - 1)));
			bedChars.add(new Character((char) (ch + 1)));

			code[i] = ch;
		}

		return code;
	}

	/**
	 * Генерация каптчи.
	 */
	private void generateCaptcha()
	{
		Graphics2D g = initCaptchaGenerator();
		AffineTransform trans = g.getTransform();
		g.translate(left, top);
		drawSymbols(g);
		g.setTransform(trans);
		drawNoise(g);
		g.dispose();
		modifyImage();

		if (config.getImageConfig().getForeground() != null)
			captcha.getGraphics().drawImage(config.getImageConfig().getForeground(), 0, 0, null);
	}

	/**
	 * Инициализация генератора картинки.
	 *
	 * @return графика.
	 */
	private Graphics2D initCaptchaGenerator()
	{
		//Картинка куда будем помещать нашу каптчу.
		captcha = new BufferedImage(config.getImageConfig().getWidth(), config.getImageConfig().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = captcha.createGraphics();

		//Заливаем цветом фона.
		g.setColor(this.bgColor);
		g.fillRect(0, 0, captcha.getWidth(), captcha.getHeight());

		//рисуем задний фон, если он загружен.
		if (config.getImageConfig().getBackground() != null)
			g.drawImage(config.getImageConfig().getBackground(), 0, 0, null);

		//устанавливаем параметры графики.
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return g;
	}

	/**
	 * Рисуем символы.
	 *
	 * @param g графика.
	 */
	private void drawSymbols(Graphics2D g)
	{
		//устанавливаем ширину пера.
		g.setStroke(new BasicStroke((float) config.getLetterConfig().getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		//устанавливаем цвет.
		g.setColor(fontColor);
		for (int i = 0; i < captchaCode.length; i++)
		{
			drawSymbol(g, captchaCode[i], width / (captchaCode.length - 0.0) * (i + 0.5));
		}
	}

	/**
	 * Рисует символ.
	 *
	 * @param g графика.
	 * @param ch символ.
	 * @param startFrom рисовать с позиции по x.
	 */
	private void drawSymbol(Graphics2D g, char ch, double startFrom)
	{
		//Запоминаем предыдущие матричные преобразования.
		AffineTransform trans = g.getTransform();
		//Получаем контур буквы.
		Shape s = getShape(rnd.nextInt(config.getVisualConfig().getFontNames().length), rnd.nextInt(4), ch, g);
		//и ее ограничивающий прямоугольник.
		Rectangle2D rect = s.getBounds2D();
		//Сдвигаем символ в случайное положение.
		double symWidth = width / (captchaCode.length - 0.0);
		double symHeight = height;
		g.translate(startFrom + symWidth * nextMoveAmplitudeFactor(), height / 2.0 + symHeight * nextMoveAmplitudeFactor());
		//Устанавливаем параметры масштабирования.
		double sx = width / (rect.getWidth() * captchaCode.length) * config.getLetterConfig().getWidth();
		double sy = height / rect.getHeight() * nextScale();
		//масштабируем.
		g.scale(sx, sy);

		//Поворачиваем на случайный угол.
		g.rotate(nextRotateAmplitudeFactor() / 180 * Math.PI);

		//Передвигаем точку центра картинки в (0, 0).
		g.translate(-rect.getX() - rect.getWidth() / 2.0, -rect.getY() - rect.getHeight() / 2.0);
		g.draw(s);
		g.setTransform(trans);
	}

	/**
	 * @return следующий масштаб.
	 */
	private double nextScale()
	{
		double scaleAmpl = config.getLetterConfig().getScaleAmplitude();
		return 1.0 - rnd.nextDouble() * scaleAmpl;
	}

	/**
	 * @return следующий множитель сдвига.
	 */
	private double nextMoveAmplitudeFactor()
	{
		double moveAmpl = config.getLetterConfig().getMoveAmplitude();
		return -moveAmpl / 2 + rnd.nextDouble() * moveAmpl;
	}

	/**
	 * @return следующий угол поворота.
	 */
	private double nextRotateAmplitudeFactor()
	{
		double rotAmpl = config.getLetterConfig().getRotateAmplitude();
		return -rotAmpl / 2 + rnd.nextDouble() * rotAmpl;
	}

	/**
	 * Возвращает контур символа.
	 * Производит кеширование записей.
	 *
	 * @param fontNameIndex индекс названия шрифта.
	 * @param fontType тип шрифта.
	 * @param ch символ.
	 * @param g графика.
	 * @return контур символа.
	 */
	private Shape getShape(int fontNameIndex, int fontType, char ch, Graphics2D g)
	{
		String key = fontNameIndex + "|" + fontType + "|" + ch;
		if (shapeCache.containsKey(key))
		{
			return shapeCache.get(key);
		}

		g.setFont(new Font(config.getVisualConfig().getFontNames()[fontNameIndex], fontType, 40));
		TextLayout tl = new TextLayout(new String(new char[]{ch}), g.getFont(), g.getFontRenderContext());
		Shape s = tl.getOutline(null);
		shapeCache.put(key, s);

		return s;
	}

	/**
	 * Рисуем шумовые линии
	 *
	 * @param g графика.
	 */
	private void drawNoise(Graphics2D g)
	{
		//Устанавливаем стиль пера.
		g.setStroke(new BasicStroke((float) config.getNoiseConfig().getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		Color color1 = fontColor;
		Color color2 = new Color(config.getImageConfig().getColor());
		double[] noiseYStartFactor = config.getNoiseConfig().getYStarts();
		double[] noiseXStartFactor = config.getNoiseConfig().getXStarts();
		for (int i = 0; i < noiseYStartFactor.length; i++)
		{
			//устанавливаем цвет.
			g.setColor(color1);
			drawHorizontalLine(g, height * noiseYStartFactor[i] + 0.1 - rnd.nextDouble() * 0.05 + top);
			//устанавливаем цвет.
			g.setColor(color2);
			drawHorizontalLine(g, height * noiseYStartFactor[i] + 0.1 - rnd.nextDouble() * 0.05 + top);
		}
		for (int i = 0; i < noiseXStartFactor.length; i++)
		{
			//устанавливаем цвет.
			g.setColor(color1);
			drawVerticalLine(g, width * noiseXStartFactor[i] + 0.1 - rnd.nextDouble() * 0.05 + left);
			//устанавливаем цвет.
			g.setColor(color2);
			drawVerticalLine(g, width * noiseXStartFactor[i] + 0.1 - rnd.nextDouble() * 0.05 + left);
		}
	}

	/**
	 * Рисуем шумовую линию.
	 *
	 * @param g графика.
	 * @param y0 начальная точка по y.
	 */
	private void drawHorizontalLine(Graphics2D g, double y0)
	{
		GeneralPath path = new GeneralPath();
		double ampl[] = new double[config.getNoiseConfig().getSegmentCount()];
		//начальная точка.
		double x0 = rnd.nextDouble() * width + left;

		for (int i = 0; i < ampl.length; i++)
		{
			ampl[i] = rnd.nextDouble() * height * config.getNoiseConfig().getMaxSegmentHeight();
		}

		double y = y0 + evalFunc(ampl, x0, captcha.getWidth());
		path.moveTo(0.0f, (float) y);
		for (int i = 0; i < captcha.getWidth(); i++)
		{
			y = y0 + evalFunc(ampl, x0 + i, captcha.getWidth());
			path.lineTo((float) i, (float) y);
		}

		g.draw(path);
	}

	/**
	 * Рисуем шумовую линию.
	 *
	 * @param g графика.
	 * @param x0 начальная точка по x.
	 */
	private void drawVerticalLine(Graphics2D g, double x0)
	{
		GeneralPath path = new GeneralPath();
		double ampl[] = new double[config.getNoiseConfig().getSegmentCount()];
		//начальная точка.
		double y0 = rnd.nextDouble() * height + top;

		for (int i = 0; i < ampl.length; i++)
		{
			ampl[i] = rnd.nextDouble() * width * config.getNoiseConfig().getMaxSegmentHeight();
		}

		double x = x0 + evalFunc(ampl, y0, captcha.getHeight());
		path.moveTo(0.0f, (float) x);
		for (int i = 0; i < captcha.getWidth(); i++)
		{
			x = x0 + evalFunc(ampl, y0 + i, captcha.getHeight());
			path.lineTo((float) x, (float) i);
		}

		g.draw(path);
	}

	/**
	 * Вычисляет функцию.
	 * Функцию состоит из сегментов синусоидальных функций.
	 * Каждый следующий сегмент меньше по периоду, чем предыдущий.
	 *
	 * @param ampl список аплитуд для каждого сегмента.
	 * @param x значение x, в котором необходимо вычислить функцию.
	 * @param periodLen длина периода.
	 * @return значение функции.
	 */
	private double evalFunc(double ampl[], double x, double periodLen)
	{
		double val = 0;
		for (int i = 0; i < ampl.length; i++)
		{
			val += ampl[i] * Math.sin(x * (2 * Math.PI) / periodLen * i);
		}

		return val;
	}

	private void modifyImage()
	{
		double x0 = captcha.getWidth() * rnd.nextDouble();
		double y0 = captcha.getHeight() * rnd.nextDouble();
		double amplX[] = new double[config.getImageConfig().getSegmentCount()];
		for (int i = 0; i < amplX.length; i++)
		{
			amplX[i] = rnd.nextDouble() * config.getImageConfig().getMaxSegmentHeight();
		}
		double amplY[] = new double[config.getImageConfig().getSegmentCount()];
		for (int i = 0; i < amplY.length; i++)
		{
			amplY[i] = rnd.nextDouble() * config.getImageConfig().getMaxSegmentHeight();
		}

		int width = captcha.getWidth();
		int height = captcha.getHeight();
		int[] src = new int[width * height];
		captcha.getRGB(0, 0, width, height, src, 0, width);
		int[] dist = new int[width * height];

		int adr = 0;
		for (int y = 0; y < height; y++)
		{
			int oldX = (int) Math.round(evalFunc(amplX, y0 + y, width));
			for (int x = 0; x < width; x++)
			{
				int oldY = (int) Math.round(evalFunc(amplY, x0 + x, height));
				dist[adr] = getPixel(x + oldX, y + oldY, src);
				adr++;
			}
		}

		captcha.setRGB(0, 0, width, height, dist, 0, width);

		//Применяем фильтр, если это необходимо.
		if (config.getImageConfig().isNeedBlur())
		{
			ConvolveOp op = new ConvolveOp(new Kernel(3, 3, new float[]{
					0.025f, 0.15f, 0.025f,
					0.15f, 0.3f, 0.15f,
					0.025f, 0.15f, 0.025f}), ConvolveOp.EDGE_NO_OP, null);

			BufferedImage img = new BufferedImage(captcha.getWidth(), captcha.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = img.createGraphics();
			g.drawImage(captcha, op, 0, 0);
			g.dispose();
			captcha = img;
		}
	}

	/**
	 * Возвращает пиксель текущей картинки, если он есть или белый цвет.
	 *
	 * @param x x.
	 * @param y y.
	 * @param src исходная картинка.
	 * @return цвет текущего пикселя.
	 */
	private int getPixel(int x, int y, int[] src)
	{
		if (0 <= x && x < captcha.getWidth() && 0 <= y && y < captcha.getHeight())
		{
			int a = x + y * captcha.getWidth();
			return src[a];
		}
		return bgColor.getRGB();
	}

	/**
	 * Возвращает картинку с каптчей.
	 *
	 * @return картинку.
	 */
	public RenderedImage getCaptcha()
	{
		return captcha;
	}

	/**
	 * Возвращает код каптчи.
	 *
	 * @return код каптчи.
	 */
	public String getCode()
	{
		return new String(captchaCode);
	}

	/**
	 * Очищает кэш букв.
	 */
	static void clearCache()
	{
		shapeCache.clear();
	}

	/**
	 * @return список символов, которые используются для создания кода.
	 */
	public static char[] getActiveChars()
	{
		return ConfigFactory.getConfig(CaptchaConfig.class).getCodeConfig().getLowerCaseChars();
	}
}

