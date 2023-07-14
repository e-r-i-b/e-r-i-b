package com.rssl.phizic.gate.utils;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author krenev
 * @ created 29.09.2010
 * @ $Author$
 * @ $Revision$
 * ��������� ������������� ���������� ������������ ��������
 * ������������ � ����� ������� ����� ���:
 * <id ��������>^< systemId >^<rbBrchId>^<loginId>
 */
public class EntityCompositeId
{
	public static final String ID_DELIMETER = "^";

	private String systemId;
	private String rbBrchId;
	protected String entityId;
	private Long loginId;

	public EntityCompositeId(String id)
	{
		String[] strings = id.trim().split("\\^");
		entityId = strings.length > 0 ? strings[0] : null;
		systemId = strings.length > 1 ? strings[1] : null;
		rbBrchId = strings.length > 2 ? strings[2] : null;
		loginId  = strings.length > 3 && !isCompositeIdElementNull(strings[3]) ? Long.parseLong(strings[3]) : null;
	}

	public EntityCompositeId(String entityId, String systemId, String rbBrchId, Long loginId)
	{
		this.entityId = entityId;
		this.systemId = systemId;
		this.rbBrchId = rbBrchId;
		this.loginId = loginId;
	}

	/**
	 * @return ������������ �������� �� ������� �������.
	 */
	public String getEntityId()
	{
		return entityId;
	}

	/**
	 * @return ������������� �������-��������� ��������
	 */
	public String getSystemId()
	{
		return systemId;
	}

	/**
	 * ���������� ������������� �������-��������� �������� ��� ���� ��������� �������� ���������� ������� � ������ ������,
	 * ���� ������� �� ������� ��������� InactiveExternalSystemException � ���������� �� ���������������� ��������
	 * @return ������������� �������-��������� ��������
	 * @throws GateException
	 */
	public String getSystemIdActiveSystem() throws GateException
	{
		return ExternalSystemHelper.getCode(systemId);
	}

	/**
	 * @return ����� ���, �������� �������
	 */
	public String getRbBrchId()
	{
		return rbBrchId;
	}

	/**
	 * @return ������������� ��������� �������� � ����
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	public final boolean isCompositeIdElementNull(String element)
	{
		return StringHelper.isEmpty(element) || "null".equals(element);
	}

	public String toString()
	{
		StringBuilder compositeId = new StringBuilder();
		compositeId.append(entityId).append(ID_DELIMETER);
		compositeId.append(systemId).append(ID_DELIMETER);
		compositeId.append(rbBrchId).append(ID_DELIMETER);
		compositeId.append(loginId);
		return compositeId.toString();
	}
}
