package com.rssl.phizic.test.webgate.dasreda;

import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.axis.Message;
import org.apache.axis.transport.http.AxisServlet;
import org.apache.axis.transport.http.HTTPConstants;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.*;

/**
 * @author akrenev
 * @ created 21.08.13
 * @ $Author$
 * @ $Revision$
 *
 * —ервлет ответа на запросы в деловую среду
 */

public class BusinessEnvironmentServlet extends AxisServlet
{
	private static final String RESPONSE_CONTENT_TYPE = "text/xml; charset=utf-8";

	private static final String DS_NAMESPACE = "http://siebel.com/CustomUI";
	private static final String NAMESPACE_PREFIX = "ns";
	private static final String DEFAULT_NAMESPACE_PREFIX = "xmls";

	private static final String VERIFY_DS_PROFILE_OUTPUT_TAG_NAME = "VerifyDSProfile_Output";
	private static final String VERIFY_DS_PROFILE_RS_TAG_NAME = "VerifyDSProfileRs";
	private static final String RQ_UID_TAG_NAME = "RqUID";
	private static final String RQ_TM_TAG_NAME = "RqTm";
	private static final String OPER_UID_TAG_NAME = "OperUID";
	private static final String SYSTEM_ID_TAG_NAME = "SystemId";
	private static final String STATUS_TAG_NAME = "Status";
	private static final String STATUS_CODE_TAG_NAME = "StatusCode";
	private static final String STATUS_DESC_TAG_NAME = "StatusDesc";

	private static final String OK_STATE = "0";
	private static final String DS_SYSTEM_ID = "DSREDA";

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		try
		{
			SOAPMessage response = getResponseMessage(req);
			res.setContentType(RESPONSE_CONTENT_TYPE);
			response.writeTo(res.getOutputStream());
		}
		catch (SOAPException e)
		{
			logException(e);
		}
		finally
		{
			res.flushBuffer();
		}
	}

	private Pair<String, String> getState() throws SOAPException
	{
		return new Pair<String, String>(OK_STATE, null);
	}

	private SOAPMessage getResponseMessage(HttpServletRequest req) throws IOException, SOAPException
	{
		Message requestMsg = new Message(req.getInputStream(),
										 false,
										 req.getHeader(HTTPConstants.HEADER_CONTENT_TYPE),
										 req.getHeader(HTTPConstants.HEADER_CONTENT_LOCATION));

		Element requestElement = requestMsg.getSOAPPart().getEnvelope().getOwnerDocument().getDocumentElement();

		SOAPMessage response = MessageFactory.newInstance().createMessage();
		SOAPEnvelope envelope = response.getSOAPPart().getEnvelope();
		SOAPBody soapBody = envelope.getBody();
		Name bodyName = envelope.createName(VERIFY_DS_PROFILE_OUTPUT_TAG_NAME, NAMESPACE_PREFIX, DS_NAMESPACE);
		SOAPBodyElement verifyDSProfile_Output = soapBody.addBodyElement(bodyName);
		SOAPElement verifyDSProfileRqElement = verifyDSProfile_Output.addChildElement(VERIFY_DS_PROFILE_RS_TAG_NAME);
		verifyDSProfileRqElement.addAttribute(envelope.createName(DEFAULT_NAMESPACE_PREFIX), DS_NAMESPACE);

		SOAPElement rqUID = verifyDSProfileRqElement.addChildElement(RQ_UID_TAG_NAME);
		rqUID.addTextNode(XmlHelper.getSimpleElementValue(requestElement, RQ_UID_TAG_NAME));

		SOAPElement rqTm = verifyDSProfileRqElement.addChildElement(RQ_TM_TAG_NAME);
		rqTm.addTextNode(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));

		SOAPElement operUID = verifyDSProfileRqElement.addChildElement(OPER_UID_TAG_NAME);
		operUID.addTextNode(XmlHelper.getSimpleElementValue(requestElement, OPER_UID_TAG_NAME));

		SOAPElement systemId = verifyDSProfileRqElement.addChildElement(SYSTEM_ID_TAG_NAME);
		systemId.addTextNode(DS_SYSTEM_ID);

		Pair<String, String> state = getState();

		SOAPElement status = verifyDSProfileRqElement.addChildElement(STATUS_TAG_NAME);
		SOAPElement statusCode = status.addChildElement(STATUS_CODE_TAG_NAME);
		statusCode.addTextNode(state.getFirst());

		if (!OK_STATE.equals(state.getFirst()))
		{
			SOAPElement statusDesc = status.addChildElement(STATUS_DESC_TAG_NAME);
			statusDesc.addTextNode(state.getSecond());
		}

		return response;
	}
}
