package com.rssl.phizic.operations.promocodes;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.RestrictionViolationException;
import com.rssl.phizic.business.operations.restrictions.DepartmentRestriction;
import com.rssl.phizic.business.promocodes.PromoCodeService;
import com.rssl.phizic.business.promocodes.PromoCodeSettings;
import com.rssl.phizic.context.EmployeeContext;

/**
 * @author gladishev
 * @ created 08.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class RemovePromoCodeSettingsOperation extends OperationBase<DepartmentRestriction> implements RemoveEntityOperation<PromoCodeSettings, DepartmentRestriction>
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

	public PromoCodeSettings getEntity()
	{
		return settings;
	}
	
	@Transactional
	public void remove() throws BusinessException
	{
		promoSerice.removePromoCodeSettings(settings);
	}
}
