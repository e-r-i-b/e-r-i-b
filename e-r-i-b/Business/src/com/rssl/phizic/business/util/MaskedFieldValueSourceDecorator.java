package com.rssl.phizic.business.util;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Декоратор для маскирования значений полей(передается на view)
 * @author niculichev
 * @ created 21.08.13
 * @ $Author$
 * @ $Revision$
 */
public class MaskedFieldValueSourceDecorator implements FieldValuesSource
{
	private FieldValuesSource valuesSource;
	private Map<String, Field> fieldsMap;
	private MaskingInfo maskingInfo;


	MaskedFieldValueSourceDecorator(MaskingInfo maskingInfo, FieldValuesSource valuesSource)
	{
		this.maskingInfo = maskingInfo;
		if(maskingInfo.getForm() != null)
			initialize(maskingInfo.getForm(), valuesSource);
		else if(maskingInfo.getMetadata() != null)
			initialize(maskingInfo.getMetadata().getForm(), valuesSource);
	}

	private void initialize(Form form, FieldValuesSource valuesSource)
	{
		this.valuesSource = valuesSource;
		this.fieldsMap = new HashMap<String, Field>();

		for(Field field : form.getFields())
			fieldsMap.put(field.getName(), field);
	}

	public String getValue(String name)
	{
		String value = valuesSource.getValue(name);
		Field field = fieldsMap.get(name);

		if(MaskPaymentFieldUtils.isMaskValue(field, value, maskingInfo))
			return MaskPaymentFieldUtils.getMaskValue(field, value, maskingInfo);

		return value;
	}

	public Map<String, String> getAllValues()
	{
		Map<String, String> values = new HashMap<String, String>(valuesSource.getAllValues());

		for(String key : fieldsMap.keySet())
		{
			String value = valuesSource.getValue(key);
			Field field = fieldsMap.get(key);

			if(MaskPaymentFieldUtils.isMaskValue(field, value, maskingInfo))
				values.put(key, MaskPaymentFieldUtils.getMaskValue(field, value, maskingInfo));
		}

		return values;
	}

	public boolean isChanged(String name)
	{
		return false;
	}

	public boolean isEmpty()
	{
		return valuesSource.isEmpty();
	}

	public boolean isMasked(String name)
	{
		return MaskPaymentFieldUtils.isMaskValue(fieldsMap.get(name), valuesSource.getValue(name), maskingInfo);
	}
}
