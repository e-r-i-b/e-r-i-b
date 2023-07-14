package com.rssl.auth.csa.front.business.regions;

/**
 * Регион пользователя
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
	 * Идентификатор
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Идентификатор региона,
	 * вводимый сотрудником
	 * @return идентификатор региона
	 */
	public Comparable<? extends String> getSynchKey()
	{
		return synchKey;
	}

	/**
	 * @param synchKey Иbентификатор региона
	 */
	public void setSynchKey(Comparable<? extends String> synchKey)
	{
		this.synchKey = synchKey;
	}

	/**
	 * @return наименование региона
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name наименование региона
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return Наследник/родитель
	 */
	public Region getParent()
	{
		return parent;
	}

	/**
	 * @param parent Наследник/родител
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
	 * @return код ТБ
	 */
	public String getCodeTB()
	{
		return codeTB;
	}

	/**
	 * @param codeTB код ТБ
	 */
	public void setCodeTB(String codeTB)
	{
		this.codeTB = codeTB;
	}
}
