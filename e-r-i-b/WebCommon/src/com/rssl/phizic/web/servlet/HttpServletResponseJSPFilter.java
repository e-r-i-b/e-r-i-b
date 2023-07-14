package com.rssl.phizic.web.servlet;

import com.rssl.phizic.web.io.JSPServletOutputStream;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * @author Rydvanskiy
 * @ created 12.11.2010
 * @ $Author$
 * @ $Revision$
 *
 * Обертка Response для очистки выходного потока от лишних пустых строк или сжатия данных
 */

public class HttpServletResponseJSPFilter extends HttpServletResponseWrapper
{
	protected ServletOutputStream stream = null;
	protected PrintWriter writer = null;
	protected HttpServletResponse origResponse = null;

	public HttpServletResponseJSPFilter(HttpServletResponse response)
	{
		super(response);
		origResponse = response;
	}

	public ServletOutputStream createOutputStream()
			throws IOException
	{
		return (new JSPServletOutputStream(origResponse));
	}

	public ServletOutputStream getOutputStream()
			throws IOException
	{
		if (writer != null)
		{
			throw new IllegalStateException("getWriter() has already been " +
					"called for this response");
		}

		if (stream == null)
		{
			stream = createOutputStream();
		}

		return stream;
	}

	public PrintWriter getWriter()
			throws IOException
	{
		if (writer != null)
		{
			return writer;
		}

		if (stream != null)
		{
			throw new IllegalStateException("getOutputStream() has already " +
					"been called for this response");
		}

		stream = createOutputStream();
		writer = new PrintWriter(stream);

		return writer;
	}

	public void flushBuffer() throws IOException
	{
		if (writer != null)
			writer.flush();
		super.flushBuffer();
	}
}
