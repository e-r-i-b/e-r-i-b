package com.rssl.phizic.business.dictionaries.basketident.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.basket.fieldFormula.Constants;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author osminin
 * @ created 29.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Валидатор, проверяющий отсутствие одинаковых выбранных полей ПУ при создании связки ПУ и документа профиля.
 * Наличие дубликатов - невалидное состояние.
 */
public class DuplicateProviderFieldValidator extends DuplicateFieldValidatorBase
{
	private Set<String> fieldValues;

	/**
	 * ctor
	 * @param fieldsIds идентификаторы измененных полей
	 * @param newFieldsIds идентификаторы добавленных полей
	 */
	public DuplicateProviderFieldValidator(String fieldsIds, String newFieldsIds)
	{
		super(fieldsIds, newFieldsIds);
		fieldValues = new HashSet<String>(getFieldIdsArr().length + getNewFieldIdsArr().length);
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		fillSet(values, StringUtils.EMPTY, getFieldIdsArr());
		fillSet(values, Constants.NEW_FIELD_PREFIX, getNewFieldIdsArr());

		return fieldValues.size() == getFieldIdsArr().length + getNewFieldIdsArr().length;
	}

	private void fillSet(Map values, String prefix, String[] ids)
	{
		for (String id : ids)
		{
			fieldValues.add((String) values.get(Constants.getProviderFieldName(id, prefix)));
		}
	}
}
