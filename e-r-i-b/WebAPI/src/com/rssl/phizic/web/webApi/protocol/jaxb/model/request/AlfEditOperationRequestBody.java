package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.NewOperation;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * @author Balovtsev
 * @since 15.05.2014
 */
public class AlfEditOperationRequestBody extends SimpleRequestBody
{
	private Long               operationId;
	private Long               operationCategoryId;
	private String             operationTitle;
	private List<NewOperation> newOperation;

	/**
	 * Обязательный элемент
	 * @return Идентификатор операции
	 */
	@XmlElement(name = "operationId", required = true)
	public Long getOperationId()
	{
		return operationId;
	}

	/**
	 * Обязательный элемент
	 * @return Идентификатор категории
	 */
	@XmlElement(name = "operationCategoryId", required = true)
	public Long getOperationCategoryId()
	{
		return operationCategoryId;
	}

	/**
	 * Обязательный элемент
	 * @return Название операции
	 */
	@XmlElement(name = "operationTitle", required = true)
	public String getOperationTitle()
	{
		return operationTitle;
	}

	/**
	 * @return Список новых операций
	 */
	@XmlElementWrapper(name = "newOperations", required = false)
	@XmlElement(name = "newOperation", required = true)
	public List<NewOperation> getNewOperation()
	{
		return newOperation;
	}

	public void setOperationId(Long operationId)
	{
		this.operationId = operationId;
	}

	public void setOperationCategoryId(Long operationCategoryId)
	{
		this.operationCategoryId = operationCategoryId;
	}

	public void setOperationTitle(String operationTitle)
	{
		this.operationTitle = operationTitle;
	}

	public void setNewOperation(List<NewOperation> newOperation)
	{
		this.newOperation = newOperation;
	}
}
