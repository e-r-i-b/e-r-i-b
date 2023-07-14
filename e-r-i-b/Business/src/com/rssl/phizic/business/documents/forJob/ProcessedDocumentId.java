package com.rssl.phizic.business.documents.forJob;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 04.09.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������������� ����������, ������� ��� �������� ���������� �� ������� ������ �����
 */
public class ProcessedDocumentId
{
	private Long documentId; // ������������� ���������
	private String jobName;  // ��� �����, � ������� ���������� �������� ��������� ����������
	private Calendar addingDate;  //���� ���������� ��������� � �������

	public ProcessedDocumentId(Long documentId, String jobName)
	{
		this.documentId = documentId;
		this.jobName = jobName;
		this.addingDate = Calendar.getInstance();
	}

	public Long getDocumentId()
	{
		return documentId;
	}

	public void setDocumentId(Long documentId)
	{
		this.documentId = documentId;
	}

	public String getJobName()
	{
		return jobName;
	}

	public void setJobName(String jobName)
	{
		this.jobName = jobName;
	}

	public Calendar getAddingDate()
	{
		return addingDate;
	}

	public void setAddingDate(Calendar addingDate)
	{
		this.addingDate = addingDate;
	}
}
