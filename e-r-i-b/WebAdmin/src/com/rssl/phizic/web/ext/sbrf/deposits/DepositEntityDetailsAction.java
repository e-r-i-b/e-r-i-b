package com.rssl.phizic.web.ext.sbrf.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.deposits.DepositDetailsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * @author EgorovaA
 * @ created 16.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityDetailsAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(DepositDetailsOperation.class, "DepositsManagement");
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		DepositEntityDetailsForm frm = (DepositEntityDetailsForm)form;
		DepositDetailsOperation op = (DepositDetailsOperation)operation;

		Long depositType = frm.getId();
		String group = StringHelper.getEmptyIfNull(frm.getGroup());
		Long groupCode = Long.valueOf(group);
		String tariff = StringHelper.getEmptyIfNull(frm.getTariff());

		DepositProductEntity depositProductEntity = op.getDepositProductEntity(depositType, groupCode, null, false, Long.valueOf(tariff), null);

		frm.setEntity(depositProductEntity);
		frm.setEntityTDog(op.getDepositAdditionalInfo(depositType, groupCode));
		frm.setMinAdditionalFee(op.getMinAdditionalFeeValues(depositType, groupCode));
	}
}
