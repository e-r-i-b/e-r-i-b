package com.rssl.phizic.auth.modes;

import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;

import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 12.12.2006
 * @ $Author: omeliyanchuk $
 * @ $Revision: 8023 $
 */
@PublicDefaultCreatable
public interface StageVerifier extends Serializable
{
	/**
	 * @param context контекст аутентификации
	 * @param stage стадия аутентификации
	 * @return true == стадия необходима в этом контексте
	 */
	boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException;
}
