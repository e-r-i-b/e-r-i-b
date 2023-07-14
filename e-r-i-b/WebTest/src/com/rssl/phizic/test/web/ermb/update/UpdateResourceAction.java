package com.rssl.phizic.test.web.ermb.update;

import com.rssl.phizic.messaging.ermb.ErmbJndiConstants;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.utils.xml.XMLMessageWriter;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Экшн заглушки отправки оповещений об изменении продукта клиента в ЕРМБ
 * @author Rtischeva
 * @ created 01.04.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateResourceAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";

	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";

	private static final JmsService jmsService = new JmsService();

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("send", "send");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response)
	{
		UpdateResourceForm form = (UpdateResourceForm) frm;
		try
		{
		    String message = buildMessageXml(form);
			jmsService.sendMessageToQueue(message, ErmbJndiConstants.ERMB_QUEUE, ErmbJndiConstants.ERMB_QCF, null, null);
		}
		catch (JMSException e)
		{
			log.error(e.getMessage(), e);
		}

		return mapping.findForward(FORWARD_START);
	}

	private String buildMessageXml(UpdateResourceForm form)
	{
		DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);

		Date rqTime = Calendar.getInstance().getTime();
		RandomGUID rqUID = new RandomGUID();

		XMLMessageWriter writer = new XMLMessageWriter("UTF-8");

		writer.startDocument();
		writer.startElement("UpdateResourceRq");
		{
			writer.writeTextElement("rqVersion", "1.0");
			writer.writeTextElement("rqUID", rqUID.getStringValue());
			writer.writeTextElement("rqTime", df.format(rqTime));
			writer.startElement("profile");
			{
				writer.startElement("clientIdentity");
				{
					writer.writeTextElement("lastname", form.getLastname());
					writer.writeTextElement("firstname", form.getFirstname());
					writer.writeTextElement("middlename", form.getMiddlename());
					writer.writeTextElement("birthday", form.getBirthday());
					writer.startElement("identityCard");
					{
						writer.writeTextElement("idType", form.getIdType());
						writer.writeTextElement("idSeries",  form.getIdSeries());
						writer.writeTextElement("idNum", form.getIdNum());
						writer.writeTextElement("issuedBy",  form.getIssuedBy());
						writer.writeTextElement("issueDt", form.getIssueDt());
					}
					writer.endElement();
					writer.writeTextElement("tb", StringHelper.addLeadingZeros(form.getTb(), 3));
				}
				writer.endElement();
				writer.startElement("resource");
				{
					writer.writeTextElement("type", form.getResourceType());
					if(form.getResourceType().equals("account"))
						writer.writeTextElement("account", form.getResourceNumber());
					else if(form.getResourceType().equals("card"))
						writer.writeTextElement("card", form.getResourceNumber());
					else if(form.getResourceType().equals("loan"))
						writer.writeTextElement("loan", form.getResourceNumber());
				}
				writer.endElement();
				writer.writeTextElement("tb", form.getProductTb());
			}
			writer.endElement();
		}
		writer.endElement();
		writer.endDocument();
		return writer.toString();
	}
}
