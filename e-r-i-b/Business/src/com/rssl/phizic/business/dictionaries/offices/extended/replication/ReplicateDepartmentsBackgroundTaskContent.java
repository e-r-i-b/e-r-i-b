package com.rssl.phizic.business.dictionaries.offices.extended.replication;

import java.sql.Blob;

/**
 * @author niculichev
 * @ created 22.09.13
 * @ $Author$
 * @ $Revision$
 */
public class ReplicateDepartmentsBackgroundTaskContent
{
	private Long replicateDepartmentTaskId;
	private Blob content;

	public ReplicateDepartmentsBackgroundTaskContent()
	{
	}

	public ReplicateDepartmentsBackgroundTaskContent(Long replicateDepartmentTaskId, Blob content)
	{
		this.replicateDepartmentTaskId = replicateDepartmentTaskId;
		this.content = content;
	}

	public Long getReplicateDepartmentTaskId()
	{
		return replicateDepartmentTaskId;
	}

	public void setReplicateDepartmentTaskId(Long replicateDepartmentTaskId)
	{
		this.replicateDepartmentTaskId = replicateDepartmentTaskId;
	}

	public Blob getContent()
	{
		return content;
	}

	public void setContent(Blob content)
	{
		this.content = content;
	}
}
