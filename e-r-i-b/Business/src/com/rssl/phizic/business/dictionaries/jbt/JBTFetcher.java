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
	 * Инициализация
	 * @param providerIds - поставщики услуг
	 * @param state - вид выгрузки
	 * @param billingId - биллинговая система
	 * @throws BusinessException
	 */
	void initialize(String[] providerIds, UnloadingState state, String billingId) throws BusinessException;

	/**
	 * @return либо код биллинговой системы, либо nalog (для налоговых получателей)
	 */
	String getSourceName() throws BusinessException;

	/**
	 * Список платежей, по которым производим выгрузку жбт
	 * @param dateFrom - начальная дата
	 * @param dateTo   - конечная дата
	 * @return - список платежей
	 */
	List<? extends AbstractPaymentDocument> getSource(final Date dateFrom, final Date dateTo) throws BusinessException;

	/**
	 * Получение заполненной записи выгрузки жбт
	 */
	public Map<String, StringBuilder> downloadArhive(List<? extends AbstractPaymentDocument> payments, String[] date) throws BusinessException, IOException, DocumentException;
}
