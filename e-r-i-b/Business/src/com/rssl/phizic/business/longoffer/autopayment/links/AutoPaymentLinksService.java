package com.rssl.phizic.business.longoffer.autopayment.links;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cache.BusinessWaitCreateCacheException;
import com.rssl.phizic.business.longoffer.autopayment.AutoPaymentHelper;
import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateWaitCreateCacheException;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPayment;
import com.rssl.phizic.gate.longoffer.autopayment.AutoPaymentService;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author osminin
 * @ created 08.02.2011
 * @ $Author$
 * @ $Revision$
 * —ервис дл€ получени€ списка линков автоплатежей
 */
public class AutoPaymentLinksService
{
	private final ExternalResourceService resourceService = new ExternalResourceService();

	/**
	 * получить линки автоплатежей по логину
	 * @param login логин клиента
	 * @return список линков автоплатежей
	 * @throws BusinessException
	 */
	public List<AutoPaymentLink> findByUserId(final CommonLogin login) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<AutoPaymentLink>>()
				{
					public List<AutoPaymentLink> run(Session session)
					{
						Query query = session.getNamedQuery(AutoPaymentLink.class.getName() + ".list");
						query.setParameter("login", login.getId());
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
	 * Ќайти линк на автоплатеж по внутреннему идентификатору
	 * @param id внутренний идентификатор
	 * @return автоплатеж
	 * @throws BusinessException
	 */
	public AutoPaymentLink findById(final Long id) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<AutoPaymentLink>()
				{
					public AutoPaymentLink run(Session session)
					{
						Criteria criteria = session.createCriteria(AutoPaymentLink.class);
						criteria.add(Expression.eq("id", id));
						return (AutoPaymentLink) criteria.uniqueResult();
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
	 * ќбновл€ет линки автоплатежей
	 * @param personLogin - логин
	 * @param cardLinks - список карт
	 * @param links - линки, которые нужно обновить
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void updateAutoPaymentLinks(Login personLogin, Collection<CardLink> cardLinks,  List<AutoPaymentLink> links) throws BusinessLogicException, BusinessException
	{
		try
		{
			if (CollectionUtils.isEmpty(cardLinks))
				return;//если нет карт, то нечего звать шлюз с пустым списком.

			AutoPaymentService autoPaymentService = GateSingleton.getFactory().service(AutoPaymentService.class);

			List<Card> cards = new ArrayList<Card>(cardLinks.size());
			for (CardLink cardLink : cardLinks)
			{
				cards.add(cardLink.toLinkedObjectInDBView());
			}

			List<AutoPaymentLink> unknownLinks = new ArrayList<AutoPaymentLink>(links);
			List<AutoPayment> autoPayments = autoPaymentService.getClientsAutoPayments(cards);
			for (AutoPayment payment : autoPayments)
			{
				//≈сли платеж не найден среди линков, добавл€ем. Ќичего не удал€ем, тк если платеж был удален в системе,
				//то в ответе он вернетс€ со статусом "”дален"
				AutoPaymentLink link = AutoPaymentHelper.findAutoPaymentLinkByEternalId(links, payment.getExternalId());
				if (link == null)
				{
					AutoPaymentLink newLink = resourceService.addAutoPaymentLink(personLogin, payment);
					links.add(newLink);
				}
				else
				{
					unknownLinks.remove(link);

					if (!StringHelper.equals(link.getName(), payment.getFriendlyName()) ||
						!StringHelper.equals(link.getNumber(), payment.getNumber()))
					{
						link.setName(payment.getFriendlyName());
						link.setNumber(payment.getNumber());
						resourceService.updateLink(link);
					}
				}
			}
			//удал€ем оставшиес€ линки из Ѕƒ, т.к. по ним не пришло информации
			for (AutoPaymentLink link : unknownLinks)
			{
				resourceService.removeLink(link);
				links.remove(link);
			}
		}
		catch (GateWaitCreateCacheException e)
		{
			throw new BusinessWaitCreateCacheException(e.getMessage(), e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
