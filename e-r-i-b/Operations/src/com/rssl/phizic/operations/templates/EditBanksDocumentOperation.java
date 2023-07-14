package com.rssl.phizic.operations.templates;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.business.template.BanksDocumentService;
import com.rssl.phizic.business.template.BanksDocument;
import com.rssl.phizic.business.template.DublicateBanksDocumentNameException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.utils.DateHelper;

/**
 * User: Novikov_A
 * Date: 02.02.2007
 * Time: 20:12:53
 */
public class EditBanksDocumentOperation extends OperationBase implements EditEntityOperation
{
	private static final BanksDocumentService service = new BanksDocumentService();
	private BanksDocument banksDocument;

	public void initialize(Long id) throws BusinessException
	{
		BanksDocument temp = service.getById(id);
		if (temp == null)
			throw new BusinessException("Документ банка с id " + id + " не найден");

		banksDocument = temp;
	}

	public void initializeNew()
	{
		banksDocument = new BanksDocument();
	}

	public BanksDocument getEntity ()
	{
		return banksDocument;
	}

    @Transactional
	public void save () throws BusinessException, DublicateBanksDocumentNameException
    {
        EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		banksDocument.setUpdate(DateHelper.getCurrentDate());
		banksDocument.setDepartmentId(employeeData.getEmployee().getDepartmentId());

		if ((banksDocument != null)  && (banksDocument.getId() != null) && (banksDocument.getId() != 0))
			service.updateBanksDocument(banksDocument);
		else
			service.createBanksDocument(banksDocument);
	}
}
