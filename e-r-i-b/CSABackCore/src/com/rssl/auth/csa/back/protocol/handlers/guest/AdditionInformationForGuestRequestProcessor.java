package com.rssl.auth.csa.back.protocol.handlers.guest;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.integration.mobilebank.MobileBankService;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.protocol.handlers.RequestProcessorBase;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.SyncUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * Обработчик запроса на получение дополнительной информации для гостя:
 * <ol>
 *     <li>Подключён ли телефон к МБ</li>
 *     <li>Есть ли в ЦСА основная учётная запись</li>
 * </ol>
 *
 * @author usachev
 * @ created 29.04.15
 * @ $Author$
 * @ $Revision$
 */
public class AdditionInformationForGuestRequestProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE = "getGuestAdditionInformationRq";
	private static final String RESPONSE_TYPE = "getGuestAdditionInformationRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		String phone = XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.PHONE_NUMBER_TAG);

		boolean hasPhoneInMB = false;
		Long clientProfileId = null;

		Long profileId = Connector.getProfileIdByPhoneNumber(phone);
		if (profileId != null && Connector.isExistNotClosedTerminalAndCSAByProfileID(profileId))
		{
			hasPhoneInMB = true;
			clientProfileId = profileId;
		}
		else
		{
			String cardNumber = MobileBankService.getInstance().getCardByPhone(phone);
			if (StringHelper.isNotEmpty(cardNumber))
			{
				hasPhoneInMB = true;
				profileId = Connector.findNotClosedTerminalAndCSAByCardNumber(cardNumber);
				if (profileId != null)
				{
					clientProfileId = profileId;
				}
				else
				{
					// получаем данные по клиенту из МБК
					CSAUserInfo userInfo = SyncUtil.getUserInfoByCardNumber(cardNumber);

					profileId = Profile.getProfileIdByUID(userInfo);
					if (profileId != null && Connector.isExistNotClosedTerminalAndCSAByProfileID(profileId))
					{
						clientProfileId = profileId;
					}
				}
			}
		}
		return getSuccessResponseBuilder().addAdditionInformationForGuest(hasPhoneInMB, clientProfileId).end();
	}
}
