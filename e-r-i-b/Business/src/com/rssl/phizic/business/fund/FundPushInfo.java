package com.rssl.phizic.business.fund;

import com.rssl.phizic.common.types.transmiters.Triplet;
import com.rssl.phizic.person.ClientData;

import java.util.List;
import java.util.Map;

/**
 * @author osminin
 * @ created 08.12.14
 * @ $Author$
 * @ $Revision$
 *
 * »нформаци€ о пуш-уведомлении
 */
public class FundPushInfo
{
	private List<String> phones;
	private ClientData clientData;
	private List<Triplet<String, String, String>> triplets;
	private Map<String, Object> properties;
	private String pushMessageKey;

	/**
	 * ctor
	 */
	public FundPushInfo()
	{
	}

	/**
	 * ctor
	 * @param phones номера телефонов
	 * @param clientData информаци€ о пользователе
	 * @param triplets тройки из id девайсов клиента, соответствующим им guid и названий мобильных устройств клиента
	 * @param properties мапа параметров дл€ текста сообщени€
	 * @param pushMessageKey ключ дл€ построени€ сообщен€и по пуш шаблону
	 */
	public FundPushInfo(List<String> phones, ClientData clientData, List<Triplet<String, String, String>> triplets, Map<String, Object> properties, String pushMessageKey)
	{
		this.clientData = clientData;
		this.pushMessageKey = pushMessageKey;
		this.phones = phones;
		this.triplets = triplets;
		this.properties = properties;
	}

	public ClientData getClientData()
	{
		return clientData;
	}

	public void setClientData(ClientData clientData)
	{
		this.clientData = clientData;
	}

	public List<Triplet<String, String, String>> getTriplets()
	{
		return triplets;
	}

	public void setTriplets(List<Triplet<String, String, String>> triplets)
	{
		this.triplets = triplets;
	}

	public Map<String, Object> getProperties()
	{
		return properties;
	}

	public void setProperties(Map<String, Object> properties)
	{
		this.properties = properties;
	}

	public List<String> getPhones()
	{
		return phones;
	}

	public void setPhones(List<String> phones)
	{
		this.phones = phones;
	}

	public String getPushMessageKey()
	{
		return pushMessageKey;
	}

	public void setPushMessageKey(String pushMessageKey)
	{
		this.pushMessageKey = pushMessageKey;
	}
}
