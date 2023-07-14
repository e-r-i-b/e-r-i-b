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

	/* �������� �� �������� ������������ */
	public Long getTbId() throws BusinessException
	{
		Long departmentId = getDepartmentId();
		Department department = departmentService.getTB(departmentId);		
		return (department == null ? null : department.getId());
	}

	/* ��������� ������� �������������� �������� ��� ������� */
	public Boolean checkLoanOfferClient() throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<LoanOffer> loanOffers = personData.getLoanOfferByPersonData(null, null);
		if (loanOffers != null && loanOffers.size() > 0)
			return true;

		return false;

	}

	/**
	 * ��������� ������� ����������� �� ��������
	 * @return - true - ���� �����������, false - ���
	 * @throws BusinessException
	 */
	public Boolean checkAvailableLoanOffer() throws BusinessException
	{
		return !NumericUtil.isEmpty(loanProductService.checkAvailableLoanOffer(getTbId()));
	}

	/* ������, ���� �� � ������� ������� �� */
	public Boolean checkClientPassportType()
	{
		return PersonHelper.hasRegularPassportRF();

	}

	/**
	 * �������� �� ��������� ����������� �� �������
	 * @param conditionId - id �������
	 * @param changeDate - ��������� ���� ��������������
	 * @return - true - ���� ���������, false - �� ����
	 * @throws BusinessException
	 */
	public boolean productIsChange(Long conditionId, Long changeDate) throws BusinessException
	{
		ModifiableLoanProduct product = loanProductService.getOfferProductByConditionId(conditionId);

		return product == null || product.getPublicity() == Publicity.UNPUBLISHED
				|| product.getChangeDate().getTimeInMillis() != changeDate ? true : false;
	}

	/**
	 * �������� �� �������� ������������
	 * @return
	 */
	private Long getDepartmentId()
	{
		// ������ �� �������� ������������ �� ���������
		PersonDataProvider personDataProvider = PersonContext.getPersonDataProvider();
		return personDataProvider.getPersonData().getPerson().getDepartmentId();
	}
}
