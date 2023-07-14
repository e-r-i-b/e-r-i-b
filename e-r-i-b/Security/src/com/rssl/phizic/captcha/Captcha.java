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
 * ������ ��� �������� ������.
 *
 * @author bogdanov
 */
public class Captcha
{
	/**
	 * ��������� ������.
	 */
	private CaptchaConfig config;
	/**
	 * �������� ������.
	 */
	private BufferedImage captcha;
	/**
	 * ��� ������.
	 */
	private char[] captchaCode;
	/**
	 * ��������� ��������� �����.
	 */
	private static final Random rnd = new Random();
	/**
	 * ��� ��� �������� �������.
	 */
	private static final Map<String, Shape> shapeCache = new HashMap<String, Shape>();

	int width;
	int height;
	int left;
	int top;
	private Color fontColor; //���� ������
	private Color bgColor;  //���� ����

	public Captcha()
	{
		this(null);
	}

	public Captcha(Integer color)
	{
		this(color, null);
	}

	/**
	 * ����������� ��� �������� ����� � ����������� ������� ������������ ����
	 * @param color ���� ������
	 * @param bgcolor ���� ���� � ������� ARGB  (������ ���� �������� �� ������������)
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
	 * ��������� ���� ������.
	 *
	 * @return ��� ������.
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
	 * ��������� ������.
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
	 * ������������� ���������� ��������.
	 *
	 * @return �������.
	 */
	private Graphics2D initCaptchaGenerator()
	{
		//�������� ���� ����� �������� ���� ������.
		captcha = new BufferedImage(config.getImageConfig().getWidth(), config.getImageConfig().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = captcha.createGraphics();

		//�������� ������ ����.
		g.setColor(this.bgColor);
		g.fillRect(0, 0, captcha.getWidth(), captcha.getHeight());

		//������ ������ ���, ���� �� ��������.
		if (config.getImageConfig().getBackground() != null)
			g.drawImage(config.getImageConfig().getBackground(), 0, 0, null);

		//������������� ��������� �������.
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		return g;
	}

	/**
	 * ������ �������.
	 *
	 * @param g �������.
	 */
	private void drawSymbols(Graphics2D g)
	{
		//������������� ������ ����.
		g.setStroke(new BasicStroke((float) config.getLetterConfig().getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		//������������� ����.
		g.setColor(fontColor);
		for (int i = 0; i < captchaCode.length; i++)
		{
			drawSymbol(g, captchaCode[i], width / (captchaCode.length - 0.0) * (i + 0.5));
		}
	}

	/**
	 * ������ ������.
	 *
	 * @param g �������.
	 * @param ch ������.
	 * @param startFrom �������� � ������� �� x.
	 */
	private void drawSymbol(Graphics2D g, char ch, double startFrom)
	{
		//���������� ���������� ��������� ��������������.
		AffineTransform trans = g.getTransform();
		//�������� ������ �����.
		Shape s = getShape(rnd.nextInt(config.getVisualConfig().getFontNames().length), rnd.nextInt(4), ch, g);
		//� �� �������������� �������������.
		Rectangle2D rect = s.getBounds2D();
		//�������� ������ � ��������� ���������.
		double symWidth = width / (captchaCode.length - 0.0);
		double symHeight = height;
		g.translate(startFrom + symWidth * nextMoveAmplitudeFactor(), height / 2.0 + symHeight * nextMoveAmplitudeFactor());
		//������������� ��������� ���������������.
		double sx = width / (rect.getWidth() * captchaCode.length) * config.getLetterConfig().getWidth();
		double sy = height / rect.getHeight() * nextScale();
		//������������.
		g.scale(sx, sy);

		//������������ �� ��������� ����.
		g.rotate(nextRotateAmplitudeFactor() / 180 * Math.PI);

		//����������� ����� ������ �������� � (0, 0).
		g.translate(-rect.getX() - rect.getWidth() / 2.0, -rect.getY() - rect.getHeight() / 2.0);
		g.draw(s);
		g.setTransform(trans);
	}

	/**
	 * @return ��������� �������.
	 */
	private double nextScale()
	{
		double scaleAmpl = config.getLetterConfig().getScaleAmplitude();
		return 1.0 - rnd.nextDouble() * scaleAmpl;
	}

	/**
	 * @return ��������� ��������� ������.
	 */
	private double nextMoveAmplitudeFactor()
	{
		double moveAmpl = config.getLetterConfig().getMoveAmplitude();
		return -moveAmpl / 2 + rnd.nextDouble() * moveAmpl;
	}

	/**
	 * @return ��������� ���� ��������.
	 */
	private double nextRotateAmplitudeFactor()
	{
		double rotAmpl = config.getLetterConfig().getRotateAmplitude();
		return -rotAmpl / 2 + rnd.nextDouble() * rotAmpl;
	}

	/**
	 * ���������� ������ �������.
	 * ���������� ����������� �������.
	 *
	 * @param fontNameIndex ������ �������� ������.
	 * @param fontType ��� ������.
	 * @param ch ������.
	 * @param g �������.
	 * @return ������ �������.
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
	 * ������ ������� �����
	 *
	 * @param g �������.
	 */
	private void drawNoise(Graphics2D g)
	{
		//������������� ����� ����.
		g.setStroke(new BasicStroke((float) config.getNoiseConfig().getPenWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		Color color1 = fontColor;
		Color color2 = new Color(config.getImageConfig().getColor());
		double[] noiseYStartFactor = config.getNoiseConfig().getYStarts();
		double[] noiseXStartFactor = config.getNoiseConfig().getXStarts();
		for (int i = 0; i < noiseYStartFactor.length; i++)
		{
			//������������� ����.
			g.setColor(color1);
			drawHorizontalLine(g, height * noiseYStartFactor[i] + 0.1 - rnd.nextDouble() * 0.05 + top);
			//������������� ����.
			g.setColor(color2);
			drawHorizontalLine(g, height * noiseYStartFactor[i] + 0.1 - rnd.nextDouble() * 0.05 + top);
		}
		for (int i = 0; i < noiseXStartFactor.length; i++)
		{
			//������������� ����.
			g.setColor(color1);
			drawVerticalLine(g, width * noiseXStartFactor[i] + 0.1 - rnd.nextDouble() * 0.05 + left);
			//������������� ����.
			g.setColor(color2);
			drawVerticalLine(g, width * noiseXStartFactor[i] + 0.1 - rnd.nextDouble() * 0.05 + left);
		}
	}

	/**
	 * ������ ������� �����.
	 *
	 * @param g �������.
	 * @param y0 ��������� ����� �� y.
	 */
	private void drawHorizontalLine(Graphics2D g, double y0)
	{
		GeneralPath path = new GeneralPath();
		double ampl[] = new double[config.getNoiseConfig().getSegmentCount()];
		//��������� �����.
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
	 * ������ ������� �����.
	 *
	 * @param g �������.
	 * @param x0 ��������� ����� �� x.
	 */
	private void drawVerticalLine(Graphics2D g, double x0)
	{
		GeneralPath path = new GeneralPath();
		double ampl[] = new double[config.getNoiseConfig().getSegmentCount()];
		//��������� �����.
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
	 * ��������� �������.
	 * ������� ������� �� ��������� �������������� �������.
	 * ������ ��������� ������� ������ �� �������, ��� ����������.
	 *
	 * @param ampl ������ ������� ��� ������� ��������.
	 * @param x �������� x, � ������� ���������� ��������� �������.
	 * @param periodLen ����� �������.
	 * @return �������� �������.
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

		//��������� ������, ���� ��� ����������.
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
	 * ���������� ������� ������� ��������, ���� �� ���� ��� ����� ����.
	 *
	 * @param x x.
	 * @param y y.
	 * @param src �������� ��������.
	 * @return ���� �������� �������.
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
	 * ���������� �������� � �������.
	 *
	 * @return ��������.
	 */
	public RenderedImage getCaptcha()
	{
		return captcha;
	}

	/**
	 * ���������� ��� ������.
	 *
	 * @return ��� ������.
	 */
	public String getCode()
	{
		return new String(captchaCode);
	}

	/**
	 * ������� ��� ����.
	 */
	static void clearCache()
	{
		shapeCache.clear();
	}

	/**
	 * @return ������ ��������, ������� ������������ ��� �������� ����.
	 */
	public static char[] getActiveChars()
	{
		return ConfigFactory.getConfig(CaptchaConfig.class).getCodeConfig().getLowerCaseChars();
	}
}

