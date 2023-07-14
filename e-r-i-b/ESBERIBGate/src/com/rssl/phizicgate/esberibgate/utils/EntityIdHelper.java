package com.rssl.phizicgate.esberibgate.utils;

import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.autosubscription.AutoSubscription;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * @author krenev
 * @ created 29.09.2010
 * @ $Author$
 * @ $Revision$
 * ��������� ����� ��� ��������� � ���������� ��������� ���������� � ��������������� ���������
 */
public class EntityIdHelper
{
	/**
	 * �������� ��������� ������������� ������������ ������������� �����  
	 * @param account ����
	 * @return CardOrAccountCompositeId
	 */
	public static CardOrAccountCompositeId getAccountCompositeId(Account account)
	{
		return getCardOrAccountCompositeId(account.getId());
	}

	/**
	 * �������� ��������� ������������� ������������ ������������� �����
	 * @param card �����
	 * @return CardOrAccountCompositeId
	 */
	public static CardOrAccountCompositeId getCardCompositeId(Card card)
	{
		return getCardOrAccountCompositeId(card.getId());
	}

	/**
	 * �������� ��������� ������������� ������������ ������������� �������
	 * @param loan - ������
	 * @return LoanCompositeId
	 */
	public static LoanCompositeId getLoanCompositeId(Loan loan)
	{
		return getLoanCompositeId(loan.getId());
	}

	/**
	 * �������� ��������� ������������� ������������ ������������� �������
	 * @param loanId ������������ �������
	 * @return LoanCompositeId
	 */
	public static LoanCompositeId getLoanCompositeId(String loanId)
	{
		return new LoanCompositeId(loanId);
	}

	/**
	 * �������� ��������� ������������� ������������ ������������� ����������� ���������
	 * @param longOfferId ������������� ���������
	 * @return LongOfferCompositeId
	 */
	public static LongOfferCompositeId getLongOfferCompositeId(String longOfferId)
	{
		return new LongOfferCompositeId(longOfferId);
	}

	/**
	 * �������� ��������� ������������� ������������ ������������� ����������� ���������
	 * @param longOffer ���������
	 * @return LongOfferCompositeId
	 */
	public static LongOfferCompositeId getLongOfferCompositeId(LongOffer longOffer)
	{
		return new LongOfferCompositeId(longOffer.getExternalId());
	}

	/**
	 * �������� ��������� ������������� ������������ ��������������
	 * @param compositeId ������������
	 * @return EntityCompositeId
	 */
	public static EntityCompositeId getCommonCompositeId(String compositeId)
	{
		return new EntityCompositeId(compositeId);
	}

	/**
	 * �������� ��������� ������������� ������������ �������������� ����� ��� �����
	 * @param compositId �������������
	 * @return CardOrAccountCompositeId
	 */
	public static CardOrAccountCompositeId getCardOrAccountCompositeId(String compositId)
	{
		return new CardOrAccountCompositeId(compositId);
	}

	/**
	 * ���������� ��������� ������������� ������������ �������������� ������� �� ��������.
	 * @param compositeId �������������.
	 * @return AutoSubscriptionPaymentCompositeId.
	 */
	public static AutoSubscriptionPaymentCompositeId getAutoSubscriptionPaymentCompositeId(String compositeId)
	{
		return new AutoSubscriptionPaymentCompositeId(compositeId);
	}

	/**
	 * �������� ��������� ������������� ���������� �������������� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId>
	 * @param entityId - ������������ �������� �� ������� �������
	 * @param systemId - ������������� �������-��������� ��������
	 * @param rbBrchId - ����� ���, �������� �������
	 * @param loginId - ������������� ��������� �������� � ����
	 * @return ��������� ������������� ��������������
	 */
	public static String createCompositeId(String entityId, String systemId, String rbBrchId, Long loginId)
	{
		EntityCompositeId compositeId = new EntityCompositeId(entityId, systemId, rbBrchId, loginId);
		return compositeId.toString();
	}

	/**
	 * �������� ��������� ������������� ���������� �������������� ����� ��� �����
	 * <id ��������>^<systemId>^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>
	 * @param entityId ������������ �������� �� ������� �������
	 * @param systemId ������������� �������-��������� ��������
	 * @param rbBrchId ����� ���, �������� �������
	 * @param loginId  ������������� ��������� �������� � ����
	 * @param regionId ����� ��������(��)
	 * @param branchId ����� �������(���)
	 * @param agencyId ����� �������������(���)
	 * @return ��������� ������������� ��������������
	 */
	public static String createCardOrAccountCompositeId(String entityId, String systemId, String rbBrchId, Long loginId, String regionId, String branchId, String agencyId)
	{
		CardOrAccountCompositeId compositeId = new CardOrAccountCompositeId(entityId, systemId, rbBrchId, loginId, regionId, branchId, agencyId);
		return compositeId.toString();
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ����� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId> 
	 * @param depAcctRec ���������� � �����, ����������� �� ����
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ��������������
	 */
	public static String createAccountCompositeId(DepAcctRec_Type depAcctRec, Long loginId)
	{
		BankInfo_Type bankInfo = depAcctRec.getBankInfo();
		return createCardOrAccountCompositeId(depAcctRec.getDepAcctId().getAcctId(), depAcctRec.getDepAcctId().getSystemId(),
				bankInfo.getRbBrchId(), loginId, bankInfo.getRegionId(), bankInfo.getBranchId(), bankInfo.getAgencyId());
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ����� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>
	 * @param detailAcctInfo ���������� � �����, ����������� �� ����
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ��������������
	 */
	public static String createAccountCompositeId(DetailAcctInfo_Type detailAcctInfo, Long loginId)
	{
		BankInfo_Type bankInfo = detailAcctInfo.getDepAccInfo().getBankInfo();
		return createCardOrAccountCompositeId(detailAcctInfo.getDepAcctId().getAcctId(), detailAcctInfo.getDepAcctId().getSystemId(),
				bankInfo == null ? "" : bankInfo.getRbBrchId(),
				loginId,
				bankInfo == null ? "" : bankInfo.getRegionId(),
				bankInfo == null ? "" : bankInfo.getBranchId(),
				bankInfo == null ? "" : bankInfo.getAgencyId());
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ����� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId>
	 * rbBrchId ������� �� CardAcctId_Type (������������� ���������� CRDWI)
	 * @param cardAcctId ���������� � �����, ����������� �� ����
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ���������� �������������� �����
	 */
	public static String createCardCompositeId(CardAcctId_Type cardAcctId, Long loginId)
	{
		//SystemId ����� �� ������ � CRDWI
		BankInfo_Type bankInfo = cardAcctId.getBankInfo();
		return createCardOrAccountCompositeId(cardAcctId.getCardNum(),
				StringHelper.isEmpty(cardAcctId.getSystemId()) ? "" : cardAcctId.getSystemId(),
				bankInfo == null ? "" : bankInfo.getRbBrchId(),
				loginId,
				bankInfo == null ? "" : bankInfo.getRegionId(),
				bankInfo == null ? "" : bankInfo.getBranchId(),
				bankInfo == null ? "" : bankInfo.getAgencyId());
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ����� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId>
	 * rbBrchId ������� �� CardAcctRec_Type (������������� ���������� GFL)
	 * @param cardAcctRec ���������� � �����, ����������� �� ����
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ���������� �������������� �����
	 */
	public static String createCardCompositeId(CardAcctRec_Type cardAcctRec, Long loginId)
	{
		return createCardOrAccountCompositeId(cardAcctRec, loginId, true);
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� �����/����� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>
	 * @param cardAcctRec ���������� � �����, ����������� �� ����
	 * @param loginId ������������� ������ �������
	 * @param isCard - ����� ��� ����
	 * @return ������� ��������� ������������� ��������������
	 */
	public static String createCardOrAccountCompositeId(CardAcctRec_Type cardAcctRec, Long loginId, boolean isCard)
	{
		CardAcctId_Type cardAcctId = cardAcctRec.getCardAcctId();
		BankInfo_Type bankInfo = cardAcctRec.getBankInfo();
		return createCardOrAccountCompositeId(isCard ? cardAcctId.getCardNum() : cardAcctId.getAcctId(),
				StringHelper.isEmpty(cardAcctId.getSystemId()) ? "" : cardAcctId.getSystemId(),
				bankInfo == null ? "" : bankInfo.getRbBrchId(),
				loginId,
				bankInfo == null ? "" : bankInfo.getRegionId(),
				bankInfo == null ? "" : bankInfo.getBranchId(),
				bankInfo == null ? "" : bankInfo.getAgencyId());
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ��� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId>
	 * @param bankAcctRec ���������� �� ���, ���������� �� ������ GFL ����
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ���������� �������������� ���
	 */
	public static String createIMAccountCompositeId(BankAcctRec_Type bankAcctRec, Long loginId)
	{
		BankAcctId_Type acctId = bankAcctRec.getBankAcctId();
		return createCompositeId(acctId.getAcctId(), acctId.getSystemId(),
				bankAcctRec.getBankInfo().getRbBrchId(), loginId);
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ��� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId>
	 * @param imsRec ���������� �� ���, ���������� �� ������ IMA_IS_s ����
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ���������� �������������� ���
	 */
	public static String createIMAccountCompositeId(ImsRec_Type imsRec, Long loginId)
	{
		ImsAcctId_Type imsAcctId = imsRec.getImsAcctId();
		return createCompositeId(imsAcctId.getAcctId(), imsAcctId.getSystemId(),
				imsRec.getImsAcctInfo().getBankInfo().getRbBrchId(), loginId);
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ����� ���� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId>
	 * @param depoAccount ���������� � ����� ����, ���������� �� ������ GFL ����
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ���������� �������������� ����� ����
	 */
	public static String createDepoAccountCompositeId(DepoAccount_Type depoAccount, Long loginId)
	{
		return createCompositeId(depoAccount.getDepoAcctId().getAcctId(),
				depoAccount.getDepoAcctId().getSystemId(), depoAccount.getBankInfo().getRbBrchId(), loginId);
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ����� ���� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId>
	 * @param depoAcctRes ���������� � ����� ����, ���������� �� ������ DEPO_IS ����
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ���������� �������������� ����� ����
	 */
	public static String createDepoAccountCompositeId(DepoAcctRes_Type depoAcctRes, Long loginId)
	{
		return createCompositeId(depoAcctRes.getDepoAcctId().getAcctId(),
				depoAcctRes.getDepoAcctId().getSystemId(),
				depoAcctRes.getAcctInfo().getBankInfo().getRbBrchId(), loginId);
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ����� ���� �� �������
	 * <id ��������>^< systemId >^<rbBrchId>^<loginId>
	 * @param depoAcctId ���������� � ����� ����, ���������� �� ���� (������ ���������)
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ���������� �������������� ����� ����
	 */
	public static String createDepoAccountCompositeId(DepoAcctId_Type depoAcctId, Long loginId)
	{
		return createCompositeId(depoAcctId.getAcctId(), depoAcctId.getSystemId(),
				depoAcctId.getBankInfo().getRbBrchId(), loginId);
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ������� �� �������
	 * <id ��������>|<productType>^< systemId >^<rbBrchId>^<loginId>
	 * @param loanAcctRec ���������� � �������, ���������� �� ������ GFL ����
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ���������� �������������� �������
	 */
	public static String createLoanCompositeId(LoanAcctRec_Type loanAcctRec, Long loginId)
	{
		LoanAcctId_Type loanAcctId = loanAcctRec.getLoanAcctId();
		LoanCompositeId compositeId = new LoanCompositeId(loanAcctId.getAcctId(), loanAcctId.getSystemId(),
				loanAcctRec.getBankInfo().getRbBrchId(), loginId, loanAcctId.getProdType());
		return compositeId.toString();
	}

	/**
	 * ������� ��������� ������������� ������������ �������������� ������� �� �������
	 * <id ��������>|<productType>^< systemId >^<rbBrchId>^<loginId>
	 * @param loanRec ���������� � �������, ���������� �� ������ LN_CI ����
	 * @param loginId ������������� ������ �������
	 * @return ������� ��������� ������������� ���������� �������������� �������
	 */
	public static String createLoanCompositeId(LoanRec_Type loanRec, Long loginId)
	{
		LoanInfo_Type loanInfo = loanRec.getLoanInfo();
		LoanAcctId_Type loanAcctId = loanInfo.getLoanAcctId();
		LoanCompositeId compositeId = new LoanCompositeId(loanAcctId.getAcctId(), loanAcctId.getSystemId(),
				loanRec.getBankInfo().getRbBrchId(), loginId, loanInfo.getProdType());
		return compositeId.toString();
	}

	/**
	 * ������� �������������� ���������� ��������� �� �������
	 * <id ��������>^<systemId>^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>^<SvcType>
	 * @param svcsAcct ���������� ���������
	 * @param loginId �������
	 * @return String
	 */
	public static String createLongOfferEntityId(SvcsAcct_Type svcsAcct, Long loginId)
	{
		SvcsAcct_TypeSvcAcctId svcAcctId = svcsAcct.getSvcAcctId();
		BankInfo_Type bankInfo = svcAcctId.getBankInfo();
		EntityCompositeId compositeId = new LongOfferCompositeId(
				String.valueOf(svcAcctId.getSvcAcctNum()),
				svcsAcct.getSystemId(),
				bankInfo.getRbBrchId(),
				loginId,
				bankInfo.getRegionId(),
				bankInfo.getBranchId(),
				bankInfo.getAgencyId(),
				svcAcctId.isSvcType()
		);
		return compositeId.toString();
	}

	/**
	 * ������� �������������� �������� �� ����������� �� �������
	 * <id ��������>^<systemId>^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>^<SvcType>
	 * @param subscriptionRec �������� �� ����������
	 * @param loginId �������
	 * @return String
	 */
	public static String createAutoSubscriptionEntityId(AutoSubscriptionRec_Type subscriptionRec, Long loginId)
	{
		AutoSubscriptionId_Type subscriptionId = subscriptionRec.getAutoSubscriptionId();
		BankInfo_Type bankInfo = subscriptionRec.getAutoSubscriptionInfo().getBankInfo();
		EntityCompositeId compositeId = new LongOfferCompositeId(
				String.valueOf(subscriptionId.getAutopayId()),
				subscriptionId.getSystemId(),
				bankInfo.getRbBrchId() == null ? "" : bankInfo.getRbBrchId(),
				loginId,
				bankInfo.getRegionId() == null ? "" : bankInfo.getRegionId(),
				bankInfo.getBranchId() == null ? "" : bankInfo.getBranchId(),
				bankInfo.getAgencyId() == null ? "" : bankInfo.getAgencyId(),
				true
		);
		return compositeId.toString();
	}

	/**
	 * ������� ������������� ������� �� �������� �� ����������.
	 * @param autoPaymentRec ������.
	 * @param autoSubscription �������� �� ����������.
	 * @return String.
	 */
	public static String createAutoSubscriptionPaymentEntityId(AutoPaymentRec_Type autoPaymentRec, AutoSubscription autoSubscription)
	{
		LongOfferCompositeId subscrId = new LongOfferCompositeId(autoSubscription.getExternalId());
		BankInfo_Type bankInfo = autoPaymentRec.getBankAcctRec() != null ? autoPaymentRec.getBankAcctRec()[0].getBankInfo() : null;

		AutoSubscriptionPaymentCompositeId scheduleItemCompositeId = new AutoSubscriptionPaymentCompositeId(
			autoPaymentRec.getAutoPaymentId().getPaymentId(),
			autoPaymentRec.getAutoPaymentId().getSystemId(),
			bankInfo == null ? subscrId.getRbBrchId() : (bankInfo.getRbBrchId() == null ? "" : bankInfo.getRbBrchId()),
			subscrId.getLoginId()
		);
		return scheduleItemCompositeId.toString();
	}

	/**
	 * �������� �������������� ���������� ��������
	 * @param entityId - reference ���������� ��������
	 * @param loginId �������
	 * @return ������������� ���������� ��������
	 */
	public static String createInsuranceCompositeId(String entityId, Long loginId)
	{
		InsuranceCompositeId compositeId = new InsuranceCompositeId(entityId, loginId);
		return compositeId.toString();
	}

	/**
	 * �������� ��������� ������������� ������������ �������������� ���������� ��������
	 * @param compositeId ������������
	 * @return EntityCompositeId
	 */
	public static InsuranceCompositeId getInsuranceCompositeId(String compositeId)
	{
		return new InsuranceCompositeId(compositeId);
	}

	/**
	 * �������� �������������� ��������������� �����������
	 * @param serialNum - ���������� ����� ������
	 * @param secType - ��� ������
	 * @param loginId �������
	 * @param systemId ������������� �������-��������� ��������
	 * @return ������������� ��������������� �����������
	 */
	public static String createSecurityAccountCompositeId(String serialNum,String secType, Long loginId, String systemId)
	{
		SecurityAccountCompositeId compositeId = new SecurityAccountCompositeId(serialNum, secType, loginId, systemId);
		return compositeId.toString();
	}

	/**
	 * �������� ��������� ������������� ������������ �������������� ��������������� �����������
	 * @param compositeId ������������
	 * @return EntityCompositeId
	 */
	public static SecurityAccountCompositeId getSecurityAccountCompositeId(String compositeId)
	{
		return new SecurityAccountCompositeId(compositeId);
	}

}
