package com.rssl.phizic.business.forms;

import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.documents.metadata.DocumentMetadatalessExtendedMetadataLoaderBase;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.claims.LoanClaimDefinitionProvider;
import com.rssl.phizic.business.loans.claims.generated.*;
import com.rssl.phizic.business.loans.products.LoanProduct;
import com.rssl.phizic.business.loans.products.LoanProductService;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import javax.xml.parsers.DocumentBuilder;

public class LoanClaimMetadataLoader extends DocumentMetadatalessExtendedMetadataLoaderBase
{
	private static final LoanProductService loanProductService = new LoanProductService();
	public static final String GUARANTORS_PREFIX = "GUARANTOR_%S_";
	private static final String LOAN_KIND_FIELD_NAME = "kind";
	private static final String LOAN_PRODUCT_FIELD_NAME = "product";
	private static final String CLIENT_REQUEST_AMOUNT_FIELD_NAME = "client-request-amount";

	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws FormException
	{
		try
		{
			String kindId = fieldSource.getValue(LOAN_KIND_FIELD_NAME);
			if (kindId == null)
				throw new FormException("Не задан [" + LOAN_KIND_FIELD_NAME + "]");

			String loanProductId = fieldSource.getValue(LOAN_PRODUCT_FIELD_NAME);
			if (loanProductId == null)
				throw new FormException("Не задан [" + LOAN_PRODUCT_FIELD_NAME + "]");

			LoanProduct loanProduct = loanProductService.findById(Long.valueOf(loanProductId));
			LoanClaimDefinitionProvider definitionProvider = new LoanClaimDefinitionProvider();

			LoanClaimDefinitionDescriptor claimDefinition = definitionProvider.getLoanDefinition(kindId);

			LoanClaimExtendedFieldsBuilder builder = new LoanClaimExtendedFieldsBuilder(kindId);

			ExtendedMetadata extendedMetadata = new ExtendedMetadata();
			extendedMetadata.setOriginal(metadata);
			extendedMetadata.addAllDictionaries(metadata.getDictionaries());
			extendedMetadata.addDictionary("extendedFields.xml", createXmlExtendedFields(claimDefinition, loanProduct));

			FormBuilder formBuilder = new FormBuilder();
			formBuilder.setName(metadata.getForm().getName());
			formBuilder.setDescription(metadata.getForm().getDescription());
			formBuilder.setDetailedDescription(metadata.getForm().getDetailedDescription());
			formBuilder.addFields(metadata.getForm().getFields());
			formBuilder.addFields(builder.build(loanProduct));
			formBuilder.addFormValidators(metadata.getForm().getFormValidators());
			if (claimDefinition.getFormValidators() != null)
			{
				List<FormValidatorDescriptor> formValidatorDescriptors = claimDefinition.getFormValidators().getFormValidator();
				LoanClaimExtendedFormValidatorsBuilder validatorsBuilder = new  LoanClaimExtendedFormValidatorsBuilder(formValidatorDescriptors);
				formBuilder.addFormValidators(validatorsBuilder.build());
			}

			extendedMetadata.setExtendedForm(formBuilder.build());

			return extendedMetadata;
		}
		catch (BusinessException e)
		{
			throw new FormException(e);
		}
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		String kindId = fieldSource.getValue(LOAN_KIND_FIELD_NAME);
		if (kindId == null)
			throw new FormException("Не задан [" + LOAN_KIND_FIELD_NAME + "]");

		String loanProductId = fieldSource.getValue(LOAN_PRODUCT_FIELD_NAME);
		if (loanProductId == null)
			throw new FormException("Не задан [" + LOAN_PRODUCT_FIELD_NAME + "]");
		return kindId + "\t" + loanProductId;
	}

	private Element createXmlExtendedFields(LoanClaimDefinitionDescriptor claimDefinition, LoanProduct loanProduct)
	{
		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document document = documentBuilder.newDocument();
		Element root = document.createElement("loan");
		document.appendChild(root);

		Element fieldsElement = XmlHelper.appendSimpleElement(root, "fields");

		//noinspection unchecked
		createXmlExtendedFields(claimDefinition.getFields().getEntities(), fieldsElement, "");
		for (int i = 0; i < loanProduct.getGuarantorsCount(); i++)
		{
			Element groupElement = XmlHelper.appendSimpleElement(fieldsElement, "group");
			addAtribute(groupElement, "description", "Анкета поручителя " + (i + 1));
			addAtribute(groupElement, "name", String.format(GUARANTORS_PREFIX, i + 1) + "group");
			addRequestAmountField(groupElement,i+1);//ИЗВРАЩЕНИЕ
			//noinspection unchecked
			createXmlExtendedFields(claimDefinition.getFields().getEntities(), groupElement, String.format(GUARANTORS_PREFIX, i + 1));
		}
		//noinspection unchecked
		if (claimDefinition.getDictionaries() != null)
			createDictionaries((List<DictionaryDescriptor.EntityListDescriptor>) claimDefinition.getDictionaries().getEntityList(), root);
		return document.getDocumentElement();
	}

	private void addRequestAmountField(Element element, int guarantorNumber)
	{
		String fieldName = String.format(GUARANTORS_PREFIX, guarantorNumber) + CLIENT_REQUEST_AMOUNT_FIELD_NAME;
		Element fieldElement = XmlHelper.appendSimpleElement(element, "field");

		addAtribute(fieldElement, "description", "Запрашиваемая сумма кредита");
		addAtribute(fieldElement, "mandatory", "true");
		addAtribute(fieldElement, "name", fieldName);
		addAtribute(fieldElement, "type", "money");
		addAtribute(fieldElement, "value", "getFieldValue('"+CLIENT_REQUEST_AMOUNT_FIELD_NAME+"')");

	}

	private void createDictionaries(List<DictionaryDescriptor.EntityListDescriptor> entityListDescriptors, Element root)
	{
		if (entityListDescriptors == null)
		{
			return;
		}
		Element dictionariesElement = XmlHelper.appendSimpleElement(root, "dictionaries");

		for (DictionaryDescriptor.EntityListDescriptor listDescriptor : entityListDescriptors)
		{
			Element entityListElement = XmlHelper.appendSimpleElement(dictionariesElement, "entity-list");
			addAtribute(entityListElement, "name", listDescriptor.getName());
			//noinspection unchecked
			for (DictionaryEntityDescriptor entityDescriptor : (List<DictionaryEntityDescriptor>) listDescriptor.getEntity())
			{
				Element entityElement = XmlHelper.appendSimpleElement(entityListElement, "entity", entityDescriptor.getValue());
				addAtribute(entityElement, "key", entityDescriptor.getKey());
			}
		}
	}

	private void createXmlExtendedFields(List<EntityDescriptor> descriptors, Element root, String prefix)
	{
		for (EntityDescriptor descriptor : descriptors)
		{
			if (descriptor instanceof FieldDescriptor)
			{
				createXmlExtendedField((FieldDescriptor) descriptor, root, prefix);
			}
			else if (descriptor instanceof GroupDescriptor)
			{
				Element groupElement = XmlHelper.appendSimpleElement(root, "group");
				addAtribute(groupElement, "description", descriptor.getDescription());
				addAtribute(groupElement, "name", prefix + descriptor.getName());
				//noinspection unchecked
				createXmlExtendedFields(((GroupDescriptor) descriptor).getEntities(), groupElement, prefix);
			}
		}
	}

	private void createXmlExtendedField(FieldDescriptor fieldDescriptor, Element root, String prefix)
	{
		if (prefix.length()>0 && !fieldDescriptor.isGuarantor()){
			return;
		}

		Element fieldElement = XmlHelper.appendSimpleElement(root, "field");

		addAtribute(fieldElement, "description", fieldDescriptor.getDescription());
		addAtribute(fieldElement, "dictionary", fieldDescriptor.getDictionary());
		addAtribute(fieldElement, "enabled", prepareScript(fieldDescriptor.getEnabled(), prefix));
		addAtribute(fieldElement, "mandatory", fieldDescriptor.getMandatory());
		addAtribute(fieldElement, "name", prefix + fieldDescriptor.getName());
		addAtribute(fieldElement, "size", fieldDescriptor.getSize());
		addAtribute(fieldElement, "type", fieldDescriptor.getType());
		addAtribute(fieldElement, "hidden", fieldDescriptor.getHidden());
		addAtribute(fieldElement, "hint", fieldDescriptor.getHint());
		addAtribute(fieldElement, "readonly", fieldDescriptor.getReadonly());
		addAtribute(fieldElement, "value", prepareScript(fieldDescriptor.getValue(), prefix));
		addAtribute(fieldElement, "input-template", fieldDescriptor.getInputTemplate());

		FieldDescriptor.ActionsDescriptor actions = fieldDescriptor.getActions();
		if (actions == null)
		{
			return;
		}

		Element actionsElement = XmlHelper.appendSimpleElement(fieldElement, "actions");
		//noinspection unchecked
		for (ClientActionDescriptor clientActionDescriptor : (List<ClientActionDescriptor>) actions.getAction())
		{
			Element clientActionElement = XmlHelper.appendSimpleElement(actionsElement, "action", prepareScript(clientActionDescriptor.getValue(), prefix));
			addAtribute(clientActionElement, "type", clientActionDescriptor.getType());
			addAtribute(clientActionElement, "call-onload", Boolean.toString(clientActionDescriptor.isCallOnload()));
		}
	}

	public static String prepareScript(String script, String prefix)
	{
		if (script == null)
		{
			return null;
		}
		script = prepareFunctionCall(script, "getFieldValue", prefix);
		script = prepareFunctionCall(script, "setFieldValue", prefix);
		script = prepareFunctionCall(script, "setFieldEnabled", prefix);
		script = prepareFunctionCall(script, "getField", prefix);
		return script;
	}

	private static String prepareFunctionCall(String script, String functionName, String prefix)
	{
		return script.replace(functionName + "(", functionName + "('" + prefix + "'+");
	}

	private void addAtribute(Element fieldElement, String name, String value)
	{
		if (value == null)
		{
			return;
		}
		fieldElement.setAttribute(name, value);
	}
}
