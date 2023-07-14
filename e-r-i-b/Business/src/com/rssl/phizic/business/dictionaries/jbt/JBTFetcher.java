package com.rssl.phizic.business.dictionaries.jbt;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 21.06.2010
 * @ $Author$
 * @ $Revision$
 */
public interface JBTFetcher
{
	/**
	 * �������������
	 * @param providerIds - ���������� �����
	 * @param state - ��� ��������
	 * @param billingId - ����������� �������
	 * @throws BusinessException
	 */
	void initialize(String[] providerIds, UnloadingState state, String billingId) throws BusinessException;

	/**
	 * @return ���� ��� ����������� �������, ���� nalog (��� ��������� �����������)
	 */
	String getSourceName() throws BusinessException;

	/**
	 * ������ ��������, �� ������� ���������� �������� ���
	 * @param dateFrom - ��������� ����
	 * @param dateTo   - �������� ����
	 * @return - ������ ��������
	 */
	List<? extends AbstractPaymentDocument> getSource(final Date dateFrom, final Date dateTo) throws BusinessException;

	/**
	 * ��������� ����������� ������ �������� ���
	 */
	public Map<String, StringBuilder> downloadArhive(List<? extends AbstractPaymentDocument> payments, String[] date) throws BusinessException, IOException, DocumentException;
}
