package com.rssl.phizic.business.template.offer;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Balovtsev
 * @since  03.06.2015.
 */
public class CreditOfferTemplate extends MultiBlockDictionaryRecordBase implements Serializable
{
	/**
	 * Переменные, использующиеся в тексте оферты
	 */
	public static final String ATTRIBUTE_FIO = "<fio/>";
	public static final String ATTRIBUTE_PASSPORT_SERIES = "<dulSeries/>";
	public static final String ATTRIBUTE_PASSPORT_NUMBER = "<dulNumber/>";
	public static final String ATTRIBUTE_PASSPORT_ISSUED_BY = "<dulLocation/>";
	public static final String ATTRIBUTE_PASSPORT_ISSUED_DATE = "<dulIssue/>";
	public static final String ATTRIBUTE_LOAN_COST = "<psk/>";
	public static final String ATTRIBUTE_AMOUNT = "<amount/>";
	public static final String ATTRIBUTE_PERIOD = "<duration/>";
	public static final String ATTRIBUTE_INTEREST_RATE = "<interest/>";
	public static final String ATTRIBUTE_ANNUITY_PAYMENT = "<order/>";
	public static final String ATTRIBUTE_ISSUE_ACCOUNT = "<issuance/>";
	public static final String ATTRIBUTE_BORROWER = "<borrower/>";
	public static final String ATTRIBUTE_REGISTRATION = "<registration/>";
	
	private Long                    id;
	private Calendar                from;
	private Calendar                to;
	private String                  text;
	private Status                  status;
	private CreditOfferTemplateType type;

	public CreditOfferTemplate()
	{
	}

	public CreditOfferTemplate(Long id, Calendar from, Calendar to, String text, Status status, CreditOfferTemplateType type)
	{
		this.id     = id;
		this.from   = from;
		this.to     = to;
		this.text   = text;
		this.status = status;
		this.type   = type;
	}

	public CreditOfferTemplate(CreditOfferTemplate template)
	{
		this(template.getId(),
			 template.getFrom(),
			 template.getTo(),
			 template.getText(),
			 template.getStatus(),
			 template.getType());
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Calendar getFrom()
	{
		return from;
	}

	public void setFrom(Calendar from)
	{
		this.from = from;
	}

	public Calendar getTo()
	{
		return to;
	}

	public void setTo(Calendar to)
	{
		this.to = to;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public CreditOfferTemplateType getType()
	{
		return type;
	}

	public void setType(CreditOfferTemplateType type)
	{
		this.type = type;
	}
}
