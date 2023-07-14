package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����������� "�������� ���������"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "family-status")
public class FamilyStatus extends AbstractDictionaryEntry
{
	/**
	 * ������������� ����������� ���� ���������� � �������
	 */
	public enum SpouseInfo
	{
		NOT,            //���������� ���� �� ����
		REQUIRED,       //���������� ��������� ���������� ����
		WITHOUT_PRENUP  //���������� ���������� ����, �� ��� ���� "���� �� ������� ��������?"
	}
	/**
	 * ��� ���������� ������ �� ������ ��������� ������� ������ � �������
	 */
	@XmlElement(name = "spouseInfoRequired", required = true)
	private SpouseInfo spouseInfoRequired;

	public SpouseInfo getSpouseInfoRequired()
	{
		return spouseInfoRequired;
	}

	public void setSpouseInfoRequired(SpouseInfo spouseInfoRequired)
	{
		this.spouseInfoRequired = spouseInfoRequired;
	}
}
