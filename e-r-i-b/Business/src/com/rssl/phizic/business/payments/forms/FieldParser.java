package com.rssl.phizic.business.payments.forms;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.FieldValidatorBuilder;
import com.rssl.common.forms.expressions.ExpressionFactory;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.BusinessCategory;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.exceptions.MalformedVersionFormatException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * Парсит описание поля из XML
 * @author Evgrafov
 * @ created 30.01.2006
 * @ $Author: egorovaav $
 * @ $Revision: 59164 $
 */
public class FieldParser
{
	private static final ExpressionFactory expressionFactory = new ExpressionFactory();
	private Element root;
	private List<Field> fields;

	/**
	 * @param root - Элемент содержа
	 */
	public FieldParser(Element root)
	{
		this.root = root;
	}

	public List<Field> getFields()
	{
		return fields;
	}

	public FieldParser parse() throws TransformerException
	{
		fields = parseFields();
		return this;
	}

	private List<Field> parseFields() throws TransformerException
	{
		NodeList fieldsNodeList = XmlHelper.selectNodeList(root, "fields/field");

		int fieldsCount = fieldsNodeList.getLength();
		fields = new ArrayList<Field>();

		for (int i = 0; i < fieldsCount; i++)
		{
			Element fieldElem = (Element) fieldsNodeList.item(i);
			FieldBuilder builder = new FieldBuilder();

			String fldName = fieldElem.getAttribute("name");
			builder.setName(fldName);
			builder.setDescription(fieldElem.getAttribute("description"));
			builder.setSource(fieldElem.getAttribute("source"));

			String signableStr = fieldElem.getAttribute("signable");
			if (signableStr.length() > 0)
				builder.setSignable(Boolean.parseBoolean(signableStr));

			String typeAttrValue = fieldElem.getAttribute("type");
			if (typeAttrValue.length() > 0)
				builder.setType(typeAttrValue);

			String subTypeAttrValue = fieldElem.getAttribute("subType");
			if (StringHelper.isNotEmpty(subTypeAttrValue))
			{
				builder.setSubType(subTypeAttrValue);
			}

			String businessCategory = fieldElem.getAttribute("category");
			if (StringHelper.isNotEmpty(businessCategory))
			{
				builder.setBusinessCategory(BusinessCategory.valueOf(StringUtils.upperCase(businessCategory)));
			}

			builder.setMask(fieldElem.getAttribute("mask"));

			String enabledAttrValue = fieldElem.getAttribute("enabled");
			if (enabledAttrValue.length() > 0)
				builder.setEnabledExpression(expressionFactory.newExpression(enabledAttrValue));

			try
			{
				String fromApi = fieldElem.getAttribute("fromApi");
				if (fromApi.length() > 0)
					builder.setFromApi(VersionNumber.fromString(fromApi));
				String toApi = fieldElem.getAttribute("toApi");
				if (toApi.length() > 0)
					builder.setToApi(VersionNumber.fromString(toApi));
			}
			catch (MalformedVersionFormatException e)
			{
				throw new TransformerException(e);
			}

			String valueAttrValue = fieldElem.getAttribute("value");
			if (valueAttrValue.length() > 0)
				builder.setValueExpression(expressionFactory.newExpression(valueAttrValue));

			String initalAttrValue = fieldElem.getAttribute("inital");
			if (initalAttrValue.length() > 0)
			{
				builder.setInitalValueExpression(expressionFactory.newExpression(initalAttrValue));
			}

			FieldValidator[] validators = parseFieldValidators(fieldElem);

			if (validators.length != 0)
			{
				builder.setValidators(validators);
			}

			FieldValueParser parser = parseParser(fieldElem);
			if (parser != null)
			{
				builder.setParser(parser);
			}

			fields.add(builder.build());
		}
		return fields;
	}

	private FieldValueParser parseParser(Element fieldElem) throws TransformerException
	{
		try
		{
			Element parserElem = XmlHelper.selectSingleNode(fieldElem, "parser");
			if (parserElem == null)
				return null;

			Class<?> parser = Class.forName(parserElem.getAttribute("class"));
			return (FieldValueParser) parser.newInstance();
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
	}

	/** Чтение валидаторов полей */
	private FieldValidator[] parseFieldValidators(Element fieldElem) throws TransformerException
	{
		NodeList validatorsNodeList = XmlHelper.selectNodeList(fieldElem, "validators/validator");

		int validatorsCount = validatorsNodeList.getLength();
		FieldValidator[] validators = new FieldValidator[validatorsCount];

		for (int i = 0; i < validatorsCount; i++)
		{
			Element validatorElem = (Element) validatorsNodeList.item(i);
			validators[i] = parseFieldValidator(validatorElem);
		}

		return validators;
	}

	private FieldValidator parseFieldValidator(Element validatorElem) throws TransformerException
	{
		try
		{
			String className = validatorElem.getAttribute("class");
			FieldValidatorBuilder validatorBuilder = new FieldValidatorBuilder(className);

			String mode = validatorElem.getAttribute("mode");
			if (!StringHelper.isEmpty(mode))
			{
				validatorBuilder.setMode(mode);
			}

			Element messageElem = XmlHelper.selectSingleNode(validatorElem, "message");
			if (messageElem != null)
			{
				String message = messageElem.getAttribute("text");
				validatorBuilder.setMessage(message);
			}
			String enabledAttrValue = validatorElem.getAttribute("enabled");
			if (enabledAttrValue.length() > 0)
				validatorBuilder.setEnabledExpression(expressionFactory.newExpression(enabledAttrValue));

			parseFiedValidatorsParameters(validatorBuilder, validatorElem);
			return validatorBuilder.build();
		}
		catch (ClassNotFoundException e)
		{
			throw new TransformerException(e);
		}
		catch (InstantiationException e)
		{
			throw new TransformerException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new TransformerException(e);
		}
	}

	/**
	 * Чтение параметров валидатора
	 *
	 * @param validatorBuilder билдер
	 *@param validatorElem @throws TransformerException
	 */
	private void parseFiedValidatorsParameters(FieldValidatorBuilder validatorBuilder, Element validatorElem) throws TransformerException
	{
		NodeList paramsNodeList = XmlHelper.selectNodeList(validatorElem, "parameter");

		for (int i = 0; i < paramsNodeList.getLength(); i++)
		{
			Element paramElem = (Element) paramsNodeList.item(i);
			String paramName = paramElem.getAttribute("name");
			String paramValue = XmlHelper.getElementText(paramElem);
			validatorBuilder.setParameter(paramName, paramValue);
		}
	}
}