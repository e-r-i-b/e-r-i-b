package com.rssl.phizic.web.configure.basketidents;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.dictionaries.basketident.AttributeForBasketIdentType;
import com.rssl.phizic.business.dictionaries.basketident.validators.DuplicateDocumentFieldValidator;
import com.rssl.phizic.business.dictionaries.basketident.validators.DuplicateProviderFieldValidator;
import com.rssl.phizic.business.fields.FieldDescriptionShortcut;
import com.rssl.phizic.common.types.basket.fieldFormula.Constants;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author osminin
 * @ created 03.08.15
 * @ $Author$
 * @ $Revision$
 *
 * Билдер логической формы для привязки параметров ПУ
 */
class FieldFormulaFormBuilder
{
	private static final String REGEXP_SPLITTER = "|";

	private String fieldIds;
	private String newFieldIds;
	private List<FieldDescriptionShortcut> fieldDescriptions;
	private Set<AttributeForBasketIdentType> attributes;
	private String providerRegExp;
	private String identTypeRegExp;

	/**
	 * ctor
	 * @param fieldIds идентификаторы измененных формул
	 * @param newFieldIds идентификаторы новых формул
	 * @param fieldDescriptions список атрибутов поставщика услуг
	 * @param attributes набор атрибутов документа профиля
	 */
	FieldFormulaFormBuilder(String fieldIds, String newFieldIds, List<FieldDescriptionShortcut> fieldDescriptions, Set<AttributeForBasketIdentType> attributes)
	{
		this.fieldIds = fieldIds;
		this.newFieldIds = newFieldIds;
		this.fieldDescriptions = fieldDescriptions;
		this.attributes = attributes;
		providerRegExp = getProviderRegexp(fieldDescriptions);
		identTypeRegExp = getIdentTypeRegexp(attributes);
	}

	/**
	 * Построить форму
	 * @return форма
	 */
	public Form build()
	{
		FormBuilder formBuilder = new FormBuilder();

		if ((StringHelper.isEmpty(fieldIds) && StringHelper.isEmpty(newFieldIds)) || CollectionUtils.isEmpty(attributes) || CollectionUtils.isEmpty(fieldDescriptions))
		{
			return formBuilder.build();
		}

		createFields(formBuilder, fieldIds, StringUtils.EMPTY);
		createFields(formBuilder, newFieldIds, Constants.NEW_FIELD_PREFIX);

		DuplicateProviderFieldValidator duplicateProviderFieldValidator = new DuplicateProviderFieldValidator(fieldIds, newFieldIds);
		DuplicateDocumentFieldValidator duplicateDocumentFieldValidator = new DuplicateDocumentFieldValidator(fieldIds, newFieldIds, attributes.size());
		formBuilder.addFormValidators(duplicateProviderFieldValidator, duplicateDocumentFieldValidator);

		return formBuilder.build();
	}

	private void createFields(FormBuilder formBuilder, String fieldIds, String prefix)
	{
		if (StringHelper.isEmpty(fieldIds))
		{
			return;
		}

		String[] ids = fieldIds.split(Constants.ID_SPLITTER);
		for (int i = 0; i < ids.length; i++)
		{
			formBuilder.addFields(createFields(ids[i], prefix));
		}
	}

	private List<Field> createFields(String id, String prefix)
	{
		int attributesSize = attributes.size();
		List<Field> fields = new ArrayList<Field>(attributesSize * 2);
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName(Constants.getProviderFieldName(id, prefix));
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(providerRegExp, "Выберите параметр поставщика услуг из имеющихся значений."));
		fields.add(fieldBuilder.build());

		for (int i = 0; i < attributesSize; i++)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(Constants.getValueFieldName(id, i, prefix));
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RegexpFieldValidator(".{0,20}", "Значение не должно превышать 20 символов."));
			fields.add(fieldBuilder.build());

			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(Constants.getIdentTypeFieldName(id, i, prefix));
			fieldBuilder.setType(StringType.INSTANCE.getName());
			RegexpFieldValidator identRegexpValidator = new RegexpFieldValidator(identTypeRegExp, "Выберите параметр документа из имеющихся значений.");
			identRegexpValidator.setEnabledExpression(new RhinoExpression("form." + Constants.getIdentTypeFieldName(id, i, prefix) + " != ''"));
			fieldBuilder.addValidators(identRegexpValidator);
			fields.add(fieldBuilder.build());
		}

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(Constants.getLastFieldName(id, prefix));
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,20}", "Значение не должно превышать 20 символов."));
		fields.add(fieldBuilder.build());

		return fields;
	}

	private String getIdentTypeRegexp(Set<AttributeForBasketIdentType> attributes)
	{
		if (CollectionUtils.isEmpty(attributes))
		{
			return StringUtils.EMPTY;
		}

		StringBuilder builder = new StringBuilder();
		Iterator iterator = attributes.iterator();

		while (iterator.hasNext())
		{
			AttributeForBasketIdentType attribute = (AttributeForBasketIdentType) iterator.next();
			builder.append(attribute.getSystemId());

			if (iterator.hasNext())
			{
				builder.append(REGEXP_SPLITTER);
			}
		}

		return builder.toString();
	}

	private String getProviderRegexp(List<FieldDescriptionShortcut> fieldDescriptions)
	{
		if (CollectionUtils.isEmpty(fieldDescriptions))
		{
			return StringUtils.EMPTY;
		}

		StringBuilder builder = new StringBuilder();
		Iterator iterator = fieldDescriptions.iterator();

		while (iterator.hasNext())
		{
			FieldDescriptionShortcut fieldDescription = (FieldDescriptionShortcut) iterator.next();
			builder.append(fieldDescription.getExternalId());

			if (iterator.hasNext())
			{
				builder.append(REGEXP_SPLITTER);
			}
		}

		return builder.toString();
	}
}
