package com.rssl.phizic.web.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;

/**
 * @author Erkin
 * @ created 13.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ByteArrayServletOutputStream extends ServletOutputStream
{
	private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

	public void write(int b) throws IOException
	{
		byteArrayOutputStream.write(b);
	}

	public ByteArrayOutputStream getByteArrayOutputStream()
	{
		return byteArrayOutputStream;
	}
}
