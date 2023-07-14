package com.rssl.phizic.operations.templates;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.template.PackageTemplate;
import com.rssl.phizic.business.template.PackageService;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * User: Novikov_A
 * Date: 03.02.2007
 * Time: 14:42:57
 */
public class RemoveTemplatePackageOperation extends OperationBase  implements RemoveEntityOperation
{
	private static final PackageService packageService = new PackageService();
	private PackageTemplate packageTemplate = null;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		packageTemplate = packageService.getTemplateById(id);
		if (packageTemplate == null)
			throw new BusinessException("Пакет шаблонов не найден. id: " + id);
	}

    @Transactional
	public void remove () throws BusinessException
    {
        packageService.remove(packageTemplate);
	}

	public PackageTemplate getEntity()
	{
		return packageTemplate;
	}
}
