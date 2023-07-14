package com.rssl.phizic.business.forms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.doc.StateMachineInfo;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.xslt.FilteredEntityListSource;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.documents.metadata.ExtendedMetadataLoader;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.OperationOptions;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.payments.forms.PaymentXmlConverter;
import org.w3c.dom.Element;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.Templates;

/**
 * @author Evgrafov
 * @ created 22.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 58441 $
 */

public class ExtendedMetadata implements Metadata
{
	private Metadata original;
	private Map<String, Element> dictionaries = new HashMap<String, Element>();
	private Form                 extendedForm;


	public String getName()
	{
		return original.getName();
	}

	public Map<String, Element> getDictionaries()
	{
		return Collections.unmodifiableMap(dictionaries);
	}

	/**
	 * Добавить справочники
	 *
	 * @param dictionaries справочники формы, которые используется в xslt
	 */
	public void addAllDictionaries(Map<String, Element> dictionaries)
	{
		this.dictionaries.putAll(dictionaries);
	}

	/**
	 * Добавить справочник
	 *
	 * @param dictionaryName имя справочника
	 * @param dictionary     справочник формы, который используется в xslt
	 */
	public void addDictionary(String dictionaryName, Element dictionary)
	{
		this.dictionaries.put(dictionaryName, dictionary);
	}

	/**
	 * @return оригинальные метаданные
	 */
	public Metadata getOriginal()
	{
		return original;
	}

	/**
	 * @param original оригинальные метаданные
	 */
	public void setOriginal(Metadata original)
	{
		this.original = original;
	}


	/**
	 * @return расширенная форма
	 */
	public Form getExtendedForm()
	{
		return extendedForm;
	}

	/**
	 * @param extendedForm расширенная форма
	 */
	public void setExtendedForm(Form extendedForm)
	{
		this.extendedForm = extendedForm;
	}

	public Form getForm()
	{
		return extendedForm;
	}

	public Form getFilterForm()
	{
		return original.getFilterForm();
	}

	public XmlConverter createConverter(String templateName)
	{
		Templates templates = original.getTemplates(templateName);
		return new PaymentXmlConverter(this, templates);
	}

	public BusinessDocument createDocument() throws FormException
	{
		return original.createDocument();
	}

	public TemplateDocument createTemplate()
	{
		return original.createTemplate();
	}

	public FilteredEntityListSource getListSource()
	{
		return original.getListSource();
	}

	/**
	 * @return null - не нужно уже больше ничего расширять
	 */
	public ExtendedMetadataLoader getExtendedMetadataLoader()
	{
		return original.getExtendedMetadataLoader();
	}

	public Templates getTemplates(String templateName)
	{
		return original.getTemplates(templateName);
	}

	public String getListFormName()
	{
		return original.getListFormName();
	}

	public boolean isNeedParent()
	{
		return original.isNeedParent();
	}

	public OperationOptions getWithdrawOptions()
	{
		return original.getWithdrawOptions();
	}

	public Map<String, Object> getAdditionalAttributes()
	{
		return original.getAdditionalAttributes();
	}

	public OperationOptions getEditOptions()
	{
		return original.getEditOptions();
	}

	public StateMachineInfo getStateMachineInfo()
	{
		return original.getStateMachineInfo();
	}

	public String getMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return original.getMetadataKey(fieldSource);
	}
}