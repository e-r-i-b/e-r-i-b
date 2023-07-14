package com.rssl.phizic.operations.templates;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.business.template.DublicatePackageNameException;
import com.rssl.phizic.business.template.PackageService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.context.EmployeeData;
import com.rssl.phizic.context.EmployeeContext;

/**
 * User: Novikov_A
 * Date: 02.02.2007
 * Time: 20:12:53
 */
public class EditTemplatePackageOperation extends OperationBase implements EditEntityOperation
{
	private PackageTemplate packageTemplate = null;

	public void initialize(Long id) throws BusinessException
	{
		PackageTemplate packageTemplate = getTemplateById(id);
		if (packageTemplate != null)
			this.packageTemplate = 	packageTemplate;
		else
			this.packageTemplate = new PackageTemplate();
	}

	public void setPackageTemplate (PackageTemplate packageTemplate)
	{
		this.packageTemplate = packageTemplate;
	}

	public PackageTemplate getTemplateById(Long id) throws BusinessException
	{
		PackageService service = new PackageService();

	     return service.getTemplateById(id);
	}

	public PackageTemplate getEntity ()
	{
		return this.packageTemplate;
	}

    @Transactional
	public void save () throws BusinessException, DublicatePackageNameException
	{
        EmployeeData employeeData = EmployeeContext.getEmployeeDataProvider().getEmployeeData();
		packageTemplate.setDepartmentId(employeeData.getEmployee().getDepartmentId());
		PackageService service = new PackageService();

		   if ((packageTemplate != null)  && (packageTemplate.getId() != null) && (packageTemplate.getId() != 0))
		   	  service.updatePackage(packageTemplate);
		   else
		      service.createPackage(packageTemplate);
	}
}
