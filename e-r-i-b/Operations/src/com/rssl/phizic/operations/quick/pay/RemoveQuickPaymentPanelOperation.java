package com.rssl.phizic.operations.quick.pay;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanel;
import com.rssl.phizic.business.quick.pay.QuickPaymentPanelService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityOperationBase;
import com.rssl.phizic.operations.dictionaries.synchronization.RemoveDictionaryEntityWithImageOperationBase;

import java.util.Iterator;
import java.util.List;

/**
 * Удаление панели быстрой оплаты
 * @author komarov
 * @ created 12.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class RemoveQuickPaymentPanelOperation  extends RemoveDictionaryEntityOperationBase
{
	private static final QuickPaymentPanelService panelService = new QuickPaymentPanelService();
	private static final DepartmentService departmentService = new DepartmentService();
	private QuickPaymentPanel panel;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		panel = panelService.findById(id, getInstanceName());
		if (panel == null)
			throw new BusinessLogicException("ПБО с id = " + id + " не найдена");

		Iterator<String> it = panel.getDepartments().iterator();
		for(;it.hasNext();)
		{
			if(departmentService.getDepartmentTBByTBNumber(it.next()) == null)
				it.remove();
		}

		List<String> allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

	    if(!allowedDepartments.containsAll(panel.getDepartments()))
 	    {
		    throw new AccessException("Вы не можете удалить данную панель, так как не имеете доступа ко всем "+
				    "подразделениям банка, для которых она создана.");
	    }
	}

	public void doRemove() throws BusinessException
	{
		panelService.remove(panel, getInstanceName());
	}

	public Object getEntity()
	{
		return panel;
	}
}
