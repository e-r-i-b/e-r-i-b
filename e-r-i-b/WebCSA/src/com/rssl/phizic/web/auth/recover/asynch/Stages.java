package com.rssl.phizic.web.auth.recover.asynch;

import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.auth.csa.front.operations.auth.*;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.common.forms.Form;
import com.rssl.phizic.web.auth.Stage;
import com.rssl.phizic.web.auth.recover.RecoverPasswordForm;
import com.rssl.phizic.web.auth.registration.RegistrationForm;

import java.util.Map;

/**
 * @author tisov
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */

final public class Stages
{
	public static final Stage PRE_CONFIRM = new Stage()
    {
        public String getName()
        {
            return "pre-confirm";
        }

	    public Form getForm(OperationInfo info)
	    {
		    return RecoverPasswordForm.PRE_CONFIRM_FORM;
	    }

        public Stage next(OperationInfo info)
        {
            return CONFIRM;
        }

        public Operation getOperation(OperationInfo info, Map<String, Object> formData)
        {
            PreRecoverPasswordOperation operation = new PreRecoverPasswordOperation();
            operation.initialize(
		            info,
                    (String) formData.get(RegistrationForm.LOGIN_FIELD_NAME));

            return operation;
        }
    };


    public static final Stage CONFIRM = new Stage()
    {
        public String getName()
        {
            return "confirm";
        }

		public Form getForm(OperationInfo info)
		{
		   return RecoverPasswordForm.CONFIRM_FORM;
		}

        public Stage next(OperationInfo info)
        {
	        RecoverPasswordOperationInfo operationInfo = (RecoverPasswordOperationInfo) info;
	        // ƒл€ Ipas логина пароль генерируетс€ и отправл€ютс€ в смс
	        ConnectorInfo.Type type = operationInfo.getConnectorType();
            return type != null && ConnectorInfo.Type.TERMINAL == type ? IPAS_FINISH_REG : POST_CONFIRM;
        }

        public Operation getOperation(OperationInfo info, Map<String, Object> params)
        {
	        if(!info.isValid())
		        return new StubRecoverPasswordConfirmOperation(info);

			ConfirmOperationBase operation = new RecoverPasswordConfirmOperation();
			operation.initialize(
					info,
					(String)params.get(RecoverPasswordForm.CONFIRM_PASSWORD_FIELD));

			return operation;
        }
    };


    public static final Stage POST_CONFIRM = new Stage()
    {
        public String getName()
        {
            return "post-confirm";
        }

        public Stage next(OperationInfo info)
        {
            return null;
        }

	    public Form getForm(OperationInfo info)
		{
		    return RecoverPasswordForm.POST_CONFIRM_FORM;
		}

        public Operation getOperation(OperationInfo info, Map<String, Object> formData)
        {
            PostRecoverPasswordOperation operation = new PostRecoverPasswordOperation();

            operation.initialize(
                info,
                (String) formData.get(RecoverPasswordForm.PASSWORD_FIELD_NAME));

            return operation;
        };
    };

	public static final Stage IPAS_FINISH_REG = new Stage()
	{
		public String getName()
		{
			return "ipas";
		}

		public Stage next(OperationInfo info)
		{
			return null;
		}

		public Operation getOperation(OperationInfo info, Map<String, Object> params)
		{
			return null;
		}

		public Form getForm(OperationInfo info)
		{
			return null;
		}
	};

}
