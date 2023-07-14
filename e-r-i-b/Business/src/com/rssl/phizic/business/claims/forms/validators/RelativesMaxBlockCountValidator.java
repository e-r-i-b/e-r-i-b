package com.rssl.phizic.business.claims.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 12.02.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ѕровер€ет, чтобы количество заполненных блоков не превышало определенного количества.
 * ѕровер€ютс€ значени€ об€зательных полей блока(по кототрым можно определить, что блок заполнен).
 * Ќазвание пол€ составл€етс€ из префикса, индекса и базовой части названи€.
 */
public class RelativesMaxBlockCountValidator extends MultiFieldsValidatorBase
{
	private static final String FIELD_1_PREFIX = "fieldType1";
	private static final String FIELD_2_PREFIX = "fieldType2";

	private static final String MAX_COUNT = "maxCount";
	private static final String REQUIRED_FIELD_BASE_NAME = "requiredField";
	private static final String ERROR_MESSAGE = "¬ы можете указать суммарно %d человек";


	public boolean validate(Map values) throws TemporalDocumentException
	{
		Integer maxCount = Integer.valueOf(getParameter(MAX_COUNT));
		String fieldBaseName = getParameter(REQUIRED_FIELD_BASE_NAME);

		setMessage(String.format(ERROR_MESSAGE, maxCount));

		List<String> fieldPrefixList = new ArrayList<String>(2);
		fieldPrefixList.add(getParameter(FIELD_1_PREFIX));
		fieldPrefixList.add(getParameter(FIELD_2_PREFIX));

		int fullFieldsCount = 0;
		for (String fieldPrefix : fieldPrefixList)
		{
			for (int i = 1; i <= maxCount + 1; i++)
			{
				String fieldName = fieldPrefix + "_" + i + "_" + fieldBaseName;
				if (StringHelper.getNullIfEmpty((String) values.get(fieldName)) != null)
				{
					fullFieldsCount++;
					if (fullFieldsCount > maxCount)
						return false;
				}
				else break;
			}
		}

		return true;
	}
}
