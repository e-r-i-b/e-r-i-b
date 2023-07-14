package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.MultiFieldsValidator;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.payments.forms.FieldParser;
import com.rssl.phizic.business.payments.forms.MultiFieldsValidatorsParser;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 30.01.2006
 * @ $Author: Kidyaev $
 * @ $Revision: 2824 $
 */
public class PaymentListFormParser
{
	/**
	 * Парсит XML описание списка и инициализирует переданный ListFormImpl
	 * @param listForm список для инициализации. Заполняются поля Title, Description, FilterForm, EntityListSource
	 * @param stream XML c описанием
	 */
	public void parse(ListFormImpl listForm, InputStream stream) throws TransformerException, BusinessException
	{
		parse(listForm, new InputSource(stream));
	}

	/**
	 * Парсит XML описание списка и инициализирует переданный ListFormImpl
	 * @param listForm список для инициализации. Заполняются поля Title, Description, FilterForm, EntityListSource
	 * @param source XML c описанием
	 */
	public void parse(ListFormImpl listForm, InputSource source) throws TransformerException, BusinessException
	{
		Document document = parseDocument(source);

		Element root = document.getDocumentElement();

		String title = root.getAttribute("title");
		String description = root.getAttribute("description");

		if(title == null)
			throw new BusinessException("title attribute can't be null");

		listForm.setTitle(title);
		listForm.setDescription(description == null ? "" : description);

		Element filterElem = XmlHelper.selectSingleNode(root, "filter");
		List<Field> filterFields = new FieldParser(filterElem).parse().getFields();
		List<MultiFieldsValidator> filterValidators = new MultiFieldsValidatorsParser(filterElem).parse().getValidators();

		FormBuilder builder = new FormBuilder();

		builder.setFields(filterFields);
		builder.setFormValidators(filterValidators);
		builder.setName("Фильтр");

		Form filterForm = builder.build();
		listForm.setFilterForm(filterForm);
		HqlQueryParser hqlQueryParser = new HqlQueryParser(root);

		hqlQueryParser.setFilterForm(filterForm);
		listForm.setEntityListSource(hqlQueryParser.parse().getSource());
	}

	private Document parseDocument(InputSource source) throws BusinessException
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document;
		try
		{
			document = factory.newDocumentBuilder().parse(source);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		return document;
	}
}