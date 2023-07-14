package com.rssl.phizic.gate.loanclaim.dictionary;

import com.rssl.phizic.common.types.exceptions.InternalErrorException;

import java.io.Serializable;

/**
 * ������ �����������, ���������� ��� ������� ����� �� ������ ����� � ��
 * ���� ������ ����������� �� ��������� ���� (����������)
 * @see #searchPostfix
 *
 * @author Puzikov
 * @ created 28.11.14
 * @ $Author$
 * @ $Revision$
 */

public abstract class MultiWordDictionaryEntry extends AbstractDictionaryEntry implements Cloneable, Serializable
{
	/**
	 * �������� ��� ������ ������
	 * ������
	 *      ����� �.������-�������
	 * ����� ������������ �������� (������� �� ������ ������������) �����������
	 *      ����� �.������-�������
	 *      �.������-�������
	 *      ������-�������
	 *      �������
	 */
	private String searchPostfix;

	public String getSearchPostfix()
	{
		return searchPostfix;
	}

	public void setSearchPostfix(String searchPostfix)
	{
		this.searchPostfix = searchPostfix;
	}

	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalErrorException("�� ������� ������������ ������ �����������", e);
		}
	}
}
