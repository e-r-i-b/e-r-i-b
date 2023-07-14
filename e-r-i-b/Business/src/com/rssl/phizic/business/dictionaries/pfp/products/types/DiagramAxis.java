package com.rssl.phizic.business.dictionaries.pfp.products.types;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 26.06.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��� ��������
 */

public class DiagramAxis
{
	private Long id;
	private String name;
	private Boolean useSteps;
	private List<DiagramStep> steps;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ��������
	 * @param name ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������������ �� ���� (true - ������������)
	 */
	public Boolean getUseSteps()
	{
		return useSteps;
	}

	/**
	 * ������ ������� ������������� �����
	 * @param useSteps ������������ �� ���� (true - ������������)
	 */
	public void setUseSteps(Boolean useSteps)
	{
		this.useSteps = useSteps;
	}

	/**
	 * @return ����
	 */
	public List<DiagramStep> getSteps()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return steps;
	}

	/**
	 * ������ ����
	 * @param steps ����
	 */
	public void setSteps(List<DiagramStep> steps)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.steps = steps;
	}

	/**
	 * ������� �������� ���
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
