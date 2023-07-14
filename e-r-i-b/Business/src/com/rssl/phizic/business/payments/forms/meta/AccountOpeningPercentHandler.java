package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * ������� ��� ����� ���������� ������ ������������ �������� ������ ��� ������������ �������
 * @author Pankin
 * @ created 20.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningPercentHandler extends BusinessDocumentHandlerBase
{
	private static final String NOT_FOUND_RATE = "�� ��������� ���� �������� ���������� ������� �����. " +
			"����������, ����������� ������������ � ��������� ������, ������� ������ �������.";
	private static final String CHANGE_RATE_MESSAGE = "�������� ��������, ���������� �������� � ���������� �����!";

	private static final DepositProductService depositProductService = new DepositProductService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + document.getId() +
					" (��������� AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		// ������������� ���������� ������ ������ ��� ������� ������� ��� ������������ �������
		if (accountOpeningClaim.isWithMinimumBalance() || !accountOpeningClaim.isNeedInitialFee())
			return;

		// ���� �������� �� ������������ ��� ������ ������ ����� ����������, �� ��� �� ��������, ������ ������������� �� ����
		if (!accountOpeningClaim.isConvertion() || accountOpeningClaim.getInputSumType() == InputSumType.DESTINATION)
			return;

		Map<String, String> depInfo = null;
		String depositTariffPlanCode = accountOpeningClaim.getDepositTariffPlanCode();
		String clientTariffCode = accountOpeningClaim.getTarifPlanCodeType();
		Long depositType = accountOpeningClaim.getAccountType();
		String accountGroup = accountOpeningClaim.getAccountGroup();

		try
		{
			BusinessDocumentOwner documentOwner = accountOpeningClaim.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("�������� � �������� ������ �� ��������������");

			if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
			{
				Long depositProductGroup = StringHelper.isEmpty(accountGroup) ? 0L : Long.valueOf(accountGroup);
				DepositProductEntity depositEntity = depositProductService.findDepositEntity(depositType, depositProductGroup);
				if (depositEntity == null)
					throw new DocumentException("� ����������� ���������� ��������� ��� ������ ��� ������ � ������� " +
							accountOpeningClaim.getAccountType());

				depInfo = DepositProductHelper.calculateEntityRate(accountOpeningClaim.getOpeningDate(),
						accountOpeningClaim.getPeriod(), accountOpeningClaim.getDestinationCurrency().getCode(),
						accountOpeningClaim.getDestinationAmount().getDecimal(), accountOpeningClaim.isPension(),
						depositType, depositTariffPlanCode, clientTariffCode, accountGroup,
						accountOpeningClaim.getSegment());
			}
			else
			{
				DepositProduct depositProduct = depositProductService.findByProductId(accountOpeningClaim.getAccountType());

				if (depositProduct == null)
					throw new DocumentException("� ����������� ���������� ��������� ��� ������ ��� ������ � ������� " +
							accountOpeningClaim.getAccountType());

				// ������������ ���������� ������, ����� ���� � ������ ������������ ���. ������
				Element description = XmlHelper.parse(depositProduct.getDescription()).getDocumentElement();
				depInfo = DepositProductHelper.calculateRate(accountOpeningClaim.getOpeningDate(),
						accountOpeningClaim.getPeriod(), accountOpeningClaim.getDestinationCurrency().getCode(),
						accountOpeningClaim.getDestinationAmount().getDecimal(), accountOpeningClaim.isPension(),
						description, depositTariffPlanCode, clientTariffCode, accountGroup,
						accountOpeningClaim.getSegment(), accountOpeningClaim.isPromoDepositProduct());

				processVerification(accountOpeningClaim, stateMachineEvent, depInfo);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}

	private void processVerification(AccountOpeningClaim accountOpeningClaim, StateMachineEvent stateMachineEvent, Map<String, String> depInfo) throws DocumentException, DocumentLogicException
	{
		// �� ���������� ���������� �� ������ ���������� ������
		if (depInfo.containsKey(DepositProductHelper.CALC_RATE_ERROR))
			throw new DocumentLogicException(NOT_FOUND_RATE);

		if (!accountOpeningClaim.getAttribute(AccountOpeningClaim.INTEREST_RATE).getStringValue().equals(
				depInfo.get(AccountOpeningClaim.INTEREST_RATE)))
		{
			// ��������� ���� ������� ������������� �������
			for (String key : depInfo.keySet())
			{
				accountOpeningClaim.setTermsPosition(key, depInfo.get(key));
			}

			try
			{
				// ��������� �������� � ����, ����� �� �������� ������������� ������
				businessDocumentService.addOrUpdate(accountOpeningClaim);
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}

			stateMachineEvent.addMessage(CHANGE_RATE_MESSAGE);
			stateMachineEvent.registerChangedField("interestRate");
		}
	}
}
