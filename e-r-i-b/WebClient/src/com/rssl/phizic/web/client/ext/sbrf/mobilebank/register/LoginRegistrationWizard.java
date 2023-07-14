package com.rssl.phizic.web.client.ext.sbrf.mobilebank.register;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.register.ConfirmMobileBankRegistrationOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.register.EditMobileBankRegistrationOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.register.StartMobileBankRegistrationOperation;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.register.ViewMobileBankRegistrationOperation;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.actions.ActionMessageHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Erkin
 * @ created 10.09.2012
 * @ $Author$
 * @ $Revision$
 */
class LoginRegistrationWizard extends RegistrationWizard
{
	LoginRegistrationWizard(OperationalActionBase action)
	{
		super(action);
	}

	String getRegistrationLoginStage()
	{
		return "registerMobileBank";
	}

	@Override
	protected StartMobileBankRegistrationOperation createStartOperation() throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		AuthenticationContext authContext = AuthenticationContext.getContext();

		StartMobileBankRegistrationOperation operation = createOperation(StartMobileBankRegistrationOperation.class);
		try
		{
			operation.initializeForLogin(authContext);
		}
		catch (BusinessLogicException e)
		{
			ActionMessageHelper.saveError(request, e);
		}
		return operation;
	}

	@Override
	protected EditMobileBankRegistrationOperation createEditOperation() throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		AuthenticationContext authContext = AuthenticationContext.getContext();

		EditMobileBankRegistrationOperation operation = createOperation(EditMobileBankRegistrationOperation.class);
		try
		{
			operation.initializeNewForLogin(authContext);
		}
		catch (BusinessLogicException e)
		{
			ActionMessageHelper.saveError(request, e);
		}
		return operation;
	}

	@Override
	protected ConfirmMobileBankRegistrationOperation createConfirmOperation(Long id) throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		AuthenticationContext authContext = AuthenticationContext.getContext();

		ConfirmMobileBankRegistrationOperation operation = createOperation(ConfirmMobileBankRegistrationOperation.class);
		try
		{
			operation.initializeForLogin(id, authContext);
		}
		catch (BusinessLogicException e)
		{
			ActionMessageHelper.saveError(request, e);
		}
		return operation;
	}

	@Override
	protected ViewMobileBankRegistrationOperation createViewOperation(Long id) throws BusinessException
	{
		HttpServletRequest request = WebContext.getCurrentRequest();
		AuthenticationContext authContext = AuthenticationContext.getContext();

		ViewMobileBankRegistrationOperation operation = createOperation(ViewMobileBankRegistrationOperation.class);
		try
		{
			operation.initializeForLogin(id, authContext);
		}
		catch (BusinessLogicException e)
		{
			ActionMessageHelper.saveError(request, e);
		}
		return operation;
	}
}
