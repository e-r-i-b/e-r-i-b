package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositEntityService;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.dictionaries.promoCodesDeposit.PromoCodeDeposit;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductRate;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ������� ��� ��������, �� ���� �� ������ �����������.
 * ���� ����������� ����� ������������, � ������ - ���������, ���������� ���������, �������� �� ������� �������� ������
 * @author EgorovaA
 * @ created 28.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AccountOpeningClaimCheckPensionHandler extends BusinessDocumentHandlerBase
{
	private static final DepositProductService depositProductService = new DepositProductService();
	private static final DepositEntityService depositEntityService = new DepositEntityService();

	private static final String BASIC_PATH = "/product/data/options/element[group/groupCode = '%s' and id = '%s' and currencyCode = '%s' and availToOpen = 'true' and segmentCode = 1 ";
	private static final String KNOWN_TARIFF_PATH = "and tariffPlanCode = '%s' ";
	private static final String UNKNOWN_TARIFF_PATH = "and (tariffPlanCode!=1 and tariffPlanCode!=2 and tariffPlanCode!=3) ";
	private static final String PENSION_GROUP_PARAMS_PATH = "and group/groupParams/pensionRate = 'true' ";

	private static final String DEPOSIT_PRODUCT_NOT_FOUND = "� ����������� ���������� ��������� ��� ������ ��� ������ � ������� ";
	private static final String ERROR_MESSAGE = "��� �������� �������� ������ ��� �������� ������. ���������� ������������ � ��������� ������ � �������� ������ ����� � ������ ���������� ������.";
	private static final String OPENING_TERMS_CHANGED = "������� ���������� ������� �� ���������� ������ ����������. ����������, �������� ����� ������.";


	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("�������� ��� ������� id=" + ((BusinessDocument) document).getId() + " (��������� AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		if (PersonHelper.isPensioner() && !accountOpeningClaim.getFromPersonalFinance())
		{
			boolean hasPensionRates = false;
			if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
				hasPensionRates = checkDepositEntityPensionRates(accountOpeningClaim);
			else
				hasPensionRates = hasPensionRates(accountOpeningClaim);

			if (hasPensionRates && !accountOpeningClaim.isPension())
				throw new DocumentLogicException(ERROR_MESSAGE);
			if (!hasPensionRates && accountOpeningClaim.isPension())
				throw new DocumentLogicException(OPENING_TERMS_CHANGED);
		}
	}

	/**
	 * �������� �� ������� ���������� ������ � ����������� ������
	 * @param claim - ����������� ������
	 * @return true, ���� ���� ���������� ������
	 * @throws DocumentException
	 */
	private Boolean hasPensionRates(AccountOpeningClaim claim) throws DocumentException
	{
		if (claim.isPromoDepositProduct())
			return false;

		DepositProduct depositProduct;
		try
		{
			depositProduct = depositProductService.findByProductId(claim.getAccountType());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		if (depositProduct == null)
			throw new DocumentException(DEPOSIT_PRODUCT_NOT_FOUND + claim.getAccountType());

		try
		{
			Element description = XmlHelper.parse(depositProduct.getDescription()).getDocumentElement();
			NodeList pensionTariffElements = XmlHelper.selectNodeList(description, getElementPath(claim));
			if (pensionTariffElements == null || pensionTariffElements.getLength() == 0)
				return false;
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
		return true;
	}

	/**
	 * ���������� ���� ��� ������ � ����������� ������ ��������� � ����������� �������� � ������ ���������� ������
	 * @param claim - ����������� ������
	 * @return ���� ��� ������ ��������� � �������� ������
	 */
	private String getElementPath(AccountOpeningClaim claim)
	{
		// ������� ����� ���� ������� ������
		String groupCode = claim.getAccountGroup();
		String subType = claim.getAccountSubType().toString();
		String currencyCode = claim.getCurrency().getCode();
		String basicPath = String.format(BASIC_PATH, groupCode, subType, currencyCode);

		// ��������� ����� ���� ��� ������� � ������ ��������� �����
		String tariffCode = claim.getDepositTariffPlanCode();
		String tariffCodePath = "";
		if (StringHelper.isNotEmpty(tariffCode) && StringHelper.equals(tariffCode, "0"))
			tariffCodePath = UNKNOWN_TARIFF_PATH;
		else
			tariffCodePath = String.format(KNOWN_TARIFF_PATH, tariffCode);

		// ��������� ����� ���� ��� ������� ���������� ������ ��� ��������� ��������, �������� ������
		String groupParamsPath = "";
		if (!StringHelper.equalsNullIgnore(groupCode, "-22"))
			groupParamsPath = PENSION_GROUP_PARAMS_PATH;

		return basicPath + tariffCodePath + groupParamsPath + "]";
	}

	/**
	 * �������� ���������� ������� � ���������, ���� �� � ���� ���������� ������
	 * @param claim - ����������� ������
	 * @return true, ���� ���������� ������ ����
	 * @throws DocumentException
	 */
	private Boolean checkDepositEntityPensionRates(AccountOpeningClaim claim) throws DocumentException
	{
		if (claim.isPromoDepositProduct())
			return false;

		Long depositType = claim.getAccountType();
		Long group = Long.valueOf(claim.getAccountGroup());
		group = -22L == group ? 0L : group;
		//�������� ������ ���������� ������, ��� ��� �� ������� �� ������� �����-����� �������
		List<PromoCodeDeposit> actualClientPromoCodes = new ArrayList<PromoCodeDeposit>();
		Long tariffCode = Long.valueOf(claim.getTarifPlanCodeType());
		String tb = claim.getAccountTb();

		try
		{
			DepositProductEntity depositProductEntity = depositEntityService.getDepositProductEntity(depositType, group, actualClientPromoCodes, TariffPlanHelper.getDictionaryTariffCodes(), true, tariffCode, false, tb);

			Map<String, Map<BigDecimal, Map<String, List<DepositProductRate>>>> rates = depositProductEntity.getDepositRates();
			for (String currency : rates.keySet())
			{
				Map<BigDecimal, Map<String, List<DepositProductRate>>> balanceRates = rates.get(currency);
				for (BigDecimal balance : balanceRates.keySet())
				{
					Map<String, List<DepositProductRate>> periodRates = balanceRates.get(balance);
					for (List<DepositProductRate> rate : periodRates.values())
					{
						if (rate.get(0).getSegment() == 1L)
							return true;
					}
				}
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		return false;
	}
}
