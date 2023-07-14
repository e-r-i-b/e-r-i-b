package com.rssl.phizic.test.web.ermb.mbproviders;

import com.rssl.phizic.test.mbproviders.dictionary.MBProvidersReqWrapper;
import com.rssl.phizic.test.mbproviders.dictionary.generated.GetProvidersRsType;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

/**
 * @author EgorovaA
 * @ created 12.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ProvidersAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("send", "send");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws ServiceException
	{
		ProvidersForm form = (ProvidersForm) frm;
		form.setSubmit(true);
		MBProvidersReqWrapper wrapper = new MBProvidersReqWrapper();
		GetProvidersRsType res = wrapper.getProviders();
		form.setServiceProvidersInfo(wrapper.buildMessageXml(res));

		return mapping.findForward(FORWARD_START);
	}
}
