package com.rssl.phizic.test.web.credit;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.utils.jms.JmsService;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.struts.action.*;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * Ёкшен дл€ теста смены статуса за€вки
 */
public class LoanClaimTestAction extends LookupDispatchAction
{
	private static final String FORWARD_START = "Start";

	private static final int JMS_TEXT_MESSAGE_MAX_SIZE = 65535;

	private final JmsService jmsService = new JmsService();

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
		try
		{
			sendResponse(makeResponseXML((LoanClaimTestForm)frm));
		}
		catch (Exception e)
		{
			log.error("Ќе удалось отправить запрос: невалидные данные на форме.", e);
		}

		return mapping.findForward(FORWARD_START);
	}

	private void sendResponse(String responseXml)
	{
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
		String queueName = loanClaimConfig.getEsbCreditBackQueueName();
		String qcfName = loanClaimConfig.getEsbCreditBackQCFName();

		try
		{
			byte[] responseXMLBytes = responseXml.getBytes("UTF-8");
			if (responseXMLBytes.length > JMS_TEXT_MESSAGE_MAX_SIZE)
				jmsService.sendBytesToQueue(responseXMLBytes, queueName, qcfName, null, null);
			else jmsService.sendMessageToQueue(responseXml, queueName, qcfName, null, null);
		}
		catch (JMSException e)
		{
			throw new RuntimeException("—бой на отправке ответа из ETSM в очередь", e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new RuntimeException("—бой на отправке ответа из ETSM в очередь", e);
		}
	}

	private String makeResponseXML(LoanClaimTestForm form)
	{
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);

		if (loanClaimConfig.isUseXSDRelease19Version())
		{
			ETSMLoanClaimResponseBuilderRelease19 responseBuilder = new ETSMLoanClaimResponseBuilderRelease19();
			com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.StatusLoanApplicationRq response= responseBuilder.makeResponse(form);
			return makeRelease19EtsmXml(response);
		}
		if (loanClaimConfig.isUseXSDRelease16Version())
		{
			ETSMLoanClaimResponseBuilderRelease16 responseBuilder = new ETSMLoanClaimResponseBuilderRelease16();
			com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.StatusLoanApplicationRq response= responseBuilder.makeResponse(form);
			return makeRelease16EtsmXml(response);
		}
		else
		{
			ETSMLoanClaimResponseBuilderRelease13 responseBuilder = new ETSMLoanClaimResponseBuilderRelease13();
			com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq response= responseBuilder.makeResponse(form);
			return makeRelease13EtsmXml(response);
		}
	}

	private String makeRelease16EtsmXml(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.StatusLoanApplicationRq response)
	{
		try
		{
			JAXBContext jaxbContext2 = JAXBContext.newInstance(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.StatusLoanApplicationRq.class);
			StringWriter writer = new StringWriter();
			Marshaller marshaller = jaxbContext2.createMarshaller();
			marshaller.marshal(response, writer);
			return writer.toString();
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("—бой на упаковке статуса за€вки в XML", e);
		}
	}

	private String makeRelease13EtsmXml(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq response)
	{
		try
		{
			JAXBContext jaxbContext2 = JAXBContext.newInstance(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq.class);
			StringWriter writer = new StringWriter();
			Marshaller marshaller = jaxbContext2.createMarshaller();
			marshaller.marshal(response, writer);
			return writer.toString();
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("—бой на упаковке статуса за€вки в XML", e);
		}
	}

	private String makeRelease19EtsmXml(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.StatusLoanApplicationRq response)
	{
		try
		{
			JAXBContext jaxbContext2 = JAXBContext.newInstance(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.StatusLoanApplicationRq.class);
			StringWriter writer = new StringWriter();
			Marshaller marshaller = jaxbContext2.createMarshaller();
			marshaller.marshal(response, writer);
			return writer.toString();
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("—бой на упаковке статуса за€вки в XML", e);
		}
	}
}
