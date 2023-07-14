package com.rssl.phizic.business.profile.images;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.profile.ProfileConfig;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;
import javax.imageio.*;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

/**
 * Сервис для работы с картинками.
 *
 * @author bogdanov
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class ImageService
{
	private static volatile ImageService self = null;
	private static final Color backgroundColor = new Color(0x00ffffff);

	/**
	 * @return сервис для работы с картинками.
	 */
	public static ImageService get()
	{
	    if (self != null)
		    return self;

		synchronized (ImageService.class)
		{
			if (self != null)
				return self;

			self = new ImageService();
			return self;
		}
	}

	private ImageService()
	{
	}

	/**
	 * @param fileName файл.
	 * @return его расширение.
	 */
	public static String getFileExtention(String fileName)
	{
		return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
	}

	/**
	 * Создание графического контекста.
	 *
	 * @param image картинка.
	 * @return контекст.
	 */
	private Graphics2D initGraphics2D(BufferedImage image)
	{
		Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.setBackground(backgroundColor);

		return g;
	}

	/**
	 * Обрезка изображения.
	 *
	 * @param srcImage исходное изображение.
	 * @param x x координата верхнего левого угла.
	 * @param y y координата верхнего левого угла.
	 * @param width ширина нового изображения.
	 * @param height высота нового изображения.
	 * @return обрезанное изображение.
	 */
	public BufferedImage clipImage(Image srcImage, int x, int y, int width, int height)
	{
		BufferedImage src = (BufferedImage) srcImage;
		BufferedImage image = new BufferedImage(width, height, src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType());
		Graphics2D g = initGraphics2D(image);

		AffineTransform transform = g.getTransform();
		g.translate(-x, -y);
		g.drawImage(src, 0, 0, null);
		g.setTransform(transform);
		g.dispose();
		return image;
	}

	/**
	 * Масштабирование изображения.
	 *
	 * @param srcImage исходное изображение.
	 * @param newWidth новая ширина.
	 * @param newHeight новая высота.
	 * @return отмасшти\абированное изображение.
	 */
	public BufferedImage resizeImage(Image srcImage, double newWidth, double newHeight)
	{
		BufferedImage src = (BufferedImage) srcImage;
		double w = src.getWidth();
		double h = src.getHeight();
		double s = 1;
		if (w >= newWidth || h >= newHeight)
		{
            double sw = newWidth / w;
			double sh = newHeight / h;
            s = Math.min(sw, sh);
		}

		BufferedImage image = new BufferedImage((int)(s * w - 1), (int)(s * h - 1), src.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : src.getType());
		Graphics2D g = initGraphics2D(image);

		AffineTransform transform = g.getTransform();
		g.setTransform(new AffineTransform());
        g.scale(s, s);
		g.drawImage(src, 0, 0, null);
		g.setTransform(transform);
		g.dispose();

		return image;
	}

	/**
	 * Загрузка изображения.
	 *
	 * @param file файл.
	 * @return изображение.
	 * @throws IOException
	 */
	public BufferedImage loadImage(File file) throws IOException, BusinessLogicException
	{
		return ImageIO.read(file);
	}

	/**
	 * Загрузка изображения.
	 *
	 * @param stream поток.
	 * @param maxSideSize максимальная длина сторон.
	 * @return загруженное изображение.
	 * @throws IOException
	 */
	public NotLoadedImage loadImage(InputStream stream, int maxSideSize) throws IOException, BusinessLogicException
	{
		ImageInputStream iis = ImageIO.createImageInputStream(stream);
		if (iis == null)
			return null;

		Iterator iter = ImageIO.getImageReaders(iis);
	    if (!iter.hasNext()) {
	        return null;
	    }

        ImageReader reader = (ImageReader)iter.next();
		reader.setInput(iis);

		if (Math.max(reader.getWidth(0), reader.getHeight(0)) > maxSideSize)
			throw new BusinessLogicException(
					"Одна из сторон изображения превышает " + maxSideSize + " пикселей.");

		NotLoadedImage image = new NotLoadedImage(reader.getWidth(0), reader.getHeight(0), stream);
        reader.dispose();
        return image;
	}

	/**
	 * Сохранение изображения.
	 *
	 * @param image изображение.
	 * @param file файл.
	 * @throws IOException
	 */
	public void saveImage(Image image, File file) throws IOException
	{
		if (image instanceof NotLoadedImage)
		{
			NotLoadedImage img = (NotLoadedImage) image;
			img.writeToFile(file);
			return;
		}

		BufferedImage img = (BufferedImage) image;
		String ext = getFileExtention(file.getName());
		if (ext.equals("jpeg") || ext.equals("jpg")) {
			ImageWriter writer = ImageIO.getImageWritersByFormatName(ext).next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(1.0F);
			writer.setOutput(new FileImageOutputStream(file));
			writer.write(null, new IIOImage(img, null, null), param);
		}
		else
		{
			ImageIO.write(img, ext, file);
		}
	}

	/**
	 * Возвращает размер изображения в байтах.
	 *
	 * @param image изображение.
	 * @param fileName имя файла.
	 * @return его размер.
	 * @throws IOException
	 */
	public long getImageFileSize(BufferedImage image, String fileName) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String ext = getFileExtention(fileName);
		if (ext.equals("jpeg") || ext.equals("jpg")) {
			ImageWriter writer = ImageIO.getImageWritersByFormatName(ext).next();
			ImageWriteParam param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(1.0F);
			writer.setOutput(new MemoryCacheImageOutputStream(baos));
			writer.write(null, new IIOImage(image, null, null), param);
		}
		else
		{
			ImageIO.write(image, ext, baos);
		}

		if (baos.size() > 0)
			return baos.size();
		throw new RuntimeException("Не возможно сохранить");
	}
}
