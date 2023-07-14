package com.rssl.phizic.web.configure.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.LengthFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.image.ImageEditFormBase;

import java.math.BigInteger;

/**
 * @author koptyaev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditLocaleForm extends ImageEditFormBase
{
	private static final BigInteger LENGTH_ID = BigInteger.valueOf(30);
	private static final BigInteger LENGTH_NAME = BigInteger.valueOf(100);

	public static final Form EDIT_FORM = getEditForm();

	private String localeId;
	private boolean isCSA = false;

	/**
	 * Получить идентификатор локали(в дефолтном механизме id - long)
	 * @return идентификатор локали
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * Установить идентификатор локали(так как EFB id - long)
	 * @param localeId идентификатор локали
	 */
	@SuppressWarnings("UnusedDeclaration")
	public void setLocaleId(String localeId)
	{
		this.localeId = localeId;
	}

	/**
	 * @return признак того, что редактируем CSA сущность
	 */
	public boolean getIsCSA()
	{
		return isCSA;
	}

	/**
	 * @param CSA признак того, что редактируем CSA сущность
	 */
	public void setIsCSA(boolean CSA)
	{
		isCSA = CSA;
	}

	/**
	 * @return форма редактирования
	 */
	@SuppressWarnings({"TooBroadScope", "OverlyLongMethod"})
	private static Form getEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Наименование");
		fieldBuilder.setName("name");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new LengthFieldValidator(LENGTH_NAME),
				new RequiredFieldValidator()
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("id");
		fieldBuilder.setName("id");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new LengthFieldValidator(LENGTH_ID),
				new RequiredFieldValidator()
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setName("state");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator()
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступность на канале ЕРИБ");
		fieldBuilder.setName("eribAvailable");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступность на канале МАПИ");
		fieldBuilder.setName("mapiAvailable");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступность на канале АТМ");
		fieldBuilder.setName("atmApiAvailable");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступность на канале вебАпи");
		fieldBuilder.setName("webApiAvailable");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Доступность на канале ЕРМБ");
		fieldBuilder.setName("ermbAvailable");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(getImageField());
		return formBuilder.build();
	}
}
