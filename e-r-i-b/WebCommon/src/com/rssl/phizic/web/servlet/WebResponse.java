package com.rssl.phizic.web.servlet;

import com.rssl.phizic.web.io.ByteArrayServletOutputStream;
import com.rssl.phizic.utils.StringHelper;

import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.ByteArrayOutputStream;
import java.util.Locale;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;

/**
 * @author Erkin
* @ created 07.06.2012
* @ $Author$
* @ $Revision$
*/

/**
 * Сохраняет сервлет-респонс в строку
 */
public class WebResponse extends HttpServletResponseWrapper
{
	private final StringWriter sw = new StringWriter();

	private final ByteArrayServletOutputStream sos = new ByteArrayServletOutputStream();

	private boolean writerUsed = false;

	private boolean streamUsed = false;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param delegate
	 */
	public WebResponse(HttpServletResponse delegate)
	{
		super(delegate);
	}

	@Override
	public PrintWriter getWriter()
	{
		if (streamUsed)
			throw new IllegalStateException("getOutputStream() has already been called for this response");
		writerUsed = true;
		return new PrintWriter(sw);
	}

	@Override
	public ServletOutputStream getOutputStream()
	{
		if (writerUsed)
			throw new IllegalStateException("getWriter() has already been called for this response");
		streamUsed = true;
		return sos;
	}

	@Override
	public void setContentType(String s)
	{
	}

	@Override
	public void setLocale(Locale locale)
	{
	}

	/**
	 * Возвращает сервлет-респонс в виде строки
	 * @return строка с сервлет-респонсом
	 */
	public String getString() throws UnsupportedEncodingException
	{
		if (writerUsed)
			return sw.toString();

		if (streamUsed)
		{
			ByteArrayOutputStream bos = sos.getByteArrayOutputStream();
			String charEncoding = getCharacterEncoding();
			if (!StringHelper.isEmpty(charEncoding))
				return bos.toString(charEncoding);
			else return bos.toString();
		}

		return "";
	}
}
