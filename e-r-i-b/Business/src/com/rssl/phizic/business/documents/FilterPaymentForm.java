package com.rssl.phizic.business.documents;

import com.rssl.phizic.common.types.documents.FormType;

import java.util.Arrays;
import java.util.List;

/**
 * Бин для для вывода фильтрируемых форм в истории операций
 *
 * @author khudyakov
 * @ created 06.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class FilterPaymentForm
{
	private String ids;
	private String name;
	private List<FormType> formType;
	private boolean autoPayment;

	public FilterPaymentForm(String name, String ids)
	{
		this.ids = ids;
		this.name = name;
		this.autoPayment = false;
	}

	public FilterPaymentForm(String name, List<FormType> formType)
	{
		this.name = name;
		this.formType = formType;
	}

	public FilterPaymentForm(String name, FormType formType)
	{
		this.name = name;
		this.formType = Arrays.asList(formType);
	}

	public FilterPaymentForm(String name, String ids, boolean autoPayment)
	{
		this.ids = ids;
		this.name = name;
		this.autoPayment = autoPayment;
	}

	/**
	 * @return список идентификаторов форм через разделитель
	 */
	public String getIds()
	{
		return ids;
	}

	public void setIds(String ids)
	{
		this.ids = ids;
	}

	/**
	 * @return псевдоним формы
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isAutoPayment()
	{
		return autoPayment;
	}

	public void setAutoPayment(boolean autoPayment)
	{
		this.autoPayment = autoPayment;
	}

	public List<FormType> getFormType()
	{
		return formType;
	}

	public void setFormType(List<FormType> formType)
	{
		this.formType = formType;
	}
}
