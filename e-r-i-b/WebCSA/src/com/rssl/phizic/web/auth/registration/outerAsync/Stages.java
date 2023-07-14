package com.rssl.phizic.web.auth.registration.outerAsync;

import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.auth.csa.front.operations.auth.*;
import com.rssl.common.forms.Form;
import com.rssl.phizic.web.auth.Stage;
import com.rssl.phizic.web.auth.registration.RegistrationForm;
import org.apache.commons.collections.CollectionUtils;

import java.util.Map;

/**
 * @author tisov
 * @ created 28.01.14
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
            return RegistrationForm.PRE_CONFIRM_FORM;
        }

        public Stage next(OperationInfo info)
        {
            return CONFIRM;
        }

        public Operation getOperation(OperationInfo info, Map<String, Object> formData)
        {
            PreRegistrationOperation operation = new PreRegistrationOperation();
            operation.initialize(info,(String) formData.get(RegistrationForm.CARD_NUMBER_FIELD_NAME));
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
            return RegistrationForm.CONFIRM_FORM;
		}


        public Stage next(OperationInfo info)
        {
	        RegistrationOperationInfo regInfo = (RegistrationOperationInfo) info;
	        if (CollectionUtils.isNotEmpty(regInfo.getConnectorInfos()))
		        return EXIST_REG;
            return POST_CONFIRM;
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
        }
    };

	public static final Stage EXIST_REG = new Stage()
	{
		public String getName()
		{
			return "exist-reg";
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
		}
	};

}
