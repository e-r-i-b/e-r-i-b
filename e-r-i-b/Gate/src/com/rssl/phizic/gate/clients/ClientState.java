package com.rssl.phizic.gate.clients;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

public class ClientState implements Serializable
{
	/** ��� �������� ���� ��������� ������� �� ����� */
	private static final Map<String, ClientState> statesByCode = new HashMap<String, ClientState>();

	/** ������ ���������� */
	public static final ClientState SUCCESS    = new ClientState(ClientStateCategory.agreement_dissolve,      "S");

	/** ������ ��� ����������� */
	public static final ClientState ERROR = new ClientState(ClientStateCategory.error_dissolve, "E");

	/** ������ ��� ����������� */
	public static final ClientState SYSTEM_ERROR = new ClientState(ClientStateCategory.system_error_dissolve, "E");

	/**�������� ������, ����� �������� � �������*/
	public static final ClientState ACTIVE    = new ClientState(ClientStateCategory.active, "A");

	/**���������*/
	public static final ClientState DELETED    = new ClientState(ClientStateCategory.cancellation, "D");

	/**������ � �������� �����������*/
	public static final ClientState TEMPLATE    = new ClientState(ClientStateCategory.creation, "T");

	/**�� �����������*/
	public static final ClientState WAIT_CANCELATION    = new ClientState(ClientStateCategory.cancellation, "W");

	/**������ � �������� �����������*/
	public static final ClientState SIGN_AGREEMENT    = new ClientState(ClientStateCategory.creation, "S");

	//��������� ��������� �������
	private ClientStateCategory category;
	//���������� ���
	private String code;
	//��������
	private String description;

	// ������ ��� ���-�������
	@Deprecated
	public ClientState() {}

	/**
	 * ctor
	 * @param category ��������� ���������
	 * @param code ���������� ���
	 */
	public ClientState(ClientStateCategory category, String code)
	{
		this.category = category;
		this.code = code;
		statesByCode.put(code, this);
	}

	public ClientState(ClientState state, String description)
	{
		this.category = state.category;
		this.code = state.code;
		this.description = description;
		statesByCode.put(code, this);
	}

	/**
	 * ctor
	 * @param category ��������� ���������
	 * @param code ���������� ���
	 * @param description ��������
	 */
	public ClientState(ClientStateCategory category, String code, String description)
	{
		this.category = category;
		this.code = code;
		this.description = description;
		statesByCode.put(code, this);
	}

	/**
	 * @return �������� �������
	 */
	public ClientStateCategory getCategory()
	{
		return category;
	}

	@Deprecated //todo ������ ��� �����������
	public void setCategory(String category)
	{

		this.category = ClientStateCategory.valueOf(category);
	}
	
	@Deprecated //todo ������ ��� �����������
	public void setCategory(ClientStateCategory category)
	{

		this.category = category;
	}

	/**
	 * @return ��� ��������� (������ ��������� �������� ����� ��������� �����)
	 */
	public String getCode()
	{
		return code;
	}

	@Deprecated //todo ������ ��� �����������
	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * �������� ��������� ������� �� ���� ���������
	 * @param code - ��� ���������
	 * @return ��������� �������
	 */
	public static ClientState getStateByCode(String code)
	{
		return statesByCode.get(code);
	}


	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClientState state = (ClientState) o;

		if (category != state.category) return false;
		if (!code.equals(state.code)) return false;

		return true;
	}

	public int hashCode()
	{
		int	result = category.hashCode();
		result = 31 * result + code.hashCode();
		return result;
	}
}
