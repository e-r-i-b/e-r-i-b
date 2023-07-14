package com.rssl.phizic.web.client.payments.forms;

import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Kosyakov
 * @ created 12.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 49377 $
 */
public class ShowMetaPaymentListForm extends ListFormBase
{

	private String       name;
	private StringBuffer filterHtml;
	private StringBuffer listHtml;
	private String       title;
	private Metadata metadata;

	public Metadata getMetadata()
	{
		return metadata;
	}

	public void setMetadata(Metadata metadata)
	{
		this.metadata = metadata;
	}

	public String getName ()
	{
		return name;
	}

	public void setName ( String name )
	{
		this.name =name;
	}

	public StringBuffer getFilterHtml()
	{
		return filterHtml;
	}

	public void setFilterHtml(StringBuffer html)
	{
		this.filterHtml = html;
	}

	public StringBuffer getListHtml()
	{
		return listHtml;
	}

	public void setListHtml(StringBuffer listHtml)
	{
		this.listHtml = listHtml;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}
}
