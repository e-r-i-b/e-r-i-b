package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.operations.finances.CardOperationDescription;

import java.util.List;

/**
 * @author Mescheryakova
 * @ created 02.04.2012
 * @ $Author$
 * @ $Revision$
 * ��������� ����� ��� ��������� ������� �� ��������� � ������� "������ ��������": ����� ����� ��� ��������� � �������� ������
 */

public interface ShowCategoryAbstractFormInterface
{
	/**
	 * Id ���������, ������� �� ������� ������� � �����������
	 */
	Long getCategoryId();
	void setCategoryId(Long categoryId);
	/**
	 * @return ���������, ������� �� ������� ������� � �����������
	 */
	CardOperationCategory getCategory();
	void setCategory(CardOperationCategory category);

	void setData(List<CardOperationDescription> cardOperationDescriptions);
}
