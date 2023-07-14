package com.rssl.phizic.web.auth.verification.satges;

import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.auth.verification.BusinessEnvironmentOperationInfo;
import com.rssl.auth.csa.front.operations.auth.verification.ConfirmVerifyBusinessEnvironmentOperation;
import com.rssl.auth.csa.front.operations.auth.verification.LoginBusinessEnvironmentOperation;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.phizic.web.auth.Stage;
import com.rssl.phizic.web.auth.verification.BusinessEnvironmentForm;

import java.util.Map;

/**
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 *
 *  1. Логинимся LOGIN
 *  2. соглашение + подтверждаем данные (чеком или смс) VERIFY
 */

public final class Stages
{
	public static final Stage VERIFY = new StageBase("verify", BusinessEnvironmentForm.VERIFY_FORM)
    {
		public Operation getOperation(OperationInfo info, Map<String, Object> params)
        {
	        ConfirmVerifyBusinessEnvironmentOperation operation = new ConfirmVerifyBusinessEnvironmentOperation();
	        operation.initialize(info, (String) params.get(BusinessEnvironmentForm.CONFIRM_CODE_FIELD_NAME));
            return operation;
        }
    };

	public static final Stage LOGIN = new StageBase("login", BusinessEnvironmentForm.LOGIN_FORM, VERIFY)
    {
        public Operation getOperation(OperationInfo info, Map<String, Object> params)
        {
            LoginBusinessEnvironmentOperation operation = new LoginBusinessEnvironmentOperation();
			operation.initialize(
					(BusinessEnvironmentOperationInfo) info,
					(String) params.get(BusinessEnvironmentForm.LOGIN_FIELD_NAME),
					(String) params.get(BusinessEnvironmentForm.PASSWORD_FIELD_NAME));
			return operation;
        }
    };
}
