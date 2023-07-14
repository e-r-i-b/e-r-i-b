package com.rssl.phizic.web.auth.guest;

import com.rssl.auth.csa.back.servises.GuestClaimType;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.auth.csa.front.operations.auth.*;
import com.rssl.common.forms.Form;
import com.rssl.phizic.web.auth.AuthenticationFormBase;
import com.rssl.phizic.web.auth.Stage;

import java.util.Map;

/**
 * @author tisov
 * @ created 24.12.14
 * @ $Author$
 * @ $Revision$
 * [Гостевой вход] Состояния операции по аутентификации гостя по номеру телефона
 */
public class GuestEntryStages
{

	public static final Stage INITIAL = new Stage()
	{

		public String getName()
		{
			return "Initial";
		}

		public Stage next(OperationInfo info)
		{
			GuestEntryOperationInfo operationInfo = (GuestEntryOperationInfo) info;
			if (!operationInfo.isValid())
			{
				return FAKE_CONFIRMATION;
			}
			else
			{
				return CONFIRMATION;
			}
		}

		public Operation getOperation(OperationInfo info, Map<String, Object> params)
		{
			String phoneNumber = (String)params.get(AuthenticationFormBase.PHONE_NUMBER);
			GuestEntryOperationInfo operationInfo = (GuestEntryOperationInfo) info;
			operationInfo.setPhoneNumber(phoneNumber);
			return new GuestEntryInitialOperation(operationInfo);
		}

		public Form getForm(OperationInfo info)
		{
			return GuestEntryForm.INITIAL_FORM;
		}
	};

	public static final Stage CONFIRMATION = new Stage()
	{

		public String getName()
		{
			return "Confirm";
		}

		public Stage next(OperationInfo info)
		{
			GuestEntryOperationInfo operationInfo = (GuestEntryOperationInfo) info;
			if (!operationInfo.isValid())
			{
				return FAKE_CONFIRMATION;
			}
			else
			{
				return null;
			}
		}

		public Operation getOperation(OperationInfo info, Map<String, Object> params) throws FrontLogicException
		{
			return new GuestEntryConfirmationOperation(info.getOUID(), (String)params.get(AuthenticationFormBase.CONFIRM_PASSWORD_FIELD), info);
		}

		public Form getForm(OperationInfo info)
		{
			return GuestEntryForm.CONFIRMATION_FORM;
		}
	};

	public static final Stage FAKE_CONFIRMATION = new Stage()
	{

		public String getName()
		{
			return "FakeConfirmation";
		}

		public Stage next(OperationInfo info)
		{
			return null;
		}

		public Operation getOperation(OperationInfo info, Map<String, Object> params)
		{
			return new GuestEntryFakeConfirmationOperation((GuestEntryOperationInfo) info);
		}

		public Form getForm(OperationInfo info)
		{
			return GuestEntryForm.CONFIRMATION_FORM;
		}
	};

}
