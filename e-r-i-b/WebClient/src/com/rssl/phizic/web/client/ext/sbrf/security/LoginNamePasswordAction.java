package com.rssl.phizic.web.client.ext.sbrf.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.security.password.UserPasswordValidator;
import com.rssl.phizic.security.password.WierdUserPasswordValidator;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.security.LoginPasswordStageAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.security.SecureRandom;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Moshenko
 * Date: 23.07.2010
 * Time: 17:57:29
 * @deprecated ��������� com.rssl.phizic.web.common.client.ext.sbrf.LoginNamePasswordAction
 */
@Deprecated
public class LoginNamePasswordAction extends LoginPasswordStageAction
{
	public static final String FORGOT_PSW = "Forgot";

	public static final String LOGIN_SERVER_RND_KEY  = "com.rssl.phizic.LoginServerRnd";

	protected Map<String, String> getKeyMethodMap()
	{
	    Map<String,String> map = super.getKeyMethodMap();
		map.put("button.getPassword","getNewPassword");
		return map;
	}

    //���������� �� �������� ����.
	public ActionForward getNewPassword (ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return mapping.findForward(FORGOT_PSW);
	}

	protected UserPasswordValidator createPasswordValidator(HttpServletRequest request, Map<String, Object> params)
	{
		WierdUserPasswordValidator validator = new WierdUserPasswordValidator();
		String clientRnd = (String)params.get("clientRandom");
		String serverRnd = getLoginServerRnd();
   		validator.initialize(clientRnd, serverRnd);
		                             	
		return validator;
	}

	public Form buildForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		//noinspection TooBroadScope
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("login");
		fieldBuilder.setDescription("�����");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("password");
		fieldBuilder.setDescription("������");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accessType");
		fieldBuilder.setDescription("��� �������");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("clientRandom");
		fieldBuilder.setDescription("������");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
	protected void fillParams(ActionForm form, HttpServletRequest request)
	{
		fillServerRandom(form,request);
	}
	/**
	 * ���������� ��������� ������ � ��������� �� � ������ � �����
	 * @param form
	 * @param request
	 */
	private void fillServerRandom(ActionForm form, HttpServletRequest request)
	{
		//������� ��������� �����
		byte[] serverRandom = new byte[8];
		SecureRandom random = new SecureRandom();
		random.nextBytes(serverRandom);

		String randomString = StringUtils.toHexString(serverRandom);
		setLoginServerRnd(randomString);

		LoginClosePasswordStageForm frm = (LoginClosePasswordStageForm) form;
		frm.setServerRandom(randomString);
	}
	/**
	 * @return ��������� ������������������ �������� ������������ ��� ��������������
	 */
	public String getLoginServerRnd()
	{
		return (String) StoreManager.getCurrentStore().restore(LOGIN_SERVER_RND_KEY);
	}
	/**
	 * @param randomString ��������� ������������������ �������� ������������ ��� ��������������
	 */
	public void setLoginServerRnd(String randomString)
	{
		StoreManager.getCurrentStore().save(LOGIN_SERVER_RND_KEY, randomString);
	}
}
