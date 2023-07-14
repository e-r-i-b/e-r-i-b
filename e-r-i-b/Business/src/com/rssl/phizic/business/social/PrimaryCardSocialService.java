package com.rssl.phizic.business.social;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.card.PrimaryCardService;
import com.rssl.phizic.business.ermb.card.PrimaryCardRecipientResolver;
import com.rssl.phizic.business.persons.clients.FakeClient;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * ќпредел€ет приоритетную карту дл€ оплаты по идентификатору клиента во внешнем приложении
 * @author Jatsky
 * @ created 06.06.14
 * @ $Author$
 * @ $Revision$
 */

public class PrimaryCardSocialService extends PrimaryCardService
{
	public static final String DEVICE_ID_IS_NULL = "deviceId не может быть равен null";
	public static final String DEVICE_INFO_IS_NULL = "deviceInfo телефона не может быть равен null";

	public Card getPrimaryCard(String deviceId, String deviceInfo, Office officeOfChargeOffResource) throws BusinessException, BackLogicException
	{
		if (deviceId == null)
			throw new NullPointerException(DEVICE_ID_IS_NULL);
		if (deviceInfo == null)
			throw new NullPointerException(DEVICE_INFO_IS_NULL);

		UserInfo clientInfo;
		try
		{
			Document clientInfoDocument = CSABackRequestHelper.sendGetUserInfoByDeviceIdAndInfoRq(deviceId, deviceInfo);
			clientInfo = CSABackResponseSerializer.getUserInfo(clientInfoDocument);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
		catch (BackLogicException e)
		{
			throw new BackLogicException(e);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
		List<Card> cards = getCardsByClientInfo(new FakeClient(clientInfo));

		if (CollectionUtils.isEmpty(cards))
			return null;
		return PrimaryCardRecipientResolver.getReceiverCardBySenderCard(cards, officeOfChargeOffResource);
	}
}
