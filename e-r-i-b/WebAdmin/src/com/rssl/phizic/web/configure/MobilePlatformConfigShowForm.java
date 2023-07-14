package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;

/**
 * Форма настроек mAPI в разрезе платформ
 * @author Jatsky
 * @ created 29.07.13
 * @ $Author$
 * @ $Revision$
 */

public class MobilePlatformConfigShowForm extends ListFormBase
{
	private String platformId;      //ID платформы
	private String platformName;    //Название платформы
	private Long version;           //Версия приложения
	private boolean scheme;         //режим использования
	public static final Form FILTER_FORM = createForm();

	public String getPlatformId()
	{
		return platformId;
	}

	public void setPlatformId(String platformId)
	{
		this.platformId = platformId;
	}

	public String getPlatformName()
	{
		return platformName;
	}

	public void setPlatformName(String platformName)
	{
		this.platformName = platformName;
	}

	public Long getVersion()
	{
		return version;
	}

	public void setVersion(Long version)
	{
		this.version = version;
	}

	public boolean isScheme()
	{
		return scheme;
	}

	public void setScheme(boolean scheme)
	{
		this.scheme = scheme;
	}

	private static Form createForm()
	{
		ArrayList<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ID");
		fieldBuilder.setName("platformId");
		fieldBuilder.setType("string");
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("platformName");
		fieldBuilder.setDescription("Название");
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("scheme");
		fieldBuilder.setDescription("Режим использования");
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
