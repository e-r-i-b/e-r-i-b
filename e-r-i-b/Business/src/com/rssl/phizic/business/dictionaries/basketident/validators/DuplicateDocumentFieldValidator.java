package com.rssl.phizic.business.dictionaries.basketident.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.basket.fieldFormula.Constants;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author osminin
 * @ created 31.07.15
 * @ $Author$
 * @ $Revision$
 *
 * ¬алидатор, провер€ющий отсутствие одинаковых выбранных полей документа в одной строчке при создании св€зки ѕ” и документа профил€.
 * Ќаличие дубликатов - невалидное состо€ние.
 * ѕустое значение дубликатом не считаетс€.
 */
public class DuplicateDocumentFieldValidator extends DuplicateFieldValidatorBase
{
	private int documentFieldsSize;

	/**
	 * ctor
	 * @param fieldsIds идентификаторы измененных полей
	 * @param newFieldsIds идентификаторы добавленных полей
	 * @param documentFieldsSize количество параметров документа
	 */
	public DuplicateDocumentFieldValidator(String fieldsIds, String newFieldsIds, int documentFieldsSize)
	{
		super(fieldsIds, newFieldsIds);
		this.documentFieldsSize = documentFieldsSize;
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		return validateFormulas(values, getFieldIdsArr(), StringUtils.EMPTY) &&
				validateFormulas(values, getNewFieldIdsArr(), Constants.NEW_FIELD_PREFIX);
	}

	private boolean validateFormulas(Map values, String[] ids, String prefix)
	{
		for (String id : ids)
		{
			//ѕроходим по каждой формуле и провер€ем наличие дубликатов среди выбранных параметров документа профил€
			if (!validateFormula(values, id, prefix))
			{
				return false;
			}
		}
		return true;
	}

	private boolean validateFormula(Map values, String id, String prefix)
	{
		Set<String> documentFieldValues = new HashSet<String>(documentFieldsSize);
		int emptyFieldsSize = 0;

		for (int i = 0; i < documentFieldsSize; i++)
		{
			//ѕолучаем значение каждого выбранного параметра документа
			String documentFieldValue = (String) values.get(Constants.getIdentTypeFieldName(id, i, prefix));

			if (documentFieldValue.equals(StringUtils.EMPTY))
			{
				//ѕустые значение дубликатами не считаютс€
				emptyFieldsSize++;
			}
			else
			{
				//Ќепустые складываем в сет
				documentFieldValues.add(documentFieldValue);
			}
		}

		//≈сли дубликатов нет, то количество пустых значений и размер сета должны соответствовать количеству параметров документа
		return documentFieldValues.size() + emptyFieldsSize == documentFieldsSize;
	}
}
