package com.rssl.phizic.operations.person;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.EmployeeInvoiceData;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.operations.ViewEntityOperation;

/**
 * @author tisov
 * @ created 28.05.15
 * @ $Author$
 * @ $Revision$
 * Операция просмотра инвойсов сотрудником
 */
public class EmployeeInvoicesListOperation extends PersonOperationBase implements ViewEntityOperation<EmployeeInvoiceData>
{

	private EmployeeInvoiceData invoiceData;           //данные об инвойсах

	public EmployeeInvoiceData getEntity() throws BusinessException, BusinessLogicException
	{
		return this.invoiceData;
	}

	/**
	 * Инициализация операции
	 * @param personId                 - ид персоны
	 * @param showAllCommonInvoices    - нужно ли отображать весь основной список инвойсов
	 * @param showAllDelayedInvoices   - нужно ли отображать весь список отложенных инвойсов
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(long personId, boolean showAllCommonInvoices, boolean showAllDelayedInvoices) throws BusinessException, BusinessLogicException
	{
		initializeViaPersonId(personId);
		this.invoiceData = new EmployeeInvoiceData(this.person, showAllCommonInvoices, showAllDelayedInvoices);
	}
}
