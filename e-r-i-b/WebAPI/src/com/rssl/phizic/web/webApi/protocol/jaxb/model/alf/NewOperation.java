package com.rssl.phizic.web.webApi.protocol.jaxb.model.alf;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author Balovtsev
 * @since 15.05.2014
 */
public class NewOperation
{
	private Long   newOperationCategoryId;
	private String newOperationTitle;
	private Double newOperationSum;

	/**
	 * Обязательный элемент
	 * @return Идентификатор категории
	 */
	@XmlElement(name = "newOperationCategoryId", required = true)
	public Long getNewOperationCategoryId()
	{
		return newOperationCategoryId;
	}

	/**
	 * Обязательный элемент
	 * @return Название новой операции
	 */
	@XmlElement(name = "newOperationTitle", required = true)
	public String getNewOperationTitle()
	{
		return newOperationTitle;
	}

	/**
	 * Обязательный элемент
	 * @return Сумма новой операции в валюте исходной операции
	 */
	@XmlElement(name = "newOperationSum", required = true)
	public Double getNewOperationSum()
	{
		return newOperationSum;
	}

	public void setNewOperationCategoryId(Long newOperationCategoryId)
	{
		this.newOperationCategoryId = newOperationCategoryId;
	}

	public void setNewOperationTitle(String newOperationTitle)
	{
		this.newOperationTitle = newOperationTitle;
	}

	public void setNewOperationSum(Double newOperationSum)
	{
		this.newOperationSum = newOperationSum;
	}
}
