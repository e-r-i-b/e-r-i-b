package com.rssl.phizic.business.documents.forJob.paymentExecution;

import java.util.Calendar;

/**
 * ������ ��������� � �����, � ������ �������� ����������� ���������
 * @author gladishev
 * @ created 20.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class PaymentExecutionRecord
{
	private Long id; // ������������� ������
	private Long documentId; // ������������� ���������
	private String jobName;  // ��� �����, � ������� ���������� ��������� ���������
	private Calendar addingDate;  //���� ���������� ��������� � �������
	private Calendar nextProcessDate;  //���� ��������� ��������� ���������
	private long counter;

	/**
	 * ��������� ����������
	 */
	public PaymentExecutionRecord() {}

	/**
	 * �����������
	 * @param documentId - ������������� ���������
	 * @param jobName - ��� �����
	 */
	public PaymentExecutionRecord(Long documentId, String jobName, Calendar nextProcessDate)
	{
		this.documentId = documentId;
		this.jobName = jobName;
		this.addingDate = Calendar.getInstance();
		this.nextProcessDate = nextProcessDate;
		this.counter = 1;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public Calendar getNextProcessDate()
	{
		return nextProcessDate;
	}

	public void setNextProcessDate(Calendar nextProcessDate)
	{
		this.nextProcessDate = nextProcessDate;
	}

	public long getCounter()
	{
		return counter;
	}

	public void setCounter(long counter)
	{
		this.counter = counter;
	}
}
