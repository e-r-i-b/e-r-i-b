package com.rssl.phizic.web.audit;

import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 03.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class PrintDocumentsForm extends ActionFormBase
{
	private String                      params;
	private List<List<PackageTemplate>> packages;
	private List<List<DocTemplate>>     templates;
	private String[]                    paymentsId;
	private List                        businessDocuments;
	private List<Map<String, String>>   fieldValues;

	public void setPaymentsId(String[] paymentsId)
	{
		this.paymentsId = paymentsId;
	}

	public String[] getPaymentsId()
	{
		return paymentsId;
	}

	public void setParams(String params)
	{
		this.params = params;
	}

	public String getParams()
	{
		return params;
	}

	public List<List<PackageTemplate>> getPackages()
	{
		return packages;
	}

	public void setPackages(List<List<PackageTemplate>> packages)
	{
		this.packages = packages;
	}

	public List<List<DocTemplate>> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<List<DocTemplate>> templates)
	{
		this.templates = templates;
	}

	public List getBusinessDocuments()
	{
		return businessDocuments;
	}

	public void setBusinessDocuments(List businessDocuments)
	{
		this.businessDocuments = businessDocuments;
	}

	public List<Map<String, String>> getFieldValues()
	{
		return fieldValues;
	}

	public void setFieldValues(List<Map<String, String>> fieldValues)
	{
		this.fieldValues = fieldValues;
	}
}
