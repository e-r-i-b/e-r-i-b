package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.web.actions.DownloadOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ author gorshkov
 * @ created 16.07.2013
 * @ $Author$
 * @ $Revision$
 * Ёкшен дл€ выгрузки данных по отчету: "ќтчет о количестве клиентов iOS"
 */
public class DownloadCountIOSReportAction extends DownloadOperationalActionBase<byte[]>
{
	private static final String CSV = ".csv";
	
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return download(mapping, form, request, response);
    }

	protected void outData(byte[] data, ServletOutputStream outputStream) throws Exception
	{
		ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);
		try
		{
			zipOutputStream.putNextEntry(new ZipEntry("report" + CSV));
			zipOutputStream.write(data);
			zipOutputStream.flush();
		}
		finally
		{
			zipOutputStream.close();
		}
	}

}
