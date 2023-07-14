package com.rssl.phizic.web.auth;

import com.rssl.phizic.utils.StringHelper;
import org.apache.struts.action.ActionForm;

import java.util.Map;
import java.util.HashMap;

/**
 * Базовая форма для аутентификации
 * @author niculichev
 * @ created 20.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class AuthenticationFormBase extends ActionForm
{
	public static final String CARD_NUMBER_FIELD_NAME   = "cardNumber";
	public static final String CONFIRM_PASSWORD_FIELD   = "confirmPassword";
	public static final String LOGIN_FIELD_NAME         = "login";
	public static final String PASSWORD_FIELD_NAME      = "password";
	public static final String IS_PAYORDER              = "payOrder";
	public static final String NEED_TURING_TEST         = "needTuringTest";
	public static final String PHONE_NUMBER             = "phoneNumber";
	public static final String CLAIM_TYPE               = "request";

	private Map<String,Object> fields = new HashMap<String, Object>();
	private Boolean isPromoCookie;
	private String redirect;
	private String form;

	public Map<String,Object> getFields()
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
	
	public Boolean getIsPromoCookie()
	{
		return isPromoCookie;
	}

	public void setIsPromoCookie(Boolean isPromoCookie)
	{
		this.isPromoCookie = isPromoCookie;
	}
	
	public String getRedirect()
	{
		return redirect;
	}

	public void setRedirect(String redirect)
	{
		this.redirect = redirect;
	}

	public String getForm()
	{
		return form;
	}

	public void setForm(String form)
	{
		this.form = form;
	}
}
