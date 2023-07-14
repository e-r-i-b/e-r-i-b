package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.handlers.profile.lock.LockProfileCHG071536RequestProcessor;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.LockMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Хендлер блокировки профиля клиента
 *
 * см. запрос CHG071536
 *
 * @author khudyakov
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 */
public class LockProfileCHG071536ByProfileIdRequestProcessor extends LockProfileCHG071536RequestProcessor
{
	private static final String REQUEST_TYPE = "lockProfileCHG071536ByProfileIdRq";
	private static final String RESPONSE_TYPE = "lockProfileCHG071536ByProfileIdRs";

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
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		Element element = body.getDocumentElement();

		String profileId = XmlHelper.getSimpleElementValue(element, Constants.PROFILE_ID_TAG);
		if (StringHelper.isEmpty(profileId))
		{
			throw new IllegalArgumentException("Идентификатор профиля не может быть пуст.");
		}

		Profile profile = Profile.findById(Long.valueOf(profileId), LockMode.UPGRADE_NOWAIT);
		return LogIdentificationContext.createByTemplateProfile(profile);
	}
}
