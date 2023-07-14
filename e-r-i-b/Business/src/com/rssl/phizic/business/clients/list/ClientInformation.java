package com.rssl.phizic.business.clients.list;

import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.ermb.ErmbStatus;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � �������
 */

public class ClientInformation
{
	private Long id;
	private String firstname;
	private String surname;
	private String patrname;
	private String document;
	private Calendar birthday;
	private String tb;
	private String agreementNumber;
	private CreationType creationType;
	private String login;
	private String userId;
	private Calendar lastLoginDate;
	private List<ClientBlock> blocks;
	private List<ClientNodeInfo> nodes;
	private String ermbActivePhone;
	private ErmbStatus ermbStatus;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * ������ ���
	 * @param firstname ���
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	/**
	 * @return �������
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * ������ �������
	 * @param surname �������
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return ��������
	 */
	public String getPatrname()
	{
		return patrname;
	}

	/**
	 * ������ ��������
	 * @param patrname ��������
	 */
	public void setPatrname(String patrname)
	{
		this.patrname = patrname;
	}

	/**
	 * @return ��������
	 */
	public String getDocument()
	{
		return document;
	}

	/**
	 * ������ ��������
	 * @param document ��������
	 */
	public void setDocument(String document)
	{
		this.document = document;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * ������ ���� ��������
	 * @param birthday ���� ��������
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return ��
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * ������ ��
	 * @param tb ��
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return ����� ��������
	 */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	/**
	 * ������ ����� ��������
	 * @param agreementNumber ����� ��������
	 */
	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	/**
	 * @return ��� ��������
	 */
	public CreationType getCreationType()
	{
		return creationType;
	}

	/**
	 * ������ ��� ��������
	 * @param creationType ��� ��������
	 */
	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}

	/**
	 * @return �����
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * ������ �����
	 * @param login �����
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * @return ������������� � ���
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * ������ ������������� � ���
	 * @param userId ������������� � ���
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/**
	 * @return ���� ���������� �����
	 */
	public Calendar getLastLoginDate()
	{
		return lastLoginDate;
	}

	/**
	 * ������ ���� ���������� �����
	 * @param lastLoginDate ���� ���������� �����
	 */
	public void setLastLoginDate(Calendar lastLoginDate)
	{
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return ������ ���������� �������
	 */
	public List<ClientBlock> getBlocks()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return blocks;
	}

	/**
	 * ������ ������ ���������� �������
	 * @param blocks ������ ���������� �������
	 */
	public void setBlocks(List<ClientBlock> blocks)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.blocks = blocks;
	}

	/**
	 * @return ���������� � ������
	 */
	public List<ClientNodeInfo> getNodes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return nodes;
	}

	/**
	 * ������ ���������� � ������
	 * @param nodes ����������
	 */
	public void setNodes(List<ClientNodeInfo> nodes)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.nodes = nodes;
	}

	/**
	 * @return �������� ������� ����
	 */
	public String getErmbActivePhone()
	{
		return ermbActivePhone;
	}

	/**
	 * @param ermbActivePhone �������� ������� ����
	 */
	public void setErmbActivePhone(String ermbActivePhone)
	{
		this.ermbActivePhone = ermbActivePhone;
	}

	/**
	 * @return ������ ������ ����
	 */
	public ErmbStatus getErmbStatus()
	{
		return ermbStatus;
	}

	/**
	 * @param ermbStatus ������ ������ ����
	 */
	public void setErmbStatus(ErmbStatus ermbStatus)
	{
		this.ermbStatus = ermbStatus;
	}
}
