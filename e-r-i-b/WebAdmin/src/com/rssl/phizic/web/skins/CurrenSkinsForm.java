package com.rssl.phizic.web.skins;

import com.rssl.phizic.web.common.FilterActionForm;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;

import java.util.List;

/**
 * @author egorova
 * @ created 19.03.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrenSkinsForm extends FilterActionForm
{
	private List<Skin> systemSkins;
	private List<Skin> usersSkins;
	private boolean changeAdminSkinAllowed;
	public static final Form FORM = createForm();

	public List<Skin> getSystemSkins()
	{
		return systemSkins;
	}

	public void setSystemSkins(List<Skin> systemSkins)
	{
		this.systemSkins = systemSkins;
	}

	public List<Skin> getUsersSkins()
	{
		return usersSkins;
	}

	public void setUsersSkins(List<Skin> usersSkins)
	{
		this.usersSkins = usersSkins;
	}

	public boolean isChangeAdminSkinAllowed()
	{
		return changeAdminSkinAllowed;
	}

	public void setChangeAdminSkinAllowed(boolean changeAdminSkinAllowed)
	{
		this.changeAdminSkinAllowed = changeAdminSkinAllowed;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		//clientSkin
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("clientSkin");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setDescription("Текущий стиль Клиентского приложения");
		formBuilder.addField(fieldBuilder.build());

		//adminSkin
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("adminSkin");
		fieldBuilder.setType("integer");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setDescription("Текущий стиль АРМ Сотрудника банка");
		formBuilder.addField(fieldBuilder.build());

		//globalUrl
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("globalUrl");
		fieldBuilder.setType("string");	
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fieldBuilder.setDescription("Каталог общих стилей");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
