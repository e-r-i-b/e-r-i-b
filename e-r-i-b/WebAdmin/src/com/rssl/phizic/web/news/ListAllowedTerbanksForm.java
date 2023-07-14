package com.rssl.phizic.web.news;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lukina
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class ListAllowedTerbanksForm extends ActionFormBase
{
	private List<Department> terbanks = new ArrayList<Department>();
	private String type; // ��� ��� ��� ���������� ������� ������ ����� ������������� ��� ����� ������,
						// ��� � ���������� ���������, �� ������������� ������ ��������

	/**
	 * @return ������ ���������
	 */
	public List<Department> getTerbanks()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return terbanks;
	}

	/**
	 * @param terbanks ������ ���������
	 */
	public void setTerbanks(List<Department> terbanks)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.terbanks = terbanks;
	}

	/**
	 * @return ��� �����������
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type ��� �����������
	 */
	public void setType(String type)
	{
		this.type = type;
	}
}
