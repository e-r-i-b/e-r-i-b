package com.rssl.auth.csa.wsclient.requests.profile;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 10.11.13
 * @ $Author$
 * @ $Revision$
 */
public abstract class FindOrCreateProfileInformationRequestDataBase extends RequestDataBase
{
	private String surName;
	private String firstName;
	private String patrName;
	private String passport;
	private Calendar birthdate;
	private String tb;

	/**
	 * Конструктор
	 * @param surName - фамилия
     * @param firstName - имя
	 * @param patrName - отчество
	 * @param passport - серия и номер документа
	 * @param birthdate - дата рождения
	 * @param tb - тербанк
	 */
	protected FindOrCreateProfileInformationRequestDataBase(String surName, String firstName, String patrName, String passport, Calendar birthdate, String tb)
	{
		this.surName = surName;
		this.firstName = firstName;
		this.patrName = patrName;
		this.passport = passport;
		this.birthdate = birthdate;
		this.tb = tb;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		root.appendChild(createTag(request, RequestConstants.SURNAME_PARAM_NAME, surName));
		root.appendChild(createTag(request, RequestConstants.FIRSTNAME_PARAM_NAME, firstName));
		root.appendChild(createTag(request, RequestConstants.PATRNAME_PARAM_NAME, patrName));
		root.appendChild(createTag(request, RequestConstants.PASSPORT_PARAM_NAME, passport));
		root.appendChild(createTag(request, RequestConstants.BIRTHDATE_PARAM_NAME, XMLDatatypeHelper.formatDateTimeWithoutTimeZone(birthdate)));
		root.appendChild(createTag(request, RequestConstants.TB_TAG, tb));
		return request;
	}
}
