package com.rssl.phizic.test.limitsApp;

import com.rssl.phizic.common.types.limits.Constants;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author osminin
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */
public class RollbackTransactionAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";
	private static final String QUEUE_NAME = "jms/limits/LimitsAppQueue";
	private static final String FACTORY_NAME = "jms/limits/LimitsAppQCF";

	private static final JmsService jmsService = new JmsService();

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>(1);
		map.put("send", "send");
		return map;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		RollbackTransactionForm frm = (RollbackTransactionForm) form;
		try
		{
			jmsService.sendMessageToQueue(buildMessage(frm), QUEUE_NAME, FACTORY_NAME, null, null);
			frm.setError("Сообщение отправлено.");
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			frm.setError(e.getMessage());
		}

		return mapping.findForward(FORWARD_START);
	}

	private String buildMessage(RollbackTransactionForm frm) throws Exception
	{
		DocumentBuilder builder = XmlHelper.getDocumentBuilder();
		Document request = builder.newDocument();

		Element root = request.createElement(Constants.ROLLBACK_TRANSACTION_REQUEST_NAME);

		XmlHelper.appendSimpleElement(root, Constants.EXTERNAL_ID_TAG, frm.getExternalId());
		XmlHelper.appendSimpleElement(root, Constants.DOCUMENT_EXTERNAL_ID_TAG, frm.getDocumentExternalId());
		XmlHelper.appendSimpleElement(root, Constants.OPERATION_DATE_TAG, frm.getOperationDate());

		request.appendChild(root);

		return XmlHelper.convertDomToText(request);
	}
}
