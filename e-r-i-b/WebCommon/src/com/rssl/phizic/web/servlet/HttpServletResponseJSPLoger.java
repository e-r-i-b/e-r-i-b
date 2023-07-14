package com.rssl.phizic.web.servlet;

import com.rssl.phizic.web.io.ServletOutputStreamLoger;

import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Rydvanskiy
 * @ created 13.11.2010
 * @ $Author$
 * @ $Revision$
 *
 * ����� �������������� jsp �������
 */

public class HttpServletResponseJSPLoger extends HttpServletResponseJSPFilter
{
	public HttpServletResponseJSPLoger(HttpServletResponse response)
	{
		super(response);
	}

	public ServletOutputStream createOutputStream() throws IOException
	{
		return (new ServletOutputStreamLoger(origResponse));
	}
}
