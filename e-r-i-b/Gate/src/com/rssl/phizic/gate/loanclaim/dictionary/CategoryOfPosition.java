package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����������� "��������� ���������� ���������"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "category-of-position")
public class CategoryOfPosition extends AbstractDictionaryEntry
{
	/**
	 * ��� ���������� ������ �� ������ ����������� ���������� ������� ��� ������ �������
	 */
	@XmlElement(name = "maxAge", required = true)
	private int maxAge;

	public int getMaxAge()
	{
		return maxAge;
	}

	public void setMaxAge(int maxAge)
	{
		this.maxAge = maxAge;
	}
}
