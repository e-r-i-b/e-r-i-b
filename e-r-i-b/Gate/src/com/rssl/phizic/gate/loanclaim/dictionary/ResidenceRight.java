package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����������� "��� ������������� ����� �� ����� ������������ ����������"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "residence-right")
public class ResidenceRight extends AbstractDictionaryEntry
{
	/**
	 * ��� ���������� ������ �� ������ ��������� ������� "�����������".
	 * ���� ��������� ������, ��� ��������� � ����������� ��������, ������ ���� ������� ���������� � ������������ (� �����������)
	 */
	@XmlElement(name = "needRealtyInfo", required = true)
	private boolean needRealtyInfo;

	public boolean isNeedRealtyInfo()
	{
		return needRealtyInfo;
	}

	public void setNeedRealtyInfo(boolean needRealtyInfo)
	{
		this.needRealtyInfo = needRealtyInfo;
	}
}
