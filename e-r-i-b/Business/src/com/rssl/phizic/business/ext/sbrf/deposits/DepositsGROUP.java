package com.rssl.phizic.business.ext.sbrf.deposits;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;

/**
 * �������� ��� ������� �� ������� QVB_GROUP ����������� ��� ��� (������ �������� ���������)
 *
 * @author EgorovaA
 * @ created 25.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositsGROUP extends DictionaryRecordBase
{
	private static final String KEY_DELIMITER = "^";

	// id � ��
	private Long id;
	// ��� (�����) ������ (GR_CODE)
	private Long groupCode;
	// ������������ ������ (GR_NAME)
	private String groupName;

	public Comparable getSynchKey()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(groupCode);
		return sb.toString();
	}

	public void updateFrom(DictionaryRecord that)
	{
		((DepositsGROUP) that).setId(getId());
		super.updateFrom(that);
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getGroupCode()
	{
		return groupCode;
	}

	public void setGroupCode(Long groupCode)
	{
		this.groupCode = groupCode;
	}

	public String getGroupName()
	{
		return groupName;
	}

	public void setGroupName(String groupName)
	{
		this.groupName = groupName;
	}
}
