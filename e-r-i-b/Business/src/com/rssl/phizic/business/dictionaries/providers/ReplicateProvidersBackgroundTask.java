package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.background.BackgroundTaskBase;
import com.rssl.phizic.business.operations.background.ReplicationTaskResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 * Фоновая задача репликации поставщков
 */
public class ReplicateProvidersBackgroundTask extends BackgroundTaskBase<ReplicationTaskResult>
{
	private static final String OPERATION_NAME = "com.rssl.phizic.operations.dictionaries.provider.ReplicateServiceProvidersOperation";

	private byte[] fileInternal; //содержимое файла для репликации
	private String billingIdsInternal; //идентфикаторы биллингов для репликации

	public ReplicateProvidersBackgroundTask()
	{
	}

	public ReplicateProvidersBackgroundTask(InputStream inputStream, List<Long> billingIds) throws BusinessException
	{
		try
		{
			fileInternal = IOUtils.toByteArray(inputStream);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		this.billingIdsInternal = StringUtils.join(billingIds, ';');
	}

	public void executed(ReplicationTaskResult result)
	{
		super.executed(result);
		setFileInternal(null);
	}

	/**
	 * @return идентфикаторы биллингов для репликации
	 */
	public List<Long> getBillingIds()
	{
		if (billingIdsInternal == null)
		{
			return null;
		}
		String[] strings = StringUtils.split(billingIdsInternal, ';');
		List<Long> result = new ArrayList<Long>(strings.length);
		for (String s : strings)
		{
			result.add(Long.valueOf(s));
		}
		return result;
	}

	public InputStream getFile()
	{
		return new ByteArrayInputStream(fileInternal);
	}

	//Методы для hibernate
	public byte[] getFileInternal()
	{
		return fileInternal;
	}

	public void setFileInternal(byte[] fileInternal)
	{
		this.fileInternal = fileInternal;
	}

	public String getBillingIdsInternal()
	{
		return billingIdsInternal;
	}

	public void setBillingIdsInternal(String billingIdsInternal)
	{
		this.billingIdsInternal = billingIdsInternal;
	}

	public String getOperationClassName()
	{
		return OPERATION_NAME;
	}
}
