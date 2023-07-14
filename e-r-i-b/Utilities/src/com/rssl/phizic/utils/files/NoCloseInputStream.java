package com.rssl.phizic.utils.files;

import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Balovtsev
 * @version 25.10.13 12:37
 */
public class NoCloseInputStream extends FilterInputStream
{
	public NoCloseInputStream(InputStream in)
	{
		super(in);
	}

	@Override
	public synchronized void reset() throws IOException
	{
		if (in instanceof FileInputStream)
		{
			((FileInputStream) in).getChannel().position(0);
			return;
		}

		super.reset();
	}

	@Override
	public void close() throws IOException {}

	/**
	 * Закрываем с помощью даноого метода
	 */
	public void closeStream()
	{
		try
		{
			super.close();
		}
		catch (IOException ignored) {}
	}
}
