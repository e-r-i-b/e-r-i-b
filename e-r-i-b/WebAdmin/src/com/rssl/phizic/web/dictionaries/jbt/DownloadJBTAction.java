package com.rssl.phizic.web.dictionaries.jbt;

import com.rssl.phizic.web.actions.DownloadOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author gorshkov
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class DownloadJBTAction extends DownloadOperationalActionBase<HashMap<String, StringBuilder>>
{
	private static final String ENCODING = "Cp866";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return download(mapping, form, request, response);
    }

	protected void outData(HashMap<String, StringBuilder> data, ServletOutputStream outputStream) throws Exception
	{
		ZipOutputStream zipOutputStream = null;
		try
		{
			zipOutputStream = new ZipOutputStream(outputStream);
			for (String key : data.keySet())
			{
				zipOutputStream.putNextEntry(new ZipEntry(key));
				zipOutputStream.write(data.get(key).toString().getBytes(ENCODING));
			}
			zipOutputStream.flush();
		}
		finally
		{
			if(zipOutputStream != null)
				zipOutputStream.close();
		}
	}
}
