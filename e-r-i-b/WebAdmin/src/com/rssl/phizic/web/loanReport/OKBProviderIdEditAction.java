package com.rssl.phizic.web.loanReport;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.loanreport.EditBKIProviderIdOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Экшн настройки идентификатора поставщика услуги для ОКБ
 * @author Rtischeva
 * @ created 08.10.14
 * @ $Author$
 * @ $Revision$
 */
public class OKBProviderIdEditAction extends EditPropertiesActionBase<EditBKIProviderIdOperation>
{
	@Override
	protected EditBKIProviderIdOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditBKIProviderIdOperation.class);
	}
}
