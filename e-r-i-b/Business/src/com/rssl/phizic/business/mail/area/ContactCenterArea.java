package com.rssl.phizic.business.mail.area;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.Set;

/**
 * �������� ��
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
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return �������� ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name �������� ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������������
	 */
	public Set<String> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	/**
	 * @param departments ������������
	 */
	public void setDepartments(Set<String> departments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.departments = departments;
	}
}
