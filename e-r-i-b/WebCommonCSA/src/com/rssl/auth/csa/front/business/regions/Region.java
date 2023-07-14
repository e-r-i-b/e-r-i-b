package com.rssl.auth.csa.front.business.regions;

/**
 * ������ ������������
 * @author komarov
 * @ created 13.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class Region
{
	private Long   id;
	private Comparable<? extends String> synchKey;
	private String name;
	private Region parent;
	private String codeTB;

	/**
	 * �������������
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * ������������� �������,
	 * �������� �����������
	 * @return ������������� �������
	 */
	public Comparable<? extends String> getSynchKey()
	{
		return synchKey;
	}

	/**
	 * @param synchKey �b����������� �������
	 */
	public void setSynchKey(Comparable<? extends String> synchKey)
	{
		this.synchKey = synchKey;
	}

	/**
	 * @return ������������ �������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name ������������ �������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ���������/��������
	 */
	public Region getParent()
	{
		return parent;
	}

	/**
	 * @param parent ���������/�������
	 */
	public void setParent(Region parent)
	{
		this.parent = parent;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Region that = (Region) o;
		if (!id.equals(that.id))
			return false;

		return true;
	}

	public int hashCode()
	{
		return id.hashCode();
	}

	/**
	 * @return ��� ��
	 */
	public String getCodeTB()
	{
		return codeTB;
	}

	/**
	 * @param codeTB ��� ��
	 */
	public void setCodeTB(String codeTB)
	{
		this.codeTB = codeTB;
	}
}
