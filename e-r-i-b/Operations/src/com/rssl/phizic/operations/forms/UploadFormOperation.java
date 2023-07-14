package com.rssl.phizic.operations.forms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.business.payments.forms.meta.TransformType;
import com.rssl.phizic.business.payments.forms.utils.PaymentFormsLoader;
import com.rssl.phizic.operations.OperationBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Roshka
 * @ created 14.02.2006
 * @ $Author$
 * @ $Revision$
 */

public class UploadFormOperation extends OperationBase
{
	private String formDefenition;
	private String xmlForm;
	private String listForm;
	private String htmlListForm;
	private String htmlListFilterForm;
	private Map<TransformType, String> transformMap = new HashMap<TransformType, String>();

	private MetadataBean savedMetadata;

	public String getFormDefenition()
	{
		return formDefenition;
	}

	public void setFormDefenition(String formDefenition)
	{
		this.formDefenition = formDefenition;
	}

	public String getHtmlForm()
	{
		return transformMap.get(TransformType.html);
	}

	public void setHtmlForm(String htmlForm)
	{
		transformMap.put(TransformType.html, htmlForm);
	}


	public String getPrintForm()
	{
		return transformMap.get(TransformType.print);
	}

	public void setPrintForm(String printForm)
	{
		transformMap.put(TransformType.print, printForm);
	}

	public String getHtmlListFilterForm()
	{
		return htmlListFilterForm;
	}

	public void setHtmlListFilterForm(String htmlListFilterForm)
	{
		this.htmlListFilterForm = htmlListFilterForm;
	}

	public String getHtmlListForm()
	{
		return htmlListForm;
	}

	public void setHtmlListForm(String htmlListForm)
	{
		this.htmlListForm = htmlListForm;
	}

	public String getListForm()
	{
		return listForm;
	}

	public void setListForm(String listForm)
	{
		this.listForm = listForm;
	}

	public String getXmlForm()
	{
		return xmlForm;
	}

	public void setXmlForm(String xmlForm)
	{
		this.xmlForm = xmlForm;
	}

	public MetadataBean getSavedForm()
	{
		return savedMetadata;
	}

	/**
	 * Сохраняет в БД формы списка и платежа.
	 * @throws BusinessException
	 */
	@Transactional
	public void save() throws BusinessException
	{
		PaymentFormsLoader paymentFormsLoader = new PaymentFormsLoader();
		savedMetadata = paymentFormsLoader.saveForm(
				formDefenition,
				xmlForm,
				listForm,
				htmlListForm,
				htmlListFilterForm,
				transformMap);
	}
}
