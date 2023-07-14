package com.rssl.phizic.gate.loanclaim.dictionary;

/**
 * @author Nady
 * @ created 20.05.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����������� "������"
 */
public class Area extends MultiWordDictionaryEntry
{
	/**
	 * ��� ���� ������
	 */
	private String typeOfArea;
	/**
	 * ��� �������
	 */
	private String region;

	public String getTypeOfArea()
	{
		return typeOfArea;
	}

	public void setTypeOfArea(String typeOfArea)
	{
		this.typeOfArea = typeOfArea;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String region)
	{
		this.region = region;
	}
}
