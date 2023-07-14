package com.rssl.phizic.operations.offerNotification;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.personalOffer.PersonalOfferNotification;
import com.rssl.phizic.business.personalOffer.PersonalOfferNotificationService;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

import java.util.List;

/**
 * @author lukina
 * @ created 20.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class RemoveOfferNotificationOperation extends OperationBase implements RemoveEntityOperation
{
	private static final PersonalOfferNotificationService offerNotificationService = new PersonalOfferNotificationService();
	private static final DepartmentService departmentService = new DepartmentService();
	private PersonalOfferNotification personalOfferNotification;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		personalOfferNotification = offerNotificationService.findById(id);
		if (personalOfferNotification == null)
			throw new BusinessLogicException("Уведомление с id = " + id + " не найдено");

		List<String> allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanksNumbers();

		if(!allowedDepartments.containsAll(personalOfferNotification.getDepartments()))
		{
			throw new AccessException("Вы не можете удалить данное уведомление,"+
					" так как не имеете доступа ко всем подразделениям банка, для которых оно было создано.");
		}
	}

	public void remove() throws BusinessException
	{
		offerNotificationService.remove(personalOfferNotification);
	}

	public Object getEntity()
	{
		return personalOfferNotification;
	}
}
