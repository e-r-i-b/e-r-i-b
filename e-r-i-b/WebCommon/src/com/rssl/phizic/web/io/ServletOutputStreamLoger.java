package com.rssl.phizic.web.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rydvanskiy
 * @ created 13.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class ServletOutputStreamLoger extends ServletOutputStream
{
	private HttpServletResponse delegate;
	private ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

	public ServletOutputStreamLoger(HttpServletResponse hss)
	{
		this.delegate = hss;
	}

	public void write(int c) throws IOException
	{
		delegate.getOutputStream().write(c);
		byteArray.write(c);
	}

	public String getLogMessage() throws UnsupportedEncodingException
	{
		return byteArray.toString("Windows-1251");
	}

}
