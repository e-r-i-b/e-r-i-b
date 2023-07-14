package com.rssl.phizic.web.dictionaries.provider;

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
public class DownloadProviderReplicationReportAction extends DownloadOperationalActionBase<String>
{
	private static final String CHAR_ENCODING = "cp1251";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return download(mapping, form, request, response);
    }

	protected void outData(String data, ServletOutputStream outputStream) throws Exception
	{
		outputStream.write(data.getBytes(CHAR_ENCODING));
		outputStream.flush();
		outputStream.close();
	}
}
