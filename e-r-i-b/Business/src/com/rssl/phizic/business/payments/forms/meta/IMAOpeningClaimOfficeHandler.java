package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.documents.IMAOpeningClaim;

/**
 * @author Mescheryakova
 * @ created 04.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAOpeningClaimOfficeHandler extends BusinessDocumentHandlerBase
{
	private static final String IS_NOT_ACTUALITY = "Условия размещения средств по выбранному ОМС изменились. Пожалуйста, отредактируйте или создайте новую заявку";
	private static final DepartmentService departmentsService = new DepartmentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof IMAOpeningClaim))
			throw new DocumentException("Неверный тип платежа. Ожидается IMAOpeningClaimHandler");

		IMAOpeningClaim imaOpeningClaim  = (IMAOpeningClaim) document;

		try
		{
			ExtendedCodeImpl officeCode = new ExtendedCodeImpl(imaOpeningClaim.getOfficeRegion(),
					imaOpeningClaim.getOfficeBranch(), imaOpeningClaim.getOfficeVSP());
			Department department = departmentsService.findByCode(officeCode);

			if (department == null || !department.isOpenIMAOffice())
				throw new DocumentLogicException(IS_NOT_ACTUALITY);
		}
		catch(BusinessException e)
		{
			throw new DocumentException("Ошибка корректности выбранного ВСП при открытии ОМС", e);
		}
	}
}
