package com.rssl.phizic.web.dictionaries.pfp.configure;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.phizic.business.dictionaries.pfp.configure.PFPConfigHelper;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentAvailableService;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 */

public class EditSegmentAvailableForm extends EditPropertiesFormBase
{
	public static final Form EDIT_FORM = createEditForm();
	private static final List<String> SEGMENTS = new ArrayList<String>(SegmentCodeType.values().length);

	static
	{
		for(SegmentCodeType segment : SegmentCodeType.values())
			SEGMENTS.add(PFPConfigHelper.PROPERTY_PREFIX.concat(SegmentAvailableService.getPropertyKey(segment)));
	}
	/**
	 * Возвращает список всех сегметнов
	 * @return список сегметнов
	 */
	public List<String> getSegments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return SEGMENTS;
	}

	private static Field createField(SegmentCodeType segment)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(PFPConfigHelper.PROPERTY_PREFIX.concat(SegmentAvailableService.getPropertyKey(segment)));
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setDescription(segment.getDescription());
		return fieldBuilder.build();
	}

	private static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		for(SegmentCodeType segment : SegmentCodeType.values())
			formBuilder.addField(createField(segment));

		return formBuilder.build();
	}

	@Override
	public Form getForm()
	{
		return EDIT_FORM;
	}
}
