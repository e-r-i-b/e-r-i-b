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
 * ������� �������
 */
public class Profile implements GUID
{
	private String surName;     //�������
	private String firstName;   //���
	private String patrName;    //��������
	private Calendar birthDay;  //���� ��������
	private String passport;    //��������
	private String tb;          //�������
	private Long nodeId;        //������������� �����
	private List<Profile> history = new ArrayList<Profile>(); //������� ���������

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
	 * @return �������
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * ���������� ������� �������
	 * @param surName - �������
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	/**
	 * @return ��� �������
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * ���������� ��� �������
	 * @param firstName - ��� �������
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return �������� �������
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * ���������� �������� �������
	 * @param patrName - ��������
	 */
	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	/**
	 * @return ���� �������� �������
	 */
	public Calendar getBirthDay()
	{
		return birthDay;
	}

	/**
	 * ���������� ���� �������� �������
	 * @param birthDay - ���� ��������
	 */
	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	/**
	 * @return �������� �������
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * ���������� �������� �������
	 * @param passport ��������
	 */
	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	/**
	 * @return ������� �������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * ���������� ������� �������
	 * @param tb - �������
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ������������� ����� �������
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * ���������� ������������� ����� �������
	 * @param nodeId - ������������� �����
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return ������� ��������� �������
	 */
	public List<Profile> getHistory()
	{
		return history;
	}

	/**
	 * ���������� ������� ��������� �������
	 * @param history - �������
	 */
	public void addHistory(Profile history)
	{
		this.history.add(history);
	}

	/**
	 * �������� ������� ��������� ������� ������� � ���������������� �������
	 * @return ������� ��������� ������� �������
	 */
	public List<Profile> getAllHistory()
	{
		List<Profile> result = new ArrayList<Profile>();
		result.addAll(history);
		result.add(this);

		return result;
	}

	/**
	 * �������� ������� ��������� ������� ������� � ���������������� �������
	 * @return ������� ��������� ������� �������
	 */
	public List<? extends GUID> getUniqueHistory()
	{
		return ClientHelper.getUniqueClientHistory(getAllHistory());
	}
}
