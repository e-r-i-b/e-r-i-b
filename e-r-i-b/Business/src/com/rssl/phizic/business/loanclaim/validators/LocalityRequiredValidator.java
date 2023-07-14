package com.rssl.phizic.business.loanclaim.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * Валидирует условие: обязательно должна быть заполнена  одна из комбинаций полей  "тип города"+ "город",  или  "Тип нас. Пункта" + "Населенный пункт".
 * @author Rtischeva
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 */
public class LocalityRequiredValidator extends MultiFieldsValidatorBase
{
	public static final String PARAMETER_CITY_TYPE_FIELD_NAME = "cityTypeFieldName";
	public static final String PARAMETER_CITY_FIELD_NAME = "cityFieldName";
	public static final String PARAMETER_LOCALITY_TYPE_FIELD_NAME = "localityTypeFieldName";
	public static final String PARAMETER_LOCALITY_FIELD_NAME = "localityFieldName";

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String cityTypeField = getParameter(PARAMETER_CITY_TYPE_FIELD_NAME);
		String cityField = getParameter(PARAMETER_CITY_FIELD_NAME);
		String localityTypeField = getParameter(PARAMETER_LOCALITY_TYPE_FIELD_NAME);
		String localityField = getParameter(PARAMETER_LOCALITY_FIELD_NAME);

		String cityType = (String) values.get(cityTypeField);
		String city = (String) values.get(cityField);
		String localityType = (String) values.get(localityTypeField);
		String locality = (String) values.get(localityField);

		if ((StringHelper.isNotEmpty(cityType) && StringHelper.isNotEmpty(city)) || (StringHelper.isNotEmpty(localityType) && StringHelper.isNotEmpty(locality)))
			return true;
		return false;
	}
}
