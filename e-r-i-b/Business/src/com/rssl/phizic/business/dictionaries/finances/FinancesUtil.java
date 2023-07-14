package com.rssl.phizic.business.dictionaries.finances;

/**
 * @author lepihina
 * @ created 18.04.14
 * $Author$
 * $Revision$
 * ��������� ����� ��� ������ � ���������� ���
 */
public class FinancesUtil
{
	private static final MerchantCategoryCodeService merchantCategoryCodeService = new MerchantCategoryCodeService();

	/**
	 * ������ mcc-�����, ����������� � ���������, �������
	 * @param categoryExternalId - ������� ������������� ���������
	 * @return ������ �������
	 */
	public static String getMCCByCategoryAsString(String categoryExternalId)
	{
		return merchantCategoryCodeService.getByCategoryAsString(categoryExternalId);
	}
}
