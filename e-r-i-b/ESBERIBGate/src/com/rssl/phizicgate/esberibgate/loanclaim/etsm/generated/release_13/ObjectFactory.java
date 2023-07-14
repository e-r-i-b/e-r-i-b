
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RqUID_QNAME = new QName("", "RqUID");
    private final static QName _ServerStatusDesc_QNAME = new QName("", "ServerStatusDesc");
    private final static QName _LoanAcctId_QNAME = new QName("", "LoanAcctId");
    private final static QName _OrigAmt_QNAME = new QName("", "OrigAmt");
    private final static QName _AcctId_QNAME = new QName("", "AcctId");
    private final static QName _DepAcctIdFrom_QNAME = new QName("", "DepAcctIdFrom");
    private final static QName _NewDepAddRs_QNAME = new QName("", "NewDepAddRs");
    private final static QName _AcctType_QNAME = new QName("", "AcctType");
    private final static QName _FirstName_QNAME = new QName("", "FirstName");
    private final static QName _AgreemtInfoClose_QNAME = new QName("", "AgreemtInfoClose");
    private final static QName _PurchaseYear_QNAME = new QName("", "PurchaseYear");
    private final static QName _HeaderInfo_QNAME = new QName("", "HeaderInfo");
    private final static QName _DepToNewDepAddRs_QNAME = new QName("", "DepToNewDepAddRs");
    private final static QName _IssuedBy_QNAME = new QName("", "IssuedBy");
    private final static QName _AcctCur_QNAME = new QName("", "AcctCur");
    private final static QName _ProdType_QNAME = new QName("", "ProdType");
    private final static QName _DepoAccSecInfoRq_QNAME = new QName("", "DepoAccSecInfoRq");
    private final static QName _Gender_QNAME = new QName("", "Gender");
    private final static QName _AgencyId_QNAME = new QName("", "AgencyId");
    private final static QName _SPNum_QNAME = new QName("", "SPNum");
    private final static QName _LoanType_QNAME = new QName("", "LoanType");
    private final static QName _AgreemtNum_QNAME = new QName("", "AgreemtNum");
    private final static QName _StmtSummType_QNAME = new QName("", "StmtSummType");
    private final static QName _IdNum_QNAME = new QName("", "IdNum");
    private final static QName _AutopayStatus_QNAME = new QName("", "AutopayStatus");
    private final static QName _DstCurAmt_QNAME = new QName("", "DstCurAmt");
    private final static QName _StartDt_QNAME = new QName("", "StartDt");
    private final static QName _StatusType_QNAME = new QName("", "StatusType");
    private final static QName _BalType_QNAME = new QName("", "BalType");
    private final static QName _MiddleName_QNAME = new QName("", "MiddleName");
    private final static QName _DepoDeptsInfoRq_QNAME = new QName("", "DepoDeptsInfoRq");
    private final static QName _SystemId_QNAME = new QName("", "SystemId");
    private final static QName _SPName_QNAME = new QName("", "SPName");
    private final static QName _CardAcctIdFrom_QNAME = new QName("", "CardAcctIdFrom");
    private final static QName _DepToNewDepAddRq_QNAME = new QName("", "DepToNewDepAddRq");
    private final static QName _Birthday_QNAME = new QName("", "Birthday");
    private final static QName _DepoArRq_QNAME = new QName("", "DepoArRq");
    private final static QName _IssueDt_QNAME = new QName("", "IssueDt");
    private final static QName _CardAcctIdTo_QNAME = new QName("", "CardAcctIdTo");
    private final static QName _IdType_QNAME = new QName("", "IdType");
    private final static QName _CustPermId_QNAME = new QName("", "CustPermId");
    private final static QName _FieldValue_QNAME = new QName("", "FieldValue");
    private final static QName _BranchId_QNAME = new QName("", "BranchId");
    private final static QName _LoanAcctIdTo_QNAME = new QName("", "LoanAcctIdTo");
    private final static QName _StmtType_QNAME = new QName("", "StmtType");
    private final static QName _DepAcctIdTo_QNAME = new QName("", "DepAcctIdTo");
    private final static QName _RqTm_QNAME = new QName("", "RqTm");
    private final static QName _DepoAccInfoRq_QNAME = new QName("", "DepoAccInfoRq");
    private final static QName _CurAmt_QNAME = new QName("", "CurAmt");
    private final static QName _CardNum_QNAME = new QName("", "CardNum");
    private final static QName _StatusCode_QNAME = new QName("", "StatusCode");
    private final static QName _AgreemtInfo_QNAME = new QName("", "AgreemtInfo");
    private final static QName _SrcCurAmt_QNAME = new QName("", "SrcCurAmt");
    private final static QName _BankAcctStatusCode_QNAME = new QName("", "BankAcctStatusCode");
    private final static QName _OperUID_QNAME = new QName("", "OperUID");
    private final static QName _SBCreditFlag_QNAME = new QName("", "SBCreditFlag");
    private final static QName _RbTbBrchId_QNAME = new QName("", "RbTbBrchId");
    private final static QName _RbBrchId_QNAME = new QName("", "RbBrchId");
    private final static QName _FieldName_QNAME = new QName("", "FieldName");
    private final static QName _SBEmployeeFlag_QNAME = new QName("", "SBEmployeeFlag");
    private final static QName _CommisionCur_QNAME = new QName("", "CommisionCur");
    private final static QName _EffDate_QNAME = new QName("", "EffDate");
    private final static QName _Commision_QNAME = new QName("", "Commision");
    private final static QName _RegionId_QNAME = new QName("", "RegionId");
    private final static QName _IdSeries_QNAME = new QName("", "IdSeries");
    private final static QName _CardName_QNAME = new QName("", "CardName");
    private final static QName _BillingCode_QNAME = new QName("", "BillingCode");
    private final static QName _EndDt_QNAME = new QName("", "EndDt");
    private final static QName _PersonName_QNAME = new QName("", "PersonName");
    private final static QName _ExpDt_QNAME = new QName("", "ExpDt");
    private final static QName _DependentFlag_QNAME = new QName("", "DependentFlag");
    private final static QName _CardAcctId_QNAME = new QName("", "CardAcctId");
    private final static QName _DepAcctId_QNAME = new QName("", "DepAcctId");
    private final static QName _NewDepAddRq_QNAME = new QName("", "NewDepAddRq");
    private final static QName _IrreducibleAmt_QNAME = new QName("", "IrreducibleAmt");
    private final static QName _StatusDesc_QNAME = new QName("", "StatusDesc");
    private final static QName _LastName_QNAME = new QName("", "LastName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SvcAcctDelRq }
     * 
     */
    public SvcAcctDelRq createSvcAcctDelRq() {
        return new SvcAcctDelRq();
    }

    /**
     * Create an instance of {@link CCAcctExtStmtInqRs }
     * 
     */
    public CCAcctExtStmtInqRs createCCAcctExtStmtInqRs() {
        return new CCAcctExtStmtInqRs();
    }

    /**
     * Create an instance of {@link XferInfo }
     * 
     */
    public XferInfo createXferInfo() {
        return new XferInfo();
    }

    /**
     * Create an instance of {@link XferOperStatusInfoRs }
     * 
     */
    public XferOperStatusInfoRs createXferOperStatusInfoRs() {
        return new XferOperStatusInfoRs();
    }

    /**
     * Create an instance of {@link SvcActInfo }
     * 
     */
    public SvcActInfo createSvcActInfo() {
        return new SvcActInfo();
    }

    /**
     * Create an instance of {@link Regular }
     * 
     */
    public Regular createRegular() {
        return new Regular();
    }

    /**
     * Create an instance of {@link DepoAccSecInfoRs }
     * 
     */
    public DepoAccSecInfoRs createDepoAccSecInfoRs() {
        return new DepoAccSecInfoRs();
    }

    /**
     * Create an instance of {@link DepoDeptDetInfoRs }
     * 
     */
    public DepoDeptDetInfoRs createDepoDeptDetInfoRs() {
        return new DepoDeptDetInfoRs();
    }

    /**
     * Create an instance of {@link DepoDeptsInfoRs }
     * 
     */
    public DepoDeptsInfoRs createDepoDeptsInfoRs() {
        return new DepoDeptsInfoRs();
    }

    /**
     * Create an instance of {@link DepoAccInfoRs }
     * 
     */
    public DepoAccInfoRs createDepoAccInfoRs() {
        return new DepoAccInfoRs();
    }

    /**
     * Create an instance of {@link CCAcctFullStmtInqRs }
     * 
     */
    public CCAcctFullStmtInqRs createCCAcctFullStmtInqRs() {
        return new CCAcctFullStmtInqRs();
    }

    /**
     * Create an instance of {@link AdditionalCardInfo }
     * 
     */
    public AdditionalCardInfo createAdditionalCardInfo() {
        return new AdditionalCardInfo();
    }

    /**
     * Create an instance of {@link SvcsAcct }
     * 
     */
    public SvcsAcct createSvcsAcct() {
        return new SvcsAcct();
    }

    /**
     * Create an instance of {@link DepAcctStmtInqRq }
     * 
     */
    public DepAcctStmtInqRq createDepAcctStmtInqRq() {
        return new DepAcctStmtInqRq();
    }

    /**
     * Create an instance of {@link PayInfo }
     * 
     */
    public PayInfo createPayInfo() {
        return new PayInfo();
    }

    /**
     * Create an instance of {@link CCAcctStmtRec }
     * 
     */
    public CCAcctStmtRec createCCAcctStmtRec() {
        return new CCAcctStmtRec();
    }

    /**
     * Create an instance of {@link SvcAcctAudRq }
     * 
     */
    public SvcAcctAudRq createSvcAcctAudRq() {
        return new SvcAcctAudRq();
    }

    /**
     * Create an instance of {@link ApplicantAddDataType }
     * 
     */
    public ApplicantAddDataType createApplicantAddDataType() {
        return new ApplicantAddDataType();
    }

    /**
     * Create an instance of {@link ApplicantDataType }
     * 
     */
    public ApplicantDataType createApplicantDataType() {
        return new ApplicantDataType();
    }

    /**
     * Create an instance of {@link EmploymentHistoryType2 }
     * 
     */
    public EmploymentHistoryType2 createEmploymentHistoryType2() {
        return new EmploymentHistoryType2();
    }

    /**
     * Create an instance of {@link ContactType }
     * 
     */
    public ContactType createContactType() {
        return new ContactType();
    }

    /**
     * Create an instance of {@link DepoBankAccountAdditionalInfoType }
     * 
     */
    public DepoBankAccountAdditionalInfoType createDepoBankAccountAdditionalInfoType() {
        return new DepoBankAccountAdditionalInfoType();
    }

    /**
     * Create an instance of {@link ApplicationStatusType }
     * 
     */
    public ApplicationStatusType createApplicationStatusType() {
        return new ApplicationStatusType();
    }

    /**
     * Create an instance of {@link ApplicationStatusType.Status }
     * 
     */
    public ApplicationStatusType.Status createApplicationStatusTypeStatus() {
        return new ApplicationStatusType.Status();
    }

    /**
     * Create an instance of {@link IntegrationInfoType }
     * 
     */
    public IntegrationInfoType createIntegrationInfoType() {
        return new IntegrationInfoType();
    }

    /**
     * Create an instance of {@link BankAcctId }
     * 
     */
    public BankAcctId createBankAcctId() {
        return new BankAcctId();
    }

    /**
     * Create an instance of {@link BankInfo }
     * 
     */
    public BankInfo createBankInfo() {
        return new BankInfo();
    }

    /**
     * Create an instance of {@link CardInfo }
     * 
     */
    public CardInfo createCardInfo() {
        return new CardInfo();
    }

    /**
     * Create an instance of {@link CardAcctIdType }
     * 
     */
    public CardAcctIdType createCardAcctIdType() {
        return new CardAcctIdType();
    }

    /**
     * Create an instance of {@link AccStopDocRs }
     * 
     */
    public AccStopDocRs createAccStopDocRs() {
        return new AccStopDocRs();
    }

    /**
     * Create an instance of {@link com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.Status }
     * 
     */
    public com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.Status createStatus() {
        return new com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.Status();
    }

    /**
     * Create an instance of {@link MessageRecvRs }
     * 
     */
    public MessageRecvRs createMessageRecvRs() {
        return new MessageRecvRs();
    }

    /**
     * Create an instance of {@link CustInfo }
     * 
     */
    public CustInfo createCustInfo() {
        return new CustInfo();
    }

    /**
     * Create an instance of {@link PersonInfo }
     * 
     */
    public PersonInfo createPersonInfo() {
        return new PersonInfo();
    }

    /**
     * Create an instance of {@link PersonNameType }
     * 
     */
    public PersonNameType createPersonNameType() {
        return new PersonNameType();
    }

    /**
     * Create an instance of {@link IdentityCard }
     * 
     */
    public IdentityCard createIdentityCard() {
        return new IdentityCard();
    }

    /**
     * Create an instance of {@link ContactInfo }
     * 
     */
    public ContactInfo createContactInfo() {
        return new ContactInfo();
    }

    /**
     * Create an instance of {@link ContactData }
     * 
     */
    public ContactData createContactData() {
        return new ContactData();
    }

    /**
     * Create an instance of {@link FullAddressType }
     * 
     */
    public FullAddressType createFullAddressType() {
        return new FullAddressType();
    }

    /**
     * Create an instance of {@link SPDefField }
     * 
     */
    public SPDefField createSPDefField() {
        return new SPDefField();
    }

    /**
     * Create an instance of {@link StatusLoanApplicationRq }
     * 
     */
    public StatusLoanApplicationRq createStatusLoanApplicationRq() {
        return new StatusLoanApplicationRq();
    }

    /**
     * Create an instance of {@link SrcRqType }
     * 
     */
    public SrcRqType createSrcRqType() {
        return new SrcRqType();
    }

    /**
     * Create an instance of {@link SvcAcctDelRq.SvcAcct }
     * 
     */
    public SvcAcctDelRq.SvcAcct createSvcAcctDelRqSvcAcct() {
        return new SvcAcctDelRq.SvcAcct();
    }

    /**
     * Create an instance of {@link CCAcctExtStmtInqRq }
     * 
     */
    public CCAcctExtStmtInqRq createCCAcctExtStmtInqRq() {
        return new CCAcctExtStmtInqRq();
    }

    /**
     * Create an instance of {@link DepToNewDepAddRsType }
     * 
     */
    public DepToNewDepAddRsType createDepToNewDepAddRsType() {
        return new DepToNewDepAddRsType();
    }

    /**
     * Create an instance of {@link IMAToCardAddRq }
     * 
     */
    public IMAToCardAddRq createIMAToCardAddRq() {
        return new IMAToCardAddRq();
    }

    /**
     * Create an instance of {@link XferIMAInfoType }
     * 
     */
    public XferIMAInfoType createXferIMAInfoType() {
        return new XferIMAInfoType();
    }

    /**
     * Create an instance of {@link LoanAcctRec }
     * 
     */
    public LoanAcctRec createLoanAcctRec() {
        return new LoanAcctRec();
    }

    /**
     * Create an instance of {@link LoanAcctIdType }
     * 
     */
    public LoanAcctIdType createLoanAcctIdType() {
        return new LoanAcctIdType();
    }

    /**
     * Create an instance of {@link BankAcctInfo }
     * 
     */
    public BankAcctInfo createBankAcctInfo() {
        return new BankAcctInfo();
    }

    /**
     * Create an instance of {@link CustRec }
     * 
     */
    public CustRec createCustRec() {
        return new CustRec();
    }

    /**
     * Create an instance of {@link ServiceInfo }
     * 
     */
    public ServiceInfo createServiceInfo() {
        return new ServiceInfo();
    }

    /**
     * Create an instance of {@link DepoBankAccountType }
     * 
     */
    public DepoBankAccountType createDepoBankAccountType() {
        return new DepoBankAccountType();
    }

    /**
     * Create an instance of {@link DepoBankAccountCurType }
     * 
     */
    public DepoBankAccountCurType createDepoBankAccountCurType() {
        return new DepoBankAccountCurType();
    }

    /**
     * Create an instance of {@link TarifPlanInfoType }
     * 
     */
    public TarifPlanInfoType createTarifPlanInfoType() {
        return new TarifPlanInfoType();
    }

    /**
     * Create an instance of {@link CCAcctExtStmtInqRs.CardAcctRec }
     * 
     */
    public CCAcctExtStmtInqRs.CardAcctRec createCCAcctExtStmtInqRsCardAcctRec() {
        return new CCAcctExtStmtInqRs.CardAcctRec();
    }

    /**
     * Create an instance of {@link AutoSubscriptionModRq }
     * 
     */
    public AutoSubscriptionModRq createAutoSubscriptionModRq() {
        return new AutoSubscriptionModRq();
    }

    /**
     * Create an instance of {@link EmployeeOfTheVSPType }
     * 
     */
    public EmployeeOfTheVSPType createEmployeeOfTheVSPType() {
        return new EmployeeOfTheVSPType();
    }

    /**
     * Create an instance of {@link AutoSubscriptionRec }
     * 
     */
    public AutoSubscriptionRec createAutoSubscriptionRec() {
        return new AutoSubscriptionRec();
    }

    /**
     * Create an instance of {@link AutoSubscriptionIdType }
     * 
     */
    public AutoSubscriptionIdType createAutoSubscriptionIdType() {
        return new AutoSubscriptionIdType();
    }

    /**
     * Create an instance of {@link AutoSubscriptionInfoType }
     * 
     */
    public AutoSubscriptionInfoType createAutoSubscriptionInfoType() {
        return new AutoSubscriptionInfoType();
    }

    /**
     * Create an instance of {@link AutoPaymentTemplateType }
     * 
     */
    public AutoPaymentTemplateType createAutoPaymentTemplateType() {
        return new AutoPaymentTemplateType();
    }

    /**
     * Create an instance of {@link MBCInfoType }
     * 
     */
    public MBCInfoType createMBCInfoType() {
        return new MBCInfoType();
    }

    /**
     * Create an instance of {@link AcctInqRec }
     * 
     */
    public AcctInqRec createAcctInqRec() {
        return new AcctInqRec();
    }

    /**
     * Create an instance of {@link BankAcctRes }
     * 
     */
    public BankAcctRes createBankAcctRes() {
        return new BankAcctRes();
    }

    /**
     * Create an instance of {@link AcctBal }
     * 
     */
    public AcctBal createAcctBal() {
        return new AcctBal();
    }

    /**
     * Create an instance of {@link DepAcctRes }
     * 
     */
    public DepAcctRes createDepAcctRes() {
        return new DepAcctRes();
    }

    /**
     * Create an instance of {@link DepAcctIdType }
     * 
     */
    public DepAcctIdType createDepAcctIdType() {
        return new DepAcctIdType();
    }

    /**
     * Create an instance of {@link CardAcctRes }
     * 
     */
    public CardAcctRes createCardAcctRes() {
        return new CardAcctRes();
    }

    /**
     * Create an instance of {@link CardToNewDepAddRq }
     * 
     */
    public CardToNewDepAddRq createCardToNewDepAddRq() {
        return new CardToNewDepAddRq();
    }

    /**
     * Create an instance of {@link XferInfo.PayerInfo }
     * 
     */
    public XferInfo.PayerInfo createXferInfoPayerInfo() {
        return new XferInfo.PayerInfo();
    }

    /**
     * Create an instance of {@link TaxColl }
     * 
     */
    public TaxColl createTaxColl() {
        return new TaxColl();
    }

    /**
     * Create an instance of {@link AgreemtInfoType }
     * 
     */
    public AgreemtInfoType createAgreemtInfoType() {
        return new AgreemtInfoType();
    }

    /**
     * Create an instance of {@link CurAmtConv }
     * 
     */
    public CurAmtConv createCurAmtConv() {
        return new CurAmtConv();
    }

    /**
     * Create an instance of {@link GetAutoSubscriptionDetailInfoRs }
     * 
     */
    public GetAutoSubscriptionDetailInfoRs createGetAutoSubscriptionDetailInfoRs() {
        return new GetAutoSubscriptionDetailInfoRs();
    }

    /**
     * Create an instance of {@link CardUsageInfoLimitModRs }
     * 
     */
    public CardUsageInfoLimitModRs createCardUsageInfoLimitModRs() {
        return new CardUsageInfoLimitModRs();
    }

    /**
     * Create an instance of {@link BankAcctResultType }
     * 
     */
    public BankAcctResultType createBankAcctResultType() {
        return new BankAcctResultType();
    }

    /**
     * Create an instance of {@link DepoAccount }
     * 
     */
    public DepoAccount createDepoAccount() {
        return new DepoAccount();
    }

    /**
     * Create an instance of {@link DepoAcctId }
     * 
     */
    public DepoAcctId createDepoAcctId() {
        return new DepoAcctId();
    }

    /**
     * Create an instance of {@link DepAcctInfoType }
     * 
     */
    public DepAcctInfoType createDepAcctInfoType() {
        return new DepAcctInfoType();
    }

    /**
     * Create an instance of {@link LoanInqRq }
     * 
     */
    public LoanInqRq createLoanInqRq() {
        return new LoanInqRq();
    }

    /**
     * Create an instance of {@link AutoSubscriptionModRs }
     * 
     */
    public AutoSubscriptionModRs createAutoSubscriptionModRs() {
        return new AutoSubscriptionModRs();
    }

    /**
     * Create an instance of {@link GetAutoSubscriptionListRs }
     * 
     */
    public GetAutoSubscriptionListRs createGetAutoSubscriptionListRs() {
        return new GetAutoSubscriptionListRs();
    }

    /**
     * Create an instance of {@link OrgInfo }
     * 
     */
    public OrgInfo createOrgInfo() {
        return new OrgInfo();
    }

    /**
     * Create an instance of {@link DoIFXRs }
     * 
     */
    public DoIFXRs createDoIFXRs() {
        return new DoIFXRs();
    }

    /**
     * Create an instance of {@link GetInsuranceAppRs }
     * 
     */
    public GetInsuranceAppRs createGetInsuranceAppRs() {
        return new GetInsuranceAppRs();
    }

    /**
     * Create an instance of {@link InsuranceApp }
     * 
     */
    public InsuranceApp createInsuranceApp() {
        return new InsuranceApp();
    }

    /**
     * Create an instance of {@link PolicyDetailsType }
     * 
     */
    public PolicyDetailsType createPolicyDetailsType() {
        return new PolicyDetailsType();
    }

    /**
     * Create an instance of {@link GetInsuranceListRs }
     * 
     */
    public GetInsuranceListRs createGetInsuranceListRs() {
        return new GetInsuranceListRs();
    }

    /**
     * Create an instance of {@link InsuranceAppList }
     * 
     */
    public InsuranceAppList createInsuranceAppList() {
        return new InsuranceAppList();
    }

    /**
     * Create an instance of {@link DocStateUpdateRs }
     * 
     */
    public DocStateUpdateRs createDocStateUpdateRs() {
        return new DocStateUpdateRs();
    }

    /**
     * Create an instance of {@link DocumentType }
     * 
     */
    public DocumentType createDocumentType() {
        return new DocumentType();
    }

    /**
     * Create an instance of {@link XferOperStatusInfoRs.TCIO }
     * 
     */
    public XferOperStatusInfoRs.TCIO createXferOperStatusInfoRsTCIO() {
        return new XferOperStatusInfoRs.TCIO();
    }

    /**
     * Create an instance of {@link XferOperStatusInfoRs.TDIO }
     * 
     */
    public XferOperStatusInfoRs.TDIO createXferOperStatusInfoRsTDIO() {
        return new XferOperStatusInfoRs.TDIO();
    }

    /**
     * Create an instance of {@link XferOperStatusInfoRs.TCDO }
     * 
     */
    public XferOperStatusInfoRs.TCDO createXferOperStatusInfoRsTCDO() {
        return new XferOperStatusInfoRs.TCDO();
    }

    /**
     * Create an instance of {@link XferOperStatusInfoRs.TDDO }
     * 
     */
    public XferOperStatusInfoRs.TDDO createXferOperStatusInfoRsTDDO() {
        return new XferOperStatusInfoRs.TDDO();
    }

    /**
     * Create an instance of {@link IMAToCardAddRs }
     * 
     */
    public IMAToCardAddRs createIMAToCardAddRs() {
        return new IMAToCardAddRs();
    }

    /**
     * Create an instance of {@link CardToIMAAddRs }
     * 
     */
    public CardToIMAAddRs createCardToIMAAddRs() {
        return new CardToIMAAddRs();
    }

    /**
     * Create an instance of {@link CardToNewIMAAddRs }
     * 
     */
    public CardToNewIMAAddRs createCardToNewIMAAddRs() {
        return new CardToNewIMAAddRs();
    }

    /**
     * Create an instance of {@link CardAuthorization }
     * 
     */
    public CardAuthorization createCardAuthorization() {
        return new CardAuthorization();
    }

    /**
     * Create an instance of {@link AgreemtInfoResponseType }
     * 
     */
    public AgreemtInfoResponseType createAgreemtInfoResponseType() {
        return new AgreemtInfoResponseType();
    }

    /**
     * Create an instance of {@link DepToNewIMAAddRs }
     * 
     */
    public DepToNewIMAAddRs createDepToNewIMAAddRs() {
        return new DepToNewIMAAddRs();
    }

    /**
     * Create an instance of {@link GetAutoPaymentDetailInfoRs }
     * 
     */
    public GetAutoPaymentDetailInfoRs createGetAutoPaymentDetailInfoRs() {
        return new GetAutoPaymentDetailInfoRs();
    }

    /**
     * Create an instance of {@link AutoPaymentRecType }
     * 
     */
    public AutoPaymentRecType createAutoPaymentRecType() {
        return new AutoPaymentRecType();
    }

    /**
     * Create an instance of {@link GetAutoPaymentListRs }
     * 
     */
    public GetAutoPaymentListRs createGetAutoPaymentListRs() {
        return new GetAutoPaymentListRs();
    }

    /**
     * Create an instance of {@link AutoSubscriptionStatusModRs }
     * 
     */
    public AutoSubscriptionStatusModRs createAutoSubscriptionStatusModRs() {
        return new AutoSubscriptionStatusModRs();
    }

    /**
     * Create an instance of {@link OTPRestrictionModRs }
     * 
     */
    public OTPRestrictionModRs createOTPRestrictionModRs() {
        return new OTPRestrictionModRs();
    }

    /**
     * Create an instance of {@link SetAccountStateRs }
     * 
     */
    public SetAccountStateRs createSetAccountStateRs() {
        return new SetAccountStateRs();
    }

    /**
     * Create an instance of {@link PfrGetInfoInqRs }
     * 
     */
    public PfrGetInfoInqRs createPfrGetInfoInqRs() {
        return new PfrGetInfoInqRs();
    }

    /**
     * Create an instance of {@link Result }
     * 
     */
    public Result createResult() {
        return new Result();
    }

    /**
     * Create an instance of {@link PfrHasInfoInqRs }
     * 
     */
    public PfrHasInfoInqRs createPfrHasInfoInqRs() {
        return new PfrHasInfoInqRs();
    }

    /**
     * Create an instance of {@link CardToNewDepAddRs }
     * 
     */
    public CardToNewDepAddRs createCardToNewDepAddRs() {
        return new CardToNewDepAddRs();
    }

    /**
     * Create an instance of {@link BankAcctPermissModRs }
     * 
     */
    public BankAcctPermissModRs createBankAcctPermissModRs() {
        return new BankAcctPermissModRs();
    }

    /**
     * Create an instance of {@link SvcAddRs }
     * 
     */
    public SvcAddRs createSvcAddRs() {
        return new SvcAddRs();
    }

    /**
     * Create an instance of {@link com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.SvcAcctId }
     * 
     */
    public com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.SvcAcctId createSvcAcctId() {
        return new com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.SvcAcctId();
    }

    /**
     * Create an instance of {@link LoanInfoRs }
     * 
     */
    public LoanInfoRs createLoanInfoRs() {
        return new LoanInfoRs();
    }

    /**
     * Create an instance of {@link LoanRec }
     * 
     */
    public LoanRec createLoanRec() {
        return new LoanRec();
    }

    /**
     * Create an instance of {@link LoanInfo }
     * 
     */
    public LoanInfo createLoanInfo() {
        return new LoanInfo();
    }

    /**
     * Create an instance of {@link MDMClientInfoUpdateRs }
     * 
     */
    public MDMClientInfoUpdateRs createMDMClientInfoUpdateRs() {
        return new MDMClientInfoUpdateRs();
    }

    /**
     * Create an instance of {@link CardBlockRs }
     * 
     */
    public CardBlockRs createCardBlockRs() {
        return new CardBlockRs();
    }

    /**
     * Create an instance of {@link SvcAcctDelRs }
     * 
     */
    public SvcAcctDelRs createSvcAcctDelRs() {
        return new SvcAcctDelRs();
    }

    /**
     * Create an instance of {@link SvcAcctAudRs }
     * 
     */
    public SvcAcctAudRs createSvcAcctAudRs() {
        return new SvcAcctAudRs();
    }

    /**
     * Create an instance of {@link SvcActInfo.SvcAct }
     * 
     */
    public SvcActInfo.SvcAct createSvcActInfoSvcAct() {
        return new SvcActInfo.SvcAct();
    }

    /**
     * Create an instance of {@link Regular.PayDay }
     * 
     */
    public Regular.PayDay createRegularPayDay() {
        return new Regular.PayDay();
    }

    /**
     * Create an instance of {@link ServiceStmtRs }
     * 
     */
    public ServiceStmtRs createServiceStmtRs() {
        return new ServiceStmtRs();
    }

    /**
     * Create an instance of {@link ExctractLine }
     * 
     */
    public ExctractLine createExctractLine() {
        return new ExctractLine();
    }

    /**
     * Create an instance of {@link BillingPayExecRs }
     * 
     */
    public BillingPayExecRs createBillingPayExecRs() {
        return new BillingPayExecRs();
    }

    /**
     * Create an instance of {@link BillingPayPrepRs }
     * 
     */
    public BillingPayPrepRs createBillingPayPrepRs() {
        return new BillingPayPrepRs();
    }

    /**
     * Create an instance of {@link RecipientRec }
     * 
     */
    public RecipientRec createRecipientRec() {
        return new RecipientRec();
    }

    /**
     * Create an instance of {@link AutopayDetailsType }
     * 
     */
    public AutopayDetailsType createAutopayDetailsType() {
        return new AutopayDetailsType();
    }

    /**
     * Create an instance of {@link RequisitesType }
     * 
     */
    public RequisitesType createRequisitesType() {
        return new RequisitesType();
    }

    /**
     * Create an instance of {@link BillingPayInqRs }
     * 
     */
    public BillingPayInqRs createBillingPayInqRs() {
        return new BillingPayInqRs();
    }

    /**
     * Create an instance of {@link XferAddRs }
     * 
     */
    public XferAddRs createXferAddRs() {
        return new XferAddRs();
    }

    /**
     * Create an instance of {@link DepoRevokeDocRs }
     * 
     */
    public DepoRevokeDocRs createDepoRevokeDocRs() {
        return new DepoRevokeDocRs();
    }

    /**
     * Create an instance of {@link DepoArRs }
     * 
     */
    public DepoArRs createDepoArRs() {
        return new DepoArRs();
    }

    /**
     * Create an instance of {@link DepoAccSecRegRs }
     * 
     */
    public DepoAccSecRegRs createDepoAccSecRegRs() {
        return new DepoAccSecRegRs();
    }

    /**
     * Create an instance of {@link DepoAccTranRs }
     * 
     */
    public DepoAccTranRs createDepoAccTranRs() {
        return new DepoAccTranRs();
    }

    /**
     * Create an instance of {@link DepoAccSecInfoRs.DepoAccSecInfoRec }
     * 
     */
    public DepoAccSecInfoRs.DepoAccSecInfoRec createDepoAccSecInfoRsDepoAccSecInfoRec() {
        return new DepoAccSecInfoRs.DepoAccSecInfoRec();
    }

    /**
     * Create an instance of {@link DepoDeptCardPayRs }
     * 
     */
    public DepoDeptCardPayRs createDepoDeptCardPayRs() {
        return new DepoDeptCardPayRs();
    }

    /**
     * Create an instance of {@link DepoDeptDetInfoRs.DepoAcctDeptInfoRec }
     * 
     */
    public DepoDeptDetInfoRs.DepoAcctDeptInfoRec createDepoDeptDetInfoRsDepoAcctDeptInfoRec() {
        return new DepoDeptDetInfoRs.DepoAcctDeptInfoRec();
    }

    /**
     * Create an instance of {@link DepoDeptsInfoRs.DepoAcctDeptInfoRec }
     * 
     */
    public DepoDeptsInfoRs.DepoAcctDeptInfoRec createDepoDeptsInfoRsDepoAcctDeptInfoRec() {
        return new DepoDeptsInfoRs.DepoAcctDeptInfoRec();
    }

    /**
     * Create an instance of {@link DepoAccInfoRs.DepoAcctInfoRec }
     * 
     */
    public DepoAccInfoRs.DepoAcctInfoRec createDepoAccInfoRsDepoAcctInfoRec() {
        return new DepoAccInfoRs.DepoAcctInfoRec();
    }

    /**
     * Create an instance of {@link DepoClientRegRs }
     * 
     */
    public DepoClientRegRs createDepoClientRegRs() {
        return new DepoClientRegRs();
    }

    /**
     * Create an instance of {@link LoanPaymentRs }
     * 
     */
    public LoanPaymentRs createLoanPaymentRs() {
        return new LoanPaymentRs();
    }

    /**
     * Create an instance of {@link LoanPaymentRec }
     * 
     */
    public LoanPaymentRec createLoanPaymentRec() {
        return new LoanPaymentRec();
    }

    /**
     * Create an instance of {@link LoanInqRs }
     * 
     */
    public LoanInqRs createLoanInqRs() {
        return new LoanInqRs();
    }

    /**
     * Create an instance of {@link CCAcctFullStmtInqRs.CardAcctRec }
     * 
     */
    public CCAcctFullStmtInqRs.CardAcctRec createCCAcctFullStmtInqRsCardAcctRec() {
        return new CCAcctFullStmtInqRs.CardAcctRec();
    }

    /**
     * Create an instance of {@link CardAdditionalInfoRs }
     * 
     */
    public CardAdditionalInfoRs createCardAdditionalInfoRs() {
        return new CardAdditionalInfoRs();
    }

    /**
     * Create an instance of {@link AdditionalCardInfo.Cards }
     * 
     */
    public AdditionalCardInfo.Cards createAdditionalCardInfoCards() {
        return new AdditionalCardInfo.Cards();
    }

    /**
     * Create an instance of {@link BankAcctStmtImgInqRs }
     * 
     */
    public BankAcctStmtImgInqRs createBankAcctStmtImgInqRs() {
        return new BankAcctStmtImgInqRs();
    }

    /**
     * Create an instance of {@link CardReissuePlaceRs }
     * 
     */
    public CardReissuePlaceRs createCardReissuePlaceRs() {
        return new CardReissuePlaceRs();
    }

    /**
     * Create an instance of {@link CardAcctDInqRs }
     * 
     */
    public CardAcctDInqRs createCardAcctDInqRs() {
        return new CardAcctDInqRs();
    }

    /**
     * Create an instance of {@link com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.CardAcctRec }
     * 
     */
    public com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.CardAcctRec createCardAcctRec() {
        return new com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.CardAcctRec();
    }

    /**
     * Create an instance of {@link BankAcctFullStmtInqRs }
     * 
     */
    public BankAcctFullStmtInqRs createBankAcctFullStmtInqRs() {
        return new BankAcctFullStmtInqRs();
    }

    /**
     * Create an instance of {@link IMSAcctRec }
     * 
     */
    public IMSAcctRec createIMSAcctRec() {
        return new IMSAcctRec();
    }

    /**
     * Create an instance of {@link BankAcctFullStmtInfo }
     * 
     */
    public BankAcctFullStmtInfo createBankAcctFullStmtInfo() {
        return new BankAcctFullStmtInfo();
    }

    /**
     * Create an instance of {@link IMSBalanceType }
     * 
     */
    public IMSBalanceType createIMSBalanceType() {
        return new IMSBalanceType();
    }

    /**
     * Create an instance of {@link BankAcctFullStmtRec }
     * 
     */
    public BankAcctFullStmtRec createBankAcctFullStmtRec() {
        return new BankAcctFullStmtRec();
    }

    /**
     * Create an instance of {@link BankAcctStmtRec }
     * 
     */
    public BankAcctStmtRec createBankAcctStmtRec() {
        return new BankAcctStmtRec();
    }

    /**
     * Create an instance of {@link StmtSummAmt }
     * 
     */
    public StmtSummAmt createStmtSummAmt() {
        return new StmtSummAmt();
    }

    /**
     * Create an instance of {@link IMAOperConvInfoType }
     * 
     */
    public IMAOperConvInfoType createIMAOperConvInfoType() {
        return new IMAOperConvInfoType();
    }

    /**
     * Create an instance of {@link BankAcctStmtInqRs }
     * 
     */
    public BankAcctStmtInqRs createBankAcctStmtInqRs() {
        return new BankAcctStmtInqRs();
    }

    /**
     * Create an instance of {@link ImaAcctInRs }
     * 
     */
    public ImaAcctInRs createImaAcctInRs() {
        return new ImaAcctInRs();
    }

    /**
     * Create an instance of {@link ImsRec }
     * 
     */
    public ImsRec createImsRec() {
        return new ImsRec();
    }

    /**
     * Create an instance of {@link ImsAcctId }
     * 
     */
    public ImsAcctId createImsAcctId() {
        return new ImsAcctId();
    }

    /**
     * Create an instance of {@link ImsAcctInfo }
     * 
     */
    public ImsAcctInfo createImsAcctInfo() {
        return new ImsAcctInfo();
    }

    /**
     * Create an instance of {@link DepAcctExtRs }
     * 
     */
    public DepAcctExtRs createDepAcctExtRs() {
        return new DepAcctExtRs();
    }

    /**
     * Create an instance of {@link DepAcct }
     * 
     */
    public DepAcct createDepAcct() {
        return new DepAcct();
    }

    /**
     * Create an instance of {@link DepAcctStmtGen }
     * 
     */
    public DepAcctStmtGen createDepAcctStmtGen() {
        return new DepAcctStmtGen();
    }

    /**
     * Create an instance of {@link DepAcctStmtRec }
     * 
     */
    public DepAcctStmtRec createDepAcctStmtRec() {
        return new DepAcctStmtRec();
    }

    /**
     * Create an instance of {@link AcctInfoRs }
     * 
     */
    public AcctInfoRs createAcctInfoRs() {
        return new AcctInfoRs();
    }

    /**
     * Create an instance of {@link DetailAcctInfo }
     * 
     */
    public DetailAcctInfo createDetailAcctInfo() {
        return new DetailAcctInfo();
    }

    /**
     * Create an instance of {@link DepAccInfo }
     * 
     */
    public DepAccInfo createDepAccInfo() {
        return new DepAccInfo();
    }

    /**
     * Create an instance of {@link InterestOnDepositType }
     * 
     */
    public InterestOnDepositType createInterestOnDepositType() {
        return new InterestOnDepositType();
    }

    /**
     * Create an instance of {@link AcctInqRs }
     * 
     */
    public AcctInqRs createAcctInqRs() {
        return new AcctInqRs();
    }

    /**
     * Create an instance of {@link BankAcctInqRs }
     * 
     */
    public BankAcctInqRs createBankAcctInqRs() {
        return new BankAcctInqRs();
    }

    /**
     * Create an instance of {@link com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.DepAcctRec }
     * 
     */
    public com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.DepAcctRec createDepAcctRec() {
        return new com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.DepAcctRec();
    }

    /**
     * Create an instance of {@link BankAcctPermiss }
     * 
     */
    public BankAcctPermiss createBankAcctPermiss() {
        return new BankAcctPermiss();
    }

    /**
     * Create an instance of {@link BankAcctRec }
     * 
     */
    public BankAcctRec createBankAcctRec() {
        return new BankAcctRec();
    }

    /**
     * Create an instance of {@link BankAcctStatus }
     * 
     */
    public BankAcctStatus createBankAcctStatus() {
        return new BankAcctStatus();
    }

    /**
     * Create an instance of {@link CardAcctInfo }
     * 
     */
    public CardAcctInfo createCardAcctInfo() {
        return new CardAcctInfo();
    }

    /**
     * Create an instance of {@link OTPRestriction }
     * 
     */
    public OTPRestriction createOTPRestriction() {
        return new OTPRestriction();
    }

    /**
     * Create an instance of {@link UsageInfoType }
     * 
     */
    public UsageInfoType createUsageInfoType() {
        return new UsageInfoType();
    }

    /**
     * Create an instance of {@link DepoAccountsType }
     * 
     */
    public DepoAccountsType createDepoAccountsType() {
        return new DepoAccountsType();
    }

    /**
     * Create an instance of {@link SvcsAcct.SvcAcctId }
     * 
     */
    public SvcsAcct.SvcAcctId createSvcsAcctSvcAcctId() {
        return new SvcsAcct.SvcAcctId();
    }

    /**
     * Create an instance of {@link CustInqRs }
     * 
     */
    public CustInqRs createCustInqRs() {
        return new CustInqRs();
    }

    /**
     * Create an instance of {@link MessageRecvRq }
     * 
     */
    public MessageRecvRq createMessageRecvRq() {
        return new MessageRecvRq();
    }

    /**
     * Create an instance of {@link DepoAccInfoRqType }
     * 
     */
    public DepoAccInfoRqType createDepoAccInfoRqType() {
        return new DepoAccInfoRqType();
    }

    /**
     * Create an instance of {@link DepAcctStmtInqRq.DepAcctRec }
     * 
     */
    public DepAcctStmtInqRq.DepAcctRec createDepAcctStmtInqRqDepAcctRec() {
        return new DepAcctStmtInqRq.DepAcctRec();
    }

    /**
     * Create an instance of {@link GetAutoPaymentDetailInfoRq }
     * 
     */
    public GetAutoPaymentDetailInfoRq createGetAutoPaymentDetailInfoRq() {
        return new GetAutoPaymentDetailInfoRq();
    }

    /**
     * Create an instance of {@link AutoPaymentIdType }
     * 
     */
    public AutoPaymentIdType createAutoPaymentIdType() {
        return new AutoPaymentIdType();
    }

    /**
     * Create an instance of {@link BillingPayPrepRq }
     * 
     */
    public BillingPayPrepRq createBillingPayPrepRq() {
        return new BillingPayPrepRq();
    }

    /**
     * Create an instance of {@link CardUsageInfoLimitModRq }
     * 
     */
    public CardUsageInfoLimitModRq createCardUsageInfoLimitModRq() {
        return new CardUsageInfoLimitModRq();
    }

    /**
     * Create an instance of {@link AcctInqRq }
     * 
     */
    public AcctInqRq createAcctInqRq() {
        return new AcctInqRq();
    }

    /**
     * Create an instance of {@link OTPRestrictionModRq }
     * 
     */
    public OTPRestrictionModRq createOTPRestrictionModRq() {
        return new OTPRestrictionModRq();
    }

    /**
     * Create an instance of {@link CardReissuePlaceRq }
     * 
     */
    public CardReissuePlaceRq createCardReissuePlaceRq() {
        return new CardReissuePlaceRq();
    }

    /**
     * Create an instance of {@link BankInfoMod1Type }
     * 
     */
    public BankInfoMod1Type createBankInfoMod1Type() {
        return new BankInfoMod1Type();
    }

    /**
     * Create an instance of {@link BankInfoLeadZeroType }
     * 
     */
    public BankInfoLeadZeroType createBankInfoLeadZeroType() {
        return new BankInfoLeadZeroType();
    }

    /**
     * Create an instance of {@link SourceIdType }
     * 
     */
    public SourceIdType createSourceIdType() {
        return new SourceIdType();
    }

    /**
     * Create an instance of {@link DocStateUpdateRq }
     * 
     */
    public DocStateUpdateRq createDocStateUpdateRq() {
        return new DocStateUpdateRq();
    }

    /**
     * Create an instance of {@link BankAcctStmtInqRq }
     * 
     */
    public BankAcctStmtInqRq createBankAcctStmtInqRq() {
        return new BankAcctStmtInqRq();
    }

    /**
     * Create an instance of {@link Field }
     * 
     */
    public Field createField() {
        return new Field();
    }

    /**
     * Create an instance of {@link RegExp }
     * 
     */
    public RegExp createRegExp() {
        return new RegExp();
    }

    /**
     * Create an instance of {@link Menu }
     * 
     */
    public Menu createMenu() {
        return new Menu();
    }

    /**
     * Create an instance of {@link GetAutoSubscriptionDetailInfoRq }
     * 
     */
    public GetAutoSubscriptionDetailInfoRq createGetAutoSubscriptionDetailInfoRq() {
        return new GetAutoSubscriptionDetailInfoRq();
    }

    /**
     * Create an instance of {@link CardAcctDInqRq }
     * 
     */
    public CardAcctDInqRq createCardAcctDInqRq() {
        return new CardAcctDInqRq();
    }

    /**
     * Create an instance of {@link DepToNewDepAddRqType }
     * 
     */
    public DepToNewDepAddRqType createDepToNewDepAddRqType() {
        return new DepToNewDepAddRqType();
    }

    /**
     * Create an instance of {@link GetCardListByCardNumRs }
     * 
     */
    public GetCardListByCardNumRs createGetCardListByCardNumRs() {
        return new GetCardListByCardNumRs();
    }

    /**
     * Create an instance of {@link LoanPaymentRq }
     * 
     */
    public LoanPaymentRq createLoanPaymentRq() {
        return new LoanPaymentRq();
    }

    /**
     * Create an instance of {@link XferOperStatusInfoRq }
     * 
     */
    public XferOperStatusInfoRq createXferOperStatusInfoRq() {
        return new XferOperStatusInfoRq();
    }

    /**
     * Create an instance of {@link SvcAddRq }
     * 
     */
    public SvcAddRq createSvcAddRq() {
        return new SvcAddRq();
    }

    /**
     * Create an instance of {@link CustId }
     * 
     */
    public CustId createCustId() {
        return new CustId();
    }

    /**
     * Create an instance of {@link DocInfo }
     * 
     */
    public DocInfo createDocInfo() {
        return new DocInfo();
    }

    /**
     * Create an instance of {@link DepoClientRegRq }
     * 
     */
    public DepoClientRegRq createDepoClientRegRq() {
        return new DepoClientRegRq();
    }

    /**
     * Create an instance of {@link SelRangeDt }
     * 
     */
    public SelRangeDt createSelRangeDt() {
        return new SelRangeDt();
    }

    /**
     * Create an instance of {@link CardToIMAAddRq }
     * 
     */
    public CardToIMAAddRq createCardToIMAAddRq() {
        return new CardToIMAAddRq();
    }

    /**
     * Create an instance of {@link DepoRevokeDocRq }
     * 
     */
    public DepoRevokeDocRq createDepoRevokeDocRq() {
        return new DepoRevokeDocRq();
    }

    /**
     * Create an instance of {@link OperInfoType }
     * 
     */
    public OperInfoType createOperInfoType() {
        return new OperInfoType();
    }

    /**
     * Create an instance of {@link BankAcctInqRq }
     * 
     */
    public BankAcctInqRq createBankAcctInqRq() {
        return new BankAcctInqRq();
    }

    /**
     * Create an instance of {@link CCAcctFullStmtInqRq }
     * 
     */
    public CCAcctFullStmtInqRq createCCAcctFullStmtInqRq() {
        return new CCAcctFullStmtInqRq();
    }

    /**
     * Create an instance of {@link ChargeLoanApplicationRq }
     * 
     */
    public ChargeLoanApplicationRq createChargeLoanApplicationRq() {
        return new ChargeLoanApplicationRq();
    }

    /**
     * Create an instance of {@link ApplicationType }
     * 
     */
    public ApplicationType createApplicationType() {
        return new ApplicationType();
    }

    /**
     * Create an instance of {@link CardBlockRq }
     * 
     */
    public CardBlockRq createCardBlockRq() {
        return new CardBlockRq();
    }

    /**
     * Create an instance of {@link BankAcctStmtImgInqRq }
     * 
     */
    public BankAcctStmtImgInqRq createBankAcctStmtImgInqRq() {
        return new BankAcctStmtImgInqRq();
    }

    /**
     * Create an instance of {@link LoanInfoRq }
     * 
     */
    public LoanInfoRq createLoanInfoRq() {
        return new LoanInfoRq();
    }

    /**
     * Create an instance of {@link DeptRec }
     * 
     */
    public DeptRec createDeptRec() {
        return new DeptRec();
    }

    /**
     * Create an instance of {@link GetAutoPaymentListRq }
     * 
     */
    public GetAutoPaymentListRq createGetAutoPaymentListRq() {
        return new GetAutoPaymentListRq();
    }

    /**
     * Create an instance of {@link PaymentStatusListType }
     * 
     */
    public PaymentStatusListType createPaymentStatusListType() {
        return new PaymentStatusListType();
    }

    /**
     * Create an instance of {@link SelRangeDtTmType }
     * 
     */
    public SelRangeDtTmType createSelRangeDtTmType() {
        return new SelRangeDtTmType();
    }

    /**
     * Create an instance of {@link GetInsuranceListRq }
     * 
     */
    public GetInsuranceListRq createGetInsuranceListRq() {
        return new GetInsuranceListRq();
    }

    /**
     * Create an instance of {@link ImaAcctInRq }
     * 
     */
    public ImaAcctInRq createImaAcctInRq() {
        return new ImaAcctInRq();
    }

    /**
     * Create an instance of {@link XferAddRq }
     * 
     */
    public XferAddRq createXferAddRq() {
        return new XferAddRq();
    }

    /**
     * Create an instance of {@link PayInfo.AcctId }
     * 
     */
    public PayInfo.AcctId createPayInfoAcctId() {
        return new PayInfo.AcctId();
    }

    /**
     * Create an instance of {@link BillingPayExecRq }
     * 
     */
    public BillingPayExecRq createBillingPayExecRq() {
        return new BillingPayExecRq();
    }

    /**
     * Create an instance of {@link CustInqRq }
     * 
     */
    public CustInqRq createCustInqRq() {
        return new CustInqRq();
    }

    /**
     * Create an instance of {@link SetAccountStateRq }
     * 
     */
    public SetAccountStateRq createSetAccountStateRq() {
        return new SetAccountStateRq();
    }

    /**
     * Create an instance of {@link AcctInfoRq }
     * 
     */
    public AcctInfoRq createAcctInfoRq() {
        return new AcctInfoRq();
    }

    /**
     * Create an instance of {@link DepoDeptDetInfoRq }
     * 
     */
    public DepoDeptDetInfoRq createDepoDeptDetInfoRq() {
        return new DepoDeptDetInfoRq();
    }

    /**
     * Create an instance of {@link DeptIdType }
     * 
     */
    public DeptIdType createDeptIdType() {
        return new DeptIdType();
    }

    /**
     * Create an instance of {@link CCAcctStmtRec.OrigCurAmt }
     * 
     */
    public CCAcctStmtRec.OrigCurAmt createCCAcctStmtRecOrigCurAmt() {
        return new CCAcctStmtRec.OrigCurAmt();
    }

    /**
     * Create an instance of {@link CCAcctStmtRec.OperationAmt }
     * 
     */
    public CCAcctStmtRec.OperationAmt createCCAcctStmtRecOperationAmt() {
        return new CCAcctStmtRec.OperationAmt();
    }

    /**
     * Create an instance of {@link CCAcctStmtRec.RemaindAmt }
     * 
     */
    public CCAcctStmtRec.RemaindAmt createCCAcctStmtRecRemaindAmt() {
        return new CCAcctStmtRec.RemaindAmt();
    }

    /**
     * Create an instance of {@link DepInfo }
     * 
     */
    public DepInfo createDepInfo() {
        return new DepInfo();
    }

    /**
     * Create an instance of {@link IntRateType }
     * 
     */
    public IntRateType createIntRateType() {
        return new IntRateType();
    }

    /**
     * Create an instance of {@link BankAcctPermissModRq }
     * 
     */
    public BankAcctPermissModRq createBankAcctPermissModRq() {
        return new BankAcctPermissModRq();
    }

    /**
     * Create an instance of {@link GetCardListByCardNumRq }
     * 
     */
    public GetCardListByCardNumRq createGetCardListByCardNumRq() {
        return new GetCardListByCardNumRq();
    }

    /**
     * Create an instance of {@link BankAcctFullStmtInqRq }
     * 
     */
    public BankAcctFullStmtInqRq createBankAcctFullStmtInqRq() {
        return new BankAcctFullStmtInqRq();
    }

    /**
     * Create an instance of {@link PfrHasInfoInqRq }
     * 
     */
    public PfrHasInfoInqRq createPfrHasInfoInqRq() {
        return new PfrHasInfoInqRq();
    }

    /**
     * Create an instance of {@link Requisite }
     * 
     */
    public Requisite createRequisite() {
        return new Requisite();
    }

    /**
     * Create an instance of {@link RequisiteTypesType }
     * 
     */
    public RequisiteTypesType createRequisiteTypesType() {
        return new RequisiteTypesType();
    }

    /**
     * Create an instance of {@link AttributeLengthType }
     * 
     */
    public AttributeLengthType createAttributeLengthType() {
        return new AttributeLengthType();
    }

    /**
     * Create an instance of {@link ValidatorsType }
     * 
     */
    public ValidatorsType createValidatorsType() {
        return new ValidatorsType();
    }

    /**
     * Create an instance of {@link EnteredDataType }
     * 
     */
    public EnteredDataType createEnteredDataType() {
        return new EnteredDataType();
    }

    /**
     * Create an instance of {@link DepToNewIMAAddRq }
     * 
     */
    public DepToNewIMAAddRq createDepToNewIMAAddRq() {
        return new DepToNewIMAAddRq();
    }

    /**
     * Create an instance of {@link SvcAcctAudRq.SvcAcct }
     * 
     */
    public SvcAcctAudRq.SvcAcct createSvcAcctAudRqSvcAcct() {
        return new SvcAcctAudRq.SvcAcct();
    }

    /**
     * Create an instance of {@link AccStopDocRq }
     * 
     */
    public AccStopDocRq createAccStopDocRq() {
        return new AccStopDocRq();
    }

    /**
     * Create an instance of {@link GetInsuranceAppRq }
     * 
     */
    public GetInsuranceAppRq createGetInsuranceAppRq() {
        return new GetInsuranceAppRq();
    }

    /**
     * Create an instance of {@link AutoSubscriptionStatusModRq }
     * 
     */
    public AutoSubscriptionStatusModRq createAutoSubscriptionStatusModRq() {
        return new AutoSubscriptionStatusModRq();
    }

    /**
     * Create an instance of {@link BillingPayInqRq }
     * 
     */
    public BillingPayInqRq createBillingPayInqRq() {
        return new BillingPayInqRq();
    }

    /**
     * Create an instance of {@link CardAccount }
     * 
     */
    public CardAccount createCardAccount() {
        return new CardAccount();
    }

    /**
     * Create an instance of {@link ServiceStmtRq }
     * 
     */
    public ServiceStmtRq createServiceStmtRq() {
        return new ServiceStmtRq();
    }

    /**
     * Create an instance of {@link DepoDeptCardPayRq }
     * 
     */
    public DepoDeptCardPayRq createDepoDeptCardPayRq() {
        return new DepoDeptCardPayRq();
    }

    /**
     * Create an instance of {@link DoIFXRq }
     * 
     */
    public DoIFXRq createDoIFXRq() {
        return new DoIFXRq();
    }

    /**
     * Create an instance of {@link CardToNewIMAAddRq }
     * 
     */
    public CardToNewIMAAddRq createCardToNewIMAAddRq() {
        return new CardToNewIMAAddRq();
    }

    /**
     * Create an instance of {@link GetAutoSubscriptionListRq }
     * 
     */
    public GetAutoSubscriptionListRq createGetAutoSubscriptionListRq() {
        return new GetAutoSubscriptionListRq();
    }

    /**
     * Create an instance of {@link AutopayStatusListType }
     * 
     */
    public AutopayStatusListType createAutopayStatusListType() {
        return new AutopayStatusListType();
    }

    /**
     * Create an instance of {@link PfrGetInfoInqRq }
     * 
     */
    public PfrGetInfoInqRq createPfrGetInfoInqRq() {
        return new PfrGetInfoInqRq();
    }

    /**
     * Create an instance of {@link MDMClientInfoUpdateRq }
     * 
     */
    public MDMClientInfoUpdateRq createMDMClientInfoUpdateRq() {
        return new MDMClientInfoUpdateRq();
    }

    /**
     * Create an instance of {@link UserInfoType }
     * 
     */
    public UserInfoType createUserInfoType() {
        return new UserInfoType();
    }

    /**
     * Create an instance of {@link DepoAccSecRegRq }
     * 
     */
    public DepoAccSecRegRq createDepoAccSecRegRq() {
        return new DepoAccSecRegRq();
    }

    /**
     * Create an instance of {@link DepoSecurityOperationInfoType }
     * 
     */
    public DepoSecurityOperationInfoType createDepoSecurityOperationInfoType() {
        return new DepoSecurityOperationInfoType();
    }

    /**
     * Create an instance of {@link DepoAccTranRq }
     * 
     */
    public DepoAccTranRq createDepoAccTranRq() {
        return new DepoAccTranRq();
    }

    /**
     * Create an instance of {@link TransferInfoType }
     * 
     */
    public TransferInfoType createTransferInfoType() {
        return new TransferInfoType();
    }

    /**
     * Create an instance of {@link CardAdditionalInfoRq }
     * 
     */
    public CardAdditionalInfoRq createCardAdditionalInfoRq() {
        return new CardAdditionalInfoRq();
    }

    /**
     * Create an instance of {@link IMAInfo }
     * 
     */
    public IMAInfo createIMAInfo() {
        return new IMAInfo();
    }

    /**
     * Create an instance of {@link MinBankInfoType }
     * 
     */
    public MinBankInfoType createMinBankInfoType() {
        return new MinBankInfoType();
    }

    /**
     * Create an instance of {@link LoanAcctIdDepoType }
     * 
     */
    public LoanAcctIdDepoType createLoanAcctIdDepoType() {
        return new LoanAcctIdDepoType();
    }

    /**
     * Create an instance of {@link PhoneNumType }
     * 
     */
    public PhoneNumType createPhoneNumType() {
        return new PhoneNumType();
    }

    /**
     * Create an instance of {@link RealEstateType }
     * 
     */
    public RealEstateType createRealEstateType() {
        return new RealEstateType();
    }

    /**
     * Create an instance of {@link LoanIssueType }
     * 
     */
    public LoanIssueType createLoanIssueType() {
        return new LoanIssueType();
    }

    /**
     * Create an instance of {@link ValidatorType }
     * 
     */
    public ValidatorType createValidatorType() {
        return new ValidatorType();
    }

    /**
     * Create an instance of {@link SecurityMarkerType }
     * 
     */
    public SecurityMarkerType createSecurityMarkerType() {
        return new SecurityMarkerType();
    }

    /**
     * Create an instance of {@link DepoDetailOperationReasonType }
     * 
     */
    public DepoDetailOperationReasonType createDepoDetailOperationReasonType() {
        return new DepoDetailOperationReasonType();
    }

    /**
     * Create an instance of {@link PlasticInfoType }
     * 
     */
    public PlasticInfoType createPlasticInfoType() {
        return new PlasticInfoType();
    }

    /**
     * Create an instance of {@link ChangeStatusType }
     * 
     */
    public ChangeStatusType createChangeStatusType() {
        return new ChangeStatusType();
    }

    /**
     * Create an instance of {@link MaritalConditionType }
     * 
     */
    public MaritalConditionType createMaritalConditionType() {
        return new MaritalConditionType();
    }

    /**
     * Create an instance of {@link DepoSecurityOperationListType }
     * 
     */
    public DepoSecurityOperationListType createDepoSecurityOperationListType() {
        return new DepoSecurityOperationListType();
    }

    /**
     * Create an instance of {@link CorrOwnerDetailType }
     * 
     */
    public CorrOwnerDetailType createCorrOwnerDetailType() {
        return new CorrOwnerDetailType();
    }

    /**
     * Create an instance of {@link DepoSecurityDictInfoType }
     * 
     */
    public DepoSecurityDictInfoType createDepoSecurityDictInfoType() {
        return new DepoSecurityDictInfoType();
    }

    /**
     * Create an instance of {@link EmploymentHistoryType }
     * 
     */
    public EmploymentHistoryType createEmploymentHistoryType() {
        return new EmploymentHistoryType();
    }

    /**
     * Create an instance of {@link SalaryDepType }
     * 
     */
    public SalaryDepType createSalaryDepType() {
        return new SalaryDepType();
    }

    /**
     * Create an instance of {@link IncomeType }
     * 
     */
    public IncomeType createIncomeType() {
        return new IncomeType();
    }

    /**
     * Create an instance of {@link DepoRecMethodrType }
     * 
     */
    public DepoRecMethodrType createDepoRecMethodrType() {
        return new DepoRecMethodrType();
    }

    /**
     * Create an instance of {@link ApplicantPersonInfoType }
     * 
     */
    public ApplicantPersonInfoType createApplicantPersonInfoType() {
        return new ApplicantPersonInfoType();
    }

    /**
     * Create an instance of {@link LoanType }
     * 
     */
    public LoanType createLoanType() {
        return new LoanType();
    }

    /**
     * Create an instance of {@link DepoAcctInfoType }
     * 
     */
    public DepoAcctInfoType createDepoAcctInfoType() {
        return new DepoAcctInfoType();
    }

    /**
     * Create an instance of {@link DivisionNumberType }
     * 
     */
    public DivisionNumberType createDivisionNumberType() {
        return new DivisionNumberType();
    }

    /**
     * Create an instance of {@link DepoSecurityRecType }
     * 
     */
    public DepoSecurityRecType createDepoSecurityRecType() {
        return new DepoSecurityRecType();
    }

    /**
     * Create an instance of {@link SpouseInfoType }
     * 
     */
    public SpouseInfoType createSpouseInfoType() {
        return new SpouseInfoType();
    }

    /**
     * Create an instance of {@link PreviousNameDataType }
     * 
     */
    public PreviousNameDataType createPreviousNameDataType() {
        return new PreviousNameDataType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link DepoAcctResType }
     * 
     */
    public DepoAcctResType createDepoAcctResType() {
        return new DepoAcctResType();
    }

    /**
     * Create an instance of {@link IMAInfoResponseType }
     * 
     */
    public IMAInfoResponseType createIMAInfoResponseType() {
        return new IMAInfoResponseType();
    }

    /**
     * Create an instance of {@link VehicleType }
     * 
     */
    public VehicleType createVehicleType() {
        return new VehicleType();
    }

    /**
     * Create an instance of {@link ShareHolderType }
     * 
     */
    public ShareHolderType createShareHolderType() {
        return new ShareHolderType();
    }

    /**
     * Create an instance of {@link ExecStatusType }
     * 
     */
    public ExecStatusType createExecStatusType() {
        return new ExecStatusType();
    }

    /**
     * Create an instance of {@link CardAcctDepoInfoType }
     * 
     */
    public CardAcctDepoInfoType createCardAcctDepoInfoType() {
        return new CardAcctDepoInfoType();
    }

    /**
     * Create an instance of {@link AutoPaymentInfoType }
     * 
     */
    public AutoPaymentInfoType createAutoPaymentInfoType() {
        return new AutoPaymentInfoType();
    }

    /**
     * Create an instance of {@link DepoAcctBalRecType }
     * 
     */
    public DepoAcctBalRecType createDepoAcctBalRecType() {
        return new DepoAcctBalRecType();
    }

    /**
     * Create an instance of {@link DepoAgreementType }
     * 
     */
    public DepoAgreementType createDepoAgreementType() {
        return new DepoAgreementType();
    }

    /**
     * Create an instance of {@link SBEmployeeType }
     * 
     */
    public SBEmployeeType createSBEmployeeType() {
        return new SBEmployeeType();
    }

    /**
     * Create an instance of {@link DepInfoResponseType }
     * 
     */
    public DepInfoResponseType createDepInfoResponseType() {
        return new DepInfoResponseType();
    }

    /**
     * Create an instance of {@link TransferRcpInfoType }
     * 
     */
    public TransferRcpInfoType createTransferRcpInfoType() {
        return new TransferRcpInfoType();
    }

    /**
     * Create an instance of {@link CustStatusType }
     * 
     */
    public CustStatusType createCustStatusType() {
        return new CustStatusType();
    }

    /**
     * Create an instance of {@link ProductDataType }
     * 
     */
    public ProductDataType createProductDataType() {
        return new ProductDataType();
    }

    /**
     * Create an instance of {@link RelativeInfoType }
     * 
     */
    public RelativeInfoType createRelativeInfoType() {
        return new RelativeInfoType();
    }

    /**
     * Create an instance of {@link SalaryCardType }
     * 
     */
    public SalaryCardType createSalaryCardType() {
        return new SalaryCardType();
    }

    /**
     * Create an instance of {@link DepoSecuritySectionInfoType }
     * 
     */
    public DepoSecuritySectionInfoType createDepoSecuritySectionInfoType() {
        return new DepoSecuritySectionInfoType();
    }

    /**
     * Create an instance of {@link IMAAcctIdType }
     * 
     */
    public IMAAcctIdType createIMAAcctIdType() {
        return new IMAAcctIdType();
    }

    /**
     * Create an instance of {@link DepoDeptResZadType }
     * 
     */
    public DepoDeptResZadType createDepoDeptResZadType() {
        return new DepoDeptResZadType();
    }

    /**
     * Create an instance of {@link DepoDeptResType }
     * 
     */
    public DepoDeptResType createDepoDeptResType() {
        return new DepoDeptResType();
    }

    /**
     * Create an instance of {@link OrgInfoExtType }
     * 
     */
    public OrgInfoExtType createOrgInfoExtType() {
        return new OrgInfoExtType();
    }

    /**
     * Create an instance of {@link PassportType }
     * 
     */
    public PassportType createPassportType() {
        return new PassportType();
    }

    /**
     * Create an instance of {@link PrevPassportType }
     * 
     */
    public PrevPassportType createPrevPassportType() {
        return new PrevPassportType();
    }

    /**
     * Create an instance of {@link PhoneType }
     * 
     */
    public PhoneType createPhoneType() {
        return new PhoneType();
    }

    /**
     * Create an instance of {@link PaymentTempType }
     * 
     */
    public PaymentTempType createPaymentTempType() {
        return new PaymentTempType();
    }

    /**
     * Create an instance of {@link IndustSectorType }
     * 
     */
    public IndustSectorType createIndustSectorType() {
        return new IndustSectorType();
    }

    /**
     * Create an instance of {@link DepoBankAcctInfoType }
     * 
     */
    public DepoBankAcctInfoType createDepoBankAcctInfoType() {
        return new DepoBankAcctInfoType();
    }

    /**
     * Create an instance of {@link EducationDataType }
     * 
     */
    public EducationDataType createEducationDataType() {
        return new EducationDataType();
    }

    /**
     * Create an instance of {@link DepoCorrOwnerDetailType }
     * 
     */
    public DepoCorrOwnerDetailType createDepoCorrOwnerDetailType() {
        return new DepoCorrOwnerDetailType();
    }

    /**
     * Create an instance of {@link ApplicantAddDataType.SalaryCardList }
     * 
     */
    public ApplicantAddDataType.SalaryCardList createApplicantAddDataTypeSalaryCardList() {
        return new ApplicantAddDataType.SalaryCardList();
    }

    /**
     * Create an instance of {@link ApplicantAddDataType.SalaryDepList }
     * 
     */
    public ApplicantAddDataType.SalaryDepList createApplicantAddDataTypeSalaryDepList() {
        return new ApplicantAddDataType.SalaryDepList();
    }

    /**
     * Create an instance of {@link ApplicantDataType.RelativeList }
     * 
     */
    public ApplicantDataType.RelativeList createApplicantDataTypeRelativeList() {
        return new ApplicantDataType.RelativeList();
    }

    /**
     * Create an instance of {@link ApplicantDataType.RealEstateList }
     * 
     */
    public ApplicantDataType.RealEstateList createApplicantDataTypeRealEstateList() {
        return new ApplicantDataType.RealEstateList();
    }

    /**
     * Create an instance of {@link ApplicantDataType.VehicleList }
     * 
     */
    public ApplicantDataType.VehicleList createApplicantDataTypeVehicleList() {
        return new ApplicantDataType.VehicleList();
    }

    /**
     * Create an instance of {@link ApplicantDataType.LoanList }
     * 
     */
    public ApplicantDataType.LoanList createApplicantDataTypeLoanList() {
        return new ApplicantDataType.LoanList();
    }

    /**
     * Create an instance of {@link EmploymentHistoryType2 .EmployeeInfo }
     * 
     */
    public EmploymentHistoryType2 .EmployeeInfo createEmploymentHistoryType2EmployeeInfo() {
        return new EmploymentHistoryType2 .EmployeeInfo();
    }

    /**
     * Create an instance of {@link ContactType.PhoneList }
     * 
     */
    public ContactType.PhoneList createContactTypePhoneList() {
        return new ContactType.PhoneList();
    }

    /**
     * Create an instance of {@link ContactType.AddressList }
     * 
     */
    public ContactType.AddressList createContactTypeAddressList() {
        return new ContactType.AddressList();
    }

    /**
     * Create an instance of {@link DepoBankAccountAdditionalInfoType.RecInstructionMethods }
     * 
     */
    public DepoBankAccountAdditionalInfoType.RecInstructionMethods createDepoBankAccountAdditionalInfoTypeRecInstructionMethods() {
        return new DepoBankAccountAdditionalInfoType.RecInstructionMethods();
    }

    /**
     * Create an instance of {@link DepoBankAccountAdditionalInfoType.RecInfoMethods }
     * 
     */
    public DepoBankAccountAdditionalInfoType.RecInfoMethods createDepoBankAccountAdditionalInfoTypeRecInfoMethods() {
        return new DepoBankAccountAdditionalInfoType.RecInfoMethods();
    }

    /**
     * Create an instance of {@link ApplicationStatusType.Approval }
     * 
     */
    public ApplicationStatusType.Approval createApplicationStatusTypeApproval() {
        return new ApplicationStatusType.Approval();
    }

    /**
     * Create an instance of {@link ApplicationStatusType.Status.Error }
     * 
     */
    public ApplicationStatusType.Status.Error createApplicationStatusTypeStatusError() {
        return new ApplicationStatusType.Status.Error();
    }

    /**
     * Create an instance of {@link IntegrationInfoType.IntegrationId }
     * 
     */
    public IntegrationInfoType.IntegrationId createIntegrationInfoTypeIntegrationId() {
        return new IntegrationInfoType.IntegrationId();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RqUID")
    public JAXBElement<String> createRqUID(String value) {
        return new JAXBElement<String>(_RqUID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ServerStatusDesc")
    public JAXBElement<String> createServerStatusDesc(String value) {
        return new JAXBElement<String>(_ServerStatusDesc_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoanAcctIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "LoanAcctId")
    public JAXBElement<LoanAcctIdType> createLoanAcctId(LoanAcctIdType value) {
        return new JAXBElement<LoanAcctIdType>(_LoanAcctId_QNAME, LoanAcctIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "OrigAmt")
    public JAXBElement<BigDecimal> createOrigAmt(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_OrigAmt_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AcctId")
    public JAXBElement<String> createAcctId(String value) {
        return new JAXBElement<String>(_AcctId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepAcctIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DepAcctIdFrom")
    public JAXBElement<DepAcctIdType> createDepAcctIdFrom(DepAcctIdType value) {
        return new JAXBElement<DepAcctIdType>(_DepAcctIdFrom_QNAME, DepAcctIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepToNewDepAddRsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NewDepAddRs")
    public JAXBElement<DepToNewDepAddRsType> createNewDepAddRs(DepToNewDepAddRsType value) {
        return new JAXBElement<DepToNewDepAddRsType>(_NewDepAddRs_QNAME, DepToNewDepAddRsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AcctTypeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AcctType")
    public JAXBElement<AcctTypeType> createAcctType(AcctTypeType value) {
        return new JAXBElement<AcctTypeType>(_AcctType_QNAME, AcctTypeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FirstName")
    public JAXBElement<String> createFirstName(String value) {
        return new JAXBElement<String>(_FirstName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AgreemtInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AgreemtInfoClose")
    public JAXBElement<AgreemtInfoType> createAgreemtInfoClose(AgreemtInfoType value) {
        return new JAXBElement<AgreemtInfoType>(_AgreemtInfoClose_QNAME, AgreemtInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PurchaseYear")
    public JAXBElement<Long> createPurchaseYear(Long value) {
        return new JAXBElement<Long>(_PurchaseYear_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "HeaderInfo")
    public JAXBElement<String> createHeaderInfo(String value) {
        return new JAXBElement<String>(_HeaderInfo_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepToNewDepAddRsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DepToNewDepAddRs")
    public JAXBElement<DepToNewDepAddRsType> createDepToNewDepAddRs(DepToNewDepAddRsType value) {
        return new JAXBElement<DepToNewDepAddRsType>(_DepToNewDepAddRs_QNAME, DepToNewDepAddRsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IssuedBy")
    public JAXBElement<String> createIssuedBy(String value) {
        return new JAXBElement<String>(_IssuedBy_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AcctCur")
    public JAXBElement<String> createAcctCur(String value) {
        return new JAXBElement<String>(_AcctCur_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ProdType")
    public JAXBElement<String> createProdType(String value) {
        return new JAXBElement<String>(_ProdType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepoAccInfoRqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DepoAccSecInfoRq")
    public JAXBElement<DepoAccInfoRqType> createDepoAccSecInfoRq(DepoAccInfoRqType value) {
        return new JAXBElement<DepoAccInfoRqType>(_DepoAccSecInfoRq_QNAME, DepoAccInfoRqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Gender")
    public JAXBElement<String> createGender(String value) {
        return new JAXBElement<String>(_Gender_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AgencyId")
    public JAXBElement<String> createAgencyId(String value) {
        return new JAXBElement<String>(_AgencyId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SPNum")
    public JAXBElement<String> createSPNum(String value) {
        return new JAXBElement<String>(_SPNum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "LoanType")
    public JAXBElement<String> createLoanType(String value) {
        return new JAXBElement<String>(_LoanType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AgreemtNum")
    public JAXBElement<String> createAgreemtNum(String value) {
        return new JAXBElement<String>(_AgreemtNum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StmtSummType")
    public JAXBElement<String> createStmtSummType(String value) {
        return new JAXBElement<String>(_StmtSummType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IdNum")
    public JAXBElement<String> createIdNum(String value) {
        return new JAXBElement<String>(_IdNum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AutopayStatusType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AutopayStatus")
    public JAXBElement<AutopayStatusType> createAutopayStatus(AutopayStatusType value) {
        return new JAXBElement<AutopayStatusType>(_AutopayStatus_QNAME, AutopayStatusType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DstCurAmt")
    public JAXBElement<BigDecimal> createDstCurAmt(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_DstCurAmt_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Calendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StartDt")
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    public JAXBElement<Calendar> createStartDt(Calendar value) {
        return new JAXBElement<Calendar>(_StartDt_QNAME, Calendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StatusType")
    public JAXBElement<String> createStatusType(String value) {
        return new JAXBElement<String>(_StatusType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "BalType")
    public JAXBElement<String> createBalType(String value) {
        return new JAXBElement<String>(_BalType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "MiddleName")
    public JAXBElement<String> createMiddleName(String value) {
        return new JAXBElement<String>(_MiddleName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepoAccInfoRqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DepoDeptsInfoRq")
    public JAXBElement<DepoAccInfoRqType> createDepoDeptsInfoRq(DepoAccInfoRqType value) {
        return new JAXBElement<DepoAccInfoRqType>(_DepoDeptsInfoRq_QNAME, DepoAccInfoRqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SystemId")
    public JAXBElement<String> createSystemId(String value) {
        return new JAXBElement<String>(_SystemId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SPNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SPName")
    public JAXBElement<SPNameType> createSPName(SPNameType value) {
        return new JAXBElement<SPNameType>(_SPName_QNAME, SPNameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CardAcctIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CardAcctIdFrom")
    public JAXBElement<CardAcctIdType> createCardAcctIdFrom(CardAcctIdType value) {
        return new JAXBElement<CardAcctIdType>(_CardAcctIdFrom_QNAME, CardAcctIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepToNewDepAddRqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DepToNewDepAddRq")
    public JAXBElement<DepToNewDepAddRqType> createDepToNewDepAddRq(DepToNewDepAddRqType value) {
        return new JAXBElement<DepToNewDepAddRqType>(_DepToNewDepAddRq_QNAME, DepToNewDepAddRqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Calendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Birthday")
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    public JAXBElement<Calendar> createBirthday(Calendar value) {
        return new JAXBElement<Calendar>(_Birthday_QNAME, Calendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepoAccInfoRqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DepoArRq")
    public JAXBElement<DepoAccInfoRqType> createDepoArRq(DepoAccInfoRqType value) {
        return new JAXBElement<DepoAccInfoRqType>(_DepoArRq_QNAME, DepoAccInfoRqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Calendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IssueDt")
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    public JAXBElement<Calendar> createIssueDt(Calendar value) {
        return new JAXBElement<Calendar>(_IssueDt_QNAME, Calendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CardAcctIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CardAcctIdTo")
    public JAXBElement<CardAcctIdType> createCardAcctIdTo(CardAcctIdType value) {
        return new JAXBElement<CardAcctIdType>(_CardAcctIdTo_QNAME, CardAcctIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IdType")
    public JAXBElement<String> createIdType(String value) {
        return new JAXBElement<String>(_IdType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CustPermId")
    public JAXBElement<String> createCustPermId(String value) {
        return new JAXBElement<String>(_CustPermId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FieldValue")
    public JAXBElement<Boolean> createFieldValue(Boolean value) {
        return new JAXBElement<Boolean>(_FieldValue_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "BranchId")
    public JAXBElement<String> createBranchId(String value) {
        return new JAXBElement<String>(_BranchId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoanAcctIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "LoanAcctIdTo")
    public JAXBElement<LoanAcctIdType> createLoanAcctIdTo(LoanAcctIdType value) {
        return new JAXBElement<LoanAcctIdType>(_LoanAcctIdTo_QNAME, LoanAcctIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StmtTypeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StmtType")
    public JAXBElement<StmtTypeType> createStmtType(StmtTypeType value) {
        return new JAXBElement<StmtTypeType>(_StmtType_QNAME, StmtTypeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepAcctIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DepAcctIdTo")
    public JAXBElement<DepAcctIdType> createDepAcctIdTo(DepAcctIdType value) {
        return new JAXBElement<DepAcctIdType>(_DepAcctIdTo_QNAME, DepAcctIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Calendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RqTm")
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    public JAXBElement<Calendar> createRqTm(Calendar value) {
        return new JAXBElement<Calendar>(_RqTm_QNAME, Calendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepoAccInfoRqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DepoAccInfoRq")
    public JAXBElement<DepoAccInfoRqType> createDepoAccInfoRq(DepoAccInfoRqType value) {
        return new JAXBElement<DepoAccInfoRqType>(_DepoAccInfoRq_QNAME, DepoAccInfoRqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CurAmt")
    public JAXBElement<BigDecimal> createCurAmt(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_CurAmt_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CardNum")
    public JAXBElement<String> createCardNum(String value) {
        return new JAXBElement<String>(_CardNum_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Long }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StatusCode")
    public JAXBElement<Long> createStatusCode(Long value) {
        return new JAXBElement<Long>(_StatusCode_QNAME, Long.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AgreemtInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "AgreemtInfo")
    public JAXBElement<AgreemtInfoType> createAgreemtInfo(AgreemtInfoType value) {
        return new JAXBElement<AgreemtInfoType>(_AgreemtInfo_QNAME, AgreemtInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SrcCurAmt")
    public JAXBElement<BigDecimal> createSrcCurAmt(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_SrcCurAmt_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "BankAcctStatusCode")
    public JAXBElement<String> createBankAcctStatusCode(String value) {
        return new JAXBElement<String>(_BankAcctStatusCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "OperUID")
    public JAXBElement<String> createOperUID(String value) {
        return new JAXBElement<String>(_OperUID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SBCreditFlag")
    public JAXBElement<String> createSBCreditFlag(String value) {
        return new JAXBElement<String>(_SBCreditFlag_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RbTbBrchId")
    public JAXBElement<String> createRbTbBrchId(String value) {
        return new JAXBElement<String>(_RbTbBrchId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RbBrchId")
    public JAXBElement<String> createRbBrchId(String value) {
        return new JAXBElement<String>(_RbBrchId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FieldName")
    public JAXBElement<String> createFieldName(String value) {
        return new JAXBElement<String>(_FieldName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SBEmployeeFlag")
    public JAXBElement<Boolean> createSBEmployeeFlag(Boolean value) {
        return new JAXBElement<Boolean>(_SBEmployeeFlag_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CommisionCur")
    public JAXBElement<String> createCommisionCur(String value) {
        return new JAXBElement<String>(_CommisionCur_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Calendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EffDate")
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    public JAXBElement<Calendar> createEffDate(Calendar value) {
        return new JAXBElement<Calendar>(_EffDate_QNAME, Calendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Commision")
    public JAXBElement<BigDecimal> createCommision(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_Commision_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "RegionId")
    public JAXBElement<String> createRegionId(String value) {
        return new JAXBElement<String>(_RegionId_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IdSeries")
    public JAXBElement<String> createIdSeries(String value) {
        return new JAXBElement<String>(_IdSeries_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CardName")
    public JAXBElement<String> createCardName(String value) {
        return new JAXBElement<String>(_CardName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "BillingCode")
    public JAXBElement<String> createBillingCode(String value) {
        return new JAXBElement<String>(_BillingCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Calendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "EndDt")
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    public JAXBElement<Calendar> createEndDt(Calendar value) {
        return new JAXBElement<Calendar>(_EndDt_QNAME, Calendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonNameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "PersonName")
    public JAXBElement<PersonNameType> createPersonName(PersonNameType value) {
        return new JAXBElement<PersonNameType>(_PersonName_QNAME, PersonNameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Calendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "ExpDt")
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    public JAXBElement<Calendar> createExpDt(Calendar value) {
        return new JAXBElement<Calendar>(_ExpDt_QNAME, Calendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DependentFlag")
    public JAXBElement<Boolean> createDependentFlag(Boolean value) {
        return new JAXBElement<Boolean>(_DependentFlag_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CardAcctIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CardAcctId")
    public JAXBElement<CardAcctIdType> createCardAcctId(CardAcctIdType value) {
        return new JAXBElement<CardAcctIdType>(_CardAcctId_QNAME, CardAcctIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepAcctIdType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DepAcctId")
    public JAXBElement<DepAcctIdType> createDepAcctId(DepAcctIdType value) {
        return new JAXBElement<DepAcctIdType>(_DepAcctId_QNAME, DepAcctIdType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DepToNewDepAddRqType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "NewDepAddRq")
    public JAXBElement<DepToNewDepAddRqType> createNewDepAddRq(DepToNewDepAddRqType value) {
        return new JAXBElement<DepToNewDepAddRqType>(_NewDepAddRq_QNAME, DepToNewDepAddRqType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "IrreducibleAmt")
    public JAXBElement<BigDecimal> createIrreducibleAmt(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_IrreducibleAmt_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "StatusDesc")
    public JAXBElement<String> createStatusDesc(String value) {
        return new JAXBElement<String>(_StatusDesc_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "LastName")
    public JAXBElement<String> createLastName(String value) {
        return new JAXBElement<String>(_LastName_QNAME, String.class, null, value);
    }

}
