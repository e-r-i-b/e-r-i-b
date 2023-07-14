package com.rssl.phizic.operations.account;

import com.rssl.phizic.operations.userprofile.DeviceType;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.operations.userprofile.AccountsAvailableSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * �������� ��������� ��������� ��������� ������� � ���
 * @author gladishev
 * @ created 14.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class SetupProductsRCSViewOperation extends SetupProductsSystemViewOperation
{
	/**
	 * ���������� ����� �������� �������� ��������� ��������� ��� ������
	 * @param selectedAccountESIds ������ ��������������� (id) ������� ������
	 * @param selectedCardESIds ������ ��������������� (id) ������� ����
	 * @param selectedLoanESIds ������ ��������������� (id) ������� ��������
	 * @param selectedIMAccountESIds ������ ��������������� (id) ������� ���
	 * @throws BusinessException ������ ��� ������ � ������-���������
	 */
	public void updateResourcesSettings(String[] selectedAccountESIds,
	                                     String[] selectedCardESIds,
	                                     String[] selectedIMAccountESIds,
	                                     String[] selectedLoanESIds) throws BusinessException
	{
		updateAccountSettingsInES(selectedAccountESIds);
		updateCardSettingsInES(selectedCardESIds);
		updateLoanSettingsInES(selectedLoanESIds);
		updateIMAccountSettingsInES(selectedIMAccountESIds);
	}

	/**
	 * ���������� ����� �������� �������� ��������� � ����������� ���������������� ��� ������
	 * @param selectedEsIds ������ ��������������� (id) ������
	 * @throws BusinessException
	 */
	public void updateAccountSettingsInES(String[] ... selectedEsIds) throws BusinessException
	{
		for (AccountLink link : accounts)
		{
			Long currentId = link.getId();
			Boolean esState = link.getShowInATM();

			if (esState != null)
			{
			    List<String> stringEs = new ArrayList<String>();
				for (int i = 0; i < selectedEsIds.length; i++)
					Collections.addAll(stringEs, selectedEsIds[i]);

				Boolean newEsState = false;
				for (String accountId : stringEs)
				{
					if (currentId.equals(Long.valueOf(accountId)))
					{
						newEsState = true;
						break;
					}
				}

				if (esState^newEsState)
				{
					link.setShowInATM(newEsState);
					addChangedResource(link);
				}
			}
		}
	}

	/**
	 * ���������� ����� �������� �������� ��������� � ����������� ���������������� ��� ���
	 * @param selectedEsIds ������ ��������������� (id) ���
	 * @throws BusinessException
	 */
	private void updateIMAccountSettingsInES(String[] selectedEsIds) throws BusinessException
	{
		for (IMAccountLink link : imAccounts)
		{
			Long currentId = link.getId();
			Boolean esState = link.getShowInATM();

			if (esState != null)
			{
			    List<String> stringEs = new ArrayList<String>();
				for (int i = 0; i < selectedEsIds.length; i++)
					Collections.addAll(stringEs, selectedEsIds[i]);

				Boolean newEsState = false;
				for (String accountId : stringEs)
				{
					if (currentId.equals(Long.valueOf(accountId)))
					{
						newEsState = true;
						break;
					}
				}

				if (esState^newEsState)
				{
					link.setShowInATM(newEsState);
					addChangedResource(link);
				}
			}
		}
	}

	/**
	 * ���������� ����� �������� �������� ��������� � ����������� ���������������� ��� ��������
	 * @param selectedEsIds ������ ��������������� (id) ��������
	 * @throws BusinessException
	 */
	private void updateLoanSettingsInES(String[] selectedEsIds) throws BusinessException
	{
		for (LoanLink link : loans)
		{
			Long currentId = link.getId();
			Boolean esState = link.getShowInATM();

			if (esState != null)
			{
			    List<String> stringEs = new ArrayList<String>();
				for (int i = 0; i < selectedEsIds.length; i++)
					Collections.addAll(stringEs, selectedEsIds[i]);

				Boolean newEsState = false;
				for (String accountId : stringEs)
				{
					if (currentId.equals(Long.valueOf(accountId)))
					{
						newEsState = true;
						break;
					}
				}

				if (esState^newEsState)
				{
					link.setShowInATM(newEsState);
					addChangedResource(link);
				}
			}
		}
	}

	/**
	 * ���������� ����� �������� �������� ��������� � ����������� ���������������� ��� ����
	 * @param selectedEsIds ������ ��������������� (id) ����
	 * @throws BusinessException
	 */
	private void updateCardSettingsInES(String[] selectedEsIds) throws BusinessException
	{
		for (CardLink link : cards)
		{
			Long currentId = link.getId();
			Boolean esState = link.getShowInATM();

			if (esState != null)
			{
			    List<String> stringEs = new ArrayList<String>();
				for (int i = 0; i < selectedEsIds.length; i++)
					Collections.addAll(stringEs, selectedEsIds[i]);

				Boolean newEsState = false;
				for (String accountId : stringEs)
				{
					if (currentId.equals(Long.valueOf(accountId)))
					{
						newEsState = true;
						break;
					}
				}

				if (esState^newEsState)
				{
					link.setShowInATM(newEsState);
					addChangedResource(link);
				}
			}
		}
	}

	public ConfirmableObject getConfirmableObject()
	{
		if (confirmableObject == null)
			confirmableObject = new AccountsAvailableSettings(
					(List<AccountLink>)changedResources.get(AccountLink.class),
					(List<CardLink>)changedResources.get(CardLink.class),
					(List<LoanLink>)changedResources.get(LoanLink.class),
					(List<IMAccountLink>)changedResources.get(IMAccountLink.class),
					DeviceType.ATM);

		return confirmableObject;
	}
}
