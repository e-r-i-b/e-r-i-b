package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Erkin
 * @ created 21.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� �������, ��������� �� ���� Transact SM � ������ ��� ������ ������������
 */
@XmlAccessorType(XmlAccessType.NONE)
public abstract class AbstractDictionaryEntry
{
	@XmlElement(name = "code", required = true)
	private String code;

	@XmlElement(name = "name", required = true)
	private String name;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return �������� � ��������� Transact SM (never null)
	 */
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return ����� ��� ������ ������������ (never null)
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public int hashCode()
	{
		return code.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (!(o instanceof AbstractDictionaryEntry))
			return false;

		AbstractDictionaryEntry other = (AbstractDictionaryEntry) o;

		return code.equals(other.code);
	}
}
