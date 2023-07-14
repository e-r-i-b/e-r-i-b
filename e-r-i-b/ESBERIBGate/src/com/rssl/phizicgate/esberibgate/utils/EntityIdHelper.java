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
 * Утилитный класс для запаковки и распаковки служебной информации в идентификаторах сущностей
 */
public class EntityIdHelper
{
	/**
	 * получить объектное представление компоситного идентифкатора счета  
	 * @param account счет
	 * @return CardOrAccountCompositeId
	 */
	public static CardOrAccountCompositeId getAccountCompositeId(Account account)
	{
		return getCardOrAccountCompositeId(account.getId());
	}

	/**
	 * получить объектное представление компоситного идентифкатора карты
	 * @param card карта
	 * @return CardOrAccountCompositeId
	 */
	public static CardOrAccountCompositeId getCardCompositeId(Card card)
	{
		return getCardOrAccountCompositeId(card.getId());
	}

	/**
	 * получить объектное представление компоситного идентифкатора кредита
	 * @param loan - кредит
	 * @return LoanCompositeId
	 */
	public static LoanCompositeId getLoanCompositeId(Loan loan)
	{
		return getLoanCompositeId(loan.getId());
	}

	/**
	 * получить объектное представление компоситного идентифкатора кредита
	 * @param loanId идентифкатор кредита
	 * @return LoanCompositeId
	 */
	public static LoanCompositeId getLoanCompositeId(String loanId)
	{
		return new LoanCompositeId(loanId);
	}

	/**
	 * получить объектное представление компоситного идентифкатора длительного поручения
	 * @param longOfferId идентификатор поручения
	 * @return LongOfferCompositeId
	 */
	public static LongOfferCompositeId getLongOfferCompositeId(String longOfferId)
	{
		return new LongOfferCompositeId(longOfferId);
	}

	/**
	 * получить объектное представление компоситного идентифкатора длительного поручения
	 * @param longOffer поручение
	 * @return LongOfferCompositeId
	 */
	public static LongOfferCompositeId getLongOfferCompositeId(LongOffer longOffer)
	{
		return new LongOfferCompositeId(longOffer.getExternalId());
	}

	/**
	 * получить объектное представление композитного идентификатора
	 * @param compositeId идентифкатор
	 * @return EntityCompositeId
	 */
	public static EntityCompositeId getCommonCompositeId(String compositeId)
	{
		return new EntityCompositeId(compositeId);
	}

	/**
	 * получить объектное представление композитного идентификатора счета или карты
	 * @param compositId идентификатор
	 * @return CardOrAccountCompositeId
	 */
	public static CardOrAccountCompositeId getCardOrAccountCompositeId(String compositId)
	{
		return new CardOrAccountCompositeId(compositId);
	}

	/**
	 * возвращает объектное представление композитного идентификатора платежа по подписке.
	 * @param compositeId идентификатор.
	 * @return AutoSubscriptionPaymentCompositeId.
	 */
	public static AutoSubscriptionPaymentCompositeId getAutoSubscriptionPaymentCompositeId(String compositeId)
	{
		return new AutoSubscriptionPaymentCompositeId(compositeId);
	}

	/**
	 * Получить строковое представление составного идентификатора по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId>
	 * @param entityId - идентифкатор продукта во внешней системе
	 * @param systemId - идентификатор системы-источника продукта
	 * @param rbBrchId - номер ОСБ, ведущего продукт
	 * @param loginId - идентификатор владельца продукта в ИКФЛ
	 * @return строковое представление идентификатора
	 */
	public static String createCompositeId(String entityId, String systemId, String rbBrchId, Long loginId)
	{
		EntityCompositeId compositeId = new EntityCompositeId(entityId, systemId, rbBrchId, loginId);
		return compositeId.toString();
	}

	/**
	 * Получить строковое представление составного идентификатора счета или карты
	 * <id сущности>^<systemId>^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>
	 * @param entityId идентифкатор продукта во внешней системе
	 * @param systemId идентификатор системы-источника продукта
	 * @param rbBrchId номер ОСБ, ведущего продукт
	 * @param loginId  идентификатор владельца продукта в ИКФЛ
	 * @param regionId Номер тербанка(ТБ)
	 * @param branchId Номер филиала(ВСП)
	 * @param agencyId Номер подразделения(ОСБ)
	 * @return строковое представление идентификатора
	 */
	public static String createCardOrAccountCompositeId(String entityId, String systemId, String rbBrchId, Long loginId, String regionId, String branchId, String agencyId)
	{
		CardOrAccountCompositeId compositeId = new CardOrAccountCompositeId(entityId, systemId, rbBrchId, loginId, regionId, branchId, agencyId);
		return compositeId.toString();
	}

	/**
	 * Создать строковое представление композитного идентификатора счета по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId> 
	 * @param depAcctRec информация о счете, вернувшаяся из шины
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление идентификатора
	 */
	public static String createAccountCompositeId(DepAcctRec_Type depAcctRec, Long loginId)
	{
		BankInfo_Type bankInfo = depAcctRec.getBankInfo();
		return createCardOrAccountCompositeId(depAcctRec.getDepAcctId().getAcctId(), depAcctRec.getDepAcctId().getSystemId(),
				bankInfo.getRbBrchId(), loginId, bankInfo.getRegionId(), bankInfo.getBranchId(), bankInfo.getAgencyId());
	}

	/**
	 * Создать строковое представление композитного идентификатора счета по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>
	 * @param detailAcctInfo информация о счете, вернувшаяся из шины
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление идентификатора
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
	 * Создать строковое представление композитного идентификатора карты по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId>
	 * rbBrchId берется из CardAcctId_Type (соответствует интерфейсу CRDWI)
	 * @param cardAcctId информация о карте, вернувшаяся из шины
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление составного идентификатора карты
	 */
	public static String createCardCompositeId(CardAcctId_Type cardAcctId, Long loginId)
	{
		//SystemId может не прийти в CRDWI
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
	 * Создать строковое представление композитного идентификатора карты по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId>
	 * rbBrchId берется из CardAcctRec_Type (соответствует интерфейсу GFL)
	 * @param cardAcctRec информация о карте, вернувшаяся из шины
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление составного идентификатора карты
	 */
	public static String createCardCompositeId(CardAcctRec_Type cardAcctRec, Long loginId)
	{
		return createCardOrAccountCompositeId(cardAcctRec, loginId, true);
	}

	/**
	 * Создать строковое представление композитного идентификатора карты/счета по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>
	 * @param cardAcctRec информация о карте, вернувшаяся из шины
	 * @param loginId идентификатор логина клиента
	 * @param isCard - карта или счет
	 * @return готовое строковое представление идентификатора
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
	 * Создать строковое представление композитного идентификатора ОМС по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId>
	 * @param bankAcctRec информация об ОМС, полученная из ответа GFL шины
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление составного идентификатора ОМС
	 */
	public static String createIMAccountCompositeId(BankAcctRec_Type bankAcctRec, Long loginId)
	{
		BankAcctId_Type acctId = bankAcctRec.getBankAcctId();
		return createCompositeId(acctId.getAcctId(), acctId.getSystemId(),
				bankAcctRec.getBankInfo().getRbBrchId(), loginId);
	}

	/**
	 * Создать строковое представление композитного идентификатора ОМС по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId>
	 * @param imsRec информация об ОМС, полученная из ответа IMA_IS_s шины
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление составного идентификатора ОМС
	 */
	public static String createIMAccountCompositeId(ImsRec_Type imsRec, Long loginId)
	{
		ImsAcctId_Type imsAcctId = imsRec.getImsAcctId();
		return createCompositeId(imsAcctId.getAcctId(), imsAcctId.getSystemId(),
				imsRec.getImsAcctInfo().getBankInfo().getRbBrchId(), loginId);
	}

	/**
	 * Создать строковое представление композитного идентификатора счета Депо по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId>
	 * @param depoAccount информация о счете Депо, полученная из ответа GFL шины
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление составного идентификатора счета Депо
	 */
	public static String createDepoAccountCompositeId(DepoAccount_Type depoAccount, Long loginId)
	{
		return createCompositeId(depoAccount.getDepoAcctId().getAcctId(),
				depoAccount.getDepoAcctId().getSystemId(), depoAccount.getBankInfo().getRbBrchId(), loginId);
	}

	/**
	 * Создать строковое представление композитного идентификатора счета Депо по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId>
	 * @param depoAcctRes информация о счете Депо, полученная из ответа DEPO_IS шины
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление составного идентификатора счета Депо
	 */
	public static String createDepoAccountCompositeId(DepoAcctRes_Type depoAcctRes, Long loginId)
	{
		return createCompositeId(depoAcctRes.getDepoAcctId().getAcctId(),
				depoAcctRes.getDepoAcctId().getSystemId(),
				depoAcctRes.getAcctInfo().getBankInfo().getRbBrchId(), loginId);
	}

	/**
	 * Создать строковое представление композитного идентификатора счета Депо по шаблону
	 * <id сущности>^< systemId >^<rbBrchId>^<loginId>
	 * @param depoAcctId информация о счете Депо, полученная из шины (анкета Депонента)
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление составного идентификатора счета Депо
	 */
	public static String createDepoAccountCompositeId(DepoAcctId_Type depoAcctId, Long loginId)
	{
		return createCompositeId(depoAcctId.getAcctId(), depoAcctId.getSystemId(),
				depoAcctId.getBankInfo().getRbBrchId(), loginId);
	}

	/**
	 * Создать строковое представление композитного идентификатора кредита по шаблону
	 * <id сущности>|<productType>^< systemId >^<rbBrchId>^<loginId>
	 * @param loanAcctRec информация о кредите, полученная из ответа GFL шины
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление составного идентификатора кредита
	 */
	public static String createLoanCompositeId(LoanAcctRec_Type loanAcctRec, Long loginId)
	{
		LoanAcctId_Type loanAcctId = loanAcctRec.getLoanAcctId();
		LoanCompositeId compositeId = new LoanCompositeId(loanAcctId.getAcctId(), loanAcctId.getSystemId(),
				loanAcctRec.getBankInfo().getRbBrchId(), loginId, loanAcctId.getProdType());
		return compositeId.toString();
	}

	/**
	 * Создать строковое представление композитного идентификатора кредита по шаблону
	 * <id сущности>|<productType>^< systemId >^<rbBrchId>^<loginId>
	 * @param loanRec информация о кредите, полученная из ответа LN_CI шины
	 * @param loginId идентификатор логина клиента
	 * @return готовое строковое представление составного идентификатора кредита
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
	 * Создать индентификатор длительнго поручения по шаблону
	 * <id сущности>^<systemId>^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>^<SvcType>
	 * @param svcsAcct длительное поручение
	 * @param loginId клиента
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
	 * Создать индентификатор подписки на автоплатежи по шаблону
	 * <id сущности>^<systemId>^<rbBrchId>^<loginId>^<regionId>^<branchId>^<agencyId>^<SvcType>
	 * @param subscriptionRec подписка на автоплатеж
	 * @param loginId клиента
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
	 * создает идентификатор платежа по подписке на автоплатеж.
	 * @param autoPaymentRec платеж.
	 * @param autoSubscription подписка на автоплатеж.
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
	 * Создание идентификатора страхового продукта
	 * @param entityId - reference страхового продукта
	 * @param loginId клиента
	 * @return идентификатор страхового продукта
	 */
	public static String createInsuranceCompositeId(String entityId, Long loginId)
	{
		InsuranceCompositeId compositeId = new InsuranceCompositeId(entityId, loginId);
		return compositeId.toString();
	}

	/**
	 * получить объектное представление композитного идентификатора страхового продукта
	 * @param compositeId идентифкатор
	 * @return EntityCompositeId
	 */
	public static InsuranceCompositeId getInsuranceCompositeId(String compositeId)
	{
		return new InsuranceCompositeId(compositeId);
	}

	/**
	 * Создание идентификатора сберегательного сертификата
	 * @param serialNum - Уникальный номер бланка
	 * @param secType - Тип бланка
	 * @param loginId клиента
	 * @param systemId идентификатор системы-источника продукта
	 * @return идентификатор сберегательного сертификата
	 */
	public static String createSecurityAccountCompositeId(String serialNum,String secType, Long loginId, String systemId)
	{
		SecurityAccountCompositeId compositeId = new SecurityAccountCompositeId(serialNum, secType, loginId, systemId);
		return compositeId.toString();
	}

	/**
	 * получить объектное представление композитного идентификатора сберегательного сертификата
	 * @param compositeId идентифкатор
	 * @return EntityCompositeId
	 */
	public static SecurityAccountCompositeId getSecurityAccountCompositeId(String compositeId)
	{
		return new SecurityAccountCompositeId(compositeId);
	}

}
