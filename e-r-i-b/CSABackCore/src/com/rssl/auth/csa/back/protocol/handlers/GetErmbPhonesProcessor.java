package com.rssl.auth.csa.back.protocol.handlers;

/**
 * @author Gulov
 * @ created 11.07.14
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.connectors.ERMBConnector;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

/**
 * Обработчик запроса на информацию о коннекторах ЕРМБ
 *
 * Параметры запроса:
 * surname                 Фамилия пользователя                                        [1]
 * firstname               Имя пользователя                                            [1]
 * patrname                Отчество пользователя                                       [1]
 * birthdate               Дата рождения пользователя                                  [1]
 * passports               Список дул пользователя                                     [1-n]
 *
 * Параметры ответа:
 * phones                  Список телефонов пользователя                               [1]
 */
public class GetErmbPhonesProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE = "getErmbPhonesListRq";
	private static final String RESPONSE_TYPE = "getErmbPhonesListRs";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		Element root = document.getDocumentElement();
		String surName = XmlHelper.getSimpleElementValue(root, Constants.SURNAME_TAG);
		String firstName = XmlHelper.getSimpleElementValue(root, Constants.FIRSTNAME_TAG);
		String patrName = XmlHelper.getSimpleElementValue(root, Constants.PATRNAME_TAG);
		Calendar birthDate = XMLDatatypeHelper.parseDateTime(XmlHelper.getSimpleElementValue(root, Constants.BIRTHDATE_TAG));
		Collection<String> passports = Arrays.asList(StringUtils.split(XmlHelper.getSimpleElementValue(root, Constants.PASSPORT_TAG), ','));

		List<ERMBConnector> connectors = ERMBConnector.findNotClosedByClientInAnyTB(surName, firstName, patrName, birthDate, passports);
		Set<String> phones = new HashSet<String>(connectors.size());
		for (ERMBConnector connector : connectors)
			phones.add(connector.getPhoneNumber());

		return getSuccessResponseBuilder().addParameter(Constants.PHONES_TAG, StringUtils.join(phones, ',')).end();
	}
}
