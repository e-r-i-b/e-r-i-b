package com.rssl.phizicgate.manager.routing;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.events.EventSender;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.manager.events.NodeEvent;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.ConstraintViolationException;

import java.util.List;

/**
 * @author akrenev
 * @ created 07.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class NodeService
{
	/**
	 * Получение узла по id
	 * @param id узла
	 * @return узел
	 * @exception GateException
	 * */
	public Node getNodeById(final Long id) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Node>()
				{
					public Node run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizicgate.manager.routing.Node.getById");
						query.setParameter("id", id);
						return (Node) query.uniqueResult();
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
	 * Получение узла по типу
	 * @param type узла
	 * @return узел
	 * @exception GateException
	 * */
	public List<Node> getNodesByType(final NodeType type) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Node>>()
			{
				public List<Node> run(Session session) throws Exception
				{
					Criteria criteria = session.createCriteria(Node.class);
					criteria.add(Expression.eq("type", type));
					return criteria.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}


	/**
	 * Получение списка узлов
	 * @return список узлов
	 * @exception GateException
	 * */
	public List<Node> getAllNodes() throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Node>>()
				{
					public List<Node> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizicgate.manager.routing.Node.getAll");
						return query.list();
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
	 * Добавление узла
	 * @param node сохраняемый узел
	 * @return сохраненный узел
	 * @exception GateException, GateLogicException
	 * */
	public Node add(final Node node) throws GateLogicException, GateException
	{
		try
		{
			Node newNode = new Node();
			HibernateExecutor executor = HibernateExecutor.getInstance();
			newNode = executor.execute(new HibernateAction<Node>()
			{
				public Node run(Session session)
				{
					session.save(node);
					session.flush();
					return node;
				}
			}
			);
			EventSender.getInstance().sendEvent(new NodeEvent(node, NodeEvent.Type.REGISTER));
			return newNode;
		}
		catch (ConstraintViolationException e)
		{
			throw new DublicateNodeException();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Обновление данных узла
	 * @param node сохраняемый узел
	 * @return сохраненный узел
	 * @exception GateException, GateLogicException
	 * */
	public void update(final Node node) throws GateLogicException, GateException
	{
		try
		{
			HibernateExecutor executor = HibernateExecutor.getInstance();
			executor.execute(new HibernateAction<Node>()
			{
				public Node run(Session session)
				{
					session.update(node);
					session.flush();
					return node;
				}
			}
			);
			EventSender.getInstance().sendEvent(new NodeEvent(node, NodeEvent.Type.UPDATE));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Удаление узла
	 * @param node удаляемый узел
	 * @return удаленный узел
	 * @exception GateException, GateLogicException
	 * */
	public Node remove(final Node node) throws GateLogicException, GateException
	{
		try
		{
			Node removedNode = new Node();
			HibernateExecutor executor = HibernateExecutor.getInstance();
			removedNode = executor.execute(new HibernateAction<Node>()
			{
				public Node run(Session session)
				{
					session.delete(node);
					return node;
				}
			}
			);
			EventSender.getInstance().sendEvent(new NodeEvent(node, NodeEvent.Type.UNREGISTER));
			return removedNode;
		}
		catch (ConstraintViolationException e)
		{
			throw new GateLogicException("Узел не может быть удален, так как имеются адаптеры, использующие его");
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
