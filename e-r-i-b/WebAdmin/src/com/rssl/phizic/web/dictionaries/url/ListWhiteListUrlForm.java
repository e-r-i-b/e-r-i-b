package com.rssl.phizic.web.dictionaries.url;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.dictionaries.url.WhiteListUrl;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lukina
 * @ created 13.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListWhiteListUrlForm extends EditFormBase
{
	private List<WhiteListUrl> data = new ArrayList<WhiteListUrl>();// список URL-адресов
	private Map<String,Object> fields = new HashMap<String, Object>();

	public List<WhiteListUrl> getData()
	{
		return data;
	}

	public void setData(List<WhiteListUrl> data)
	{
		this.data = data;
	}

	public Map<String, Object> getFields()
	{
		return fields;
	}

	public void setFields(Map<String, Object> fields)
	{
		this.fields = fields;
	}

	public Object getField(String key)
	{
	    return fields.get(key);
	}

	public void setField(String key, Object obj)
	{
	    fields.put(key, obj);
	}


	public Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("urlMaskCount");
		fieldBuilder.setDescription("Количество URL-адресов");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		long urlMaskCount = Long.parseLong((String) getField("urlMaskCount"));

		for (int i = 0; i <= urlMaskCount; i++)
		{
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName("urlMaskList" + i);
			fieldBuilder.setDescription("Маска URL-адреса");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RegexpFieldValidator("^.{0,250}", "Поле URL-адрес не должно превышать 250 символов."),
								new RegexpFieldValidator("^[-\\dA-Za-zА-Яа-я./:_]{0,250}$", "В строке URL-адреса введен неразрешенный символ."));
			formBuilder.addField(fieldBuilder.build());
		}

		return formBuilder.build();
	}
}
