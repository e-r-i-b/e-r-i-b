package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.integration.mobilebank.MobileBankService;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author osminin
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������� �� ��������� ����������
 * ���� �� ������ �������� ��������� ����, ���� ���� ��������� UserInfo,
 * ���� ���������� ���, ����� � ��� �� ������� �����
 * ���� �� ������ ����� ����������, ���� ���� ��������� UserInfo,
 * ���� �� ������ �� �������, ���������� ������
 *
 * ��������� �������
 * phoneNumber                  ����� ��������                                              [1]
 *
 * ��������� ������
 * UserInfo	                    ���������� � ������������                                   [1]
 *      firstname               ��� ������������                                            [1]
 *      patrname                �������� ������������                                       [0..1]
 *      surname                 ������� ������������                                        [1]
 *      birthdate               ���� �������� ������������                                  [1]
 *      passport                ��� ������������                                            [1]
 *      tb                      ������������� ������������                                  [1]
 *
 */
public class GetUserInfoByPhoneRequestProcessor extends GetUserInfoRequestProcessorBase
{
	private static final String RESPONSE_TYPE = "getUserInfoByPhoneRs";
	private static final String REQUEST_TYPE  = "getUserInfoByPhoneRq";

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
		Element element = document.getDocumentElement();
        String phoneNumber = XmlHelper.getSimpleElementValue(element, Constants.PHONE_NUMBER_TAG);
		boolean usingCardByPhone = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(element, Constants.USING_CARD_BY_PHONE_TAG));

		return doRequest(phoneNumber, usingCardByPhone);
	}

	protected ResponseInfo doRequest(String phoneNumber, boolean usingCardByPhone) throws Exception
	{
		Connector connector = Connector.findByPhoneNumber(phoneNumber);
		if (connector != null)
		{
			return createUserAndNodeInfoRs(connector.getProfile());
		}

		if(usingCardByPhone)
		{
			return createUserInfoByCard(phoneNumber);
		}

		return getFailureResponseBuilder().buildUserInfoByPhoneNotFoundResponse(phoneNumber);
	}

	private ResponseInfo createUserInfoByCard(String phoneNumber) throws Exception
	{
		String cardNumber = MobileBankService.getInstance().getCardByPhone(phoneNumber);
		if (StringHelper.isEmpty(cardNumber))
		{
			return getFailureResponseBuilder().buildCardByPhoneNotFoundResponse(phoneNumber);
		}

		List<Connector> connectors = Connector.findByCardNumber(cardNumber);
		if (CollectionUtils.isNotEmpty(connectors))
		{
			return createUserAndNodeInfoRs(connectors.get(0).getProfile());
		}

		return getFailureResponseBuilder().buildUserInfoByCardNotFoundResponse(cardNumber);
	}
}
