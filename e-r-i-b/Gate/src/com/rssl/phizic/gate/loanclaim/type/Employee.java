package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.gate.loanclaim.dictionary.CategoryOfPosition;
import com.rssl.phizic.gate.loanclaim.dictionary.JobExperience;
import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �� ������� ���������
 */
@Immutable
public class Employee
{
	private final CategoryOfPosition positionCategory;

	private final String positionName;

	private final JobExperience jobExperience;

	private final int workPlacesCount;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param positionCategory - ��������� ���������� ��������� (never null)
	 * @param positionName - ������������ ��������� (never null)
	 * @param jobExperience - ���� �� ������� ����� ������ (never null)
	 * @param workPlacesCount - ���������� ������� ���� �� ��������� 3 ����
	 */
	public Employee(CategoryOfPosition positionCategory, String positionName, JobExperience jobExperience, int workPlacesCount)
	{
		if (positionCategory == null)
		    throw new IllegalArgumentException("�� ������� ��������� ���������� ���������");
		if (StringHelper.isEmpty(positionName))
		    throw new IllegalArgumentException("�� ������� ������������ ���������");
		if (jobExperience == null)
		    throw new IllegalArgumentException("�� ������ ���� �� ������� ����� ������");

		this.positionCategory = positionCategory;
		this.positionName = positionName;
		this.jobExperience = jobExperience;
		this.workPlacesCount = workPlacesCount;
	}

	/**
	 * @return ��������� ���������� ��������� (never null)
	 */
	public CategoryOfPosition getPositionCategory()
	{
		return positionCategory;
	}

	/**
	 * @return ������������ ��������� (never null)
	 */
	public String getPositionName()
	{
		return positionName;
	}

	/**
	 * @return ���� �� ������� ����� ������ (never null)
	 */
	public JobExperience getJobExperience()
	{
		return jobExperience;
	}

	/**
	 * @return ���������� ������� ���� �� ��������� 3 ����
	 */
	public int getWorkPlacesCount()
	{
		return workPlacesCount;
	}
}
