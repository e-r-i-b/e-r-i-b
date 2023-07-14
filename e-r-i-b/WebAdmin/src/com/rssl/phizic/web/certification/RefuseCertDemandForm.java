package com.rssl.phizic.web.certification;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 02.04.2007
 * @ $Author$
 * @ $Revision$
 */

public class RefuseCertDemandForm  extends ActionFormBase
{
	private Long id;
	private Map<String, Object> fields = new HashMap<String, Object>();

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Map<String, Object> getFields()
	{
		return fields;
	}

	public Object getField(String key)
	{
		return fields.get(key);
	}

	public void setField(String key, Object obj)
	{
		fields.put(key, obj);
	}

	public static final Form REFUSE_FORM     = createForm();

	@SuppressWarnings({"OverlyLongMethod"})
	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		// Название группы
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Причина отказа");
		fieldBuilder.setName("refuseReason");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators( new RequiredFieldValidator() );

		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
