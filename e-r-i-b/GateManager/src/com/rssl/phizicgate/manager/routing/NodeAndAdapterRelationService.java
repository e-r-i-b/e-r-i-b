package com.rssl.phizicgate.manager.routing;

import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;

/**
 * @author akrenev
 * @ created 07.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class NodeAndAdapterRelationService
{

	/**
	 * Получение отношения
	 * @param adapter адаптер
	 * @param node узел
	 * @return отношение
	 * @exception GateException
	 * */
	public NodeAndAdapterRelation getRelation(final Adapter adapter, final Node node) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<NodeAndAdapterRelation>()
				{
					public NodeAndAdapterRelation run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizicgate.manager.routing.NodeAndAdapterRelation.getRelation");
						query.setParameter("adapter", adapter);
						query.setParameter("node", node);
						return (NodeAndAdapterRelation) query.uniqueResult();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Сохранение отношения
	 * @param relation сохраняемое отношение
	 * @exception GateLogicException, GateException
	 * */
	public void addOrUpdate(final NodeAndAdapterRelation relation) throws GateLogicException, GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
				{
					public Void run(Session session)
					{
						session.saveOrUpdate(relation);
						session.flush();
						return null;
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Получение списка узлов адаптера
	 * @param adapter адаптер
	 * @return Список узлов
	 * @exception GateException
	 * */
	public List<Node> getNodes(final Adapter adapter) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Node>>()
				{
					public List<Node> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizicgate.manager.routing.NodeAndAdapterRelation.getNodes");
						query.setParameter("adapter", adapter);
						return (List<Node>) query.list();
					}
				}
			);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

}
