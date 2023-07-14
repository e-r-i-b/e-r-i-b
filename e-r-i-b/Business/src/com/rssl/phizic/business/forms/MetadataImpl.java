package com.rssl.phizic.business.forms;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormException;
import com.rssl.common.forms.doc.StateMachineInfo;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.xslt.FilteredEntityListSource;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.documents.metadata.ExtendedMetadataLoader;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.NullOperationOptions;
import com.rssl.phizic.business.documents.metadata.OperationOptions;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.HelperSource;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.payments.forms.PaymentXmlConverter;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import org.w3c.dom.Element;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Templates;

/**
 * ћетаданные
 * @author Kidyaev
 * @ created 17.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class MetadataImpl implements Metadata
{
	private String                            name;
	private Form                              form;
	private Form                              filterForm;
	private FilteredEntityListSource          listSource;
	private Class<? extends BusinessDocument> documentClass;
	private Class<? extends TemplateDocument> templateClass;
	private Map<String, Element>              dictionaries = new HashMap<String, Element>();
	private Map<String, Object>               additionalAttributes = new HashMap<String, Object>();
	private Map<String, Templates>            templatesMap = new HashMap<String, Templates>();
	private ExtendedMetadataLoader            extendedMetadataLoader;
	private String                            listFormName;
	private boolean                           needParent;
	private OperationOptions                  withdrawOptions = NullOperationOptions.getInstance();
	private OperationOptions                  editOptions = NullOperationOptions.getInstance();
	private StateMachineInfo                  stateMachineInfo;
	private static final String MOBILE = "mobile";

	public String getName()
	{
		return name;
	}

	/**
	 * @param name им€
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	public Map<String, Element> getDictionaries()
	{
		return Collections.unmodifiableMap(dictionaries);
	}

	/**
	 * ƒобавить справочники
	 * @param dictionaries справочники формы, которые используетс€ в xslt
	 */
	public void addAllDictionaries(Map<String, Element> dictionaries)
	{
		this.dictionaries.putAll(dictionaries);
	}

	/**
	 * ƒобавить справочник
	 * @param dictionaryName им€ справочника
	 * @param dictionary справочник формы, который используетс€ в xslt
	 */
	public void addDictionary(String dictionaryName, Element dictionary)
	{
		this.dictionaries.put(dictionaryName, dictionary);
	}

	public Form getForm()
	{
		return form;
	}

	/**
	 * @param form форма
	 */
	public void setForm(Form form)
	{
		this.form = form;
	}

	public Form getFilterForm()
	{
		return filterForm;
	}

	/**
	 * @param filterForm форма фильтра
	 */
	public void setFilterForm(Form filterForm)
	{
		this.filterForm = filterForm;
	}

	public XmlConverter createConverter(String templateName)
	{
		Templates templates = getTemplates(templateName);

		return new PaymentXmlConverter(this, templates);
	}

	public Templates getTemplates(String templateName)
	{
		Templates templates = null;
		
		//mAPI - берем максимальную доступную версию, но не выше текущей
		if (ApplicationUtil.isMobileApi() && MOBILE.equals(templateName))
		{
			Integer currentVersion = MobileApiUtil.getApiVersionNumber().getMajor();
			List<Integer> versions = ApplicationUtil.getAllSortApiVersions();
			Collections.reverse(versions);
			for (Integer version : versions)
			{
				String mobileKey = MOBILE + (version == 1 ? "" : version.toString());
				if (currentVersion >= version && templatesMap.containsKey(mobileKey))
				{
					templates = templatesMap.get(mobileKey);
					break;
				}
			}
		}
		//desktop
		else
		{
			templates = templatesMap.get(templateName);
		}

		if ( templates == null )
			throw new FormException("Ќеизвестный шаблон: " + templateName);
		return templates;
	}

	public BusinessDocument createDocument() throws FormException
	{
		try
		{
			BusinessDocument document = documentClass.newInstance();
			document.setFormName(name);

			ApplicationInfo applicationInfo = ApplicationConfig.getIt().getApplicationInfo();
			HelperSource.updateOwner(document);
			return document;
		}
		catch (Exception e)
		{
			throw new FormException(e);
		}
	}

	public TemplateDocument createTemplate()
	{
		if (templateClass == null)
			throw new UnsupportedOperationException("Ќе определЄн templateClass. —м. атрибут template-implementation в " + getName() + ".pfd.xml");
		try
		{
			return templateClass.newInstance();
		}
		catch (Exception e)
		{
			throw new FormException(e);
		}
	}

	public FilteredEntityListSource getListSource()
	{
		return listSource;
	}

	public ExtendedMetadataLoader getExtendedMetadataLoader()
	{
		return extendedMetadataLoader;
	}


	/**
	 * @param extendedMetadataLoader загрзчик расширенных данных
	 */
	public void setExtendedMetadataLoader(ExtendedMetadataLoader extendedMetadataLoader)
	{
		this.extendedMetadataLoader = extendedMetadataLoader;
	}

	/**
	 * @param listSource источник отфильтрованного списка документов
	 */
	public void setListSource(FilteredEntityListSource listSource)
	{
		this.listSource = listSource;
	}

	/**
	 * @param templateName им€ скомпилированного xslt
	 * @param templates скомпилированное xslt
	 */
	public void addTemplate(String templateName, Templates templates)
	{
		this.templatesMap.put(templateName, templates);
	}

	/**
	 * @return класс создаваемого документа (платеж, за€вка и т.д.)
	 */
	public Class<? extends BusinessDocument> getDocumentClass()
	{
		return documentClass;
	}

	/**
	 * @param documentClass класс создаваемого документа (платеж, за€вка и т.д.)
	 */
	public void setDocumentClass(Class<? extends BusinessDocument> documentClass)
	{
		this.documentClass = documentClass;
	}

	/**
	 * @return класс создаваемого шаблона
	 */
	public Class<? extends TemplateDocument> getTemplateClass()
	{
		return templateClass;
	}

	/**
	 * @param templateClass класс создаваемого шаблона
	 */
	public void setTemplateClass(Class<? extends TemplateDocument> templateClass)
	{
		this.templateClass = templateClass;
	}

	/**
	 * @param listFormName форма дл€ списка платежа
	 */
	public void setListFormName(String listFormName)
	{
		this.listFormName = listFormName;
	}

	public String getListFormName()
	{
		return listFormName;
	}

	public boolean isNeedParent()
	{
		return needParent;
	}

	public void setNeedParent(boolean needParent)
	{
		this.needParent = needParent;
	}

	public OperationOptions getWithdrawOptions()
	{
		return withdrawOptions;
	}

	public void setWithdrawOptions(OperationOptions withdrawOptions)
	{
		this.withdrawOptions = withdrawOptions;
	}

	public Map<String, Object> getAdditionalAttributes()
	{
		return additionalAttributes;
	}

	public void setAdditionalAttributes(Map<String, Object> additionalAttributes)
	{
		this.additionalAttributes = additionalAttributes;
	}

	public OperationOptions getEditOptions()
	{
		return editOptions;
	}

	public void setEditOptions(OperationOptions options)
	{
		this.editOptions = options;
	}

	public StateMachineInfo getStateMachineInfo()
	{
		return stateMachineInfo;
	}

	public String getMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		if (form == null)
		{
			throw new FormException("Ќевалидное состоени€е метаданных: отсутствует форма");
		}
		String formName = form.getName();
		if (extendedMetadataLoader == null)
		{
			return formName;
		}
		return formName +"\t"+extendedMetadataLoader.getExtendedMetadataKey(fieldSource);
	}

	public void setStateMachineInfo(StateMachineInfo stateMachineInfo)
	{
		this.stateMachineInfo = stateMachineInfo;
	}
}
