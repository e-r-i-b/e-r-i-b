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
 * @ author: with
 * @ created: 17.06.15
 * @ $Author$
 * @ $Revision$
 */
public class OfferResultTicketTestAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";
	private static final String MSG = "com/rssl/ikfl/crediting/OfferResultTicket.xml";
	private final CRMMessageSender messageSender = new CRMMessageSender();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("send", "send");
		return map;
	}
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		OfferResultTicketTestForm frm = (OfferResultTicketTestForm) form;
		String requestExampleXML = ResourceHelper.loadResourceAsString(MSG, "UTF-8");
		frm.setOfferResultTicketXML(requestExampleXML);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws JAXBException
	{
		OfferResultTicketTestForm frm = (OfferResultTicketTestForm) form;
		try
		{
			messageSender.sendMessageToERIBForLoanClaim( frm.getOfferResultTicketXML());
		}
		catch (Exception e)
		{
			log.error("Сбой при отправке сообщения SearchApplicationRs в ЕРИБ", e);
		}
		return mapping.findForward(FORWARD_START);
	}
}
