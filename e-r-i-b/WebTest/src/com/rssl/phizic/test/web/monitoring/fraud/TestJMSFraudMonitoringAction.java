package com.rssl.phizic.test.web.monitoring.fraud;

import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author khudyakov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */
public class TestJMSFraudMonitoringAction extends LookupDispatchAction
{
	private static final String START_FORWARD = "Start";

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(START_FORWARD);
	}
}
