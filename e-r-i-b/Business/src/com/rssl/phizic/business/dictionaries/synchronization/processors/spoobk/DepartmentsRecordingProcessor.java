package com.rssl.phizic.business.dictionaries.synchronization.processors.spoobk;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding;
import com.rssl.phizic.business.dictionaries.departments.DepartmentsRecordingService;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Jatsky
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 */

public class DepartmentsRecordingProcessor extends ProcessorBase<DepartmentsRecoding>
{
	private static final DepartmentsRecordingService service = new DepartmentsRecordingService();

	@Override protected Class<DepartmentsRecoding> getEntityClass()
	{
		return DepartmentsRecoding.class;
	}

	protected DepartmentsRecoding getEntity(String uuid) throws BusinessException
	{
		String[] code = uuid.split("\\||:");
		try
		{
			String tb = null;
			String osb = null;
			String office = null;

			if (code.length > 0)
				tb = code[0];
			if (code.length > 1)
				osb = code[1];
			if (code.length > 2)
				office = code[2];

			return service.getDepartmentsRecodingByEribCodesInternal(tb, osb, office);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	@Override protected DepartmentsRecoding getNewEntity()
	{
		return new DepartmentsRecoding();
	}

	@Override protected void update(DepartmentsRecoding source, DepartmentsRecoding destination) throws BusinessException
	{

		destination.setTbErib(source.getTbErib());
		destination.setOsbErib(source.getOsbErib());
		destination.setOfficeErib(source.getOfficeErib());
		destination.setTbSpoobk2(source.getTbSpoobk2());
		destination.setOsbSpoobk2(source.getOsbSpoobk2());
		destination.setOfficeSpoobk2(source.getOfficeSpoobk2());
		destination.setDespatch(source.getDespatch());
		destination.setDateSuc(source.getDateSuc());
	}

	@Override protected void doRemove(String uid) throws BusinessException, BusinessLogicException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.clearDictionary");
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
