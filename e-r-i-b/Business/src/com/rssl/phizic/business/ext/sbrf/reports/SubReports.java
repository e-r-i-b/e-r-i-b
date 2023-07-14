package com.rssl.phizic.business.ext.sbrf.reports;

import java.util.List;

/**
 * Класс, определяющий, состоит ли отчет из подотчетов (например, it-отчет по бизнес-параметрам)
 * Подотчет представляет собой независимый отчет, хранящийся в собственной таблице, но объединенный с одним
 * или несколькими такими же независимыми отчетами в один большой отчет, для которого нет собственной таблицы
 * в БД
 * @author Mescheryakova
 * @ created 13.09.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class SubReports
{
	/**
	 * Служит для возврата списка классов подотчетов данного отчета
	 * @return по умолчанию возвращает null - нет подотчетов
	 */
 	public List<ReportAdditionalInfo> getSubReportsList(ReportAbstract report)
	{
		return null;
	}
}
