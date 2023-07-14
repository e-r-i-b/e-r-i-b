package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.XmlCalendarAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * @author Balovtsev
 * @since 14.05.2014
 */
@XmlRootElement(name = "body")
public class AddAlfOperationRequestBody extends SimpleRequestBody
{
	private String   operationName;
	private Double   operationAmount;
	private Calendar operationDate;
	private Long     operationCategoryId;

	/**
	 * ������������ �������
	 * @return �������� ��������
	 */
	@XmlElement(name = "operationName", required = true)
	public String getOperationName()
	{
		return operationName;
	}

	/**
	 * ������������ �������
	 * @return ����� ��������
	 */
	@XmlElement(name = "operationAmount", required = true)
	public Double getOperationAmount()
	{
		return operationAmount;
	}

	/**
	 * ������������ �������
	 * @return ���� ��������
	 */
	@XmlJavaTypeAdapter(XmlCalendarAdapter.class)
	@XmlElement(name = "operationDate", required = true)
	public Calendar getOperationDate()
	{
		return operationDate;
	}

	/**
	 * ������������ �������
	 * @return ������������� ���������
	 */
	@XmlElement(name = "operationCategoryId", required = true)
	public Long getOperationCategoryId()
	{
		return operationCategoryId;
	}

	public void setOperationName(String operationName)
	{
		this.operationName = operationName;
	}

	public void setOperationAmount(Double operationAmount)
	{
		this.operationAmount = operationAmount;
	}

	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	public void setOperationCategoryId(Long operationCategoryId)
	{
		this.operationCategoryId = operationCategoryId;
	}
}
