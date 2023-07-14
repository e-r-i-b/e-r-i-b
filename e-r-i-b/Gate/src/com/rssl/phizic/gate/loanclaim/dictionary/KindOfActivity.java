package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����������� "��� ������������ ��������"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "types-of-company")
public class KindOfActivity extends AbstractDictionaryEntry
{
	/**
	 * ��� ���������� ������ �� ������ ��������� ������� "�����������"
	 * �� 13 (����������� ������ �� ������, 5.19.8.1, ��� ������ � �����):
	 * "
	 * ���� ������������, � ����������� ��� ����������, ���� � ���� ����� ������������ ������� ��������:
	 *  �	������� / ��������� �������� (���������)
	 *  �	������ (���������)
	 *  �	������ ������� (���������)
	 * "
	 */
	@XmlElement(name = "orgActivityDescRequired", required = true)
	private boolean orgActivityDescRequired;

	public boolean isOrgActivityDescRequired()
	{
		return orgActivityDescRequired;
	}

	public void setOrgActivityDescRequired(boolean orgActivityDescRequired)
	{
		this.orgActivityDescRequired = orgActivityDescRequired;
	}
}
