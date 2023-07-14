package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author akrenev
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� �� ���������� ���. ���������� �������
 */

public class UpdateProfileAdditionalDataRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_DATA_NAME = "updateProfileAdditionalDataRq";

	private UserInfo userInfo;
	private CreationType creationType;
	private String agreementNumber;
	private String phone;

	/**
	 * �����������
	 * @param userInfo ����������������� ������ �������
	 * @param creationType ��� ��������
	 * @param agreementNumber ����� ��������
	 * @param phone �������
	 */
	public UpdateProfileAdditionalDataRequestData(UserInfo userInfo, CreationType creationType, String agreementNumber, String phone)
	{
		this.userInfo = userInfo;
		this.creationType = creationType;
		this.agreementNumber = agreementNumber;
		this.phone = phone;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		Element root = document.getDocumentElement();

		fillUserInfo(XmlHelper.appendSimpleElement(root, RequestConstants.USER_INFO_TAG), userInfo);
		fillAdditionalData(XmlHelper.appendSimpleElement(root, RequestConstants.CLIENT_ADDITIONAL_DATA_TAG));

		return document;
	}

	private void fillAdditionalData(Element element)
	{
		XmlHelper.appendSimpleElement(element, RequestConstants.CREATION_TYPE_TAG, StringHelper.getNullIfNull(creationType));
		XmlHelper.appendSimpleElement(element, RequestConstants.AGREEMENT_NUMBER_TAG, agreementNumber);
		XmlHelper.appendSimpleElement(element, RequestConstants.PHONE_NUMBER_PARAM_NAME, phone);
	}
}
