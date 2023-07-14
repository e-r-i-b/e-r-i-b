package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.business.finances.CardOperation;

import java.util.List;

/**
 * @author lepihina
 * @ created 23.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class AsyncShowCategoryOperationsAbstractForm extends ShowCategoryOperationsAbstractForm implements ShowCategoryAbstractFormInterface
{
	private List<CardOperation> cardOperations;

	private int searchPage; //Номер страницы поиска
	private int resOnPage; //Количество строк на странице

	public List<CardOperation> getCardOperations()
	{
		return cardOperations;
	}

	public void setCardOperations(List<CardOperation> cardOperations)
	{
		this.cardOperations = cardOperations;
	}

	public int getSearchPage()
	{
		return searchPage;
	}

	public void setSearchPage(int searchPage)
	{
		this.searchPage = searchPage;
	}

	public int getResOnPage()
	{
		return resOnPage;
	}

	public void setResOnPage(int resOnPage)
	{
		this.resOnPage = resOnPage;
	}
}
