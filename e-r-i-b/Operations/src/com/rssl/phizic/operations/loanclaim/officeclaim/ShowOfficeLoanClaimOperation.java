package com.rssl.phizic.operations.loanclaim.officeclaim;

import com.rssl.phizgate.ext.sbrf.etsm.OfficeLoanClaim;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.common.CodeImpl;
import com.rssl.phizic.business.loanclaim.officeClaim.OfficeLoanClaimCaheService;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author Nady
 * @ created 17.07.15
 * @ $Author$
 * @ $Revision$
 */
public class ShowOfficeLoanClaimOperation extends OperationBase implements ViewEntityOperation
{
	private OfficeLoanClaim officeLoanClaim;
	private static final OfficeLoanClaimCaheService officeLoanClaimCaheService = new OfficeLoanClaimCaheService();
	private static final DepartmentService departmentService = new DepartmentService();

	public void initialize(String applicationNumber)
	{
		officeLoanClaim = officeLoanClaimCaheService.getOfficeLoanClaimByAppNum(applicationNumber);
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return officeLoanClaim;
	}


	/**
	 * Получение офиса из строки (для заявок, созданных в каналах, отличных от УКО)
	 * @return офис
	 */
	public Department getDepartment() throws BusinessException
	{
		String departmentCode = officeLoanClaim.getDepartment();
		if (StringHelper.isEmpty(departmentCode))
			return null;

		Code code  = new CodeImpl();
		Map<String, String> fields = code.getFields();
		fields.put("region",StringHelper.removeLeadingZeros(departmentCode.substring(0, 2)));
		fields.put("branch", StringHelper.removeLeadingZeros(departmentCode.substring(2, 6)));
		fields.put("office", StringHelper.removeLeadingZeros(departmentCode.substring(6)));
		return departmentService.findByCode(code);
	}
}
