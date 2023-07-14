package com.rssl.phizic.business.dictionaries.pfp.products.types;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * Ось диаграмы
 */

public class DiagramAxis
{
	private Long id;
	private String name;
	private Boolean useSteps;
	private List<DiagramStep> steps;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return название
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать название
	 * @param name название
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return использовать ли шаги (true - использовать)
	 */
	public Boolean getUseSteps()
	{
		return useSteps;
	}

	/**
	 * задать признак использования шагов
	 * @param useSteps использовать ли шаги (true - использовать)
	 */
	public void setUseSteps(Boolean useSteps)
	{
		this.useSteps = useSteps;
	}

	/**
	 * @return шаги
	 */
	public List<DiagramStep> getSteps()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return steps;
	}

	/**
	 * задать шаги
	 * @param steps шаги
	 */
	public void setSteps(List<DiagramStep> steps)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.steps = steps;
	}

	/**
	 * очистка настроек оси
	 */
	public void clear()
	{
		setName(null);
		setUseSteps(false);
		if (getSteps() != null)
			getSteps().clear();
		else
			setSteps(new ArrayList<DiagramStep>());
	}
}
