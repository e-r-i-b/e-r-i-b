package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.web.client.dictionaries.ConfirmCSAPaymentReceiverAction;
import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.operations.payment.ConfirmCSAPaymentOperation;
import com.rssl.phizic.operations.dictionaries.receivers.ConfirmCSAPaymentReceiverOperation;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;

import java.util.Map;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 08.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class ConfirmCSAReceiverAction extends ConfirmCSAPaymentReceiverAction
{
	private static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> m = new HashMap<String, String>(); //без этого отображение jsp падает :(
		m.put("button.confirm", "start");                     //зачем оно там надо я не понял
		return m;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmCSAPaymentReceiverOperation operation = (ConfirmCSAPaymentReceiverOperation)createOperation();
		
		ConfirmCSAPaymentForm frm = (ConfirmCSAPaymentForm)form;
		AuthConfig config = AuthGateSingleton.getAuthService().getConfig();
		frm.setCSAPath(config.getProperty("csa.password.url") + "?" + request.getQueryString());
		return mapping.findForward(FORWARD_SHOW);
	}
}
