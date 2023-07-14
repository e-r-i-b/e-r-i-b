package com.rssl.phizic.business.calendar;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Gainanov
 * @ created 24.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class WorkCalendar extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private String name;
	private String tb;//����������� � �������� �������� ���������.
	/** ���� �������� ������ �� ��� (������� ��� ��������), ������� ���������� �� �����������
	 * �� ����, ���� ������������ ������ ��������� "�� ���������", �� workDays = ������ ��� **/
	private Set<WorkDay> workDays = new HashSet<WorkDay>();

	/**
	 * �������� �� ���������
	 * @return �� ���������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ���������� �� ���������
	 * @param id �� ���������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * �������� ��� ���������
	 * @return ��� ���������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ���������� ��� ���������
	 * @param name ��� ���������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * �������� ����� ���� � ���������
	 * @return ����� ���� � ���������
	 */
	public Set<WorkDay> getWorkDays()
	{
		return workDays;
	}

	/**
	 * ���������� ����� ���� � ���������
	 * @param workDays ����� ���� � ���������
	 */
	public void setWorkDays(Set<WorkDay> workDays)
	{
		this.workDays = workDays;
	}

	/**
	 * @return ����� �� � �������� �������� ���������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param tb ����� �� � �������� �������� ���������
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
