package com.rssl.phizic.business.promocodes;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 07.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodeService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ����� �� id
	 * @param id - �������������
	 * @return ��������� �����-�����
	 */
	public PromoCodeSettings findById(Long id) throws BusinessException
	{
	    return simpleService.findById(PromoCodeSettings.class, id);
	}
	/**
	 * ����������/���������� ��������� �����-����
	 * @param settings- ���������
	 * @return ����������� ������
	 */
	public PromoCodeSettings addOrUpdatePromoCodeSettings(PromoCodeSettings settings) throws BusinessException, ConstraintViolationException
	{
		return simpleService.addOrUpdateWithConstraintViolationException(settings, null);
	}

	/**
	 * �������� ��������� �����-����
	 * @param settings ���������
	 */
	public void removePromoCodeSettings(PromoCodeSettings settings) throws BusinessException
	{
		simpleService.remove(settings, null);
	}

	/**
	 * ���� �� �����-����� �� ���� ��� ������������
	 * @param TBId - id ������������
	 * @param date - ����
	 * @return true - ���� ����������� �����-�����
	 */
	public boolean existPromoAction(final Long TBId, final Calendar date) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
				{
					public Boolean run(Session session)
					{
						Query query = session.getNamedQuery(PromoCodeService.class.getName() + ".existPromoAction");
						query.setParameter("TBId", TBId);
						query.setParameter("date", date);

						return query.uniqueResult() != null;
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
