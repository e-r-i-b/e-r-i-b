package com.rssl.phizic.web.forms;

import com.rssl.phizic.web.actions.ActionFormBase;
import org.apache.struts.upload.FormFile;

public class UploadFormsForm  extends ActionFormBase
{
	private FormFile form;
	private FormFile htmlForm;
	private FormFile xmlForm;

	private FormFile listForm;
	private FormFile htmlFilterListForm;
	private FormFile htmlListForm;

	public FormFile getForm()
	{
		return form;
	}

	public void setForm(FormFile form)
	{
		this.form = form;
	}

	public FormFile getHtmlFilterListForm()
	{
		return htmlFilterListForm;
	}

	public void setHtmlFilterListForm(FormFile htmlFilterListForm)
	{
		this.htmlFilterListForm = htmlFilterListForm;
	}

	public FormFile getHtmlForm()
	{
		return htmlForm;
	}

	public void setHtmlForm(FormFile htmlForm)
	{
		this.htmlForm = htmlForm;
	}

	public FormFile getHtmlListForm()
	{
		return htmlListForm;
	}

	public void setHtmlListForm(FormFile htmlListForm)
	{
		this.htmlListForm = htmlListForm;
	}

	public FormFile getListForm()
	{
		return listForm;
	}

	public void setListForm(FormFile listForm)
	{
		this.listForm = listForm;
	}

	public FormFile getXmlForm()
	{
		return xmlForm;
	}

	public void setXmlForm(FormFile xmlForm)
	{
		this.xmlForm = xmlForm;
	}


}