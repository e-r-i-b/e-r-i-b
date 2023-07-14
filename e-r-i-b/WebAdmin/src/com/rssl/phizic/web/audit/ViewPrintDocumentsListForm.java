package com.rssl.phizic.web.audit;

import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 02.12.2008
 * @ $Author$
 * @ $Revision$
 */
 public class ViewPrintDocumentsListForm extends ActionFormBase
{
	private Long id;
	private String[] paymentsId;
	private List packages   = new ArrayList();
	private List templates  = new ArrayList();

	private String[] selectedPackages = new String[0];
	private String[] selectedTemplates = new String[0];
	private boolean  selectAllPackages   = false;
	private boolean  selectAllTemplates  = false;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String[] getPaymentsId()
	{
		return paymentsId;
	}

	public void setPaymentsId(String[] paymentsId)
	{
		this.paymentsId = paymentsId;
	}

	public List getPackages()
	{
		return packages;
	}

	public void setPackages(List packages)
	{
		this.packages = packages;
	}
	//добавляем пакеты, если их ещё нет
	public void addPackages(List<PackageTemplate> packageTemplates)
	{
		for(PackageTemplate pack : packageTemplates)
			if(!packages.contains(pack))
				packages.add(pack);
	}

	public List getTemplates()
	{
		return templates;
	}

	public void setTemplates(List templates)
	{
		this.templates = templates;
	}
	//добавляем шаблоны, если их ещё нет
	public void addTamplates(List<DocTemplate> templates)
	{
		for(DocTemplate temp: templates)
			if(!templates.contains(temp))
				templates.add(temp);
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

