package com.rssl.phizic.business.documents.metadata.converters;

import com.rssl.common.forms.*;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.types.FieldValueFormatter;
import com.rssl.common.forms.xslt.FormDataBuilder;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.metadata.MetadataCache;
import com.rssl.phizic.business.documents.metadata.source.DocumentFieldValuesSource;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.sun.org.apache.xml.internal.serialize.Method;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Source;

/**
 * Формирование данных формы из BusinessDocument
 * @author Roshka
 * @ created 17.05.2006
 * @ $Author$
 * @ $Revision$
 */
public class FormDataConverter implements Serializable
{
	private Form form;
	private FieldValuesSource valuesSource;
	private Map<String, String> fieldValues;
	private Map<String, Map<String,String>> fieldAttributes;

	public FormDataConverter(BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		Metadata metadata = MetadataCache.getExtendedMetadata(document);
		form = metadata.getForm();
		valuesSource = new DocumentFieldValuesSource(metadata, document);
	}

	public Map<String, String> getFieldValues()
	{
		return fieldValues;
	}

	public void setFieldValues(Map<String, String> fieldValues)
	{
		this.fieldValues = fieldValues;
	}

	public Form getForm()
	{
		return form;
	}

	public void setForm(Form form)
	{
		this.form = form;
	}

	public FieldValuesSource getValuesSource()
	{
		return valuesSource;
	}

	public void setValuesSource(FieldValuesSource valuesSource)
	{
		this.valuesSource = valuesSource;
	}

	/**
	 * @param form   форма документа
	 * @param valuesSource источник данных полей формы
	 */
	public FormDataConverter(Form form, FieldValuesSource valuesSource)
	{
		this.form = form;
		this.valuesSource = valuesSource;
	}

	/**
	 * Преобразует документ в данные формы.
	 * @return form-data
	 */
	public Source toFormData() throws FormException
	{
		prepareStringValues();

		FormDataBuilder formDataBuilder = new FormDataBuilder();
		List<Field> fields = form.getFields();

		for (int i = 0; i < fields.size(); i++)
		{
			Field field = fields.get(i);
			String fieldName = field.getName();
			String value = fieldValues.get(fieldName);
			formDataBuilder.appendField(fieldName, value, fieldAttributes.get(fieldName));
		}
		return formDataBuilder.getFormData();
	}

	/**
	 * @return преобразует документ в форму пригодную для подписи
	 */
	public byte[] toSignableForm()
	{
		prepareStringValues();

		try
		{
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			XMLSerializer serializer = new XMLSerializer(stream, new OutputFormat(Method.XML, "UTF-8", false));
			serializer.startDocument();
			serializer.startElement("signable-document", null);
			List<Field> fields = form.getFields();

			for (int i = 0; i < fields.size(); i++)
			{
				Field field = fields.get(i);

				if (!field.isSignable())
					continue;

				String fieldName = field.getName();
				String stringValue = fieldValues.get(fieldName);
				FieldValueFormatter formatter = field.getType().getFormatter();
				String signable = formatter.toSignableForm(stringValue);

				serializer.startElement(fieldName, null);
				serializer.characters(signable.toCharArray(), 0, signable.length());
				serializer.endElement(fieldName);
			}
			serializer.endElement("signable-document");
			serializer.endDocument();

			return stream.toByteArray();
		}
		catch (SAXException e)
		{
			throw new FormException(e);
		}
		catch (Exception e)
		{
			throw new FormException(e);
		}
	}

	private void prepareStringValues()
	{
		try
		{
			if (fieldValues == null)
			{
				fieldValues = buildStringFieldValues();
				fieldAttributes = buildAttributes();
			}
		}
		catch (DocumentException e)
		{
			throw new FormException(e);
		}
	}

	private Map<String, Map<String, String>> buildAttributes()
	{
		List<Field> fields = form.getFields();
		Map<String, Map<String,String>> result = new HashMap<String,Map<String,String>>();
		for (Field field : fields)
		{
			String isChange = String.valueOf(valuesSource.isChanged(field.getName()));
			Map<String,String> attr = new HashMap<String,String>();
			attr.put("changed", isChange);
			attr.put("masked", Boolean.toString(valuesSource.isMasked(field.getName())));
			result.put(field.getName(), attr);
		}
		return result;
	}

	private Map<String, String> buildStringFieldValues() throws DocumentException
	{
		List<Field> fields = form.getFields();
		Map<String, String> result = new HashMap<String,String>();
		for (Field field : fields)
		{
			String value = valuesSource.getValue(field.getName());
			result.put(field.getName(),value);
		}
		return result;
	}
}