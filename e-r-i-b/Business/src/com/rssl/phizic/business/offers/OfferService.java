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
	 * получение списка всех предложений клиента по картам.
	 * @param person - пользователь
	 * @param number -  кол-во возвращаемых записей. Если null то все.
	 * @param isViewed - true(показанные)/false(непоказанные)/null(все подряд)
	 * @throws com.rssl.phizic.business.BusinessException
	 * @return список number предложений клиента по картам или всех предложений, если number = null.
	 */
	List<LoanCardOffer> getLoanCardOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException;

	/**
	 * Получить предложения клиента по кредитам.
	 * @param person - пользователь
	 * @param number - колличества предложений
	 * @param isViewed - true(показанные)/false(непоказанные)/null(все подряд)
	 * @throws com.rssl.phizic.business.BusinessException
	 * @return список number предложений клиента по кредитам или всех предложений, если number = null.
	 */
	List<LoanOffer> getLoanOfferByPersonData(Integer number, ActivePerson person, Boolean isViewed) throws BusinessException;

	/**
	 * Получить предложение по кредиту по offerId
	 * @param offerId - идентификатор предложения
	 * @return предложение по кредиту
	 * @throws BusinessException
	 */
	LoanOffer findLoanOfferById(OfferId offerId) throws BusinessException;

	/**
	 * Получить предложение по карте по offerId
	 * @param offerId - идентификатор предложения
	 * @return предложение по карте
	 * @throws BusinessException
	 */
	LoanCardOffer findLoanCardOfferById(OfferId offerId) throws BusinessException;

	/**
	 * Отправить отклик на предложения по кредитам
	 * @param loanOfferIds - список идентификаторов предложений по кредитам
	 * @param feedbackType - тип отклика
	 * @throws BusinessException
	 */
	void sendLoanOffersFeedback(List<OfferId> loanOfferIds, FeedbackType feedbackType) throws BusinessException;

	/**
	 * Отправить отклик на предложения по картам
	 * @param loanCardOfferIds - список идентификаторов предложений по картам
	 * @param feedbackType - тип отклика
	 * @throws BusinessException
	 */
	void sendLoanCardOffersFeedback(List<OfferId> loanCardOfferIds, FeedbackType feedbackType) throws BusinessException;

	/**
	 * Определение ожидания ответа (получения предложений) от CRM
	 * @param person - пользователь
	 * @throws BusinessException
	 * @return true - ожидается ответ
	 */
	boolean isOfferReceivingInProgress(ActivePerson person) throws BusinessException;

	/**
	 * Пометить предложение по кредиту как использованное
	 * @param offerId - идентификатор предложения
	 * @throws BusinessException
	 */
	void markLoanOfferAsUsed(OfferId offerId) throws BusinessException;

	/**
	 * Пометить предложение по карте как использованное
	 * @param offerId - идентификатор предложения
	 * @throws BusinessException
	 */
	void markLoanCardOfferAsUsed(OfferId offerId) throws BusinessException;

	/**
	 * Возвращает приоритетный кредит для отображения на главной странице
	 * @param person - персона
	 * @return приоритетный кредит
	 * @throws BusinessException
	 */
	LoanOffer getLoanOfferForMainPage(ActivePerson person) throws BusinessException;

	/**
	 * Возвращает приоритетную кредитную карту для отображения на главной странице
	 * @param person - персона
	 * @return приоритетная кредитная карта
	 * @throws BusinessException
	 */
	LoanCardOffer getLoanCardOfferForMainPage(ActivePerson person) throws BusinessException;

	/**
	 * Отправлен ли отклик на предложение на кредит
	 * @param offerId - идентификатор предложения
	 * @param feedbackType - тип отклика
	 * @return
	 */
	boolean isFeedbackForLoanOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException;

	/**
	 * Отправлен ли отклик на предложение на кредитную карту
	 * @param offerId - идентификатор предложения
	 * @param feedbackType - тип отклика
	 * @return
	 */
	boolean isFeedbackForLoanCardOfferSend(OfferId offerId, FeedbackType feedbackType) throws BusinessException;

	/**
	 * @param offerTypes Типы предложений по картам для которых будет делаться выборка
	 * @param person клиент
	 * @return Список содержащий информацию по связанным объектам: продукт, заявка и условие по кредитной карте.
	 */
	List<ConditionProductByOffer> getCardOfferCompositProductCondition(final List<String> offerTypes, ActivePerson person) throws BusinessException;

	/**
	 * @param person клиент
	 * @param conditionId  id условия в разрезе валюты для Карточного кредитного продукта
	 * @return Класс-сущность который содержит продукт, завку и условие по предложению на кредитную карту
	 * @throws BusinessException
	 */
	ConditionProductByOffer findConditionProductByOffer(ActivePerson person, final Long conditionId) throws BusinessException;

	/**
	 * Проверяет существует ли еще предложение, по которому была оформлена заявка на кред. карту
	 * @param conditionId id условия по кредитной карте
	 * @param person клиент
	 * @return true - предложение еще существует, false - предложение уже удалено
	 * @throws BusinessException
	 */
	boolean existCreditCardOffer(ActivePerson person, Long conditionId) throws BusinessException;

	/**
	 * Получени предложения по кредитной карте со связанным с ним условием и продуктом по id предложения, и id условия
	 * @param loanCardOfferId  - id предложения по кредитной карте
	 * @param conditionId      - id условия
	 * @return  объект состоящий из 3-х элементов: CreditCardCondition, LoanCardOffer, CreditCardProduct
	 *          null - если ничего не найдено
	 * @throws BusinessException
	 */
	ConditionProductByOffer findConditionProductByOfferIdAndConditionId(final OfferId loanCardOfferId, final Long conditionId) throws BusinessException;

	/**
	 * Получение условия по id предложения по карте
	 * @param loanCardOfferId  - id предложения по карте
	 * @return id условия
	 * @throws BusinessException
	 */
	public Long findConditionIdByOfferId(final OfferId loanCardOfferId) throws BusinessException;

	/**
	 * Получить предложение по карте с типом "новая карта"
	 * @param person - персона
	 * @return предложение по карте с типом "новая карта"
	 * @throws BusinessException
	 */
	LoanCardOffer getNewLoanCardOffer(ActivePerson person) throws BusinessException;

	/**
	 * Обновить дату регистрации заявки на кредитную карту
	 * @param offerId - идентификатор предложения на кредитную карту
	 * @throws BusinessException
	 */
	void updateLoanCardOfferRegistrationDate(OfferId offerId) throws BusinessException;

	/**
	 * Обновить дату перехода на страницу оформления заявки на кредитную карту
	 * @param offerId - идентификатор предложения на кредитную карту
	 * @throws BusinessException
	 */
	void updateLoanCardOfferTransitionDate(OfferId offerId) throws BusinessException;
}
