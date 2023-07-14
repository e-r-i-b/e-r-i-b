package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.operations.payment.ConfirmCSAPaymentOperation;
import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthGateSingleton;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 12.08.2009
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmCSAPaymentAction extends ConfirmCSADocumentAction
{
	private static final String FORWARD_SHOW = "Show";


	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> m = new HashMap<String, String>(); //без этого отображение jsp падает :(
		m.put("button.dispatch", "start");                     //зачем оно там надо я не понял
		return m;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmCSAPaymentForm frm = (ConfirmCSAPaymentForm)form;
		AuthConfig config = AuthGateSingleton.getAuthService().getConfig();
		frm.setCSAPath(config.getProperty("csa.password.url") + "?" + request.getQueryString());
		ConfirmCSAPaymentOperation operation = (ConfirmCSAPaymentOperation) getConfirmOperation(request, frm);
		frm.setMetadata(operation.getMetadata());
		frm.setConfirmStrategyType(operation.getStrategyType());
	    frm.setMetadataPath(operation.getMetadataPath());
		return mapping.findForward(FORWARD_SHOW);
	}
}
