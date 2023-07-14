package com.rssl.phizic.web.auth.registration;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CompareStringValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.phizic.business.payments.forms.validators.CardNumberChecksumFieldValidator;
import com.rssl.phizic.web.auth.AuthStageFormBase;

/**
 * @author niculichev
 * @ created 17.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationForm extends AuthStageFormBase
{
	private static final String INVALID_CARD_NUMBER_MESSAGE = "Вы неправильно указали номер карты. Пожалуйста, проверьте количество цифр введенного номера карты и их последовательность.";
	public static final Form PRE_CONFIRM_FORM = getPreForm();
	public static final Form CONFIRM_FORM = getConfirmForm();
	public static final Form POST_CONFIRM_FORM = createPostForm();
	public static final Form CHECK_LOGIN_FORM = createCheckLoginForm();
	public static final Form CHECK_PASSWORD_FORM = createCheckPasswordForm();
	public static final String REGISTRATION_CAPTCHA_SERVLET = "registrationCaptchaServlet";

	private boolean showPromoBlock;
	private String actionPath;
	private int hintDelay;
	private int minLoginLength;

	private static Form getPreForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(CARD_NUMBER_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Номер карты");
		fb.addValidators(
				new RequiredFieldValidator(INVALID_CARD_NUMBER_MESSAGE),
				new RegexpFieldValidator("(\\d{15})|(\\d{16})|(\\d{18})|(\\d{19})", INVALID_CARD_NUMBER_MESSAGE),
				new CardNumberChecksumFieldValidator(INVALID_CARD_NUMBER_MESSAGE)
		);
		formBuilder.addField(fb.build());
		return formBuilder.build();
	}

	private static Form getConfirmForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(CONFIRM_PASSWORD_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("SMS-пароль");
		fb.addValidators(new RequiredFieldValidator("Введите пароль, полученный через sms"));
		formBuilder.addField(fb.build());
		return formBuilder.build();
	}

	private static Form createPostForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(createLoginField());
		formBuilder.addField(createPasswordField());

		FieldBuilder fb = new FieldBuilder();
		fb = new FieldBuilder();
		fb.setName(CONFIRM_PASSWORD_FIELD);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Повторите пароль");
		fb.addValidators(new RequiredFieldValidator("Введите значение в поле Повторите пароль"));
		formBuilder.addField(fb.build());

		CompareStringValidator compareValidator = new CompareStringValidator(true, false);
		compareValidator.setMessage("Логин не должен совпадать с паролем");
		compareValidator.setBinding(CompareStringValidator.FIELD_S1, LOGIN_FIELD_NAME);
		compareValidator.setBinding(CompareStringValidator.FIELD_S2, PASSWORD_FIELD_NAME);
		formBuilder.addFormValidators(compareValidator);

		compareValidator = new CompareStringValidator(false, false);
		compareValidator.setMessage("Пароли не совпадают");
		compareValidator.setBinding(CompareStringValidator.FIELD_S1, PASSWORD_FIELD_NAME);
		compareValidator.setBinding(CompareStringValidator.FIELD_S2, CONFIRM_PASSWORD_FIELD);
		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}

	private static Form createCheckLoginForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addField(createLoginField());
		return formBuilder.build();
	}

	private static Form createCheckPasswordForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addField(createPasswordField());
		return formBuilder.build();
	}

	private static Field createLoginField()
	{
		FieldBuilder fb = new FieldBuilder();
		fb.setName(LOGIN_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Идентификатор");
		fb.addValidators(
				new RequiredFieldValidator("Пожалуйста, введите идентификатор или логин пользователя."),
				new PasswordStrategyValidator("csa_client_login"));
		return fb.build();
	}

	private static Field createPasswordField()
	{
		FieldBuilder fb = new FieldBuilder();
		fb = new FieldBuilder();
		fb.setName(PASSWORD_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Пароль");
		fb.addValidators(
				new RequiredFieldValidator("Пожалуйста, введите пароль."),
				new PasswordStrategyValidator("csa_client_password"));
		return fb.build();
	}

	public boolean getShowPromoBlock()
	{
		return showPromoBlock;
	}

	public void setShowPromoBlock(boolean showPromoBlock)
	{
		this.showPromoBlock = showPromoBlock;
	}

	public String getActionPath()
	{
		return actionPath;
	}

	public void setActionPath(String actionPath)
	{
		this.actionPath = actionPath;
	}

	public int getHintDelay()
	{
		return hintDelay;
	}

	public void setHintDelay(int hintDelay)
	{
		this.hintDelay = hintDelay;
	}

	public void setMinLoginLength(int minLoginLength)
	{
		this.minLoginLength = minLoginLength;
	}

	public int getMinLoginLength()
	{
		return minLoginLength;
	}
}
