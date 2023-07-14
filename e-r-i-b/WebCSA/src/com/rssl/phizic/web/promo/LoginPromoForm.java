package com.rssl.phizic.web.promo;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.promoters.PromoChannel;
import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.OneFromRegexpListFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.web.common.FilterActionForm;

import java.util.ArrayList;
import java.util.List;

/**
 * @ author: Gololobov
 * @ created: 10.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoginPromoForm extends FilterActionForm
{
	//Список каналов
	private List<PromoChannel> promoChannels;
	//Список тербанков
	private List<Department> promoTbList;
	//Меняющаяся часть куки. Вынесена на форму из-за того, что DataPower не позволяет прикрепить 2 куки. CHG054739
	private String tempCookie;

	private static final String[] OR_RUCHAR_OR_NUMBER_REGEXPS_LIST = new String[]{"[А-Яа-яЁ-]+", "\\d+"};
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		ArrayList<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("channelId");
		fieldBuilder.setDescription("Канал");
		fieldBuilder.addValidators(new RequiredFieldValidator("Выберите канал"));
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("tb");
		fieldBuilder.setDescription("Тербанк");
		fieldBuilder.addValidators(new RequiredFieldValidator("Выберите тербанк"));
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("osb");
		fieldBuilder.setDescription("ОСБ");
		fieldBuilder.addValidators(new RequiredFieldValidator("Укажите код ОСБ"));
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d*$","Укажите цифровой код ОСБ"));
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("vsp");
		fieldBuilder.setDescription("ВСП");
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d*$","Укажите цифровой код ВСП"));
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("promoId");
		fieldBuilder.setDescription("Идентификатор промоутера");

		//Поле может содержать только цифры или только заглавные буквы русского алфавита
		fieldBuilder.addValidators(new RequiredFieldValidator("Укажите идентификатор промоутера"));
		fieldBuilder.addValidators(new OneFromRegexpListFieldValidator(OR_RUCHAR_OR_NUMBER_REGEXPS_LIST,
				"Введите идентификатор, содержащий цифры или заглавные буквы русского алфавита."));
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}

	public List<PromoChannel> getPromoChannels()
	{
		return promoChannels;
	}

	public void setPromoChannels(List<PromoChannel> promoChannels)
	{
		this.promoChannels = promoChannels;
	}

	public List<Department> getPromoTbList()
	{
		return promoTbList;
	}

	public void setPromoTbList(List<Department> promoTbList)
	{
		this.promoTbList = promoTbList;
	}

	public String getTempCookie()
	{
		return tempCookie;
	}

	public void setTempCookie(String tempCookie)
	{
		this.tempCookie = tempCookie;
	}
}
