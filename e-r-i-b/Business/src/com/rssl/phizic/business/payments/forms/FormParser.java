package com.rssl.phizic.business.payments.forms;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.doc.*;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.metadata.checkers.DocumentChecker;
import com.rssl.phizic.business.documents.metadata.checkers.ChainDocumentChecker;
import com.rssl.phizic.business.documents.metadata.DefaultOperationOptions;
import com.rssl.phizic.business.documents.metadata.NullOperationOptions;
import com.rssl.phizic.business.documents.metadata.OperationOptions;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

/**
 * @author Evgrafov
 * @ created 08.12.2005
 * @ $Author: khudyakov $
 * @ $Revision: 58441 $
 */

@SuppressWarnings({"ReturnOfCollectionOrArrayField"})
public class FormParser
{
	private FormBuilder builder;
	private Map<String, Element> dictionaries;
	private Map<String, Object> additionalAttributes = new HashMap<String, Object>();
	private String documentClassName;
	private String templateClassName;
	private String extendedMetadataLoaderClassName;
	private String listFormName;
	private boolean needParent;
	private OperationOptions withdrawOptions;
	private OperationOptions editOptions;
	private StateMachineInfo stateMachineInfo;

	/**
	 * @param stream поток содержащий PFD
	 */
	public void parse(InputStream stream) throws BusinessException
	{
		parse(new InputSource(stream));
	}

	/**
	 * @param definition строка содержащая PFD
	 */
	public void parse(String definition) throws BusinessException
	{

		parse(new InputSource(new StringReader(definition)));
	}

	/**
	 * @param inputSource источнк содержащий PFD
	 */
	public void parse(InputSource inputSource) throws BusinessException
	{
		try
		{
			DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
			Document document = documentBuilder.parse(inputSource);
			Element root = document.getDocumentElement();

			parse(root);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param root корневой элемент pfd
	 * @throws BusinessException
	 */
	public void parse(Element root) throws BusinessException
	{
		this.builder = new FormBuilder();

		try
		{
			builder.setName(root.getAttribute("name"));
			builder.setDescription(root.getAttribute("description"));
			builder.setTemplateName(root.getAttribute("templateName"));
			builder.setDetailedDescription(root.getAttribute("detailedDescription"));
			builder.setConfirmDescription(root.getAttribute("confirmDescription"));

			Element implementationElement = XmlHelper.selectSingleNode(root, "implementation");
			if (implementationElement == null) // backward compatibility
				throw new BusinessException("не найден implementation");

			documentClassName = implementationElement.getAttribute("class");

			Element templateImplementationElement = XmlHelper.selectSingleNode(root, "template-implementation");
			if (templateImplementationElement != null)
			{
				templateClassName = templateImplementationElement.getAttribute("class");
			}

			Element stateMachineElement = XmlHelper.selectSingleNode(root, "statemachine");

			this.stateMachineInfo = new StateMachineInfo();
			
			// по умолчанию берем в качестве имени машины состояний имя формы документа
			stateMachineInfo.setName(root.getAttribute("name"));

			if (stateMachineElement != null)
			{
				stateMachineInfo.setName(stateMachineElement.getAttribute("name"));
			}

			Element extendedMetadataLoaderElement = XmlHelper.selectSingleNode(root, "extended-metadata-loader");
			extendedMetadataLoaderClassName = extendedMetadataLoaderElement == null ? null : extendedMetadataLoaderElement.getAttribute("class");

			//withdrawOptions
			withdrawOptions = parseOperationOptions(root, "withdraw-options");
			//editOptions
			editOptions = parseOperationOptions(root, "edit-options");

			Element listFormElement = XmlHelper.selectSingleNode(root, "list-form");
			if (listFormElement != null)
				listFormName = listFormElement.getAttribute("name");

			needParent = false;
			Element needParentElement = XmlHelper.selectSingleNode(root, "need-parent");
			if (needParentElement != null)
				needParent = needParentElement.getAttribute("value").toUpperCase().equals("TRUE");

			parseFields(root);
			parseFormValidators(root);
			parseDictionaries(root);
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}

	private OperationOptions parseOperationOptions(Element root, String optionXPath) throws TransformerException, BusinessException
	{
		Element optionElement = XmlHelper.selectSingleNode(root, optionXPath);

		if (optionElement == null)
		{
			return NullOperationOptions.getInstance();
		}

		ChainDocumentChecker chainDocumentChecker = new ChainDocumentChecker();

		Element formElem = XmlHelper.selectSingleNode(optionElement, "form");

		String formName = null;
		if (formElem != null)
			formName = formElem.getAttribute("name");

		try
		{
			NodeList actionsNodeList = XmlHelper.selectNodeList(optionElement, "check");
			for (int i = 0; i < actionsNodeList.getLength(); i++)
			{
				Element actionElement = (Element) actionsNodeList.item(i);
				Class<?> checkerClass = Class.forName(actionElement.getAttribute("class"));
				DocumentChecker documentChecker = (DocumentChecker) checkerClass.newInstance();

				NodeList parametersNodeList = XmlHelper.selectNodeList(actionElement, "parameter");
				for (int j = 0; j < parametersNodeList.getLength(); j++)
				{
					Element parameterElement = (Element) parametersNodeList.item(j);
					String name = parameterElement.getAttribute("name");
					documentChecker.setParameter(name, parameterElement.getTextContent());
				}
				chainDocumentChecker.addChecker(documentChecker);
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new TransformerException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new TransformerException(e);
		}
		catch (InstantiationException e)
		{
			throw new TransformerException(e);
		}

		return new DefaultOperationOptions(formName, chainDocumentChecker);
	}

	/**
	 * @return отпарсенная форма
	 */
	public Form getForm()
	{
		return builder.build();
	}

	/**
	 * Справочники документа
	 * @return Map справочников. Ключ - имя, значение корневой элемент справочникка
	 */
	public Map<String, Element> getDictionaries()
	{
		return dictionaries;
	}

	/**
	 * @return класс документа
	 */
	public String getDocumentClassName()
	{
		return documentClassName;
	}

	public String getTemplateClassName()
	{
		return templateClassName;
	}

	/**
	 * @return класс загрузчик доп полей. null если отсутствует
	 */
	public String getExtendedMetadataLoaderClassName()
	{
		return extendedMetadataLoaderClassName;
	}

	// Чтение полей
	private void parseFields(Element root) throws TransformerException
	{
		FieldParser fieldParser = new FieldParser(root);
		List<Field> fields = fieldParser.parse().getFields();
		builder.setFields(fields);
	}

	private void parseFormValidators(Element root) throws TransformerException, BusinessException
	{
		List<MultiFieldsValidator> formValidators = new MultiFieldsValidatorsParser(root).parse().getValidators();

		builder.setFormValidators(formValidators);
	}

	private void parseDictionaries(Element root) throws TransformerException
	{
		NodeList entityListNodeList = XmlHelper.selectNodeList(root, "dictionaries/entity-list");

		Map<String, Element> dictMap = new HashMap<String, Element>();

		int length = entityListNodeList.getLength();
		for (int i = 0; i < length; i++)
		{
			Element dictElem = (Element) entityListNodeList.item(i);
			String dictName = dictElem.getAttribute("name");
			dictMap.put(dictName, dictElem);
		}

		dictionaries = dictMap;
	}

	public String getListFormName()
	{
		return listFormName;
	}

	public boolean isNeedParent()
	{
		return needParent;
	}

	public OperationOptions getWithdrawOptions()
	{
		return withdrawOptions;
	}

	public Map<String, Object> getAdditionalAttributes()
	{
		return additionalAttributes;
	}

	public OperationOptions getEditOptions()
	{
		return editOptions;
	}

	public StateMachineInfo getStateMachineInfo()
	{
		return stateMachineInfo;
	}
}