package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.phizic.web.security.LoginPasswordStageAction;
import com.rssl.phizic.web.security.LoginForm;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.security.password.NamePasswordValidator;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.auth.LoginExistValidator;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import org.apache.struts.action.*;

import java.util.Map;
import java.security.SecureRandom;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Omeliyanchuk
 * @ created 22.04.2008
 * @ $Author$
 * @ $Revision$
 */

public class LoginClosePasswordStageAction extends LoginPasswordStageAction
{
	public static final String LOGIN_SERVER_RND_KEY  = "com.rssl.phizic.LoginServerRnd";

	private static final String FORWARD_SHOW = "Show";

	protected void fillParams(ActionForm form, HttpServletRequest request)
	{
		fillServerRandom(form,request);
	}

	/**
	 * генерирует случайную строку и добавляет ее в сессию и форму
	 * @param form
	 * @param request
	 */
	private void fillServerRandom(ActionForm form, HttpServletRequest request)
	{
		//создать случайное число
		byte[] serverRandom = new byte[8];
		SecureRandom random = new SecureRandom();
		random.nextBytes(serverRandom);

		String randomString = StringUtils.toHexString(serverRandom);
		setLoginServerRnd(randomString);

		LoginClosePasswordStageForm frm = (LoginClosePasswordStageForm) form;
		frm.setServerRandom(randomString);
	}

	/**
	 * Создает валидатор, для проверки пароля по алгоритмы СБРФ
	 * @param request запрос
	 * @param params Мап со значениями от пользователя
	 * @return валидатор
	 */
	protected NamePasswordValidator createPasswordValidator(HttpServletRequest request, Map<String, Object> params)
	{
		return new LoginExistValidator();
	}

	public Form buildForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		//noinspection TooBroadScope
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("login");
		fieldBuilder.setDescription("Логин");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accessType");
		fieldBuilder.setDescription("Тип доступа");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("clientRandom");
		fieldBuilder.setDescription("Пароль");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	/**
	 * @return Случайная последовательность выданная пользователю для аутентификации
	 */
	public String getLoginServerRnd()
	{
		return (String) StoreManager.getCurrentStore().restore(LOGIN_SERVER_RND_KEY);
	}

	/**
	 * @param randomString Случайная последовательность выданная пользователю для аутентификации
	 */
	public void setLoginServerRnd(String randomString)
	{
		StoreManager.getCurrentStore().save(LOGIN_SERVER_RND_KEY, randomString);
	}
}
