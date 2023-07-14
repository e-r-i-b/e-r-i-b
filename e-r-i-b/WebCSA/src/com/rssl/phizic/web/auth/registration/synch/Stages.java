package com.rssl.phizic.web.auth.registration.synch;

import com.rssl.auth.csa.front.operations.auth.*;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.auth.Stage;
import com.rssl.phizic.web.auth.registration.RegistrationForm;
import org.apache.commons.collections.CollectionUtils;

import java.util.Map;

/**
 * @author niculichev
 * @ created 28.10.13
 * @ $Author$
 * @ $Revision$
 */
public class Stages
{
	/**
	 * Начало регистрации, форма ввода номера карты
	 */
	public static final Stage START_REG = new Stage()
	{
		public String getName()
		{
			return "start-reg";
		}

		public Form getForm(OperationInfo info)
		{
			return RegistrationForm.PRE_CONFIRM_FORM;
		}

		public Stage next(OperationInfo info)
		{
			return CONFIRM_REG;
		}

		public Operation getOperation(OperationInfo info, Map<String, Object> formData)
		{
			PreRegistrationOperation operation = new PreRegistrationOperation();
			operation.initialize(
					info,
					(String) formData.get(RegistrationForm.CARD_NUMBER_FIELD_NAME));

			return operation;
		}
	};

	/**
	 * Подверждение регистрации, форма ввода смс пароля
	 */
	public static final Stage CONFIRM_REG = new Stage()
	{
		public String getName()
		{
			return "confirm-reg";
		}

		public Form getForm(OperationInfo info)
		{
			return RegistrationForm.CONFIRM_FORM;
		}

		public Stage next(OperationInfo info)
		{
			RegistrationOperationInfo operationInfo = (RegistrationOperationInfo) info;
			if(CollectionUtils.isEmpty(operationInfo.getConnectorInfos()))
				return FINISH_REG;

			return EXIST_REG;
		}

		public Operation getOperation(OperationInfo info, Map<String, Object> params)
		{
			if(!info.isValid())
				return new StubRegistrationConfirmOperation(info);

			// обновляем данными операцию
			ConfirmOperationBase operation = new RegistrationConfirmOperation();
			operation.initialize(
					info,
					(String)params.get(RegistrationForm.CONFIRM_PASSWORD_FIELD));

			return operation;
		}
	};

	/**
	 * Предупреждение о существующей регистрации
	 */
	public static final Stage EXIST_REG = new Stage()
	{
		public String getName()
		{
			return "confirm-reg";
		}

		public Form getForm(OperationInfo info)
		{
			return FormBuilder.EMPTY_FORM;
		}

		public Stage next(OperationInfo info)
		{
			return FINISH_REG;
		}

		public Operation getOperation(OperationInfo info, Map<String, Object> params)
		{
			return new EmptyOperation();
		}
	};

	/**
	 * Заполнение данных аутентификации, форма ввода нового логина и пароля
	 */
	public static final Stage FINISH_REG = new Stage()
	{
		public String getName()
		{
			return "finish-reg";
		}

		public Stage next(OperationInfo info)
		{
			return null;
		}

		public Form getForm(OperationInfo info)
		{
			return RegistrationForm.POST_CONFIRM_FORM;
		}

		public Operation getOperation(OperationInfo info, Map<String, Object> formData)
		{
			PostRegistrationOperation operation = new PostRegistrationOperation();
			operation.initialize(
					info,
					(String) formData.get(RegistrationForm.LOGIN_FIELD_NAME),
					(String) formData.get(RegistrationForm.PASSWORD_FIELD_NAME));

			return operation;
		};
	};
}
