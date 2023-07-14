package com.rssl.auth.csa.back.servises;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.servises.OperationBase;
import com.rssl.auth.csa.back.servises.OperationState;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.ExceptionUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

import java.util.Calendar;
import java.util.List;

/**
 * ������� ����� ��� �������� ��������� �������
 * @author niculichev
 * @ created 15.01.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class GuestOperation extends OperationBase
{
	private static final int INFO_LIMIT = 4000;
	private String phone;

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	protected void refused(Exception e) throws Exception
	{
		setState(OperationState.REFUSED);
		setInfo(ExceptionUtil.printStackTrace(e).substring(0, INFO_LIMIT));
		save();
	}

	public static int getOperationLifeTime()
	{
		return ConfigFactory.getConfig(Config.class).getConfirmationTimeout();
	}

	/**
	 * ���� ��������� count �������� ���� aClass ���������������� �� �������� phone
	 * @param phone ����� ��������
	 * @param aClass ��� ��������
	 * @param count ���������� ��������� ��������
	 * @param startDate ����, � ������
	 * @return ������ ���
	 * @throws Exception
	 */
	public static List<Calendar> getCreateOperationLastDates(final String phone, final Class<? extends GuestOperation> aClass, final int count, final Calendar startDate) throws Exception
	{
		if (aClass == null)
			throw new IllegalArgumentException("����� �������� �� ����� ���� null");

		if(count <= 0)
			throw new IllegalArgumentException("���������� �������� ������ ���� �������������� ������");

		/*
			���� ��������� count �������� ���� aClass ���������������� �� �������� phone
			������� ������: GUEST_OPERATIONS_PTSC
			��������� �������: ("THIS_"."PHONE"=:PHONE AND "THIS_"."TYPE"=:CLASSNAME AND
               SYS_OP_DESCEND("CREATION_DATE")<=SYS_OP_DESCEND(TO_TIMESTAMP(:STARTDATE)))
			��������������: count �� ���������� � ������ ����������� ������
		*/
		return getHibernateExecutor().execute(new HibernateAction<List<Calendar>>()
		{
			public List<Calendar> run(org.hibernate.Session session) throws Exception
			{
				Criteria criteria = session.createCriteria(GuestOperation.class);
				criteria.add(Expression.eq("class", aClass.getSimpleName()));
				criteria.add(Expression.eq("phone", phone));
				criteria.add(Expression.ge("creationDate", startDate));
				criteria.addOrder(Order.desc("creationDate"));
				criteria.setMaxResults(count);

				criteria.setProjection(Property.forName("creationDate"));

				return (List<Calendar>) criteria.list();
			}
		});
	}
}
