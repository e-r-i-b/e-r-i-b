package com.rssl.phizic.business.documents.strategies.monitoring;

import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * Стратегия выполнения документа, проверка на мошейничество
 *
 * @author khudyakov
 * @ created 21.05.15
 * @ $Author$
 * @ $Revision$
 */
public interface FraudMonitoringDocumentStrategy extends ProcessDocumentStrategy
{
	/**
	 * @return получить тип взаимодействия с ВС ФМ
	 */
	InteractionType getInteractionType();

	/**
	 * @return получить стадию проверки
	 */
	PhaseType getPhaseType();
}
