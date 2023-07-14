package com.rssl.phizic.web.templates;

import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.GenericServlet;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletException;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 12.02.2007
 * Time: 12:27:03
 * To change this template use File | Settings | File Templates.
 */
public class DownloadTemplateServlet extends GenericServlet
{
    protected byte[] getTemplate(Long id) throws BusinessException
	{
		DocTemplate docTemplate = null;
		byte[] templateByte = null;
		SimpleService service = new SimpleService();

		try
		{
		   docTemplate = service.findById(DocTemplate.class, id);
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}

		if (docTemplate != null)
		   templateByte = docTemplate.getData();

		return templateByte;
	}

	public void service(javax.servlet.ServletRequest servletRequest, javax.servlet.ServletResponse servletResponse) throws javax.servlet.ServletException, java.io.IOException
	{
		Long id = Long.valueOf(servletRequest.getParameter("id"));

	    if (id != null)
	    {
		    byte[] template = null;

		    try
		    {
			   template = getTemplate(id);
		    }
		    catch ( Exception e )
			{
			   throw new ServletException(e);
			}

			if (template != null)
			{
				try
				{
					ByteArrayOutputStream buffer = new ByteArrayOutputStream();
					buffer.write(template);

					servletResponse.setContentLength(buffer.size());
					servletResponse.setContentType("application/octetstream");
//					response.setHeader("Content-Disposition", "attachment; filename=template.doc");
//					response.setHeader("cache-control", "");

					ServletOutputStream outStream = servletResponse.getOutputStream();
					buffer.writeTo(outStream);
					outStream.flush();
					outStream.close();
				}
				catch ( IOException e )
				{
				   throw new ServletException(e);
				}
			}
		}
	}
}
