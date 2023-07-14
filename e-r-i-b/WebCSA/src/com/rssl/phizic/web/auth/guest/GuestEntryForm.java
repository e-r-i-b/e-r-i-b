package com.rssl.phizic.web.auth.guest;

import com.rssl.auth.csa.back.servises.GuestClaimType;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.auth.AuthStageFormBase;


/**
 * @author tisov
 * @ created 17.12.14
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Форма гостевого авторизации по номеру телефона
 */
public class GuestEntryForm extends AuthStageFormBase
{
	public static final Form INITIAL_FORM = createInitialForm();
	public static final Form CONFIRMATION_FORM = createConfirmationForm();

	private GuestClaimType type;              //тип целевой заявки на оформление
	private boolean SMSAttemptsEnded;         //Закончились попытки ввода SMS

	public GuestClaimType getClaimType()
	{
		return type;
	}

	/**
	 * Получение значения флага "кончились ли попытки отправки СМС или нет".
	 * @return Да, если кончились. Нет в противном случае.
	 */
	public boolean getSMSAttemptsEnded()
	{
		return SMSAttemptsEnded;
	}

	/**
	 * Установка флага "кончились ли попытки отправки СМС или нет".
	 * @param SMSAttemptsEnded Да, если кончились. Нет в противном случае.
	 */
	public void setSMSAttemptsEnded(boolean SMSAttemptsEnded)
	{
		this.SMSAttemptsEnded = SMSAttemptsEnded;
	}

	public String getRequest()
	{
		return type == null ? null : type.toString();
	}

	public void setClaimType(GuestClaimType type)
	{
		this.type = type;
	}

	public void setRequest(String type)
	{
		this.type = GuestClaimType.valueOf(type);
	}

	private static Form createInitialForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(PHONE_NUMBER);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Номер телефона");
		fb.addValidators(
				new RequiredFieldValidator("Введите номер вашего мобильного телефона"),
				new RegexpFieldValidator("7\\d{10}"," Неверный формат номера телефона")
		);
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName(CLAIM_TYPE);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Тип оформляемой заявки");
		formBuilder.addField(fb.build());
		return formBuilder.build();
	}

	private static Form createConfirmationForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(CONFIRM_PASSWORD_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("sms-пароль");
		fb.addValidators(
				new RequiredFieldValidator("Пожалуйста, введите пароль.")
		);
		formBuilder.addField(fb.build());
		return formBuilder.build();
	}
}
