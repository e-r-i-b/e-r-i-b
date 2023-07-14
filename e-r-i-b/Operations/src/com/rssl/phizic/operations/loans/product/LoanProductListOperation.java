package com.rssl.phizic.operations.loans.product;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.business.loans.products.ModifiableLoanProductService;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.NumericUtil;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 02.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductListOperation  extends OperationBase implements ListEntitiesOperation
{
	private static final DepartmentService departmentService = new DepartmentService();
	private static final ModifiableLoanProductService loanProductService = new ModifiableLoanProductService();

	/* получить ТБ текущего пользователя */
	public Long getTbId() throws BusinessException
	{
		Long departmentId = getDepartmentId();
		Department department = departmentService.getTB(departmentId);		
		return (department == null ? null : department.getId());
	}

	/* проверяет наличие предодобренных кредитов для клиента */
	public Boolean checkLoanOfferClient() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<LoanOffer> loanOffers = personData.getLoanOfferByPersonData(null, null);
		if (loanOffers != null && loanOffers.size() > 0)
			return true;

		return false;

	}

	/**
	 * Проверяет наличие предложений по тербанку
	 * @return - true - есть предложения, false - нет
	 * @throws BusinessException
	 */
	public Boolean checkAvailableLoanOffer() throws BusinessException
	{
		return !NumericUtil.isEmpty(loanProductService.checkAvailableLoanOffer(getTbId()));
	}

	/* узнать, есть ли у клиента паспорт РФ */
	public Boolean checkClientPassportType()
	{
		return PersonHelper.hasRegularPassportRF();

	}

	/**
	 * Проверка на изменение предложения по кредиту
	 * @param conditionId - id условия
	 * @param changeDate - последняя дата редактирования
	 * @return - true - было изменение, false - не было
	 * @throws BusinessException
	 */
	public boolean productIsChange(Long conditionId, Long changeDate) throws BusinessException
	{
		ModifiableLoanProduct product = loanProductService.getOfferProductByConditionId(conditionId);

		return product == null || product.getPublicity() == Publicity.UNPUBLISHED
				|| product.getChangeDate().getTimeInMillis() != changeDate ? true : false;
	}

	/**
	 * получить ТБ текущего пользователя
	 * @return
	 */
	private Long getDepartmentId()
	{
		// узнаем ТБ текущего пользователя из контекста
		PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider();
		return personDataProvider.getPersonData().getPerson().getDepartmentId();
	}
}
