package com.rssl.phizic.operations.passwords;

import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.TemporalBusinessException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.password.PasswordValidationException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.utils.StringHelper;

/**
 * Операция изменения пароля ползователя
 * @author Kidyaev
 * @ created 23.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class ChangePersonPasswordOperation extends ChangePasswordOperationBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Инициализировать
	 */
	public void initialize() throws BusinessException, BusinessLogicException
	{
		super.initialize(AuthModule.getAuthModule().getPrincipal().getLogin());
	}

	@Transactional
	public void changePassword() throws PasswordValidationException, BusinessException
	{
		FieldValidator pswValidator = new PasswordStrategyValidator();
		pswValidator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, "client");

		try
		{
			if( !pswValidator.validate(getNewPassword()) )
			{
				String message = pswValidator.getMessage();
				if (StringHelper.isEmpty(message))
					message = "Пароль содержит недопустимые символы";
				log.info("[Ошибка валидации(ChangePersonPasswordOperation)]: " + message);
				throw new BusinessException(message);
			}
		}
		catch (TemporalDocumentException e)
		{
			throw new TemporalBusinessException(e);
		}

		try
		{
			securityService.changePassword(getLogin(), getNewPassword().toCharArray());
		}
		catch (SecurityDbException e)
		{
			throw new BusinessException(e);
		}
	}
}
