package com.rssl.phizic.web.auth.payOrder;

import com.rssl.auth.csa.front.operations.auth.LoginOperationInfo;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.phizic.web.auth.AuthStageFormBase;
import com.rssl.phizic.web.auth.AuthenticationFormBase;
import com.rssl.phizic.web.auth.login.LoginAction;
import com.rssl.phizic.web.auth.login.Stages;

/**
 * @ author: Vagin
 * @ created: 06.02.2013
 * @ $Author
 * @ $Revision
 * ¬ход клиента при оплате с внешней ссылки
 */
public class LoginPayOrderAction extends LoginAction
{
	protected OperationInfo getOperationInfo()
	{
		return new LoginOperationInfo(Stages.LOGIN_PAYORDER);
	}

	protected void updateForm(OperationInfo operationInfo, AuthStageFormBase frm, boolean isFirstShowForm)
	{
		frm.setField(AuthenticationFormBase.IS_PAYORDER, true);
		frm.setOperationInfo(operationInfo);
	}

}
