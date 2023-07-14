package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author osminin
 * @ created 17.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Запрос на обновление регистраций телефонов
 */
public class UpdatePhoneRegistrationsRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_DATA_NAME = "updatePhoneRegistrationsRq";

	private UserInfo userInfo;
	private String phoneNumber;
	private List<String> addPhones;
	private List<String> removePhones;

	/**
	 * ctor
	 * @param phoneNumber номер телефона
	 * @param userInfo информация о пользователе
	 * @param addPhones добавляемые телефоны
	 * @param removePhones удаляемые телефоны
	 */
	public UpdatePhoneRegistrationsRequestData(String phoneNumber, UserInfo userInfo, List<String> addPhones, List<String> removePhones)
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("информация о пользователе не может быть null");
		}

		this.phoneNumber = phoneNumber;
		this.userInfo = userInfo;
		this.removePhones = removePhones;
		this.addPhones = addPhones;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		Element root = document.getDocumentElement();
		fillUserInfo(XmlHelper.appendSimpleElement(root, RequestConstants.PROFILE_INFO_TAG), userInfo);

		if (StringHelper.isNotEmpty(phoneNumber))
		{
			XmlHelper.appendSimpleElement(root, RequestConstants.MAIN_PHONE_TAG, phoneNumber);
		}

		if (CollectionUtils.isNotEmpty(addPhones))
		{
			XmlHelper.appendSimpleListElement(root, RequestConstants.ADD_PHONES_TAG, RequestConstants.ADD_PHONE_PARAM_NAME, addPhones);
		}

		if (CollectionUtils.isNotEmpty(removePhones))
		{
			XmlHelper.appendSimpleListElement(root, RequestConstants.REMOVE_PHONES_TAG, RequestConstants.REMOVE_PHONE_PARAM_NAME, removePhones);
		}

		return document;
	}
}
