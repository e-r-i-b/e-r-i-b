package com.rssl.phizic.operations.loanclaim;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.ClientConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Nady
 * @ created 14.01.2014
 * @ $Author$
 * @ $Revision$
 * Операция для расширенных заявок на кредит
 */
public class ExtendedLoanClaimListOperation extends OperationBase implements ListEntitiesOperation
{
	private static final String NOT_SET_LOGIN = "Не указан логин";
	private static final BusinessDocumentService service = new BusinessDocumentService();

	private List<ExtendedLoanClaim> listLoanClaims;
	private boolean haveNotShowClaims;

	/**
	 * Инициализациия операции
	 * @param login Логин пользователя
	 * @throws BusinessException
	 */
	public void init(Login login) throws BusinessException
	{
		if (login == null)
		{
			throw new IllegalArgumentException(NOT_SET_LOGIN);
		}
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		ClientConfig clientConfig = ConfigFactory.getConfig(ClientConfig.class);
		int limit = (int)clientConfig.getClaimsLimit();
		int maxResult = limit + 1;

		List<ExtendedLoanClaim> bufList = service.findExtendedLoanClaimByLogin(login, config.getPeriodRequestLoanStatus(), maxResult);

		haveNotShowClaims = bufList.size() == maxResult;

		listLoanClaims = new ArrayList<ExtendedLoanClaim>(limit);
		Iterator<ExtendedLoanClaim> iterator = bufList.iterator();

		while (iterator.hasNext() && limit != 0)
		{
			listLoanClaims.add(iterator.next());
			limit--;
		}
	}

	/**
	 * Получение последних N расширенных кредитных заявок. N - параметер системы.
	 * @return Список расширенных кредитных заявок
	 */
	public List<ExtendedLoanClaim> getListLoanClaims()
	{
		return listLoanClaims;
	}

	/**
	 * Получение списка заявок за период. Период является настройкой системы.
	 * @param login Логин пользователя
	 * @return Список расширенных кредитных заявок
	 * @throws BusinessException
	 */
	public List<ExtendedLoanClaim> getAllLoanClaims(Login login) throws BusinessException
	{
		if (login == null)
		{
			throw new IllegalArgumentException(NOT_SET_LOGIN);
		}
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		return service.findExtendedLoanClaimByLogin(login, config.getPeriodRequestLoanStatus());
	}

	/**
	 * Есть ли не показанные заявки
	 * @return Да, если есть. Нет, впротивном случае.
	 */
	public boolean isHaveNotShowClaims()
	{
		return haveNotShowClaims;
	}
}
