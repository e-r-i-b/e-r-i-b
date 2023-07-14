package com.rssl.phizgate.common.payments.offline.basket;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author vagin
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 * Сервис работы с информацие о маршрутизации по блокам входящих инвойсов
 */
public class BasketRouteInfoService
{
	private static final DatabaseServiceBase databaseService = new DatabaseServiceBase();
	private static final String OFFLINE_DOC_DB_INSTANCE = "OfflineDoc";

	/**
	 * Добавление информации о маршрутизации входящих инвойсов.
	 * @param operUID - внутренний идентификатор заявки на подписку.
	 * @param blockNumber - номер блока.
	 * @throws Exception
	 */
	public static void addInfo(String operUID, Long blockNumber) throws Exception
	{
		databaseService.add(new BasketRouteInfo(operUID, blockNumber), OFFLINE_DOC_DB_INSTANCE);
    }

	/**
	 * Поиск информации о маршрутизации входящих инвойсов.
	 * @param operUID - внутренний идентификатор заявки на подписку. OperUID в теле запроса.
	 * @return инфо
	 * @throws Exception
	 */
	public static BasketRouteInfo getInfo(String operUID) throws Exception
	{
		/*
		Опорный элемент: I_BASKET_ROUTE_INFO_OPER_UID
		Предикат доступа: "THIS_"."OPER_UID"=:operUID
		Кардинальность: 1
		 */
		DetachedCriteria criteria = DetachedCriteria.forClass(BasketRouteInfo.class);
        criteria.add(Expression.eq("operUID", operUID));
		return databaseService.findSingle(criteria,null,OFFLINE_DOC_DB_INSTANCE);
	}

	/**
	 * Удалить маршрутизирующую информацию по идентификатору
	 * @param operUID идентификатор
	 * @throws Exception
	 */
	public static void remove(final String operUID) throws Exception
	{
		if (StringHelper.isEmpty(operUID))
		{
			throw new IllegalArgumentException("Внутренний идентификатор заявки на подписку не может быть null.");
		}
		HibernateExecutor.getInstance(OFFLINE_DOC_DB_INSTANCE).execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				session.getNamedQuery("com.rssl.phizgate.common.payments.offline.basket.BasketRouteInfo.remove")
						.setParameter("oper_uid", operUID)
						.executeUpdate();
				return null;
			}
		});
	}
}
