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
 * Данные по наёмному работнику
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
	 * @param positionCategory - категория занимаемой должности (never null)
	 * @param positionName - наименование должности (never null)
	 * @param jobExperience - стаж на текущем месте работы (never null)
	 * @param workPlacesCount - количество рабочих мест за последние 3 года
	 */
	public Employee(CategoryOfPosition positionCategory, String positionName, JobExperience jobExperience, int workPlacesCount)
	{
		if (positionCategory == null)
		    throw new IllegalArgumentException("Не указана категория занимаемой должности");
		if (StringHelper.isEmpty(positionName))
		    throw new IllegalArgumentException("Не указано наименование должности");
		if (jobExperience == null)
		    throw new IllegalArgumentException("Не указан стаж на текущем месте работы");

		this.positionCategory = positionCategory;
		this.positionName = positionName;
		this.jobExperience = jobExperience;
		this.workPlacesCount = workPlacesCount;
	}

	/**
	 * @return категория занимаемой должности (never null)
	 */
	public CategoryOfPosition getPositionCategory()
	{
		return positionCategory;
	}

	/**
	 * @return наименование должности (never null)
	 */
	public String getPositionName()
	{
		return positionName;
	}

	/**
	 * @return стаж на текущем месте работы (never null)
	 */
	public JobExperience getJobExperience()
	{
		return jobExperience;
	}

	/**
	 * @return количество рабочих мест за последние 3 года
	 */
	public int getWorkPlacesCount()
	{
		return workPlacesCount;
	}
}
