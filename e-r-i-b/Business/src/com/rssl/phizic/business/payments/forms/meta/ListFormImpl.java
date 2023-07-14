package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.xslt.FilteredEntityListSource;

/**
 * @author Evgrafov
 * @ created 30.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 2789 $
 */

public class ListFormImpl
{
	private String                   title;
	private String                   description;
	private Form                     filterForm;
	private String                   listTransformation;
	private String                   filterTransformation;
	private FilteredEntityListSource entityListSource;

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}


	public Form getFilterForm()
	{
		return filterForm;
	}

	void setFilterForm(Form filterForm)
	{
		this.filterForm = filterForm;
	}

	public FilteredEntityListSource getListSource()
	{
		return entityListSource;
	}

	public void setEntityListSource(FilteredEntityListSource entityListSource)
	{
		this.entityListSource = entityListSource;
	}

	public String getListTransformation()
	{
		return listTransformation;
	}

	public void setListTransformation(String listTransformation)
	{
		this.listTransformation = listTransformation;
	}

	public String getFilterTransformation()
	{
		return filterTransformation;
	}

	public void setFilterTransformation(String filterTransformation)
	{
		this.filterTransformation = filterTransformation;
	}
}