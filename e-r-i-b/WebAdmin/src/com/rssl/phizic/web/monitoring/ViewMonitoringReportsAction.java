package com.rssl.phizic.web.monitoring;

import com.rssl.phizic.operations.monitoring.GetMonitoringReportValuesOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 01.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ViewMonitoringReportsAction extends OperationalActionBase
{
	protected static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		return null;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewMonitoringReportsForm frm = (ViewMonitoringReportsForm) form;		
		GetMonitoringReportValuesOperation operation = createOperation(GetMonitoringReportValuesOperation.class);
		Long departmentId = frm.getDepartmentId();
		operation.initialize(departmentId);
		frm.setMonitoringReportValues(operation.getMonitoringReportValues());
		frm.setThresholdValueses(operation.getThresholdValueses());
		frm.setDepartmentId(operation.getDepartmentId());
		return mapping.findForward(FORWARD_START);
	}	
}
