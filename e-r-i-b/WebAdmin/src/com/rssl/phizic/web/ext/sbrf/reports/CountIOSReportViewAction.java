package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.ext.sbrf.reports.ShowCountIOSReportOperation;
import com.rssl.phizic.web.actions.UnloadOperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 25.11.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ёкшен дл€ просмотра и выгрузки данных по отчету: "ќтчет о количестве клиентов iOS"
 */
public class CountIOSReportViewAction extends UnloadOperationalActionBase<byte[]>
{
	private static final String FORWARD_START = "Start";
	private static final String CSV = ".csv";
	private static final String ZIP = ".zip";

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String,String> map = new HashMap<String, String>();
		map.put("button.countIOSReport.unload", "unload");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ReportViewForm frm = (ReportViewForm) form;
		ShowCountIOSReportOperation operation = createOperation(ShowCountIOSReportOperation.class);
		operation.initialize(frm.getId());
		frm.setReportAbstract(operation.getReportAbstract());
		frm.setReportMap(operation.getReportMap());
		return mapping.findForward(FORWARD_START);
	}	

	public Pair<String,byte[]> createData(ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ReportViewForm frm = (ReportViewForm) form;
		ShowCountIOSReportOperation operation = createOperation(ShowCountIOSReportOperation.class);
		operation.initialize(frm.getId());
		return new Pair<String,byte[]>(operation.getUnloadFileName() + ZIP ,operation.getUnloadedData());
	}

	public ActionForward actionAfterUnload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return start(mapping,form,request,response);
	}
}
