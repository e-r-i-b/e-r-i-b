package com.rssl.phizic.operations.promocodes;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.business.promocodes.PromoCodeSettings;
import com.rssl.phizic.business.promocodes.PromoCodeService;
import com.rssl.phizic.context.EmployeeContext;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditPromoCodeSettingsOperation extends OperationBase<DepartmentRestriction> implements EditEntityOperation<PromoCodeSettings, DepartmentRestriction>
{
	private static final PromoCodeService promoSerice = new PromoCodeService();

	private PromoCodeSettings settings;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		settings = promoSerice.findById(id);
		if(settings == null)
			throw new BusinessLogicException("Настройка с id " + id + " не найдена");

		if (!getRestriction().accept(settings.getTerbank().getId()))
		{
			throw new RestrictionViolationException(" Нет прав на редактирование настройки промо-акций id=" + settings.getId() +
					". loginId сотрудника " + EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin().getId());
		}
	}

	public void initializeNew()
	{
		settings = new PromoCodeSettings();
		settings.setStartDate(Calendar.getInstance());
	}

	public PromoCodeSettings getEntity() throws BusinessException, BusinessLogicException
	{
		return settings;
	}
	
	public void save() throws BusinessException
	{
		promoSerice.addOrUpdatePromoCodeSettings(settings);
	}
}