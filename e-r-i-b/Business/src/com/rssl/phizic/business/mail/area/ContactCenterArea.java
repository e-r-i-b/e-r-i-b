package com.rssl.phizic.business.mail.area;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.Set;

/**
 * Площадка КЦ
 * @author komarov
 * @ created 04.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class ContactCenterArea extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private String name;
	private Set<String> departments;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id иднтификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return название площадки
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name название площадки
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return департаменты
	 */
	public Set<String> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	/**
	 * @param departments департаменты
	 */
	public void setDepartments(Set<String> departments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.departments = departments;
	}
}
