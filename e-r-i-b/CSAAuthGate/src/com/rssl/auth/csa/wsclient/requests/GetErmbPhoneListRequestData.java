package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

/**
 * @author Gulov
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */
public class GetErmbPhoneListRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "getErmbPhonesListRq";

	private final String surName;
	private final String firstName;
	private final String patrName;
	private final Calendar birthdate;
	private final Collection<String> passport;

	/**
	 * ctor
	 * @param surName
	 * @param firstName
	 * @param patrName
	 * @param birthdate
	 * @param passport
	 */
	public GetErmbPhoneListRequestData(String surName, String firstName, String patrName, Calendar birthdate, Collection<String> passport)
	{
		this.surName = surName;
		this.firstName = firstName;
		this.patrName = patrName;
		this.birthdate = birthdate;
		this.passport = new ArrayList<String>(passport);
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		root.appendChild(createTag(request, RequestConstants.SURNAME_PARAM_NAME, surName));
		root.appendChild(createTag(request, RequestConstants.FIRSTNAME_PARAM_NAME, firstName));
		root.appendChild(createTag(request, RequestConstants.PATRNAME_PARAM_NAME, patrName));
		root.appendChild(createTag(request, RequestConstants.BIRTHDATE_PARAM_NAME, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(birthdate)));
		root.appendChild(createTag(request, RequestConstants.PASSPORT_PARAM_NAME, StringUtils.join(passport, ',')));
		return request;
	}
}
