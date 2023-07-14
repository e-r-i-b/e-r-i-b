package com.rssl.phizic.web.auth;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.common.forms.Form;

import java.util.Map;

/**
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public interface Stage
{
	/**
	 * @return имя стадии
	 */
	String getName();

	/**
	 * @param info контект операции
	 * @return Следующая стадия
	 */
    Stage next(OperationInfo info);

	/**
	 * @param info контектс операции
	 * @param params параметры для инициализации операции
	 * @return проинициализированная операция
	 */
    Operation getOperation(OperationInfo info, Map<String, Object> params) throws FrontLogicException;

	/**
	 * @param info контекст операции
	 * @return логичесткая форма текущей стадии
	 */
	Form getForm(OperationInfo info);
}
