package com.rssl.phizic.business.offers;

import com.rssl.ikfl.crediting.FeedbackType;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanCardOffer.ConditionProductByOffer;
import com.rssl.phizic.business.persons.ActivePerson;

import java.util.List;

/**
 * @author Rtischeva
 * @ created 19.01.15
 * @ $Author$
 * @ $Revision$
 */
public interface OfferService
{
	/**
	 * ��������� ������ ���� ����������� ������� �� ������.
	 * @param person - ������������
	 * @param number -  ���-�� ������������ �������. ���� null �� ���.
	 * @param isViewed - true(����������)/false(������������)/null(��� ������)
	 * @throws com.rssl.phizic.business.BusinessException
	 * @return ������ number ����������� ������� �� ������ ��� ���� �����������, ���� number = null.
	 */
	List<LoanCardOffer> getLoanCardOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException;

	/**
	 * �������� ����������� ������� �� ��������.
	 * @param person - ������������
	 * @param number - ����������� �����������
	 * @param isViewed - true(����������)/false(������������)/null(��� ������)
	 * @throws com.rssl.phizic.business.BusinessException
	 * @return ������ number ����������� ������� �� �������� ��� ���� �����������, ���� number = null.
	 */
	List<LoanOffer> getLoanOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException;

	/**
	 * �������� ����������� �� ������� �� offerId
	 * @param offerId - ������������� �����������
	 * @return ����������� �� �������
	 * @throws BusinessException
	 */
	LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException;

	/**
	 * �������� ����������� �� ����� �� offerId
	 * @param offerId - ������������� �����������
	 * @return ����������� �� �����
	 * @throws BusinessException
	 */
	LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException;

	/**
	 * ��������� ������ �� ����������� �� ��������
	 * @param loanOfferIds - ������ ��������������� ����������� �� ��������
	 * @param feedbackType - ��� �������
	 * @throws BusinessException
	 */
	void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException;

	/**
	 * ��������� ������ �� ����������� �� ������
	 * @param loanCardOfferIds - ������ ��������������� ����������� �� ������
	 * @param feedbackType - ��� �������
	 * @throws BusinessException
	 */
	void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException;

	/**
	 * ����������� �������� ������ (��������� �����������) �� CRM
	 * @param person - ������������
	 * @throws BusinessException
	 * @return true - ��������� �����
	 */
	boolean isOfferReceivingInProgress(ActivePerson person) throws BusinessException;

	/**
	 * �������� ����������� �� ������� ��� ��������������
	 * @param offerId - ������������� �����������
	 * @throws BusinessException
	 */
	void markLoanOfferAsUsed(OfferId offerId) throws BusinessException;

	/**
	 * �������� ����������� �� ����� ��� ��������������
	 * @param offerId - ������������� �����������
	 * @throws BusinessException
	 */
	void markLoanCardOfferAsUsed(OfferId offerId) throws BusinessException;

	/**
	 * ���������� ������������ ������ ��� ����������� �� ������� ��������
	 * @param person - �������
	 * @return ������������ ������
	 * @throws BusinessException
	 */
	LoanOffer getLoanOfferForMainPage(ActivePerson person) throws BusinessException;

	/**
	 * ���������� ������������ ��������� ����� ��� ����������� �� ������� ��������
	 * @param person - �������
	 * @return ������������ ��������� �����
	 * @throws BusinessException
	 */
	LoanCardOffer getLoanCardOfferForMainPage(ActivePerson person) throws BusinessException;

	/**
	 * ��������� �� ������ �� ����������� �� ������
	 * @param offerId - ������������� �����������
	 * @param feedbackType - ��� �������
	 * @return
	 */
	boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException;

	/**
	 * ��������� �� ������ �� ����������� �� ��������� �����
	 * @param offerId - ������������� �����������
	 * @param feedbackType - ��� �������
	 * @return
	 */
	boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException;

	/**
	 * @param offerTypes ���� ����������� �� ������ ��� ������� ����� �������� �������
	 * @param person ������
	 * @return ������ ���������� ���������� �� ��������� ��������: �������, ������ � ������� �� ��������� �����.
	 */
	List<ConditionProductByOffer> getCardOfferCompositProductCondition(final List<String> offerTypes, ActivePerson person) throws BusinessException;

	/**
	 * @param person ������
	 * @param conditionId  id ������� � ������� ������ ��� ���������� ���������� ��������
	 * @return �����-�������� ������� �������� �������, ����� � ������� �� ����������� �� ��������� �����
	 * @throws BusinessException
	 */
	ConditionProductByOffer findConditionProductByOffer(ActivePerson person, final Long conditionId) throws BusinessException;

	/**
	 * ��������� ���������� �� ��� �����������, �� �������� ���� ��������� ������ �� ����. �����
	 * @param conditionId id ������� �� ��������� �����
	 * @param person ������
	 * @return true - ����������� ��� ����������, false - ����������� ��� �������
	 * @throws BusinessException
	 */
	boolean existCreditCardOffer(ActivePerson person, Long conditionId) throws BusinessException;

	/**
	 * �������� ����������� �� ��������� ����� �� ��������� � ��� �������� � ��������� �� id �����������, � id �������
	 * @param loanCardOfferId  - id ����������� �� ��������� �����
	 * @param conditionId      - id �������
	 * @return  ������ ��������� �� 3-� ���������: CreditCardCondition, LoanCardOffer, CreditCardProduct
	 *          null - ���� ������ �� �������
	 * @throws BusinessException
	 */
	ConditionProductByOffer findConditionProductByOfferIdAndConditionId(final OfferId loanCardOfferId, final Long conditionId) throws BusinessException;

	/**
	 * ��������� ������� �� id ����������� �� �����
	 * @param loanCardOfferId  - id ����������� �� �����
	 * @return id �������
	 * @throws BusinessException
	 */
	public Long findConditionIdByOfferId(final OfferId loanCardOfferId) throws BusinessException;

	/**
	 * �������� ����������� �� ����� � ����� "����� �����"
	 * @param person - �������
	 * @return ����������� �� ����� � ����� "����� �����"
	 * @throws BusinessException
	 */
	LoanCardOffer getNewLoanCardOffer(ActivePerson person) throws BusinessException;

	/**
	 * �������� ���� ����������� ������ �� ��������� �����
	 * @param offerId - ������������� ����������� �� ��������� �����
	 * @throws BusinessException
	 */
	void updateLoanCardOfferRegistrationDate(OfferId offerId) throws BusinessException;

	/**
	 * �������� ���� �������� �� �������� ���������� ������ �� ��������� �����
	 * @param offerId - ������������� ����������� �� ��������� �����
	 * @throws BusinessException
	 */
	void updateLoanCardOfferTransitionDate(OfferId offerId) throws BusinessException;
}
