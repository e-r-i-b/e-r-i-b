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

/* Форма запроса согласия на оферту
* @ author: Moshenko
* @ created: 5.06.15
* @ $Author$
* @ $Revision$
*/
public class InitiateConsumerProductOfferRqTestAction  extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";
	private static final String InitiateConsumerProductOfferRq = "com/rssl/ikfl/crediting/InitiateConsumerProductOfferRq.xml";
	private final CRMMessageSender messageSender = new CRMMessageSender();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("send", "send");
		return map;
	}
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		InitiateConsumerProductOfferRqTestForm frm = (InitiateConsumerProductOfferRqTestForm) form;
		String requestExampleXML = ResourceHelper.loadResourceAsString(InitiateConsumerProductOfferRq, "UTF-8");
		frm.setInitiateConsumerProductOfferRqXML(requestExampleXML);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws JAXBException
	{
		InitiateConsumerProductOfferRqTestForm frm = (InitiateConsumerProductOfferRqTestForm) form;
		try
		{
			messageSender.sendMessageToERIBForLoanClaim(frm.getInitiateConsumerProductOfferRqXML());
		}
		catch (Exception e)
		{
			log.error("Сбой при отправке сообщения InitiateConsumerProductOfferRq в ЕРИБ", e);
		}
		return mapping.findForward(FORWARD_START);
	}
}
