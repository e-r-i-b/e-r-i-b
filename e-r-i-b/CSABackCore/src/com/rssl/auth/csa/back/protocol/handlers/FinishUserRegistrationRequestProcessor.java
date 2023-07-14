package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.rsa.FraudMonitoringSendersFactory;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.UserRegistrationOperation;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.EnrollInitializationData;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import java.util.Map;
import java.util.TreeMap;

import static com.rssl.phizic.context.Constants.*;
import static com.rssl.phizic.context.Constants.PAGE_ID_PARAMETER_NAME;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * ���������� ������� �� ���������� ����������� ������������ finishUserRegistrationRq
 *
 * ���������� ������� ����������� ������� �� �������:
 * 1) ������ �������� �����������, ����� ����� ����� �����.
 * 2) ���� ������ startUserRegistrationRq � ��� BACK
 * 3) ���������� ������������� ������������, �������� ����������� ����������� � ������� ��� ���� �� ������������� ��������
 * 4) ������������ ������ ��� �������������
 * 5) ���� ������ confirmOperationRq �� ������������� ��������
 * 6) ����� ��������� ������������� ������������ ������������ ��������� ����������� ������ ������ � ������
 * 7) ���� ������ finishUserRegistrationRq c ��������� ������ � ������
 * 8) BACK �� ������������ ������������(��������� �������� �����������).
 *
 * ��������� �������:
 * OUID		            ������������� ��������.     [1]
 * Login		        ����� ������������. 	    [1]
 * Password		        ������ ������������	        [1]
 * NeedNotification     ������������� ����������.   [0-1]
 *
 * ��������� ������:
 */

public class FinishUserRegistrationRequestProcessor extends LogableProcessorBase
{
	private static final String REQUEST_TYPE = "finishUserRegistrationRq";
	private static final String RESPONCE_TYPE = "finishUserRegistrationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		trace("��������� ������� ������");
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		String login = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.LOGIN_TAG);
		String needNotification = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.NEED_NOTIFICATION_TAG);

		String password = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PASSWORD_TAG);
		//finishUserRegistration
		final UserRegistrationOperation registrationOperation = findOperation(ouid, context);
		trace("������ " + registrationOperation.getOuid() + " ������� ��������. �������� �� ��.");
		doFraudControl(registrationOperation, requestInfo);
		trace("������ " + registrationOperation.getOuid() + " ������� ��������. ��������� ��.");
		Connector connector = registrationOperation.execute(login, password, StringHelper.isEmpty(needNotification) || Boolean.parseBoolean(needNotification));
		info("������ " + registrationOperation.getOuid() + " ������� ���������.");

		// ���� ����� ����� ����������� ��������� ����
		Config config = ConfigFactory.getConfig(Config.class);
		if(config.isPostRegistrationLogin())
		{
			info("������ �� ���������� ���������� " + connector.getGuid());
			return new LogableResponseInfo(prepareLogon(context, connector));
		}

		return new LogableResponseInfo(buildSuccessResponse());
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String ouid = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.OUID_TAG);
		return LogIdentificationContext.createByOperationUID(ouid);
	}

	protected UserRegistrationOperation findOperation(String ouid, final IdentificationContext identificationContext) throws Exception
	{
		return identificationContext.findOperation(UserRegistrationOperation.class, ouid, UserRegistrationOperation.getLifeTime());
	}

	protected void doFraudControl(UserRegistrationOperation operation, RequestInfo requestInfo) throws ProhibitionOperationFraudGateException
	{
		try
		{
			initialize(requestInfo);

			FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.ENROLL);
			//noinspection unchecked
			sender.initialize(new EnrollInitializationData(operation.getUserId(), operation.getProfile().getTb(), requestInfo.getIP(), operation.getProfileId()));
			sender.send();
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			throw e;
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}
		finally
		{
			RSAContext.clear();
			HeaderContext.clear();
		}
	}

	private void initialize(RequestInfo requestInfo)
	{
		Map<String, String> data = getContextData(requestInfo);
		RSAContext.initialize(data);
		HeaderContext.initialize(data);
	}

	private Map<String, String> getContextData(RequestInfo requestInfo)
	{
		Map<String, String> map = new TreeMap<String, String>();

		Node rsaSource = requestInfo.getBody().getDocumentElement().getElementsByTagName(RSA_DATA_NAME).item(0);
		map.put(DOM_ELEMENTS_PARAMETER_NAME,        XmlHelper.getSimpleElementValue((Element) rsaSource, DOM_ELEMENTS_PARAMETER_NAME));
		map.put(JS_EVENTS_PARAMETER_NAME,           XmlHelper.getSimpleElementValue((Element) rsaSource, JS_EVENTS_PARAMETER_NAME));
		map.put(DEVICE_PRINT_PARAMETER_NAME,        XmlHelper.getSimpleElementValue((Element) rsaSource, DEVICE_PRINT_PARAMETER_NAME));
		map.put(DEVICE_TOKEN_FSO_PARAMETER_NAME,    XmlHelper.getSimpleElementValue((Element) rsaSource, DEVICE_TOKEN_FSO_PARAMETER_NAME));
		map.put(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, XmlHelper.getSimpleElementValue((Element) rsaSource, DEVICE_TOKEN_COOKIE_PARAMETER_NAME));

		Node headerSource = requestInfo.getBody().getDocumentElement().getElementsByTagName(HEADER_DATA_NAME).item(0);
		map.put(HTTP_ACCEPT_HEADER_NAME,            XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_HEADER_NAME));
		map.put(HTTP_ACCEPT_CHARS_HEADER_NAME,      XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_CHARS_HEADER_NAME));
		map.put(HTTP_ACCEPT_ENCODING_HEADER_NAME,   XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_ENCODING_HEADER_NAME));
		map.put(HTTP_ACCEPT_LANGUAGE_HEADER_NAME,   XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		map.put(HTTP_REFERRER_HEADER_NAME,          XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_REFERRER_HEADER_NAME));
		map.put(HTTP_USER_AGENT_HEADER_NAME,        XmlHelper.getSimpleElementValue((Element) headerSource, HTTP_USER_AGENT_HEADER_NAME));
		map.put(PAGE_ID_PARAMETER_NAME,             XmlHelper.getSimpleElementValue((Element) headerSource, PAGE_ID_PARAMETER_NAME));

		return map;
	}
}
