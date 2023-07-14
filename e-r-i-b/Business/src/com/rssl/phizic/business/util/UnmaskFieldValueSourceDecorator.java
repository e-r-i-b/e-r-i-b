package com.rssl.phizic.business.util;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.util.MaskPaymentFieldUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Декоратор размаскирующий значения полей(пришедших с view)
 * @author niculichev
 * @ created 21.08.13
 * @ $Author$
 * @ $Revision$
 */
public class UnmaskFieldValueSourceDecorator implements FieldValuesSource
{
	private Map<String, Field> fieldsMap;
	private FieldValuesSource maskedValuesSource;
	private FieldValuesSource unmaskedValuesSource;
	private MaskingInfo maskingInfo;

	UnmaskFieldValueSourceDecorator(MaskingInfo maskingInfo, FieldValuesSource maskedValuesSource, FieldValuesSource unmaskedValuesSource)
	{
		this.maskingInfo = maskingInfo;
		if(maskingInfo.getForm() != null)
			initialize(maskingInfo.getForm(), maskedValuesSource, unmaskedValuesSource);
		else if(maskingInfo.getMetadata() != null)
			initialize(maskingInfo.getMetadata().getForm(), maskedValuesSource, unmaskedValuesSource);
	}

	private void initialize(Form form, FieldValuesSource maskedValuesSource, FieldValuesSource unmaskedValuesSource)
	{
		this.maskedValuesSource = maskedValuesSource;
		this.unmaskedValuesSource = unmaskedValuesSource;

		this.fieldsMap = new HashMap<String, Field>();
		for(Field field : form.getFields())
			fieldsMap.put(field.getName(), field);
	}

	public String getValue(String name)
	{
		Field field = fieldsMap.get(name);
		String originalValue = maskedValuesSource.getValue(name);

		if(field == null)
			return originalValue;

		String unmaskedValue = unmaskedValuesSource.getValue(name);

		// если значение могло быть замаскировано и его замаскированное значение в оригинальном источнике
		// совпадает с замаскированным, которое пришло
		if(MaskPaymentFieldUtils.isMaskValue(field, unmaskedValue, maskingInfo)
				&& MaskPaymentFieldUtils.getMaskValue(field, unmaskedValue, maskingInfo).equals(originalValue))
			return unmaskedValue;

		return originalValue;
	}

	public Map<String, String> getAllValues()
	{
		Map<String, String> result = new HashMap<String, String>();
		for(Map.Entry<String, String> entry : maskedValuesSource.getAllValues().entrySet())
			result.put(entry.getKey(), getValue(entry.getKey()));

		return result;
	}

	public boolean isChanged(String name)
	{
		return maskedValuesSource.isChanged(name);
	}

	public boolean isEmpty()
	{
		return false;
	}

	public boolean isMasked(String name)
	{
		return false;
	}
}
