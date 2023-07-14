package com.rssl.phizic.operations.loans.offer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.creditcards.incomes.IncomeLevelService;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;
import com.rssl.phizic.business.loanCardClaim.LoanCardClaimHelper;
import com.rssl.phizic.business.loanCardOffer.AvailableCreditCardProductsComparator;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferLinkWithProductComparator;
import com.rssl.phizic.business.loans.products.Publicity;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collections;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 02.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanOfferListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final IncomeLevelService incomeLevelService = new IncomeLevelService();
	private static final CreditCardProductService creditCardProductService = new CreditCardProductService();

	/* ������, ���� �� � ������� ������� �� */
	public Boolean checkClientPassportType()
	{
		return PersonHelper.hasRegularPassportRF();
	}

	public PersonData getPersonData()
	{
		 return  PersonContext.getPersonDataProvider().getPersonData();
	}

	/** ��������� ������� �������������� ��������� ���� ��� �������
	 * @return ���� �������������� ����  ���, �� null,
	 *         ���� ���� ���� � ���� "����������� �� ���������� ���������� ������", �� id �����������
	 *         ����� ���� ����������� �� ��������� ����, �� -2
	 *         ����� -1
	 * @throws BusinessException
	 */
	public Long checkLoanCardOfferClient() throws BusinessException
	{
		return LoanCardClaimHelper.checkLoanCardOfferClient();
	}

	/*  ���������� ��������� ��������� ��������� ��������� �� ������ */
	public Object sortProductObject(List<Object[]> availableCreditCardProducts)
	{
		if(!ApplicationUtil.isATMApi())
			Collections.sort(availableCreditCardProducts, new AvailableCreditCardProductsComparator());
		return availableCreditCardProducts;
	}

	/**
	 * ��������� �� ��������� �� ������� ����� URL �������� ������ ���� ��������� �� ��������� ������ ��� ������������ ������� ������.
	 * ���� ���������� ���� ���� ��������� "����� <-> ��������� ������", �� � ��������� �������� ������ ������������� � ����������� �� ��� ���������.
	 * @param incomeLevelId
	 * @throws BusinessException
	 */
	public void checkExistIncomeLevels(String incomeLevelId) throws BusinessException
	{
		if(StringHelper.isEmpty(incomeLevelId) && incomeLevelService.isIncomeLevelsExists())
			throw new BusinessException("������� �������� ����� URL ��� ��������� ��������� �������� ��� ������������ ������� ������");
	}

	/**
	 * �������� �� ��������� ����������� �� ��������� �����
	 * @param conditionId - id �������
	 * @param changeDate - ��������� ���� ��������������
	 * @return - true - ���� ���������, false - �� ����
	 * @throws BusinessException
	 */
	public boolean productIsChange(Long conditionId, Long changeDate) throws BusinessException
	{
		CreditCardProduct product = creditCardProductService.findCreditCardProductByCreditCardConditionId(conditionId);

		return product == null || product.getPublicity() == Publicity.UNPUBLISHED
				|| product.getChangeDate().getTimeInMillis() != changeDate;
	}

	/**
	 * @return
	 * @throws BusinessException
	 */
	public List<ConditionProductByOffer> getCardOfferCompositProductCondition(List<String> offerTypes) throws BusinessException
	{
		List<ConditionProductByOffer> cardOfferCompositProductCondition = getPersonData().getCardOfferCompositProductCondition(offerTypes);
		if (!cardOfferCompositProductCondition.isEmpty())
			Collections.sort(cardOfferCompositProductCondition, new LoanCardOfferLinkWithProductComparator());
		return cardOfferCompositProductCondition;
	}

	@Override
	public Query createQuery(String name)
	{
		if("card_product".equals(name))
			return MultiLocaleQueryHelper.getOperationQuery(this, name, getInstanceName());
		return super.createQuery(name);
	}
}