package com.rssl.phizic.business.payments.forms;

import com.rssl.common.forms.FormValidatorBuilder;
import com.rssl.common.forms.expressions.ExpressionFactory;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author Evgrafov
 * @ created 30.01.2006
 * @ $Author: rtishcheva $
 * @ $Revision: 73973 $
 */

public class MultiFieldsValidatorsParser
{
	private static final ExpressionFactory expressionFactory = new ExpressionFactory();
	private Element root;
	private List<MultiFieldsValidator> validators;

	public MultiFieldsValidatorsParser(Element root)
	{
		this.root = root;
	}

	public List<MultiFieldsValidator> getValidators()
	{
		return validators;
	}

	public MultiFieldsValidatorsParser parse() throws TransformerException, BusinessException
	{
		NodeList validatorsNodeList = XmlHelper.selectNodeList(root, "form-validators/form-validator");

		int validatorsCount = validatorsNodeList.getLength();
		validators = new ArrayList<MultiFieldsValidator>();
		for (int i = 0; i < validatorsCount; i++)
		{
			Element validatorElem = (Element) validatorsNodeList.item(i);
			validators.add(parseValidator(validatorElem));
		}
		return this;
	}

	private MultiFieldsValidator parseValidator(Element validatorElem) throws TransformerException
	{
		try
		{
			String validatorClassName = validatorElem.getAttribute("class");
			String enabledAttrValue = validatorElem.getAttribute("enabled");

			FormValidatorBuilder builder = new FormValidatorBuilder(validatorClassName);

			String mode = validatorElem.getAttribute("mode");
			if (!StringHelper.isEmpty(mode))
			{
				builder.setMode(mode);
			}

			Element messageElem = XmlHelper.selectSingleNode(validatorElem, "message");
			if (messageElem != null)
			{
				String message = messageElem.getAttribute("text");
				builder.setMessage(message);
			}

			if (enabledAttrValue.length() > 0)
				builder.setEnabledExpression(expressionFactory.newExpression(enabledAttrValue));

			parseFormValidatorsFieldRefs(validatorElem, builder);
			parseFormValidatorsParams(validatorElem, builder);
			parseFormValidatorsErrorFields(validatorElem, builder);
			return builder.build();
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

	private void parseFormValidatorsFieldRefs(Element validatorElem, FormValidatorBuilder formValidator) throws TransformerException
	{
		NodeList fieldRefsNodeList = XmlHelper.selectNodeList(validatorElem, "field-ref");

		for (int i = 0; i < fieldRefsNodeList.getLength(); i++)
		{
			Element fieldRefElem = (Element) fieldRefsNodeList.item(i);
			String validatorFieldName = fieldRefElem.getAttribute("name");
			String formFieldName = XmlHelper.getElementText(fieldRefElem);
			formValidator.setBinding(validatorFieldName, formFieldName);
		}
	}

	private void parseFormValidatorsParams(Element validatorElem, FormValidatorBuilder formValidator) throws TransformerException
	{
		NodeList paremenersNodeList = XmlHelper.selectNodeList(validatorElem, "parameter");

		for (int i = 0; i < paremenersNodeList.getLength(); i++)
		{
			Element parameterElem = (Element) paremenersNodeList.item(i);
			String paramName = parameterElem.getAttribute("name");
			String paramValue = XmlHelper.getElementText(parameterElem);

			formValidator.setParameter(paramName, paramValue);
		}
	}

	private void parseFormValidatorsErrorFields(Element validatorElem, FormValidatorBuilder formValidator) throws TransformerException
	{
		NodeList errorFieldNodeList = XmlHelper.selectNodeList(validatorElem, "error-field");

		for (int i = 0; i < errorFieldNodeList.getLength(); i++)
		{
			Element parameterElem = (Element) errorFieldNodeList.item(i);
			String paramName = parameterElem.getAttribute("name");
			String paramValue = XmlHelper.getElementText(parameterElem);

			formValidator.setErrorField(paramName, paramValue);
		}
	}
}