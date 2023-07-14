package com.rssl.phizic.web.forms;

import java.util.List;
import java.util.ArrayList;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 24.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditPrintFormForm extends EditFormBase
{
	private String formName;	
	private List packages   = new ArrayList();
	private List templates  = new ArrayList();
	private String[] selectedPackages = new String[0];
	private String[] selectedTemplates = new String[0];
	private boolean  selectAllPackages   = false;
	private boolean  selectAllTemplates  = false;

	public String getFormName()
	{
		return formName;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public List getPackages()
	{
		return packages;
	}

	public void setPackages(List packages)
	{
		this.packages = packages;
	}

	public List getTemplates()
	{
		return templates;
	}

	public void setTemplates(List templates)
	{
		this.templates = templates;
	}

	public void setSelectedPackages(String[] selectedPackages)
	{
		this.selectedPackages = selectedPackages;
	}

	public String[] getSelectedPackages()
	{
		return selectedPackages;
	}

	public void setSelectedTemplates(String[] selectedTemplates)
	{
		this.selectedTemplates = selectedTemplates;
	}

	public String[] getSelectedTemplates()
	{
		return selectedTemplates;
	}

	public boolean isSelectAllPackages()
	{
		return selectAllPackages;
	}

	public void setSelectAllPackages(boolean selectAllPackages)
	{
		this.selectAllPackages = selectAllPackages;
	}

	public boolean isSelectAllTemplates()
	{
		return selectAllTemplates;
	}

	public void setSelectAllTemplates(boolean selectAllTemplates)
	{
		this.selectAllTemplates = selectAllTemplates;
	}
}
