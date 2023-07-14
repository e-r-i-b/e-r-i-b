package com.rssl.phizic.web.common.client.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.deposits.DepositDetailsOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;

/**
 * Ёкшн дл€ отображени€ детальной информации о вкладном продукте
 * @author EgorovaA
 * @ created 10.04.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositEntityDetailsAction extends ViewActionBase
{
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(DepositDetailsOperation.class, "AccountOpeningClaim");
	}

	protected void updateFormData(ViewEntityOperation operation, EditFormBase form) throws BusinessException, BusinessLogicException
	{
		DepositEntityDetailsForm frm = (DepositEntityDetailsForm)form;
		DepositDetailsOperation op = (DepositDetailsOperation)operation;

		Long depositType = frm.getId();
		Long group = frm.getGroup();
		String segment =  StringHelper.getEmptyIfNull(frm.getSegment());

		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		String tariff = StringHelper.getZeroIfEmptyOrNull(personData.getPerson().getTarifPlanCodeType());
		Department personTB = personData.getTb();

		DepositProductEntity depositProductEntity = op.getDepositProductEntity(depositType, group, segment, PersonHelper.isPensioner(), Long.valueOf(tariff), personTB.getRegion());
		frm.setEntity(depositProductEntity);
		frm.setEntityTDog(op.getDepositAdditionalInfo(depositType, group));
		frm.setMinAdditionalFee(op.getMinAdditionalFeeValues(depositType, group));
	}

	protected boolean getEmptyErrorPage()
	{
		return true;
	}
}
