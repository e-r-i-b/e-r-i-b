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
 * ������ ������ � ���������� � ������������� �� ������ �������� ��������
 */
public class BasketRouteInfoService
{
	private static final DatabaseServiceBase databaseService = new DatabaseServiceBase();
	private static final String OFFLINE_DOC_DB_INSTANCE = "OfflineDoc";

	/**
	 * ���������� ���������� � ������������� �������� ��������.
	 * @param operUID - ���������� ������������� ������ �� ��������.
	 * @param blockNumber - ����� �����.
	 * @throws Exception
	 */
	public static void addInfo(String operUID, Long blockNumber) throws Exception
	{
		databaseService.add(new BasketRouteInfo(operUID, blockNumber), OFFLINE_DOC_DB_INSTANCE);
    }

	/**
	 * ����� ���������� � ������������� �������� ��������.
	 * @param operUID - ���������� ������������� ������ �� ��������. OperUID � ���� �������.
	 * @return ����
	 * @throws Exception
	 */
	public static BasketRouteInfo getInfo(String operUID) throws Exception
	{
		/*
		������� �������: I_BASKET_ROUTE_INFO_OPER_UID
		�������� �������: "THIS_"."OPER_UID"=:operUID
		��������������: 1
		 */
		DetachedCriteria criteria = DetachedCriteria.forClass(BasketRouteInfo.class);
        criteria.add(Expression.eq("operUID", operUID));
		return databaseService.findSingle(criteria,null,OFFLINE_DOC_DB_INSTANCE);
	}

	/**
	 * ������� ���������������� ���������� �� ��������������
	 * @param operUID �������������
	 * @throws Exception
	 */
	public static void remove(final String operUID) throws Exception
	{
		if (StringHelper.isEmpty(operUID))
		{
			throw new IllegalArgumentException("���������� ������������� ������ �� �������� �� ����� ���� null.");
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
