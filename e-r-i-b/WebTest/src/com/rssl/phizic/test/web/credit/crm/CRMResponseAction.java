package com.rssl.phizic.test.web.credit.crm;

import com.rssl.ikfl.crediting.CRMMessageMarshaller;
import com.rssl.ikfl.crediting.CRMMessageSender;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetCampaignerInfoRs;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 * User: Moshenko
 * Date: 16.01.15
 * Time: 13:19
 * Взаимодействие с "Розничным" CRM
 */
public class CRMResponseAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";

	private static final String GET_OFFERS_RESPONSE_EX01_FILE = "com/rssl/ikfl/crediting/GetCampaignerInfoRs.ex01.xml";

	private final CRMMessageMarshaller messageMarshaller = new CRMMessageMarshaller();

	private final CRMMessageSender messageSender = new CRMMessageSender();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("send", "send");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CRMResponseForm frm = (CRMResponseForm) form;
		String responseExampleXML = ResourceHelper.loadResourceAsString(GET_OFFERS_RESPONSE_EX01_FILE, "UTF-8");
		frm.setGetCampaignerInfoRsXML(responseExampleXML);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws JAXBException
	{
		CRMResponseForm frm = (CRMResponseForm) form;
		String xml = updateGetCampaignerInfoRsXml(frm.getGetCampaignerInfoRsXML(), frm.getGetCampaignerInfoRqUID());
		try
		{
			messageSender.sendMessageToERIBForOffers(xml);
		}
		catch (Exception e)
		{
			log.error("Сбой при отправке сообщения GetCampaignerInfoRs в ЕРИБ", e);
		}
		return mapping.findForward(FORWARD_START);
	}


	private String updateGetCampaignerInfoRsXml(String xml,String uid) throws JAXBException
	{
		GetCampaignerInfoRs response = messageMarshaller.unmarshalOfferResponse(xml);
		if (StringHelper.isNotEmpty(uid))
			response.setRqUID(uid);
		response.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
		return messageMarshaller.marshalOfferResponse(response);
	}
}
