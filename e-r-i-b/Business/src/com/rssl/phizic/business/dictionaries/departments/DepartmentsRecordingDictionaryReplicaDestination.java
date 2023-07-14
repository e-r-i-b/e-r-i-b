package com.rssl.phizic.business.dictionaries.departments;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.dictionaries.synchronization.MultiBlockDictionaryRecord;
import org.hibernate.Session;

import java.util.List;

/**
 * @author Balovtsev
 * @version 23.04.13 10:41
 */
public class DepartmentsRecordingDictionaryReplicaDestination extends PreClearReplicaDestinationBase
{
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();

	@Override
	protected String getDestinationEntityName()
	{
		return "com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding";
	}

	@Override
	protected String getDestinationClearQuery()
	{
		return "com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.clearDictionary";
	}

	@Override
	protected void beforeAdding()
	{
		Session session = getSession();

		addChangesToLog(session, new DepartmentsRecoding(), ChangeType.delete);

		List<Department> departments = session.getNamedQuery("com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.findDepartmentsIds")
				  	                          .list();

		for (Department department : departments)
		{
			department.setCreditCardOffice(false);
			session.update(department);
			addChangesToLog(session, department, ChangeType.update);
		}

		departments = getSession().getNamedQuery("com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.findDepartmentsInExceptionTable")
					  	          .list();

		for (Department department : departments)
		{
			department.setCreditCardOffice(false);
			session.update(department);
			addChangesToLog(session, department, ChangeType.update);
		}

		super.beforeAdding();
	}

	protected void addChangesToLog(Session session, MultiBlockDictionaryRecord newValue, ChangeType changeType)
	{
		dictionaryRecordChangeInfoService.addChangesToLog(session, newValue, changeType);
	}
}
