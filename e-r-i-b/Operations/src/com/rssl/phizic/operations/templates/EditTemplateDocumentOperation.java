package com.rssl.phizic.operations.templates;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.business.template.DocTemplate;
import com.rssl.phizic.business.template.DocTemplateService;
import com.rssl.phizic.business.template.DublicateDocTemplateNameException;
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
public class EditTemplateDocumentOperation extends OperationBase implements EditEntityOperation
{
	private static final DocTemplateService service = new DocTemplateService();

	private DocTemplate docTemplate;

	public void initialize(Long id) throws BusinessException
	{
		DocTemplate temp = service.getById(id);
		if (temp == null)
			throw new BusinessException("шаблон с id " + id + " не найден");

		docTemplate = temp;
	}

	public void initializeNew()
	{
		docTemplate = new DocTemplate();
	}

	public DocTemplate getEntity()
	{
		return docTemplate;
	}

    @Transactional
	public void save () throws BusinessException, DublicateDocTemplateNameException
    {
        EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		docTemplate.setUpdate(DateHelper.getCurrentDate());
		docTemplate.setDepartmentId(employeeData.getEmployee().getDepartmentId());

		DocTemplateService service = new DocTemplateService();
		if ((docTemplate != null) && (docTemplate.getId() != null) && (docTemplate.getId() != 0))
			service.updateDocTemplate(docTemplate);
		else
			service.createDocTemplate(docTemplate);
	}
}

