package com.rssl.phizic.web.ext.sbrf.mail;

import com.rssl.phizic.web.actions.DownloadOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author gorshkov
 * @ created 17.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class DownloadMailAction extends DownloadOperationalActionBase<byte[]>
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return download(mapping, form, request, response);
    }

	protected void outData(byte[] data, ServletOutputStream outputStream) throws Exception
	{
		outputStream.write(data);
		outputStream.flush();
		outputStream.close();
	}
}
