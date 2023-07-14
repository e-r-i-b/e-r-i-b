package com.rssl.phizic.business.cardAmountStep;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;
import java.math.BigDecimal;

/**
 * User: Moshenko
 * Date: 03.06.2011
 * Time: 10:38:52
 * Сервис для работы с лимитами сумм предодобренных предложений.
 */
public class CardAmountStepService {
    private static SimpleService simpleService = new SimpleService();

    /**
     * Создание/Обновление  лимита
     *
     * @param cardAmountStep
     * @return
     * @throws BusinessException
     */

    public CardAmountStep addOrUpdate(final CardAmountStep cardAmountStep) throws BusinessException {
        return simpleService.addOrUpdate(cardAmountStep);
    }

    /**
     * Удаление лимита
     *
     * @param cardAmountStep
     * @throws BusinessException
     */
    public void removeCardAmountStep(final CardAmountStep cardAmountStep) throws RemoveCardAmountStepException, BusinessException {
        try {
            simpleService.removeWithConstraintViolationException(cardAmountStep);
        } catch (ConstraintViolationException e) {

            throw new RemoveCardAmountStepException(e);
        }
    }

    /**
     * Найти кредитный лимит по id
     *
     * @param id Лимита
     * @return
     * @throws BusinessException
     */
    public CardAmountStep getById(Long id) throws BusinessException {
        return simpleService.findById(CardAmountStep.class, id);
    }

	/**
	 * Получение списка кредитных лимитов, отсортированных по сумме (необходимо для отображения)
	 * @return - список кредитных лимитов
	 * @throws BusinessException
	 */
	public List<CardAmountStep> getAll() throws BusinessException
	{
		try
		{
			return	HibernateExecutor.getInstance().execute(new HibernateAction<List<CardAmountStep> >()
			{
				public List<CardAmountStep>  run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.cardAmountStep.CardAmountStep.findAll");
					return query.list();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

    /**
     *  Получение списка кредитных лимитов по Money и Id
     * @param cardAmountStep - искомый кредитный лимит
     * @return - список кредитных лимитов
     * @throws BusinessException
     */
    public List<CardAmountStep>  getListByMoney(final CardAmountStep cardAmountStep) throws BusinessException
	{
		try
		{
		 return	HibernateExecutor.getInstance().execute(new HibernateAction<List<CardAmountStep> >()
				{
					public List<CardAmountStep>  run(Session session)
					{
						if (cardAmountStep.getId() == null)
						{
							Query query = session.getNamedQuery("com.rssl.phizic.business.cardAmountStep.CardAmountStep.getListByMoney");
							query.setParameter("amount", cardAmountStep.getValue().getWholePart());
							query.setParameter("currency", cardAmountStep.getValue().getCurrency().getCode());
							return query.list();
						}
						else
						{
							Query query = session.getNamedQuery("com.rssl.phizic.business.cardAmountStep.CardAmountStep.getListByMoneyAndId");
							query.setParameter("amount", cardAmountStep.getValue().getWholePart());
							query.setParameter("currency", cardAmountStep.getValue().getCurrency().getCode());
							query.setParameter("id", cardAmountStep.getId());
							return query.list();
						}
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получает все суммы лимитов от и  до
	 * @param from - сумма от
	 * @param to - сумма до
	 * @param include верхняя граница: true - включается, false - не включается
	 * @return список лимитов в заданном диапазоне
	 * @throws BusinessException
	 */
	public List<CardAmountStep> getRangeLimit(final Money from, final Money to, final Boolean include) throws BusinessException
	{
		try
		{
		 return	HibernateExecutor.getInstance().execute(new HibernateAction<List<CardAmountStep> >()
				{
					public List<CardAmountStep>  run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.cardAmountStep.CardAmountStep.getRangeLimit");
						query.setParameter("fromAmount", from.getDecimal());
						query.setParameter("toAmount", to.getDecimal());
						query.setParameter("fromCurrency", from.getCurrency().getCode());
						query.setParameter("toCurrency", to.getCurrency().getCode());
						query.setParameter("include", include.toString());
						return query.list();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Получает все суммы лимитов от и  до (включительно),
	 * валюта второй суммы не задана, берется из первой суммы
	 * @param from - сумма от
	 * @param to - сумма (без валюты) до
	 * @param include верхняя граница: true - включается, false - не включается
	 * @return список лимитов в заданном диапазоне
	 * @throws BusinessException
	 */
	public List<CardAmountStep> getRangeLimit(final Money from, final BigDecimal to, final Boolean include) throws BusinessException
	{
		try
		{
		 return	HibernateExecutor.getInstance().execute(new HibernateAction<List<CardAmountStep> >()
				{
					public List<CardAmountStep>  run(Session session) throws BusinessException
					{
						return getRangeLimit(from, new Money(to, from.getCurrency()), include);
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Проверяет использование кредитного лимита в карточных кредитных продуктах и в уровнях доходов
	 * @param id Идентификатор кредитного лимита
	 * @return true - используется
	 * @throws BusinessException
	 */
	public Boolean isUsed(final Long id) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(CardAmountStep.class.getName() + ".isUsed");
					query.setParameter("cardLimitId", id);
					return !query.uniqueResult().equals(BigDecimal.ZERO);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
