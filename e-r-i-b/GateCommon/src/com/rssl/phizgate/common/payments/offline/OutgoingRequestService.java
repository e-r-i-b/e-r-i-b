package com.rssl.phizgate.common.payments.offline;

import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * ������ ���������� �������������� ��������� ��������
 * @author Puzikov
 * @ created 10.10.14
 * @ $Author$
 * @ $Revision$
 */

public class OutgoingRequestService
{
	private static final String OFFLINE_DOC_DB_INSTANCE = "OfflineDoc";

	private final DatabaseServiceBase databaseService = new DatabaseServiceBase();

	/**
	 * �������� ���������� �� ��������� �������
	 * @param requestEntry - ������ � ������� ���������� �������
	 */
	public void addRequestEntry(OutgoingRequestEntry requestEntry) throws Exception
	{
		databaseService.add(requestEntry, OFFLINE_DOC_DB_INSTANCE);
	}

	/**
	 * ����� �� id
	 * @param rqUID id ������� (never null)
	 * @return ��������� ������
	 */
	public OutgoingRequestEntry getById(String rqUID) throws Exception
	{
//		������� ������: PK_RQ_UID
//		��������� �������: "RQ_UID"=:RQUID
//		��������������: 1
		DetachedCriteria criteria = DetachedCriteria.forClass(OutgoingRequestEntry.class);
		criteria.add(Expression.eq("rqUID", rqUID));
		return databaseService.findSingle(criteria, null, OFFLINE_DOC_DB_INSTANCE);
	}

	/**
	 * ������� �� id
	 * @param rqUID id �������
	 */
	public void deleteByRqId(final String rqUID) throws Exception
	{
		HibernateExecutor.getInstance(OFFLINE_DOC_DB_INSTANCE).execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				session.getNamedQuery("com.rssl.phizgate.common.payments.offline.OutgoingRequestEntry.delete")
						.setParameter("rqUID", rqUID)
						.executeUpdate();
				return null;
			}
		});
	}
}
