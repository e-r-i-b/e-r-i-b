package com.rssl.ikfl.crediting;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Erkin
 * @ created 29.12.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отклик на предодобренное предложение
 */
@PlainOldJavaObject
@SuppressWarnings("PackageVisibleField")
public class Feedback implements Serializable
{
	/**
	 * Идентификатор отклика на предодобренное предложение [PK]
	 */
	long id;

	/**
	 * Фамилия
	 */
	String surName;

	/**
	 * Имя
	 */
	String firstName;

	/**
	 * Отчество
	 */
	String patrName;

	/**
	 * День рождения
	 */
	Calendar birthDay;

	/**
	 * Серия документа
	 */
	String docSeries;

	/**
	 * Номер документа
	 */
	String docNumber;

	/**
	 * Код предложения [1]
	 */
	String sourceCode;

	/**
	 * Код участника кампании АП [1]
	 */
	String campaignMemberId;

	/**
	 * тип отклика
	 */
	FeedbackType feedbackType;

	/**
	 * канал отклика
	 */
	OfferResponseChannel channel;

	/**
	 * Дата+время отклика
	 */
	Calendar feedbackTime;

	/**
	 * Дата+время окончания действия предложения [1]
	 */
	Calendar offerEndDate;

	@Override
	public String toString()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("surName", surName)
				.append("firstName",        firstName)
				.append("patrName",         patrName)
				.append("birthDay",         dateFormat.format(birthDay.getTime()))
				.append("docSeries",        docSeries)
				.append("docNumber",        docNumber)
				.append("sourceCode",       sourceCode)
				.append("campaignMemberId", campaignMemberId)
				.append("feedbackTime",       (feedbackTime != null) ? dateFormat.format(feedbackTime.getTime()) : null)
				.append("channel",        channel)
				.append("feedbackType", feedbackType)
				.append("offerEndDate",     dateFormat.format(offerEndDate.getTime()))
				.toString();
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getDocSeries()
	{
		return docSeries;
	}

	public void setDocSeries(String docSeries)
	{
		this.docSeries = docSeries;
	}

	public String getDocNumber()
	{
		return docNumber;
	}

	public void setDocNumber(String docNumber)
	{
		this.docNumber = docNumber;
	}

	public String getSourceCode()
	{
		return sourceCode;
	}

	public void setSourceCode(String sourceCode)
	{
		this.sourceCode = sourceCode;
	}

	public String getCampaignMemberId()
	{
		return campaignMemberId;
	}

	public void setCampaignMemberId(String campaignMemberId)
	{
		this.campaignMemberId = campaignMemberId;
	}

	public FeedbackType getFeedbackType()
	{
		return feedbackType;
	}

	public void setFeedbackType(FeedbackType feedbackType)
	{
		this.feedbackType = feedbackType;
	}

	public OfferResponseChannel getChannel()
	{
		return channel;
	}

	public void setChannel(OfferResponseChannel channel)
	{
		this.channel = channel;
	}

	public Calendar getFeedbackTime()
	{
		return feedbackTime;
	}

	public void setFeedbackTime(Calendar feedbackTime)
	{
		this.feedbackTime = feedbackTime;
	}


	public Calendar getOfferEndDate()
	{
		return offerEndDate;
	}

	public void setOfferEndDate(Calendar offerEndDate)
	{
		this.offerEndDate = offerEndDate;
	}
}
