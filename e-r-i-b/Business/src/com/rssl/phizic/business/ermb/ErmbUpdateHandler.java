package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.common.type.ErmbProfileIdentity;
import com.rssl.phizic.common.type.ErmbProfileIdentityCard;
import com.rssl.phizic.common.type.ErmbUpdateProfileInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 @author: EgorovaA
 @ created: 30.01.2013
 @ $Author$
 @ $Revision$
 */
public class ErmbUpdateHandler
{
	private final static SimpleService simpleService = new SimpleService();
	private final static ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public static void updateConfirmProfiles(List<ErmbUpdateProfileInfo> list) throws GateException, BusinessException
	{
		Map<Long, Long> profilesInfo = new HashMap<Long, Long>();
        List<ErmbProfileImpl> profilesList = new ArrayList<ErmbProfileImpl>();
		for (ErmbUpdateProfileInfo info : list)
		{
			ErmbProfileIdentity clientIdentity = info.getClientIdentity();
            String surName = clientIdentity.getSurName();
            String name = clientIdentity.getFirstName();
            String patrName = clientIdentity.getPatrName();
			ErmbProfileIdentityCard identityCard = clientIdentity.getIdentityCard();
            String docSeries = identityCard.getIdSeries();
            String docNumber = identityCard.getIdNum();
            Calendar birthDate = clientIdentity.getBirthDay();
            ErmbProfileImpl profile = findProfile(surName, name, patrName, docSeries, docNumber, birthDate, clientIdentity.getTb());
            if (profile != null)
            {
	            profilesList.add(profile);
	            profilesInfo.put(profile.getId(), info.getProfileVersion());
            }
        }


		for (ErmbProfileImpl profile : profilesList)
		{
			Long version = profilesInfo.get(profile.getId());
			profile.setConfirmProfileVersion(version);
		}

		simpleService.addOrUpdateList(profilesList, null);
	}

	private static ErmbProfileImpl findProfile(String surName, String name, String patrName, String docSeries, String docNumber, Calendar birthDate, String tb)
	{
		try
		{
			return profileService.findByFIOAndDocInTB(surName, name, patrName, docSeries, docNumber, birthDate, tb);
		}
		catch (BusinessException e)
		{
			log.error(StringUtils.join(new String[]{"Ошибка поиска ЕРМБ профиля клиента", surName, name, patrName}, " "), e);
			return null;
		}
	}
}
