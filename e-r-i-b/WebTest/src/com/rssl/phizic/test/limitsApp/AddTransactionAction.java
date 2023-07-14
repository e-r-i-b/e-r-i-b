package com.rssl.phizic.test.limitsApp;

import com.rssl.phizic.common.types.limits.Constants;
import com.rssl.phizic.utils.StringHelper;
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
 * @ created 23.01.14
 * @ $Author$
 * @ $Revision$
 */
public class AddTransactionAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";
	private static final JmsService jmsService = new JmsService();

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
		AddTransactionForm frm = (AddTransactionForm) form;
		try
		{
			jmsService.sendMessageToQueue(buildMessage(frm), Constants.QUEUE_NAME, Constants.FACTORY_NAME, null, null);
			frm.setError("Сообщение отправлено.");
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			frm.setError(e.getMessage());
		}

		return mapping.findForward(FORWARD_START);
	}

	private String buildMessage(AddTransactionForm frm) throws Exception
	{
		DocumentBuilder builder = XmlHelper.getDocumentBuilder();
		Document request = builder.newDocument();

		Element root = request.createElement(Constants.ADD_TRANSACTION_REQUEST_NAME);

		Element profileInfo = XmlHelper.appendSimpleElement(root, Constants.PROFILE_INFO_TAG);
		XmlHelper.appendSimpleElement(profileInfo, Constants.FIRST_NAME_TAG, frm.getFirstName());
		XmlHelper.appendSimpleElement(profileInfo, Constants.SUR_NAME_TAG, frm.getSurName());
		XmlHelper.appendSimpleElement(profileInfo, Constants.PATR_NAME_TAG, frm.getPatrName());
		XmlHelper.appendSimpleElement(profileInfo, Constants.PASSPORT_NAME_TAG, frm.getDocSeries() + frm.getDocNumber());

		XmlHelper.appendSimpleElement(profileInfo, Constants.BIRTH_DATE_TAG, frm.getBirthDate());
		XmlHelper.appendSimpleElement(profileInfo, Constants.TB_TAG, frm.getTb());
		root.appendChild(profileInfo);

		Element transaction = XmlHelper.appendSimpleElement(root, Constants.TRANSACTION_TAG);
		XmlHelper.appendSimpleElement(transaction, Constants.EXTERNAL_ID_TAG, frm.getExternalId());
		XmlHelper.appendSimpleElement(transaction, Constants.DOCUMENT_EXTERNAL_ID_TAG, frm.getDocumentExternalId());

		Element amount = XmlHelper.appendSimpleElement(transaction, Constants.AMOUNT_TAG);
		XmlHelper.appendSimpleElement(amount, Constants.AMOUNT_VALUE_TAG, frm.getAmountValue());
		XmlHelper.appendSimpleElement(amount, Constants.AMOUNT_CUR_TAG, frm.getCurrency());

		transaction.appendChild(amount);

		XmlHelper.appendSimpleElement(transaction, Constants.OPERATION_DATE_TAG, frm.getOperationDate());
		XmlHelper.appendSimpleElement(transaction, Constants.CHANNEL_TYPE_TAG, frm.getChannelType());

		transaction.appendChild(createLimitsTag(frm.getLimits(), transaction));
		root.appendChild(transaction);
		request.appendChild(root);

		return XmlHelper.convertDomToText(request);
	}

	private Element createLimitsTag(String data, Element transaction) throws Exception
	{
		Element limits = XmlHelper.appendSimpleElement(transaction, Constants.LIMITS_TAG);

		String[] limitArray = data.split(Constants.LIMIT_DELIMITER);
		for (int i = 0; i < limitArray.length; i++)
		{
			String limitData = limitArray[i];
			String[] limitProperties = limitData.split(Constants.LIMIT_PROPERTY_DELIMITER);

			Element limit = XmlHelper.appendSimpleElement(limits, Constants.LIMIT_TAG);
			XmlHelper.appendSimpleElement(limit, Constants.LIMIT_TYPE_TAG, limitProperties[0]);
			XmlHelper.appendSimpleElement(limit, Constants.RESTRICTION_TYPE_TAG, limitProperties[1]);
			if (limitProperties.length == 3)
			{
				XmlHelper.appendSimpleElement(limit, Constants.EXTERNAL_GROUP_RISK_ID_TAG, StringHelper.getEmptyIfNull(limitProperties[2]));
			}

			limits.appendChild(limit);
		}

		return limits;
	}
}
