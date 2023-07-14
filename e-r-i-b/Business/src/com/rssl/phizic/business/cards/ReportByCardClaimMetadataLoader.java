package com.rssl.phizic.business.cards;

import com.rssl.common.forms.*;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.forms.ExtendedMetadata;
import com.rssl.phizic.business.payments.forms.meta.receivers.ExtendedMetadataLoaderBase;
import org.w3c.dom.Element;

import java.util.*;

/**
 * Метадата для Заявки по карте на выписку на email.
 *
 * @author bogdanov
 * @ created 01.06.15
 * @ $Author$
 * @ $Revision$
 */

public class ReportByCardClaimMetadataLoader extends ExtendedMetadataLoaderBase
{
	public Metadata load(Metadata metadata, FieldValuesSource fieldSource) throws BusinessException, BusinessLogicException
	{
		ExtendedMetadata newMetadata = new ExtendedMetadata();
		newMetadata.setOriginal(metadata);

		FormBuilder formBuilder = getFormBuilder(metadata.getForm());

		Map<String, String> fieldFromSource = fieldSource.getAllValues();

		List<Field> fields =  new ArrayList<Field>();
		for (String key : fieldFromSource.keySet())
		{
		    FieldBuilder fieldBuilder = getFieldBuilder(key, fieldFromSource.get(key));
			fields.add(fieldBuilder.build());
		}
		formBuilder.setFields(fields);
		newMetadata.setExtendedForm(formBuilder.build());

		return newMetadata;
	}

	public Metadata load(Metadata metadata, BusinessDocument document) throws BusinessException, BusinessLogicException
	{
		return load(metadata, metadata.getForm().getFields(), new HashMap<String, Element>(), metadata.getForm().getDescription());
	}

	public Metadata load(Metadata metadata, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	public Metadata load(Metadata metadata, BusinessDocument document, TemplateDocument template) throws BusinessException, BusinessLogicException
	{
		throw new UnsupportedOperationException();
	}

	public String getExtendedMetadataKey(FieldValuesSource fieldSource) throws FormException
	{
		return null;
	}

	private FormBuilder getFormBuilder(Form form)
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setName(form.getName());
		formBuilder.setDetailedDescription(form.getDetailedDescription());
		formBuilder.setDescription(form.getDescription());
		formBuilder.addFields(form.getFields());
		formBuilder.setFormValidators(form.getFormValidators());

		return formBuilder;
	}

	private FieldBuilder getFieldBuilder(String name, String value)
	{
		FieldBuilder field = new FieldBuilder();
		field.setName(name);
		field.setType(new StringType().getName());
		if (value != null)
		{
			Expression expression = new ConstantExpression(value);
			field.setValueExpression(expression);
			field.setInitalValueExpression(expression);
		}
		field.setSource(String.format(BusinessDocument.EXTENDED_FIELD_SOURCE_PATTERN, name));

		return field;
	}
}
