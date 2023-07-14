package com.rssl.phizic.business.profile.images;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

/**
 * Изображение, которое нет неоходимости загружать
 *
 * @author bogdanov
 * @ created 17.06.14
 * @ $Author$
 * @ $Revision$
 */

public class NotLoadedImage extends Image
{
	private final int width;
	private final int height;
	private final InputStream stream;

	public NotLoadedImage(int width, int height, InputStream stream)
	{
		this.width = width;
		this.height = height;
		this.stream = stream;
	}

	@Override
	public int getWidth(ImageObserver observer)
	{
		return width;
	}

	@Override
	public int getHeight(ImageObserver observer)
	{
		return height;
	}

	@Override
	public ImageProducer getSource()
	{
		throw new RuntimeException("Реализация не предусмотрена");
	}

	@Override
	public Graphics getGraphics()
	{
		throw new RuntimeException("Реализация не предусмотрена");
	}

	@Override
	public Object getProperty(String name, ImageObserver observer)
	{
		throw new RuntimeException("Реализация не предусмотрена");
	}

	@Override
	public void flush()
	{
		throw new RuntimeException("Реализация не предусмотрена");
	}

	/**
	 * Сохранить изображение в файл.
	 * @param file файл.
	 */
	public void writeToFile(File file) throws IOException
	{
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
		stream.reset();
		BufferedInputStream bis = new BufferedInputStream(stream);
		while (true) {
			int b = bis.read();
			if (b == -1)
				break;
			bos.write(b);
		}
		bis.close();
		bos.close();
	}
}
