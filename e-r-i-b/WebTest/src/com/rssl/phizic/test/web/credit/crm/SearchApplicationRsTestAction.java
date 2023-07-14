package com.rssl.phizic.test.web.credit.crm;

import com.rssl.ikfl.crediting.CRMMessageSender;
import com.rssl.phizic.utils.resources.ResourceHelper;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 * Ёкшн ответа на запрос поиска кредитных предложений(SearchApplicationRs) из ETSM
 * @ author: with
 * @ created: 14.06.15
 * @ $Author$
 * @ $Revision$
 */
public class SearchApplicationRsTestAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";
	private static final String SEARCH_APPLICATION_RS = "com/rssl/ikfl/crediting/SearchApplicationRs.xml";
	private final CRMMessageSender messageSender = new CRMMessageSender();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("send", "send");
		return map;
	}
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SearchApplicationRsTestForm frm = (SearchApplicationRsTestForm) form;
		String requestExampleXML = ResourceHelper.loadResourceAsString(SEARCH_APPLICATION_RS, "UTF-8");
		frm.setSearchApplicationRsXML(requestExampleXML);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws JAXBException
	{
		SearchApplicationRsTestForm frm = (SearchApplicationRsTestForm) form;
		try
		{
			messageSender.sendMessageToERIBForLoanClaim( frm.getSearchApplicationRsXML());
		}
		catch (Exception e)
		{
			log.error("—бой при отправке сообщени€ SearchApplicationRs в ≈–»Ѕ", e);
		}
		return mapping.findForward(FORWARD_START);
	}
}
