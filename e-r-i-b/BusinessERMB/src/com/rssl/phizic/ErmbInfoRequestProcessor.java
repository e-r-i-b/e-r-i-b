package com.rssl.phizic;

import com.rssl.phizic.common.types.ermb.ErmbStatus;
import com.rssl.phizic.gate.ermb.ErmbInfo;
import com.rssl.phizic.gate.ermb.ErmbInfoImpl;
import com.rssl.phizic.person.ClientErmbProfile;
import com.rssl.phizic.person.ErmbProfileService;
import com.rssl.phizic.person.QueryErmbProfileOptions;
import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * Обработчик запросов на получение информации о состоянии ЕРМБ
 * @author Puzikov
 * @ created 11.09.14
 * @ $Author$
 * @ $Revision$
 */
class ErmbInfoRequestProcessor extends CSABackRefRequestProcessorBase<ErmbInfoRequest, ErmbInfo>
{
	private final ErmbProfileService ermbProfileService = new ErmbProfileService();

	protected ErmbInfo doProcessRequest(ErmbInfoRequest request)
	{
		QueryErmbProfileOptions queryOptions = new QueryErmbProfileOptions();
		queryOptions.findByActualIdentity = true;
		queryOptions.findByOldIdentity = false;
		ClientErmbProfile profile = ermbProfileService.queryProfile(request.clientIdentity, queryOptions);

		if (profile == null || profile.mainPhone == null)
		{
			return new ErmbInfoImpl(null, ErmbStatus.NOT_CONNECTED);
		}
		else
		{
			String activePhone = PhoneNumberFormat.MOBILE_INTERANTIONAL.format(profile.mainPhone);
			return new ErmbInfoImpl(activePhone, getStatus(profile));
		}
	}

	private ErmbStatus getStatus(ClientErmbProfile profile)
	{
		if (!profile.serviceStatus)
		{
			return ErmbStatus.NOT_CONNECTED;
		}
		else if (profile.clientBlocked)
		{
			return ErmbStatus.BLOCKED;
		}
		else if (profile.paymentBlocked)
		{
			return ErmbStatus.UNPAID;
		}
		else
		{
			return ErmbStatus.ACTIVE;
		}
	}

	public String getRequestName()
	{
		return ErmbInfoRequest.REQUEST_NAME;
	}
}

