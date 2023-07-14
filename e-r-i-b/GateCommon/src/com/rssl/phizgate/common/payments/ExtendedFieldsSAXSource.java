package com.rssl.phizgate.common.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizgate.common.documents.payments.fields.StringFieldValidationParameter;
import com.rssl.phizgate.common.payments.systems.recipients.CommonField;
import com.rssl.phizgate.common.payments.systems.recipients.FieldValidationRuleImpl;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.XmlReplicaSourceBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;

import static com.rssl.phizic.common.types.documents.ServiceProvidersConstants.*;

/**
 * @author khudyakov
 * @ created 13.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class ExtendedFieldsSAXSource extends XmlReplicaSourceBase
{
	private List<Field> fields = new ArrayList<Field>();
	private InputStream stream;

	public ExtendedFieldsSAXSource(String data) throws DocumentException
	{
		try
		{
			stream = new ByteArrayInputStream(data.getBytes("UTF-8"));
			internalParse();
		}
		catch (GateLogicException e)
		{
			throw new DocumentException(e);
		}
		catch (GateException e)
		{
			throw new DocumentException(e);
		}
		catch (UnsupportedEncodingException e)
		{
			throw new DocumentException(e);
		}
	}

	protected void clearData()
	{
		fields.clear();
	}

	protected InputStream getDefaultStream()
	{
		return stream;
	}

	protected XMLFilter getDefaultFilter() throws ParserConfigurationException, SAXException
	{
		return new SaxFilter(XmlHelper.newXMLReader());
	}

	public void initialize(GateFactory factory) throws GateException
	{
		throw new UnsupportedOperationException();
	}

	public Iterator iterator() throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public List<Field> getSource()
	{
		return Collections.unmodifiableList(fields);
	}

	protected class SaxFilter extends XMLFilterImpl
	{
		private FieldValidationRuleImpl validationRule;
		private List<FieldValidationRule> validationRules;
		private Map<String, Object> mapValidatorParameter;
		private boolean validationRuleLocation = false;

		private List<RequisiteType> requisiteTypes;
		private ListValue listValue;
		private List<ListValue> listValues;
		private boolean listValueLocation = false;

		protected StringBuffer tagValue = new StringBuffer();

		protected CommonField field;


		protected SaxFilter ( XMLReader parent )
		{
			super(parent);
		}

		public void characters (char ch[], int start, int length) throws SAXException
		{
			tagValue.append(ch, start, length);
		}

		public void startElement (String uri, String localName, String qName, Attributes attributes)
			   throws SAXException
		{
			tagValue = new StringBuffer();

			if (ATTRIBUTE_FIELD.equalsIgnoreCase(qName))
			{
				field = new CommonField();
				return;
			}
			if (ATTRIBUTE_MENU_FIELD.equalsIgnoreCase(qName))
			{
				listValues = new ArrayList<ListValue>();
				return;
			}
			if (ATTRIBUTE_MENU_ITEM_FIELD.equalsIgnoreCase(qName))
			{
				listValue = new ListValue();
				listValueLocation = true;
				return;
			}
			if (ATTRIBUTE_REQUISITE_TYPES.equalsIgnoreCase(qName))
			{
				requisiteTypes = new ArrayList<RequisiteType>();
				return;
			}
			if (VALIDATORS_FIELD.equalsIgnoreCase(qName))
			{
				validationRules = new ArrayList<FieldValidationRule>();
				return;
			}
			if (VALIDATOR_FIELD.equalsIgnoreCase(qName))
			{
				validationRuleLocation = true;
				validationRule = new FieldValidationRuleImpl();
				mapValidatorParameter = new HashMap<String, Object>();
				return;
			}
			prepareFieldValue(qName);
		}

		public void endElement (String uri, String localName, String qName)
			   throws SAXException
		{
			try
			{
				if (ATTRIBUTE_FIELD.equalsIgnoreCase(qName))
				{
					fields.add(field);
					return;
				}
				if (ATTRIBUTE_NAME_BS_FIELD.equalsIgnoreCase(qName))
				{
					field.setExternalId(tagValue.toString());
					return;
				}
				if (ATTRIBUTE_NAME_VISIBLE_FIELD.equalsIgnoreCase(qName))
				{
					field.setName(tagValue.toString());
					return;
				}
				if (ATTRIBUTE_DESCRIPTION_FIELD.equalsIgnoreCase(qName))
				{
					field.setDescription(tagValue.toString());
					return;
				}
				if (ATTRIBUTE_EXTENDED_DESCRIPTION_ID_FIELD.equalsIgnoreCase(qName))
				{
					field.setExtendedDescId(tagValue.toString());
					return;
				}
				if (COMMENT_FIELD.equalsIgnoreCase(qName))
				{
					field.setHint(tagValue.toString());
					return;
				}

				if (POPUP_HINT.equalsIgnoreCase(qName))
				{
					field.setPopupHint(tagValue.toString());
					return;
				}

				if (TYPE_FIELD.equalsIgnoreCase(qName))
				{
					field.setType(FieldDataType.fromValue(tagValue.toString()));
				}
				if (ATTRIBUTE_NUMBER_PRECISION_FIELD.equalsIgnoreCase(qName)
						&& FieldDataType.number == field.getType())
				{
					if (!StringHelper.isEmpty(tagValue.toString()))
						field.setNumberPrecision(Integer.parseInt(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_DATE_PERIOD_FIELD.equalsIgnoreCase(qName)
						&& FieldDataType.calendar == field.getType())
				{
					if (!StringHelper.isEmpty(tagValue.toString()))
						field.setPeriod(tagValue.toString());
					return;
				}
				if (ID_FIELD.equalsIgnoreCase(qName)
						&& listValueLocation)
				{
					listValue.setId(tagValue.toString());
					return;
				}
				if (VALUE_FIELD.equalsIgnoreCase(qName)
						&& listValueLocation)
				{
					listValue.setValue(tagValue.toString());
					return;
				}
				if (ATTRIBUTE_MENU_ITEM_FIELD.equalsIgnoreCase(qName)
						&& (FieldDataType.set == field.getType() || FieldDataType.list == field.getType() || FieldDataType.graphicset == field.getType()))
				{
					listValues.add(listValue);
					listValueLocation = false;
					return;
				}
				if (ATTRIBUTE_MENU_FIELD.equalsIgnoreCase(qName))
				{
					field.setValues(listValues);
					return;
				}
				if (ATTRIBUTE_GRAPHIC_TEMPLATE_NAME_FIELD.equalsIgnoreCase(qName))
				{
					field.setGraphicTemplateName(tagValue.toString());
					return;
				}
				if (ATTRIBUTE_IS_REQUIRED_FIELD.equalsIgnoreCase(qName))
				{
					field.setRequired(Boolean.valueOf(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_IS_EDITABLE_FIELD.equalsIgnoreCase(qName))
				{
					field.setEditable(Boolean.valueOf(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_IS_VISIBLE_FIELD.equalsIgnoreCase(qName))
				{
					field.setVisible(Boolean.valueOf(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_IS_MAIN_SUM_FIELD.equalsIgnoreCase(qName))
				{
					field.setMainSum(Boolean.valueOf(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_IS_KEY_FIELD.equalsIgnoreCase(qName))
				{
					field.setKey(Boolean.valueOf(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_IS_FOR_BILL_FIELD.equalsIgnoreCase(qName))
				{
					field.setRequiredForBill(Boolean.valueOf(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_REQUIRED_FOR_CONFIRMATION_FIELD.equalsIgnoreCase(qName))
				{
					field.setRequiredForConformation(Boolean.valueOf(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_HIDE_IN_CONFIRMATION_FIELD.equalsIgnoreCase(qName))
				{
					field.setHideInConfirmation(Boolean.valueOf(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_REQUISITE_TYPE.equalsIgnoreCase(qName))
				{
					requisiteTypes.add(RequisiteType.fromValue(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_REQUISITE_TYPES.equalsIgnoreCase(qName))
				{
					field.setRequisiteTypes(requisiteTypes);
					return;
				}
				if (ATTRIBUTE_SAVE_IN_TEMPLATE_FIELD.equalsIgnoreCase(qName))
				{
					field.setSaveInTemplate(Boolean.valueOf(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_MAX_LENGTH_FIELD.equalsIgnoreCase(qName))
				{
					if (!StringHelper.isEmpty(tagValue.toString()))
						field.setMaxLength(Long.parseLong(tagValue.toString()));
					return;
				}
				if (ATTRIBUTE_MIN_LENGTH_FIELD.equalsIgnoreCase(qName))
				{
					if (!StringHelper.isEmpty(tagValue.toString()))
						field.setMinLength(Long.parseLong(tagValue.toString()));
					return;
				}
				if (VALIDATOR_TYPE_FIELD.equalsIgnoreCase(qName)
						&& validationRuleLocation)
				{
					validationRule.setFieldValidationRuleType(FieldValidationRuleType.fromValue(tagValue.toString()));
					return;
				}
				if (VALIDATOR_MESSAGE_FIELD.equalsIgnoreCase(qName)
						&& validationRuleLocation)
				{
					validationRule.setErrorMessage(tagValue.toString());
					return;
				}
				if (VALIDATOR_PARAMETER_FIELD.equalsIgnoreCase(qName)
						&& validationRuleLocation)
				{
					String type = validationRule.getFieldValidationRuleType().toString();
					mapValidatorParameter.put(type, new StringFieldValidationParameter(tagValue.toString()));
					return;
				}
				if (VALIDATOR_FIELD.equalsIgnoreCase(qName))
				{
					validationRuleLocation = false;
					validationRule.setFieldValidators(mapValidatorParameter);
					validationRules.add(validationRule);
					return;
				}
				if (VALIDATORS_FIELD.equalsIgnoreCase(qName))
				{
					field.setFieldValidationRules(validationRules);
					return;
				}
				if (ATTRIBUTE_DEFAULT_VALUE_FIELD.equalsIgnoreCase(qName))
				{
					field.setDefaultValue(tagValue.toString());
					return;
				}
				if (ATTRIBUTE_ERROR_FIELD.equalsIgnoreCase(qName))
				{
					field.setError(tagValue.toString());
					return;
				}
				if (ATTRIBUTE_GROUP_NAME_FIELD.equalsIgnoreCase(qName))
				{
					field.setGroupName(tagValue.toString());
					return;
				}
				if (ATTRIBUTE_MASK_FIELD.equalsIgnoreCase(qName))
				{
					field.setMask(tagValue.toString());
					return;
				}
				if (ATTRIBUTE_BUSINESS_SUB_TYPE_FIELD.equalsIgnoreCase(qName))
				{
					if(StringHelper.isNotEmpty(tagValue.toString()))
						field.setBusinessSubType(BusinessFieldSubType.valueOf(tagValue.toString()));
					return;
				}
				processFieldValue(qName);
			}
			finally
			{
				tagValue = new StringBuffer();
			}
		}

		protected void processFieldValue(String qName)
		{
			//для документов значения в мете на храним
		}

		protected void prepareFieldValue(String qName)
		{
			//для документов значения в мете на храним
		}
	}
}
