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
 * ������ ��� ������ � push-�������������
 * @author basharin
 * @ created 07.08.13
 * @ $Author$
 * @ $Revision$
 */

public class PushHelper
{
	/**
	 * �������� ������ �� id �������� �������, ��������������� �� guid � �������� ��������� ��������� �������
	 * @param person - ������
	 * @return ������ �� id �������� �������, ��������������� �� guid � �������� ��������� ��������� �������
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
	 * ��������� ������ �������� �� id �������� �������, ��������������� �� guid � �������� ��������� ��������� �������
	 * @param listClientConnected ���������� � ����������� ������������
	 * @return ������
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