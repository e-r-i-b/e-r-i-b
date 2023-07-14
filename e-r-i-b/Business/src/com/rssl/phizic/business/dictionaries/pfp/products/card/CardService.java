package com.rssl.phizic.business.dictionaries.pfp.products.card;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.PFPDictionaryServiceBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.List;

/**
 * @author akrenev
 * @ created 08.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с картами пфп
 */

public class CardService
{
	private static final PFPDictionaryServiceBase service = new PFPDictionaryServiceBase();

	/**
	 * @return новый инстанс карты
	 */
	public Card getNewCard()
	{
		Card card = new Card();
		CardDiagramParameters diagramParameters = new CardDiagramParameters();
		diagramParameters.setUseNet(true);
		card.setDiagramParameters(diagramParameters);
		return card;
	}

	/**
	 * получить по id
	 * @param id идентификатор
	 * @return карта
	 * @throws BusinessException
	 */
	public Card findById(Long id) throws BusinessException
	{
		return findById(id, null);
	}

	/**
	 * получить по id
	 * @param id идентификатор
	 * @param instance имя инстанса модели БД
	 * @return карта
	 * @throws BusinessException
	 */
	public Card findById(Long id, String instance) throws BusinessException
	{
		return service.findById(Card.class, id, instance);
	}

	/**
	 * @param cardProductIds идентификаторы
	 * @param instance имя инстанса модели БД
	 * @return список карт по идентификаторам
	 * @throws BusinessException
	 */
	public List<Card> getListByIds(Long[] cardProductIds, String instance) throws BusinessException
	{
		return service.getListByIds(Card.class, cardProductIds, instance);
	}

	/**
	 * добавить или обновить
	 * @param card карта
	 * @param instance имя инстанса модели БД
	 * @return сохраненная карта
	 * @throws BusinessException
	 */
	public Card addOrUpdate(Card card, String instance) throws BusinessException
	{
		return service.addOrUpdate(card, instance);
	}

	/**
	 * удалить
	 * @param card карта
	 * @param instance имя инстанса модели БД
	 * @throws BusinessException
	 */
	public void remove(Card card, String instance) throws BusinessException
	{
		service.remove(card, instance);
	}

	/**
	 * ищет карту с признаком "Предлагать по умолчанию"
	 * Опорный объект: PFP_CARDS
	 * Предикаты доступа: fullscan
	 * Кардинальность: 1
	 *
	 * @param instance имя модели БД
	 * @return карта с признаком "Предлагать по умолчанию"
	 * @throws BusinessException
	 */
	public Card findDefaultCard(String instance) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Card.class);
		criteria.add(Expression.eq("showAsDefault", true));
		return service.findSingle(criteria, instance);
	}
}
