package com.rssl.phizic.operations.security;

import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.auth.pin.PINEnvelope;
import org.hibernate.Session;
import org.hibernate.Query;

import java.util.List;

/**
 * @author Roshka
 * @ created 10.10.2006
 * @ $Author$
 * @ $Revision$
 */

public class ShowAvailablePINsTask extends SafeTaskBase
{
	private String departmentId;

	public void setDepartmentId(String departmentId)
	{
		this.departmentId = departmentId;
	}

	public void safeExecute() throws Exception
	{
		List<PINEnvelope> pinEnvelopes;
		try
		{
			pinEnvelopes = HibernateExecutor.getInstance().execute(new HibernateAction<List<PINEnvelope>>()
			{
				public List<PINEnvelope> run(Session session) throws Exception
				{
					Query query = session.createQuery("select envelope " +
							"from com.rssl.phizic.auth.pin.PINEnvelope envelope " +
							"where envelope.state = '" + PINEnvelope.STATE_UPLOADED + "'" +
							(departmentId != null && departmentId.length() > 0 ? " and envelope.departmentId = '" + departmentId + "'" : ""));

					System.out.println(query.toString());
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}

		System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");

		for (PINEnvelope envelope : pinEnvelopes)
			System.out.println(envelope.getDepartmentId() + " " + envelope.getUserId());

		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

	}
}