package com.rssl.auth.csa.back.servises.client;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.integration.erib.ERIBService;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.gate.ermb.ErmbInfo;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import java.util.*;

/**
 * Подгрузить из блоков информацию по статусу услуги ермб
 * @author Puzikov
 * @ created 03.02.15
 * @ $Author$
 * @ $Revision$
 */

public class ErmbClientInformationLoader
{
	private static final ERIBService eribService = new ERIBService();
	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	/**
	 * Запрос делается только по клиентам, для которых есть активный коннектор ермб
	 * Клиенты для запроса группируются по пачке в блок
	 * @param infoList информация из бд цса
	 * @return информация с заполненным статусом ермб
	 */
	List<ClientInformation> fillErmbInfo(List<ClientInformation> infoList)
	{
		try
		{
			Collection<Profile> ermbActiveProfiles = filterErmbProfiles(infoList);
			if (ermbActiveProfiles.isEmpty())
				return infoList;

			Map<Long, List<Profile>> nodeIdToProfileList = groupByNode(ermbActiveProfiles);
			for (Map.Entry<Long, List<Profile>> nodeProfiles : nodeIdToProfileList.entrySet())
			{
				Node node = Node.findById(nodeProfiles.getKey());
				Collection<ErmbInfo> ermbInfoList = eribService.getErmbInformation(nodeProfiles.getValue(), node);
				for (ErmbInfo ermbInfo : ermbInfoList)
				{
					for (ClientInformation clientInformation : infoList)
					{
						if (StringUtils.equals(clientInformation.getErmbPhoneNumber(), ermbInfo.getActivePhone()))
							clientInformation.setErmbStatus(ermbInfo.getStatus());
					}
				}
			}
		}
		catch (Exception e)
		{
			log.error("Не удалось получить информацию по ЕРМБ из блоков", e);
		}

		return infoList;
	}

	private List<Profile> filterErmbProfiles(List<ClientInformation> infoList)
	{
		List<Profile> resultList = new ArrayList<Profile>();
		for (ClientInformation clientInformation : infoList)
		{
			if (StringUtils.isNotEmpty(clientInformation.getErmbPhoneNumber()))
			{
				resultList.add(clientInformation.asProfile());
			}
		}
		return resultList;
	}

	private Map<Long, List<Profile>> groupByNode(Collection<Profile> eribActiveProfiles) throws Exception
	{
		Map<Long, List<Profile>> result = new HashMap<Long, List<Profile>>();
		for (Profile profile : eribActiveProfiles)
		{
			ProfileNode profileNode = Utils.getActiveProfileNode(profile.getId(), CreateProfileNodeMode.CREATION_DENIED);
			if (profileNode == null)
			{
				log.warn("Не найдена активная нода для профиля id=" + profile.getId());
				continue;
			}

			Long key = profileNode.getNode().getId();
			if (result.containsKey(key))
				result.get(key).add(profile);
			else
				result.put(key, new ArrayList<Profile>(Arrays.asList(profile)));
		}

		return result;
	}
}