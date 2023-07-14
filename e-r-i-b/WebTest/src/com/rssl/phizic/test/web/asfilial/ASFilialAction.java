package com.rssl.phizic.test.web.asfilial;

import com.rssl.phizic.test.wsgateclient.asfilial.ASFilialClientReqWrapper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.LookupDispatchAction;
import org.apache.axis.AxisFault;
import org.apache.axis.MessageContext;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.xml.sax.SAXException;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;
import java.text.ParseException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * User: moshenko
 * Date: 18.12.2012
 * Time: 11:03:14
 */
public class ASFilialAction extends LookupDispatchAction
{

	private static final String FORWARD_START = "Start";
	private static final String FORWARD_INFO = "Info";
	private static final String FORWARD_QUERY_PROFILE = "QueryProfileFwd";
	private static final String FORWARD_UPDATE_PROFILE = "UpdateProfileFwd";
	private static final String FORWARD_CONFIRM_PHONE_HOLDER = "ConfirmPhoneHolderFwd";
	private static final String FORWARD_REQUEST_PHONE_HOLDER = "RequestPhoneHolderFwd";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("ConfirmPhoneHolder","confirmPhoneHolder");
		map.put("QueryProfile","queryProfile");
		map.put("UpdateProfile","updateProfile");
		map.put("RequestPhoneHolder","requestPhoneHolder");
		map.put("Back","start");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		return mapping.findForward(FORWARD_START);
	}

	public ActionForward queryProfile(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws ServiceException, RemoteException, ParseException
	{
		ASFilialForm form = (ASFilialForm) frm;

		if (StringHelper.isEmpty(form.getFirstName()))
			return mapping.findForward(FORWARD_QUERY_PROFILE);

		ASFilialClientReqWrapper wrapper = new ASFilialClientReqWrapper(form.getASListenerUrl());

		String messagesText = wrapper.queryProfile(form);
		form.setMessagesText(messagesText);

		return mapping.findForward(FORWARD_INFO);
	}

	public ActionForward updateProfile(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws ServiceException, ParseException, RemoteException
	{
		ASFilialForm form = (ASFilialForm) frm;

		if (form.getFirstName() == null)
			return mapping.findForward(FORWARD_UPDATE_PROFILE);

		ASFilialClientReqWrapper wrapper = new ASFilialClientReqWrapper(form.getASListenerUrl());

		String messagesText = wrapper.updateProfile(form);
		form.setMessagesText(messagesText);

		return mapping.findForward(FORWARD_INFO);
	}

	public ActionForward confirmPhoneHolder(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws ServiceException, ParseException, RemoteException
	{
		ASFilialForm form = (ASFilialForm) frm;

		if (form.getPhoneNumber() == null)
					return mapping.findForward(FORWARD_CONFIRM_PHONE_HOLDER);

		ASFilialClientReqWrapper wrapper = new ASFilialClientReqWrapper(form.getASListenerUrl());

		String messagesText = wrapper.confirmPhoneHolder(form);
		form.setMessagesText(messagesText);

		return mapping.findForward(FORWARD_INFO);

	}

	public ActionForward requestPhoneHolder(ActionMapping mapping, ActionForm frm, HttpServletRequest request, HttpServletResponse response) throws ServiceException, ParseException, RemoteException
	{
		ASFilialForm form = (ASFilialForm) frm;

		if (form.getPhoneNumber() == null)
			return mapping.findForward(FORWARD_REQUEST_PHONE_HOLDER);

		ASFilialClientReqWrapper wrapper = new ASFilialClientReqWrapper(form.getASListenerUrl());

		String messagesText = wrapper.requestPhoneHolder(form);
		form.setMessagesText(messagesText);

		return mapping.findForward(FORWARD_INFO);

	}

	private String getMessages(MessageContext mc)
	{
		String result = null;
		try
		{
			String requestStr = mc.getRequestMessage().getSOAPPartAsString();
			String responseStr = mc.getResponseMessage().getSOAPPartAsString();

			result = XmlHelper.convertDomToText(XmlHelper.parse(requestStr)) + "\n" + XmlHelper.convertDomToText(XmlHelper.parse(responseStr));
		}
		catch (AxisFault axisFault)
		{
			result = axisFault.getMessage();
		}
		catch (IOException e)
		{
			result = e.getMessage();
		}
		catch (SAXException e)
		{
			result = e.getMessage();
		}
		catch (ParserConfigurationException e)
		{
			result = e.getMessage();
		}
		catch (TransformerException e)
		{
			result = e.getMessage();
		}
		return result;
	}

}
