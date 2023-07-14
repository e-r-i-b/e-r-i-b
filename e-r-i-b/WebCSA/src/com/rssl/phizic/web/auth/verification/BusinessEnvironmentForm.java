package com.rssl.phizic.web.auth.verification;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.auth.login.LoginForm;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class BusinessEnvironmentForm extends LoginForm
{
	private static final Pattern CUST_ID_PATTEN = Pattern.compile("[0-9a-zA-Z]{5,15}");

	public static final String CUST_ID_PARAMETER_NAME = "CustId";
	public static final String CONFIRM_CODE_FIELD_NAME = "confirmCode";

	public static final Form VERIFY_FORM = createVerifyForm();

	private String cardId;
	private String confirmationError;

	/**
	 * @return ������������� �����
	 */
	public String getCardId()
	{
		return cardId;
	}

	/**
	 * ������ ������������� �����
	 * @param cardId ������������� �����
	 */
	public void setCardId(String cardId)
	{
		this.cardId = cardId;
	}

	/**
	 * @return ������ �������������
	 */
	public String getConfirmationError()
	{
		return confirmationError;
	}

	/**
	 * ������ ������ �������������
	 * @param confirmationError ������ �������������
	 */
	public void setConfirmationError(String confirmationError)
	{
		this.confirmationError = confirmationError;
	}

	/**
	 * �������� ������� ������������� �������
	 * @param request ������� �������
	 * @return ������� ������������� �������
	 */
	public String getCustId(HttpServletRequest request)
	{
		String custId = StringHelper.getEmptyIfNull(request.getParameter(CUST_ID_PARAMETER_NAME));
		Matcher matcher = CUST_ID_PATTEN.matcher(custId);
		if (matcher.matches())
			return custId;
		
		return StringUtils.EMPTY;
	}

	private static Form createVerifyForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(CONFIRM_CODE_FIELD_NAME);
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("������");
		fb.addValidators(new RequiredFieldValidator("������� �������� � ���� ������."));
		formBuilder.addField(fb.build());

		return formBuilder.build();
	}
}
