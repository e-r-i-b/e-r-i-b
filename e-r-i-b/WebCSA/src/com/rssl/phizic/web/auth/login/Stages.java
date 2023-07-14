package com.rssl.phizic.web.auth.login;

import com.rssl.auth.csa.front.operations.auth.*;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.common.forms.Form;
import com.rssl.phizic.web.auth.Stage;

import java.util.Map;

/**
 * @author niculichev
 * @ created 21.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class Stages
{
	public static final Stage LOGIN = new Stage()
    {
        public String getName()
        {
            return "login";
        }

	    public Form getForm(OperationInfo info)
	    {
		    return LoginForm.LOGIN_FORM;
	    }

        public Stage next(OperationInfo info)
        {
	        if(((LoginOperationInfo) info).getConnectorInfos() != null)
                return CHOICE_LOGINS;

	        return null;
        }

        public Operation getOperation(OperationInfo info, Map<String, Object> formData)
        {
            LoginOperation operation = new LoginOperation();
			operation.initialize(
					info,
					(String) formData.get(LoginForm.LOGIN_FIELD_NAME),
					(String) formData.get(LoginForm.PASSWORD_FIELD_NAME));
			return operation;
        }
    };

	public static final Stage CHOICE_LOGINS = new Stage()
    {
        public String getName()
        {
            return "choice-logins";
        }

	    public Form getForm(OperationInfo info)
	    {
		    return LoginForm.CHOICE_LOGINS_FORM;
	    }

        public Stage next(OperationInfo info)
        {
            return null;
        }

        public Operation getOperation(OperationInfo info, Map<String, Object> formData)
        {
	        ChoiceLoginsOperation operation = new ChoiceLoginsOperation();
	        operation.initialize(info, (String) formData.get(LoginForm.CONNECTOR_GUID));
            return operation;
        }
    };

	public static final Stage LOGIN_PAYORDER = new Stage()
    {
        public String getName()
        {
            return "payOrder";
        }

	    public Form getForm(OperationInfo info)
	    {
		    return LoginForm.LOGIN_FORM;
	    }

        public Stage next(OperationInfo info)
        {
	        if(((LoginOperationInfo) info).getConnectorInfos() != null)
                return CHOICE_LOGINS_PAY_ORDER;

	        return null;
        }

        public Operation getOperation(OperationInfo info, Map<String, Object> formData)
        {
            LoginPayOrderOperation operation = new LoginPayOrderOperation();
			operation.initialize(
					info,
					(String) formData.get(LoginForm.LOGIN_FIELD_NAME),
					(String) formData.get(LoginForm.PASSWORD_FIELD_NAME));
			return operation;
        }
    };

	public static final Stage CHOICE_LOGINS_PAY_ORDER = new Stage()
    {
        public String getName()
        {
            return "choice-logins-payorder";
        }

	    public Form getForm(OperationInfo info)
	    {
		    return LoginForm.CHOICE_LOGINS_FORM;
	    }

        public Stage next(OperationInfo info)
        {
            return null;
        }

        public Operation getOperation(OperationInfo info, Map<String, Object> formData)
        {
	        ChoiseLoginPayOrderOperation operation = new ChoiseLoginPayOrderOperation();
	        operation.initialize(info, (String) formData.get(LoginForm.CONNECTOR_GUID));
            return operation;
        }
    };
}
