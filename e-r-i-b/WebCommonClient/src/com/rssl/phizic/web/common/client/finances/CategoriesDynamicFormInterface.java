package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 30.03.2012
 * @ $Author$
 * @ $Revision$
 * ��������� ����� �������� �������� ������� �� ����������: ����� ����� ��� ��������� � �������� ������
 */

public interface CategoriesDynamicFormInterface
{
	// ������ ��������� ��������� �� ������� � ������������ ���� ��������
	List<CardOperationCategory> getCategoriesList();
	void setCategoriesList(List<CardOperationCategory> categoriesList);

	// ID ��������� ���������
	public long getCurrentCategoryId();
	void setCurrentCategoryId(long currentCategoryId);

	// ������ ������
	public void setData(List data);
	List getData();
}
