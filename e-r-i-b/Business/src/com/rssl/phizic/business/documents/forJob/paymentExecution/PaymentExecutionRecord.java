package com.rssl.phizic.business.documents.forJob.paymentExecution;

import java.util.Calendar;

/**
 * Связка документа и джоба, в рамках которого выполняется обработка
 * @author gladishev
 * @ created 20.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class PaymentExecutionRecord
{
	private Long id; // идентификатор записи
	private Long documentId; // идентификатор документа
	private String jobName;  // имя джоба, в котором происходит обработка документа
	private Calendar addingDate;  //дата добавления документа в таблицу
	private Calendar nextProcessDate;  //дата следующей обработки документа
	private long counter;

	/**
	 * Дефолтный контруктор
	 */
	public PaymentExecutionRecord() {}

	/**
	 * Конструктор
	 * @param documentId - идентификатор документа
	 * @param jobName - имя джоба
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
