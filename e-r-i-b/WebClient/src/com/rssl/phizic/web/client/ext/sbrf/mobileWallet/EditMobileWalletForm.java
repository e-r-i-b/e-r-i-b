package com.rssl.phizic.web.client.ext.sbrf.mobileWallet;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.EditFormBase;

import java.math.BigDecimal;

/**
 * @author osminin
 * @ created 10.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditMobileWalletForm extends EditFormBase
{
	private BigDecimal totalAmountValue;
	private BigDecimal limitAmountValue;
	private ConfirmableObject confirmableObject;
	private ConfirmStrategy confirmStrategy;

	public static final Form EDIT_MOBILE_WALLET_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder.setName("totalAmount");
		fieldBuilder.setDescription("—ÛÏÏ‡");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public BigDecimal getLimitAmountValue()
	{
		return limitAmountValue;
	}

	public void setLimitAmountValue(BigDecimal limitAmountValue)
	{
		this.limitAmountValue = limitAmountValue;
	}

	public BigDecimal getTotalAmountValue()
	{
		return totalAmountValue;
	}

	public void setTotalAmountValue(BigDecimal totalAmountValue)
	{
		this.totalAmountValue = totalAmountValue;
	}
}
