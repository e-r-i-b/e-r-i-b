package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.deposits.EditProlongationDateAlgorithmOperation;
import com.rssl.phizic.web.settings.EditPropertiesActionBase;

/**
 * Редактирование алгоритма определения даты пролонгации вкладов
 * @author Jatsky
 * @ created 09.12.14
 * @ $Author$
 * @ $Revision$
 */

public class EditProlongationAlgorithmAction extends EditPropertiesActionBase<EditProlongationDateAlgorithmOperation>
{
	@Override protected EditProlongationDateAlgorithmOperation getEditOperation() throws BusinessException
	{
		return createOperation(EditProlongationDateAlgorithmOperation.class);
	}
}
