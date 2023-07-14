package com.rssl.phizic.messaging.push;

import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.phizic.business.csa.ConnectorsService;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.person.Person;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ’элпер дл€ работы с push-уведомлени€ми
 * @author basharin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public class PushHelper
{
	/**
	 * ѕолучить тройку из id девайсов клиента, соответствующим им guid и названий мобильных устройств клиента
	 * @param person - клиент
	 * @return тройка из id девайсов клиента, соответствующим им guid и названий мобильных устройств клиента
	 * @throws IKFLMessagingException
	 */
	public static List<Triplet<String, String, String>> getTripletDeviceIdGuidName(Person person) throws IKFLMessagingException
	{
		try
		{
			List<ConnectorInfo> listClientConnected = ConnectorsService.getClientMAPIConnectors(person);
			return fillTripletList(listClientConnected);
		}
		catch (Exception e)
		{
			throw new IKFLMessagingException(e);
		}
	}

	/**
	 * «аполнить список тройками из id девайсов клиента, соответствующим им guid и названий мобильных устройств клиента
	 * @param listClientConnected информаци€ о коннекторах пользовател€
	 * @return список
	 */
	public static List<Triplet<String, String, String>> fillTripletList(List<ConnectorInfo> listClientConnected)
	{
		if (CollectionUtils.isEmpty(listClientConnected))
		{
			return Collections.emptyList();
		}

		List<Triplet<String, String, String>> triplets = new ArrayList<Triplet<String, String, String>>(listClientConnected.size());

		for (ConnectorInfo clientConnected : listClientConnected)
		{
			if (clientConnected.isPushSupported())
			{
				Triplet<String, String, String> triplet = new Triplet<String, String, String>();
				triplet.setFirst(clientConnected.getDeviceID());
				triplet.setSecond(clientConnected.getGuid());
				triplet.setThird(clientConnected.getDeviceInfo());
				triplets.add(triplet);
			}
		}

		return triplets;
	}
}