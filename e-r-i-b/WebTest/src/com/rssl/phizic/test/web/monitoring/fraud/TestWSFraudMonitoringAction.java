package com.rssl.phizic.test.web.monitoring.fraud;

import com.rssl.phizic.test.web.monitoring.fraud.support.SendOperation;
import com.rssl.phizic.utils.ExceptionUtil;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author khudyakov
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */
public class TestWSFraudMonitoringAction extends LookupDispatchAction
{
	private static final String START_FORWARD           = "Start";
	private static final String CLOSE_FORWARD           = "Close";

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("send", "send");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(START_FORWARD);
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		TestFraudMonitoringForm frm = (TestFraudMonitoringForm) form;

		try
		{
			SendOperation operation = new SendOperation(frm.getRequest());
			operation.send();

			frm.setResponse(operation.getResponse());
		}
		catch (Exception e)
		{
			frm.setError(ExceptionUtil.printStackTrace(e));
		}

		return mapping.findForward(CLOSE_FORWARD);
	}
}
