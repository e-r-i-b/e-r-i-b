package com.rssl.phizic.config.loanclaim;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


@XmlType(name = "AgreeOnProcessPersonDataBean")
@XmlAccessorType(XmlAccessType.NONE)
@PlainOldJavaObject
class AgreeOnProcessPersonDataBean
{
	@XmlElement(required = true)
	private int id;

	@XmlElement(required = true)
	private Calendar effectiveDate;

	@XmlElement(required = true)
	private String conditionsText;

	int getId()
	{
		return id;
	}

	void setId(int id)
	{
		this.id = id;
	}

	Calendar getEffectiveDate()
	{
		return effectiveDate;
	}

	void setEffectiveDate(Calendar effectiveDate)
	{
		this.effectiveDate = effectiveDate;
	}

	String getConditionsText()
	{
		return conditionsText;
	}

	void setConditionsText(String conditionsText)
	{
		this.conditionsText = conditionsText;
	}
}

