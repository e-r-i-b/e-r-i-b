package com.rssl.phizic.csaadmin.business.operation;

import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.SimpleService;
import com.rssl.phizic.csaadmin.business.session.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author mihaylov
 * @ created 04.11.13
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ���������� ��������
 */
public class OperationContextService extends SimpleService<OperationContext>
{

	@Override
	protected Class<OperationContext> getEntityClass()
	{
		return OperationContext.class;
	}

	/**
	 * ����� ��������� ��������� ��� ������
	 * @param session - ������
	 * @return - �������� �������� � ������ ������
	 * @throws AdminException
	 */
	public OperationContext findActiveForSession(Session session) throws AdminException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(OperationContext.class);
		criteria.add(Expression.eq("session", session));
		criteria.add(Expression.eq("state", OperationContextState.ACTIVE));
		return findSingle(criteria);
	}

	/**
	 * ������� �������� ��������
	 * @param operationContext - ��������
	 * @throws AdminException
	 */
	public void close(OperationContext operationContext) throws AdminException
	{
		operationContext.setState(OperationContextState.CLOSED);
		super.save(operationContext);
	}

}
