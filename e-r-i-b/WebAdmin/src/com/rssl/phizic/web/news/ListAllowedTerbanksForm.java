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
	private String type; // так как при построении данного списка может подребоваться как выбор одного,
						// так и нескольких тербанков, то устанавливаем данный параметр

	/**
	 * @return список тербанков
	 */
	public List<Department> getTerbanks()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return terbanks;
	}

	/**
	 * @param terbanks список тербанков
	 */
	public void setTerbanks(List<Department> terbanks)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.terbanks = terbanks;
	}

	/**
	 * @return тип справочника
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type тип справочника
	 */
	public void setType(String type)
	{
		this.type = type;
	}
}
