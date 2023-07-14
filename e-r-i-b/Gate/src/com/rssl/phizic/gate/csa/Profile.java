package com.rssl.phizic.gate.csa;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientHelper;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.exceptions.GateException;
import org.omg.PortableInterceptor.ServerRequestInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author mihaylov
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 * Профиль клиента
 */
public class Profile implements GUID
{
	private String surName;     //фамилия
	private String firstName;   //имя
	private String patrName;    //отчество
	private Calendar birthDay;  //дата рождения
	private String passport;    //документ
	private String tb;          //тербанк
	private Long nodeId;        //идентификатор блока
	private List<Profile> history = new ArrayList<Profile>(); //история изменений

	public Profile()
	{}

	public Profile(Client client) throws GateException
	{
		surName   = client.getSurName();
		firstName = client.getFirstName();
		patrName  = client.getPatrName();
		birthDay  = client.getBirthDay();
		passport  = ClientHelper.getClientWayDocument(client).getDocSeries();
		tb = client.getOffice().getCode().getFields().get("region");
	}

	public Profile(String surName, String firstName, String patrName, Calendar birthDay, String passport, String tb)
	{
		this.surName   = surName;
		this.firstName = firstName;
		this.patrName  = patrName;
		this.birthDay  = birthDay;
		this.passport  = passport;
		this.tb = tb;
	}

	/**
	 * @return фамилия
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * Установить фамилию клиента
	 * @param surName - фамилия
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	/**
	 * @return имя клиента
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * Установить имя клиента
	 * @param firstName - имя клиента
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return отчество клиента
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * Установить отчество клиента
	 * @param patrName - отчество
	 */
	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	/**
	 * @return дата рождения клиента
	 */
	public Calendar getBirthDay()
	{
		return birthDay;
	}

	/**
	 * Установить дату рождения клиента
	 * @param birthDay - дата рождения
	 */
	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	/**
	 * @return документ клиента
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * Установить документ клиента
	 * @param passport документ
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * @return тербанк клиента
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * Установить тербанк клиента
	 * @param tb - тербанк
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return идентификатор блока клиента
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * Установить идентификатор блока клиента
	 * @param nodeId - идентификатор блока
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return история изменений клиента
	 */
	public List<Profile> getHistory()
	{
		return history;
	}

	/**
	 * Установить историю изменений клиента
	 * @param history - история
	 */
	public void addHistory(Profile history)
	{
		this.history.add(history);
	}

	/**
	 * Получить историю изменений профиля клиента с текущисостоянием профиля
	 * @return история изменений профиля клиента
	 */
	public List<Profile> getAllHistory()
	{
		List<Profile> result = new ArrayList<Profile>();
		result.addAll(history);
		result.add(this);

		return result;
	}

	/**
	 * Получить историю изменений профиля клиента с текущисостоянием профиля
	 * @return история изменений профиля клиента
	 */
	public List<? extends GUID> getUniqueHistory()
	{
		return ClientHelper.getUniqueClientHistory(getAllHistory());
	}
}
