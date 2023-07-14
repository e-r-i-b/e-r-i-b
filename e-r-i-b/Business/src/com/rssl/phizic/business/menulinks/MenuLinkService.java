package com.rssl.phizic.business.menulinks;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;

/**
 * @author lukina
 * @ created 19.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class MenuLinkService
{
	private SimpleService simpleService = new SimpleService();

	/**
	 * ���������� ���������
	 * @param link - ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public MenuLink addOrUpdate(MenuLink link) throws BusinessException
	{
		return simpleService.addOrUpdate(link);
	}

	/**
	 * �������� ���������
	 * @param link
	 * @throws BusinessException
	 */
	public void remove(MenuLink link) throws BusinessException
	{
		simpleService.remove(link);
	}
	
	/**
	 * ����� �������� ����������� �������� ����
	 * @param loginId - ����� ������������
	 * @return ������ MenuLink
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<MenuLink> findByUserId(final CommonLogin loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<MenuLink>>()
				{
					public List<MenuLink> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.menulinks.MenuLink.getUserLinks");
						query.setParameter("loginId", loginId.getId());
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
	 * �������� ������ ����, ������� ������������ � �������
	 * @param loginId - ����� ������������
	 * @return ������ MenuLink
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public List<MenuLink> findUsedByUserId(final CommonLogin loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<MenuLink>>()
				{
					public List<MenuLink> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.menulinks.MenuLink.getUsedUserLinks");
						query.setParameter("loginId", loginId.getId());
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
	 * �������� �������� ����������� �������� ����
	 * @param loginId - ����� ������������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void removeByUserId(final CommonLogin loginId) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizic.business.menulinks.MenuLink.removeByUserId");
						query.setParameter("loginId", loginId.getId());
						query.executeUpdate();
						return null;
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
