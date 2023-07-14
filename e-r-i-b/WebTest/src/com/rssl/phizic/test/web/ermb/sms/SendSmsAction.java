package com.rssl.phizic.test.web.ermb.sms;

import com.rssl.phizic.messaging.ermb.ErmbJndiConstants;
import com.rssl.phizic.utils.RandomGUID;
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
 * @author Erkin
 * @ created 27.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class SendSmsAction extends LookupDispatchAction
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

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward send(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response)
	{
		final SendSmsForm form = (SendSmsForm) frm;
		final String message = buildMessageXml(form);
		for (int i = 0; i < form.getThreadCount(); i++)
		{
			new Thread(new Runnable()
			{
				public void run()
				{
					try
					{
						boolean isCSAApplication = form.getApplication().equals("csa");
						String queueName = isCSAApplication ? TestSmsConstants.CSA_SMS_MESSAGE_QUEUE : ErmbJndiConstants.SMS_MESSAGE_QUEUE;
						String factoryName = isCSAApplication ? TestSmsConstants.CSA_SMS_MESSAGE_FACTORY : ErmbJndiConstants.SMS_MESSAGE_CQF;

						jmsService.sendMessageToQueue(message, queueName, factoryName, null, null);
					}
					catch (JMSException e)
					{
						log.error(e.getMessage(), e);
					}
				}
			}).start();
		}
		return mapping.findForward(FORWARD_START);
	}

	private String buildMessageXml(SendSmsForm form)
	{
		DateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);

		Date rqTime = Calendar.getInstance().getTime();
		RandomGUID rqUID = new RandomGUID();

		XMLMessageWriter writer = new XMLMessageWriter("UTF-8");

		writer.startDocument();
		writer.startElement("SMSRq");
		writer.writeTextElement("rqVersion", "1.0");
		writer.writeTextElement("rqUID", rqUID.getStringValue());
		writer.writeTextElement("rqTime", df.format(rqTime));
		writer.writeTextElement("phone", form.getPhone());
		writer.writeTextElement("text", form.getText());
		writer.endElement();
		writer.endDocument();

		return writer.toString();
	}
}
