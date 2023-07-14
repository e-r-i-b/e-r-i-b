package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.integration.mobilebank.MobileBankService;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author osminin
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на получение информации
 * Ищем по номеру телефона коннектор ЕРМБ, если есть формируем UserInfo,
 * если коннектора нет, лезем в МБК за номером карты
 * ищем по номеру карты коннекторы, если есть формируем UserInfo,
 * если ни одного не найдено, лезем в МБК за информацией о пользователе
 *
 * Параметры запроса
 * phoneNumber                  Номер телефона                                              [1]
 *
 * Параметры ответа
 * UserInfo	                    Информация о пользователе                                   [1]
 *      firstname               Имя пользователя                                            [1]
 *      patrname                Отчество пользователя                                       [0..1]
 *      surname                 Фамилия пользователя                                        [1]
 *      birthdate               Дата рождения пользователя                                  [1]
 *      passport                ДУЛ пользователя                                            [1]
 *      cbCode                  cbCode пользователя (0 если tb 1)                           [0..1]
 *      tb                      Подразделение пользователя (0 если cbCode 1)                [0..1]
 *
 */
public class GetUserInfoByPhoneWithMBRequestProcessor extends GetUserInfoRequestProcessorBase
{
	private static final String RESPONSE_TYPE = "getUserInfoByPhoneWithMBRs";
	private static final String REQUEST_TYPE  = "getUserInfoByPhoneWithMBRq";

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		Element element = document.getDocumentElement();
		return doRequest(XmlHelper.getSimpleElementValue(element, Constants.PHONE_NUMBER_TAG));
	}

	private ResponseInfo doRequest(String phoneNumber) throws Exception
	{
		Connector connector = Connector.findByPhoneNumber(phoneNumber);
		if (connector != null)
		{
			return createUserAndNodeInfoRs(connector.getProfile());
		}

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

		UserInfo userInfo = MobileBankService.getInstance().getClientByCardNumber(cardNumber);
		if (userInfo == null)
		{
			return getFailureResponseBuilder().buildUserInfoByCardNotFoundResponse(cardNumber);
		}

		CSAUserInfo info = new CSAUserInfo(userInfo, CSAUserInfo.Source.WAY4U);
		Profile profile = Profile.getByUserInfo(info, true);
		if (profile != null)
		{
			return createUserAndNodeInfoRs(profile);
		}
		else
		{
			Profile newProfile = Profile.create(info);
			ProfileNode.create(newProfile);
			return createUserAndNodeInfoRs(newProfile);
		}
	}
}
