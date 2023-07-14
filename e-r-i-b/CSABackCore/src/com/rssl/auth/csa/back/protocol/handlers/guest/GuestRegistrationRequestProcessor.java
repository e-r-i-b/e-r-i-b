package com.rssl.auth.csa.back.protocol.handlers.guest;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.GuestProfile;
import com.rssl.auth.csa.back.servises.operations.guest.GuestIdentificationContext;
import com.rssl.auth.csa.back.servises.operations.guest.GuestRegistrationOperation;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * Процессор для обработки запроса на регистрацию гостя
 * @author niculichev
 * @ created 31.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestRegistrationRequestProcessor extends RequestProcessorBase
{
	public static final String REQUEST_TYPE = "guestRegistrationRq";
	public static final String RESPONCE_TYPE = "guestRegistrationRs";

	@Override
	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Element body = requestInfo.getBody().getDocumentElement();

		String login = XmlHelper.getSimpleElementValue(body, Constants.LOGIN_TAG);
		String password = XmlHelper.getSimpleElementValue(body, Constants.PASSWORD_TAG);
		Long nodeId = Long.valueOf(XmlHelper.getSimpleElementValue(body, Constants.NODE_ID_TAG));
		String code = XmlHelper.getSimpleElementValue(body, Constants.GUEST_CODE_TAG);
		String phone = XmlHelper.getSimpleElementValue(body, Constants.PHONE_NUMBER_TAG);

		String firstname = XmlHelper.getSimpleElementValue(body, Constants.FIRSTNAME_TAG);
		String partname = XmlHelper.getSimpleElementValue(body, Constants.PATRNAME_TAG);
		String surname = XmlHelper.getSimpleElementValue(body, Constants.SURNAME_TAG);
		String passport = XmlHelper.getSimpleElementValue(body, Constants.PASSPORT_TAG);
		String birthdayStr = XmlHelper.getSimpleElementValue(body, Constants.BIRTHDATE_TAG);
		String cbCode = XmlHelper.getSimpleElementValue(body, Constants.CB_CODE_TAG);
		String tb = StringHelper.isNotEmpty(cbCode) ? Utils.getCutTBByCBCode(cbCode) : null;

		Calendar birthday = null;
		if (StringHelper.isNotEmpty(birthdayStr))
			birthday = XMLDatatypeHelper.parseDateTime(birthdayStr);

		GuestProfile regTempProfile = new GuestProfile(
				phone, Long.valueOf(code), firstname, partname, surname, birthday, passport, tb);

		return registration(phone, login, password, nodeId, regTempProfile);
	}

	protected ResponseInfo registration(String phone, String login, String password, Long nodeId, GuestProfile regTempProfile) throws Exception
	{
		GuestRegistrationOperation operation = createRegistrationOperation(phone);
		GuestProfile profile =  operation.execute(login, password, nodeId, regTempProfile);

		return getSuccessResponseBuilder().addGuestIdInfo(profile.getId()).end().getResponceInfo();
	}

	private GuestRegistrationOperation createRegistrationOperation(String phone) throws Exception
	{
		GuestRegistrationOperation operation = new GuestRegistrationOperation();
		operation.initialize(phone);
		return operation;
	}
}
