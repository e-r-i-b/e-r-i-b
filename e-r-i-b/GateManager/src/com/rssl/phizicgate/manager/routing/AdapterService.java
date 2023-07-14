package com.rssl.phizicgate.manager.routing;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Projections;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCExceptionHelper;

import java.util.List;

/**
 * @author akrenev
 * @ created 07.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class AdapterService
{
	private static final NodeAndAdapterRelationService nodeAndAdapterRelationService = new NodeAndAdapterRelationService();
	private static final NodeService nodeService = new NodeService();
	private static final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	private static final String BEGIN_CONSTRAINT_MESSAGE = "�������� ����������� ����������� (";
	private static final String END_CONSTRAINT_MESSAGE = ") - ���������� ����������� ������";

	/**
	 * ��������� �������� �� id
	 * @param id ��������
	 * @return �������
	 * @exception GateException
	 * */
	public Adapter getAdapterById(final Long id) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Adapter>()
				{
					public Adapter run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizicgate.manager.routing.Adapter.getById");
						query.setParameter("id", id);
						return (Adapter) query.uniqueResult();
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
	 * ��������� �������� �� UUID
	 * @param UUID ��������
	 * @return �������
	 * @exception GateException
	 * */
	public Adapter getAdapterByUUID(final String UUID) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Adapter>()
				{
					public Adapter run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizicgate.manager.routing.Adapter.getByUUID");
						query.setParameter("UUID", UUID);
						return (Adapter) query.uniqueResult();
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
	 * ��������� ������ ���������
	 * @return ������ ���������
	 * @exception GateException
	 * */
	public List<Adapter> getAllAdapters() throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Adapter>>()
				{
					public List<Adapter> run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizicgate.manager.routing.Adapter.getAll");
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
	 * ���������� ��������
	 * @param adapter ����������� �������
	 * @return ����������� ����
	 * @exception GateException, GateLogicException
	 * */
	public Adapter addOrUpdate(final Adapter adapter) throws GateLogicException, GateException
	{
		try
		{
			HibernateExecutor executor = HibernateExecutor.getInstance();
			return executor.execute(new HibernateAction<Adapter>()
				{
					public Adapter run(Session session)
					{
						session.saveOrUpdate(adapter);
						session.flush();
						return adapter;
					}
				}
			);
		}
		catch (ConstraintViolationException e)
		{
			throw new DublicateAdapterException();
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * �������� ��������
	 * @param adapter ��������� �������
	 * @return ��������� �������
	 * @exception GateException, GateLogicException
	 * */
	public Adapter remove(final Adapter adapter) throws GateLogicException, GateException
	{
		try
		{
			HibernateExecutor executor = HibernateExecutor.getInstance();
			return executor.execute(new HibernateAction<Adapter>()
				{
					public Adapter run(Session session)
					{
						session.delete(adapter);
						return adapter;
					}
				}
			);
		}
		catch (ConstraintViolationException e)
		{
			// ���� ��������� �� ���� ����������, ������� ���������� ����
			String constraintName = e.getConstraintName() == null ? getConstraintName(e) : e.getConstraintName();
			// ���� �� ������ �����������
			if(constraintName == null)
			{
				throw new GateLogicException("�� �� ������ ������� �������, ��� ��� � ���� �������� ������ �������.");
			}
			else if (constraintName.lastIndexOf("TECHNOBREAK_ADAPTER") >= 0)
			{
				throw new GateLogicException("� ��������� ������ ��������������� �������. �������� ����������.");
			}
			else if (constraintName.lastIndexOf("BILLINGS") >= 0)
			{
				throw new GateLogicException("� ��������� ������� ����������� �������. �������� ����������.");
			}
			else
			{
				throw new GateLogicException("� ��������� ������� ������������� �����. �������� ����������.");
			}
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private String getConstraintName(ConstraintViolationException e)
	{
		/*
		��������� ���������� ��� ����������� �� ������� �� ���������� �����, � ��� ��������� ����� ������ �������,
		������� ������� ���������� ��� ����������� �� ������� ���������
		��� ����������:
		   int errorCode = JDBCExceptionHelper.extractErrorCode(sqle);
			if ( errorCode == 1 || errorCode == 2291 || errorCode == 2292 ) {
				return extractUsingTemplate( "constraint (", ") violated", sqle.getMessage() );
			}
		 */

		String errorMessage = e.getSQLException().getMessage();
		int errorCode = JDBCExceptionHelper.extractErrorCode(e.getSQLException());
		if ( errorCode == 1 || errorCode == 2291 || errorCode == 2292 )
		{
			int beginPosition = errorMessage.indexOf(BEGIN_CONSTRAINT_MESSAGE);
			// �� ������������� �������
			if(beginPosition < 0)
				return null;

			int endposition = errorMessage.indexOf(END_CONSTRAINT_MESSAGE);
			return errorMessage.substring(
					beginPosition + BEGIN_CONSTRAINT_MESSAGE.length(),
					endposition < 0 ? errorMessage.length() : endposition);
		}

		return null;
	}

	/**
	 * ��������� ������ ����� ��� ��������
	 * @param adapter �������
	 * @return ������ �����
	 * @exception GateException, GateLogicException
	 * */
	public List<Node> getNodes(final Adapter adapter) throws GateException
	{
		return nodeAndAdapterRelationService.getNodes(adapter);
	}

	/**
	 * ��������� ������ ����� ��� ��������
	 * @param id ������������� ��������
	 * @return ������ �����
	 * @exception GateException, GateLogicException
	 * */
	public Node getNode(final Long id) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Node>()
				{
					public Node run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizicgate.manager.routing.NodeAndAdapterRelation.getNodesByAdapterId");
						query.setMaxResults(1);
						query.setParameter("adapter_id", id);
						//noinspection unchecked
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
	 * ��������� ���� ��� ��������
	 * @param adapterUUID ������������� ��������
	 * @return ������ �����
	 * @exception GateException, GateLogicException
	 * */
	public Node getNode(final String adapterUUID) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Node>()
				{
					public Node run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizicgate.manager.routing.NodeAndAdapterRelation.getNodesByAdapterUUID");
						query.setMaxResults(1);
						query.setParameter("adapter_UUID", adapterUUID);
						//noinspection unchecked
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
	 * ��������� ������ ���� �����
	 * @return ������ �����
	 * @exception GateException, GateLogicException
	 * */
	public List<Node> getNodes() throws GateException
	{
		return nodeService.getAllNodes();
	}

	/**
	 * ��������� UUID �� ���� �������������
	 * ���� ���� ���������������� ���������� ��� �����, �� ���������� ��
	 * ���� ���, �� ��������� UUID ��� ���������������� ���	
	 * @param office ����
	 * @return UUID
	 * @throws GateException
	 * */
	public String getAdapterUUIDByOffice(final Office office) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
				{
					public String run(Session session)
					{
						Query query = session.getNamedQuery("com.rssl.phizicgate.manager.routing.Adapter.getByOfficeCode");
						query.setMaxResults(1);

						String branch = office.getCode().getFields().get("branch");
						String vsp = office.getCode().getFields().get("office");

						query.setParameter("region", office.getCode().getFields().get("region"));
						query.setParameter("branch", StringHelper.isEmpty(branch) ? "NULL" : branch);
						query.setParameter("office", StringHelper.isEmpty(vsp) ? "NULL" : vsp);
						return (String)query.uniqueResult();
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
	 * ��������� ������ ����������� ��������� �� ��
	 * @param uuid - uuid ��������
	 * @return ����� �����������
	 */
	public String getAdapterListenerUrl(String uuid) throws GateException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Adapter.class);
		criteria.add(Expression.eq("UUID", uuid));
		try
		{
			return databaseService.findSingle(criteria.setProjection(Projections.property("listenerUrl")), null, null);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}

