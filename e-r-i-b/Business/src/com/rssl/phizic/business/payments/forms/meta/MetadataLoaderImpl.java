package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.metadata.ExtendedMetadataLoader;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataLoader;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.forms.MetadataImpl;
import com.rssl.phizic.business.payments.IncludesResolver;
import com.rssl.phizic.business.payments.forms.FormParser;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.xml.TransformErrorListener;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;
import org.apache.commons.collections.CollectionUtils;

import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Evgrafov
 * @ created 10.11.2006
 * @ $Author: erkin $
 * @ $Revision: 58472 $
 */
@PublicDefaultCreatable
public class MetadataLoaderImpl implements MetadataLoader
{
	private static final PaymentFormService formService = new PaymentFormService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	public Metadata load(String formName) throws FormException
	{
		try
		{
			MetadataImpl metadata = new MetadataImpl();

			List<PaymentFormTransformation> transformationList = formService.findTransformationsByFormName(formName);
			if (CollectionUtils.isEmpty(transformationList)) //хот€ бы одно должно быть всегда - html, остальные - опционально.
				throw new FormException("Ќе найдены преобразовани€ платежа " + formName);

			MetadataBean metadataBean = transformationList.get(0).getForm(); //берем любое преобразование из списка, форма у всех должна быть одна и та же

			FormParser formParser = new FormParser();
			formParser.parse(metadataBean.getDefinition());

			metadata.setName(formName);
			metadata.setForm(formParser.getForm());

			metadata.setDocumentClass(ClassHelper.<BusinessDocument>loadClass(formParser.getDocumentClassName()));
			String templateClassName = formParser.getTemplateClassName();
			if (StringHelper.isNotEmpty(templateClassName))
				metadata.setTemplateClass(ClassHelper.<TemplateDocument>loadClass(templateClassName));
			metadata.setWithdrawOptions(formParser.getWithdrawOptions());
			metadata.setEditOptions(formParser.getEditOptions());
			metadata.setListFormName(formParser.getListFormName());
			metadata.setNeedParent(formParser.isNeedParent());
			metadata.setAdditionalAttributes(formParser.getAdditionalAttributes());

			if (metadata.getListFormName() == null)
			{
				metadata.setListFormName(metadata.getName());
				ListFormImpl tempList = formService.findListFormByName(formName);
				if (tempList != null)
				{
					metadata.setFilterForm(tempList.getFilterForm());
					metadata.setListSource(tempList.getListSource());
					metadata.addTemplate("list-filter-html", createTemplates(tempList.getFilterTransformation()));
					metadata.addTemplate("list-html", createTemplates(tempList.getListTransformation()));
				}
			}

			String loaderClassName = formParser.getExtendedMetadataLoaderClassName();
			if (loaderClassName != null)
			{
				Class<ExtendedMetadataLoader> loaderClass = ClassHelper.loadClass(loaderClassName);
				metadata.setExtendedMetadataLoader(loaderClass.newInstance());
			}

			metadata.addAllDictionaries(formParser.getDictionaries());

			metadata.addTemplate("xml", createTemplates(metadataBean.getXmlTransformation()));

			//если шаблона print нет, то вместо него сохран€етс€ html, поэтому запоминаем шаблон html и признак наличи€ print
			boolean isPrintExist = false;
			Templates htmlTemplates = null;
			
			for (PaymentFormTransformation pft : transformationList)
			{
				TransformType type = pft.getType();
				Templates templates = createTemplates(pft.getTransformation());
				metadata.addTemplate(type.name(), templates);
				switch (type) {
					case html:
						htmlTemplates = templates;
						break;
					case print:
						isPrintExist = true;
						break;
				}
			}

			Map<String, Object> additionalAttributes = new HashMap<String,Object>();
			if (!isPrintExist)
			{
				//todo недоделано нужно сетить параметры необходимые преобразованию дл€ html view
				metadata.addTemplate(TransformType.print.name(), htmlTemplates);
				additionalAttributes.put("defaultPrintForm", true);
			}
			metadata.setAdditionalAttributes(additionalAttributes);

			metadata.setStateMachineInfo(formParser.getStateMachineInfo());

			return metadata;
		}
		catch (FormException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new FormException(e);
		}
	}

	private Templates createTemplates(String txt) throws BusinessException
	{
		try
		{
			StreamSource htmlSource = new StreamSource(new StringReader(txt));
			TransformerFactory factory = TransformerFactory.newInstance();
			factory.setErrorListener(new TransformErrorListener());
			// URI resolver дл€ тэгов import и include добавл€етс€ пр€мо в фабрику
			factory.setURIResolver(new IncludesResolver());
			return factory.newTemplates(htmlSource);
		}
		catch (TransformerConfigurationException e)
		{
 			throw new BusinessException("ќшибка при создании шаблона", e);
		}
	}
}
