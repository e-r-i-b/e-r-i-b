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
 * ������ ����������� "��� ������������ � �������������"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "type-of-realty")
public class TypeOfRealty extends AbstractDictionaryEntry
{
	/**
	 * ���� ��� ���������� ��������� ������ ��������� ������, ��� ��������� � ����������� ��������
	 * (�������� �� ����������� ResidenceRight needRealtyInfo == true), �� ������ ���� ������� ���������� � �������� (���� residence ���� ������������ �.�. true).
	 * ��. �� 13 (����������� ������ �� ������, 5.19.8.1, ��� ������������� � �����)
	 */
	@XmlElement(name = "residence", required = true)
	private boolean residence;

	public boolean isResidence()
	{
		return residence;
	}

	public void setResidence(boolean residence)
	{
		this.residence = residence;
	}
}
