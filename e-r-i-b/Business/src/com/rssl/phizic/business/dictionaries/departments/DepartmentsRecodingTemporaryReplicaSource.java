package com.rssl.phizic.business.dictionaries.departments;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.gate.dictionaries.NamedQueryReplicaSource;

/**
 * @author Balovtsev
 * @version 23.04.13 9:46
 */
public class DepartmentsRecodingTemporaryReplicaSource extends NamedQueryReplicaSource
{
	public DepartmentsRecodingTemporaryReplicaSource()
	{
		super("com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding.listOfAllFromTemporary");
	}

	@Override protected String getInstanceName()
	{
		return  MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
