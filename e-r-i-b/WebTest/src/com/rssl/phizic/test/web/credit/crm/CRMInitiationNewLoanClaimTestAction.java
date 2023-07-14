package com.rssl.phizic.test.web.credit.crm;

import com.rssl.ikfl.crediting.CRMExtendedLoanClaimMessageMarshaller;
import com.rssl.ikfl.crediting.CRMMessageSender;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBSendETSMApplRq;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 * Экшн инициации из CRM создания через ЕРИБ новой заявки в ETSM
 * @ author: Gololobov
 * @ created: 05.03.15
 * @ $Author$
 * @ $Revision$
 */
public class CRMInitiationNewLoanClaimTestAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";
	private static final String ERIB_SEND_ETSM_APP_RQ_FILE = "com/rssl/phizic/test/web/credit/ERIBSendETSMApplRq.xml";
	private final CRMMessageSender messageSender = new CRMMessageSender();
	private final CRMExtendedLoanClaimMessageMarshaller messageMarshaller = new CRMExtendedLoanClaimMessageMarshaller();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("send", "send");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CRMInitiationNewLoanClaimTestForm frm = (CRMInitiationNewLoanClaimTestForm) form;
		String requestExampleXML = ResourceHelper.loadResourceAsString(ERIB_SEND_ETSM_APP_RQ_FILE, "UTF-8");
		frm.setEribSendETSMApplRqXML(requestExampleXML);
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws JAXBException
	{
		CRMInitiationNewLoanClaimTestForm frm = (CRMInitiationNewLoanClaimTestForm) form;
		String xml = updateEribSendETSMApplRqXML(frm.getEribSendETSMApplRqXML(), frm.getEribSendETSMApplRqUID());
		try
		{
			messageSender.sendMessageToERIBForLoanClaim(xml);
		}
		catch (Exception e)
		{
			log.error("Сбой при отправке сообщения EribSendETSMApplRq в ЕРИБ", e);
		}
		return mapping.findForward(FORWARD_START);
	}

	private String updateEribSendETSMApplRqXML(String xml,String uid) throws JAXBException
	{
		//Десериализация XML-строки в ERIBSendETSMApplRq-запрос с проверкой по xsd-хе
		ERIBSendETSMApplRq request = messageMarshaller.unmarshalInitiationNewLoanClaimRequest(xml);
		if (StringHelper.isNotEmpty(uid))
			request.setRqUID(uid);
		request.setRqTm(Calendar.getInstance());
		return messageMarshaller.marshalInitiationNewLoanClaim(request);
	}
}
