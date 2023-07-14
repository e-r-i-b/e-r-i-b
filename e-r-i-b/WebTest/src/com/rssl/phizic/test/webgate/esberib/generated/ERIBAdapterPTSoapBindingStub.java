/**
 * ERIBAdapterPTSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.webgate.esberib.generated;

public class ERIBAdapterPTSoapBindingStub extends org.apache.axis.client.Stub implements com.rssl.phizic.test.webgate.esberib.generated.ERIBAdapterPT {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[1];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("DoIFX");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DoIFXRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IFXRq_Type"), com.rssl.phizic.test.webgate.esberib.generated.IFXRq_Type.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IFXRs_Type"));
        oper.setReturnClass(com.rssl.phizic.test.webgate.esberib.generated.IFXRs_Type.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DoIFXRs"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

    }

    public ERIBAdapterPTSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ERIBAdapterPTSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ERIBAdapterPTSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        addBindings0();
        addBindings1();
        addBindings2();
        addBindings3();
        addBindings4();
        addBindings5();
        addBindings6();
    }

    private void addBindings0() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>>>>PaymentList_Type>BankAcctRec>CardAcctId>CustInfo>PersonInfo>ContactInfo>PostAddr");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentList_TypeBankAcctRecCardAcctIdCustInfoPersonInfoContactInfoPostAddr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>>>AcceptBillBasketExecuteRq_Type>CardAcctId>CustInfo>PersonInfo>ContactInfo>PostAddr");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcceptBillBasketExecuteRq_TypeCardAcctIdCustInfoPersonInfoContactInfoPostAddr.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>>>PaymentList_Type>BankAcctRec>CardAcctId>CustInfo>PersonInfo>ContactInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentList_TypeBankAcctRecCardAcctIdCustInfoPersonInfoContactInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>>>PaymentList_Type>BankAcctRec>CardAcctId>CustInfo>PersonInfo>IdentityCard");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentList_TypeBankAcctRecCardAcctIdCustInfoPersonInfoIdentityCard.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>>AcceptBillBasketExecuteRq_Type>CardAcctId>CustInfo>PersonInfo>ContactInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcceptBillBasketExecuteRq_TypeCardAcctIdCustInfoPersonInfoContactInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>>AcceptBillBasketExecuteRq_Type>CardAcctId>CustInfo>PersonInfo>IdentityCard");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcceptBillBasketExecuteRq_TypeCardAcctIdCustInfoPersonInfoIdentityCard.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>>PaymentList_Type>BankAcctRec>CardAcctId>CustInfo>PersonInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentList_TypeBankAcctRecCardAcctIdCustInfoPersonInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>AcceptBillBasketExecuteRq_Type>CardAcctId>CustInfo>PersonInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcceptBillBasketExecuteRq_TypeCardAcctIdCustInfoPersonInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>PaymentList_Type>BankAcctRec>CardAcctId>BankInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentList_TypeBankAcctRecCardAcctIdBankInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>PaymentList_Type>BankAcctRec>CardAcctId>CustInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentList_TypeBankAcctRecCardAcctIdCustInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>PrivateLoanDetails_Type>Accounts>Account>Element");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanDetails_TypeAccountsAccountElement.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>PrivateLoanDetails_Type>Accounts>Card>Element");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanDetails_TypeAccountsCardElement.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>AcceptBillBasketExecuteRq_Type>CardAcctId>BankInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcceptBillBasketExecuteRq_TypeCardAcctIdBankInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>AcceptBillBasketExecuteRq_Type>CardAcctId>CustInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcceptBillBasketExecuteRq_TypeCardAcctIdCustInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>PaymentList_Type>BankAcctRec>CardAcctId");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentList_TypeBankAcctRecCardAcctId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>PrivateLoanDetails_Type>Accounts>Account");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanDetails_TypeAccountsAccountElement[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>PrivateLoanDetails_Type>Accounts>Account>Element");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Element");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>PrivateLoanDetails_Type>Accounts>Card");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanDetails_TypeAccountsCardElement[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">>>PrivateLoanDetails_Type>Accounts>Card>Element");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Element");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">AcceptBillBasketExecuteRq_Type>BankInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcceptBillBasketExecuteRq_TypeBankInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">AcceptBillBasketExecuteRq_Type>CardAcctId");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcceptBillBasketExecuteRq_TypeCardAcctId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">AdditionalCardInfo_Type>Cards");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AdditionalCardInfo_TypeCards.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BankInfoESB_Type>RegionId");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BankInfoLeadZero_Type>AgencyId");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BankInfoLeadZero_Type>BranchId");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BankInfoLeadZero_Type>RegionId");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BankInfoMod1_Type>AgencyId");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BankInfoMod1_Type>BranchId");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BankInfoMod1_Type>RegionId");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BillingPayCanRq_Type>SumCancel");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BlankInfo_Type>SerialNum");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BlankPackage_Type>SerialNum");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">BlankPackage_Type>SerialNumLast");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CardReissuePlaceRq_Type>CardType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CardReissuePlaceRq_Type>RqUID");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CardReissuePlaceRs_Type>RqUID");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctExtStmtInqRs_Type>CardAcctRec");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CCAcctExtStmtInqRs_TypeCardAcctRec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctFullStmtInqRs_Type>CardAcctRec");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CCAcctFullStmtInqRs_TypeCardAcctRec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctStmtRec_Type>OperationAmt");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CCAcctStmtRec_TypeOperationAmt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctStmtRec_Type>OrigCurAmt");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CCAcctStmtRec_TypeOrigCurAmt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">CCAcctStmtRec_Type>RemaindAmt");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CCAcctStmtRec_TypeRemaindAmt.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepAcctStmtInqRq_Type>DepAcctRec");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtInqRq_TypeDepAcctRec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepoAccInfoRsType>DepoAcctInfoRec");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAcctRes_Type[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctRes_Type");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctRes");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepoAccSecInfoRsType>DepoAccSecInfoRec");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAccSecInfoRsTypeDepoAccSecInfoRec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepoBankAccountAdditionalInfo_Type>RecInfoMethods");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoRecMethodr_Type[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoRecMethodr_Type");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecInfoMethod");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepoBankAccountAdditionalInfo_Type>RecInstructionMethods");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoRecMethodr_Type[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoRecMethodr_Type");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecInstructionMethod");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepoDeptDetInfoRsType>DepoAcctDeptInfoRec");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDeptDetInfoRsTypeDepoAcctDeptInfoRec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">DepoDeptsInfoRsType>DepoAcctDeptInfoRec");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDeptsInfoRsTypeDepoAcctDeptInfoRec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">Document_Type>DocNumber");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">EmployeeOfTheVSP_Type>Login");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">GetPrivateLoanListRs_Type>LoanRec");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateLoanListRs_TypeLoanRec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">GetPrivateOperationScanRq_Type>BankInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_TypeBankInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">GetPrivateOperationScanRq_Type>ContactInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_TypeContactInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">GetPrivateOperationScanRs_Type>BankInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_TypeBankInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">GetPrivateOperationScanRs_Type>Status");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_TypeStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">IntegrationInfo_Type>IntegrationId");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IntegrationInfo_TypeIntegrationId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">PayInfo_Type>AcctId");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PayInfo_TypeAcctId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">PaymentList_Type>BankAcctRec");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentList_TypeBankAcctRec.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">PersonInfoSec_Type>IdentityCards");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IdentityCard_Type[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdentityCard");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">PlasticInfoType>CompanyName");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">PostAddr_Type>AreaCode");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">PrivateLoanDetails_Type>Accounts");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanDetails_TypeAccounts.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">Regular_Type>PayDay");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Regular_TypePayDay.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SecuritiesDocument_Type>Annotation");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SecuritiesDocument_Type>DocNum");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SecuritiesDocument_Type>DocType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SourceId_Type>SourceIdType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SvcAcctAudRq_Type>SvcAcct");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcAcctAudRq_TypeSvcAcct.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SvcAcctDelRq_Type>SvcAcct");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcAcctDelRq_TypeSvcAcct.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SvcActInfo_Type>SvcAct");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcActInfo_TypeSvcAct.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">SvcsAcct_Type>SvcAcctId");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcsAcct_TypeSvcAcctId.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferInfo_Type>PayerInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferInfo_TypePayerInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TCDO");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferOperStatusInfoRs_TypeTCDO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TCIO");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferOperStatusInfoRs_TypeTCIO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TDDO");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferOperStatusInfoRs_TypeTDDO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">XferOperStatusInfoRs_Type>TDIO");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferOperStatusInfoRs_TypeTDIO.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcceptBillBasketExecuteRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcceptBillBasketExecuteRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcceptBillBasketExecuteRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcceptBillBasketExecuteRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AccountStateAction_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AccountStatusEnum_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AccountStatusEnum_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AccStopDocRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AccStopDocRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AccStopDocRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AccStopDocRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctBal_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcctBal_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctCur_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctId_NC_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctIdType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctInfoRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcctInfoRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctInfoRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcctInfoRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctInqRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcctInqRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcctInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcctInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AcctType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AcctType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Action_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Action_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ActionName_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ActionName_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddBillBasketInfoRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AddBillBasketInfoRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddBillBasketInfoRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AddBillBasketInfoRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddInfo_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AdditionalCard_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AdditionalCard_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AdditionalCardInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AdditionalCardInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Addr1_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddressType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AddrType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

    }
    private void addBindings1() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgencyId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreeId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtInfoResponse_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfoResponse_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AgreemtNum_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AlterMinRemainder_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AlterMinRemainder_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Amt_Type");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AttributeLength_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AttributeLength_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayDetails_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutopayDetails_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoPaymentTemplate_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoPaymentTemplate_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayStatus_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutopayStatus_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayStatusList_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutopayStatus_Type[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayStatus");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutopayType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutopayTypeList_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutopayTypeList_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionModRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionModRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionModRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionModRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionStatusModRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionStatusModRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "AutoSubscriptionStatusModRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.AutoSubscriptionStatusModRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BackgroundId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BalType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctFullStmtInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctFullStmtInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctFullStmtInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctFullStmtRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctFullStmtRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctPermiss_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctPermiss_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctPermissModRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctPermissModRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctPermissModRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctPermissModRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctRes_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctRes_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctResult_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctResult_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStatus_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctStatus_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStatusCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStmtImgInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctStmtImgInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStmtImgInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctStmtImgInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStmtInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctStmtInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStmtInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctStmtInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankAcctStmtRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankAcctStmtRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoESB_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankInfoESB_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoExt_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankInfoExt_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoLeadZero_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankInfoLeadZero_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BankInfoMod1_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BankInfoMod1_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BIC_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayCanRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BillingPayCanRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayCanRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BillingPayCanRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayExecRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BillingPayExecRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayExecRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BillingPayExecRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BillingPayInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BillingPayInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayPrepRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BillingPayPrepRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BillingPayPrepRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BillingPayPrepRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BirthPlace_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BlankInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlankPackage_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BlankPackage_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BlockReasonType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BlockReasonType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BonusInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.BonusInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BonusProgram_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Boolean");
            cachedSerQNames.add(qName);
            cls = boolean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "BranchId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Building_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CalcCardToCardTransferCommissionRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CalcCardToCardTransferCommissionRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CalcCardToCardTransferCommissionRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CalcCardToCardTransferCommissionRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaignId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaignMember_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CampaignMember_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaignMemberId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CampaignName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAccount_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAccount_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctDepoInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAcctDepoInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctDInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAcctDInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctDInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAcctDInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAcctId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAcctInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctInfoType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAcctInfoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAcctRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAcctRes_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAcctRes_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAdditionalInfoRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAdditionalInfoRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAdditionalInfoRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAdditionalInfoRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardAuthorization_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardAuthorization_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardBlockRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardBlockRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardBlockRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardBlockRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardBonusSign_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardContract_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardContract_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardLevel_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNumberHash_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

    }
    private void addBindings2() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardNumType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardReissuePlaceRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardReissuePlaceRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardReissuePlaceRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardReissuePlaceRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardStatusEnum_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardStatusEnum_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardToIMAAddRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardToIMAAddRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardToIMAAddRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardToIMAAddRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardToNewDepAddRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardToNewDepAddRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardToNewDepAddRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardToNewDepAddRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardToNewIMAAddRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardToNewIMAAddRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardToNewIMAAddRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardToNewIMAAddRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardUsageInfoLimitModRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardUsageInfoLimitModRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CardUsageInfoLimitModRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CardUsageInfoLimitModRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CCAcctExtStmtInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CCAcctExtStmtInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CCAcctExtStmtInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CCAcctExtStmtInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CCAcctFullStmtInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CCAcctFullStmtInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CCAcctFullStmtInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CCAcctFullStmtInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CCAcctStmtRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CCAcctStmtRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ChangeAccountInfoRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ChangeAccountInfoRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ChangeAccountInfoRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ChangeAccountInfoRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ChangeStatus_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ChangeStatus_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ChangeStatusAction_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ChangeStatusAction_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Channel_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Channel_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CitizenShip_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "City_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClassifierCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ClientSegment_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Commission_Type");
            cachedSerQNames.add(qName);
            cls = boolean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CompanyNameForPlastic_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ConcludeEDBORq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ConcludeEDBORq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ConcludeEDBORs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ConcludeEDBORs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactData_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ContactData_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ContactInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfoIssue_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ContactInfoIssue_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContactInfoSec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ContactInfoSec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Contract_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Contract_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ContractNumber_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Corpus_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CorrOwnerDetail_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CorrOwnerDetail_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Country_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CountryCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CreateCardContractRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CreateCardContractRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CreateCardContractRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CreateCardContractRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CreditingRate_Type");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CreditRole_Type");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurAmtConv_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CurAmtConv_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CurCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustAddRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CustAddRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustAddRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CustAddRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CustId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CustInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CustInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CustInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustPermId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CustRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "CustStatus_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.CustStatus_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Date");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DateTime");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Decimal");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAccInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAccInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcct_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAcct_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctExtRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAcctExtRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAcctId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAcctInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAcctRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctRes_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAcctRes_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctStmtGen_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtGen_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctStmtInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepAcctStmtRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepAcctStmtRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepInfoResponse_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepInfoResponse_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccInfoRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAccInfoRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccInfoRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAccInfoRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccount_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAccount_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccounts_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAccounts_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccSecInfoRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAccSecInfoRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccSecRegRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAccSecRegRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccSecRegRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAccSecRegRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctBalRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAcctBalRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAcctId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAcctInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccTranRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAccTranRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAccTranRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAccTranRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAcctRes_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAcctRes_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoAgreement_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoAgreement_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoArRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoArRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoBankAccount_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoBankAccount_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoBankAccountAdditionalInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoBankAccountAdditionalInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoBankAccountCur_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoBankAccountCur_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoBankAcctInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoBankAcctInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoClientRegRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoClientRegRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoClientRegRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoClientRegRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoCorrOwnerDetail_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoCorrOwnerDetail_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeliveryType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDeliveryType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptCardPayRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDeptCardPayRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptCardPayRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDeptCardPayRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptDetInfoRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDeptDetInfoRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptDetInfoRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDeptDetInfoRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptRes_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDeptRes_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptResZad_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDeptResZad_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings3() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDeptsInfoRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDeptsInfoRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoDetailOperationReason_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoDetailOperationReason_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoOperationSubType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoOperationSubType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoOperType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoOperType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoRecMethodr_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoRecMethodr_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoRevokeDocRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoRevokeDocRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoRevokeDocRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoRevokeDocRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityDictInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityDictInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityOperationInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityOperationInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityOperationList_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityOperationList_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityOperationType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityOperationType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecurityRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoSecurityRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoSecuritySectionInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoSecuritySectionInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepoStorageMethod_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepoStorageMethod_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DeptId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DeptId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepToNewDepAddRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepToNewDepAddRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepToNewDepAddRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepToNewDepAddRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepToNewIMAAddRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepToNewIMAAddRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DepToNewIMAAddRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DepToNewIMAAddRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DeptRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DeptRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DetailAcctInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DetailAcctInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DirectSaler_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DirectSaler_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DirectSalerId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "District_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DistrictCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DivisionNumber_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DivisionNumber_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DocInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocInfoStatRqDocumentsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DocInfoStatRqDocumentsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocInfoStatRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DocInfoStatRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocInfoStatRsDocumentsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DocInfoStatRsDocumentsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocInfoStatRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DocInfoStatRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocStateUpdateRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DocStateUpdateRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocStateUpdateRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DocStateUpdateRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocStatNotRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DocStatNotRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DocStatNotRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.DocStatNotRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Document_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Document_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EarlyRepayment_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.EarlyRepayment_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EDBOContract_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.EDBOContract_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EDBONum_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Element_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Element_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EMailAddr_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EmbossedText_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EmployeeOfTheVSP_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.EmployeeOfTheVSP_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EmploymentHistoryType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.EmploymentHistoryType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EndDt_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EnteredData_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "DataItem");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "EribCustId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExctractLine_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ExctractLine_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExecStatus_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ExecStatus_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ExeEventCodeASAP_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ExeEventCodeASAP_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Field_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Field_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FieldValue_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FirstName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FirstNameForPlastic_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FlagAnn_Type");
            cachedSerQNames.add(qName);
            cls = boolean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Flat_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FNC10");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullAddress_IssueCard_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.FullAddress_IssueCard_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullAddress_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.FullAddress_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "FullName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Gender_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetAutoPaymentDetailInfoRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetAutoPaymentDetailInfoRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetAutoPaymentDetailInfoRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetAutoPaymentDetailInfoRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetAutoPaymentListRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetAutoPaymentListRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetAutoPaymentListRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetAutoPaymentListRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetAutoSubscriptionDetailInfoRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetAutoSubscriptionDetailInfoRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetAutoSubscriptionDetailInfoRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetAutoSubscriptionDetailInfoRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetAutoSubscriptionListRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetAutoSubscriptionListRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetAutoSubscriptionListRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetAutoSubscriptionListRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetCampaignerInfoRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetCampaignerInfoRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetCampaignerInfoRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetCampaignerInfoRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetCardListByCardNumRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetCardListByCardNumRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetCardListByCardNumRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetCardListByCardNumRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetInsuranceAppRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetInsuranceAppRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetInsuranceAppRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetInsuranceAppRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetInsuranceListRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetInsuranceListRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetInsuranceListRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetInsuranceListRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetPrivateClientRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateClientRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetPrivateClientRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateClientRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetPrivateLoanDetailsRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateLoanDetailsRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetPrivateLoanDetailsRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateLoanDetailsRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetPrivateLoanListRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateLoanListRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetPrivateLoanListRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateLoanListRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetPrivateOperationScanRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "GetPrivateOperationScanRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.GetPrivateOperationScanRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "House_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Identifier");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdentityCard_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IdentityCard_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdNum_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdSeries_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdSource_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IdType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IFXRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IFXRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IFXRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IFXRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAAcctId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IMAAcctId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImaAcctInRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ImaAcctInRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImaAcctInRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ImaAcctInRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IMAInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAInfoResponse_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IMAInfoResponse_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings4() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAOperConvInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IMAOperConvInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAOperCurType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IMAOperCurType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAToCardAddRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IMAToCardAddRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMAToCardAddRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IMAToCardAddRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsAcctId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ImsAcctId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsAcctInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ImsAcctInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMSAcctRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IMSAcctRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMSBalance_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IMSBalance_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ImsRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ImsRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IMSStatusEnum_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IMSStatusEnum_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InsuranceApp_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.InsuranceApp_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InsuranceAppList_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.InsuranceApp_Type[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InsuranceApp");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Integer");
            cachedSerQNames.add(qName);
            cls = java.math.BigInteger.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IntegrationInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IntegrationInfo_TypeIntegrationId[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", ">IntegrationInfo_Type>IntegrationId");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IntegrationId");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InterestOnDeposit_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.InterestOnDeposit_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "InternalProduct_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.InternalProduct_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IntRate_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IntRate_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IssueCardRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IssueCardRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IssueCardRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.IssueCardRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "IssuedBy_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Issuer_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Issuer_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LastName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LastNameForPlastic_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LevelSale_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Limit_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcct_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanAcct_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanAcctId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctIdDepo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanAcctIdDepo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanAcctRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanAcctRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanInfoRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanInfoRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanInfoRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanInfoRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanPaymentRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanPaymentRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanPaymentRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanPaymentRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanPaymentRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanPaymentRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanStatus_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.LoanStatus_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "LoanType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Long");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MainProductId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarketingResponse_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.MarketingResponse_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarResCampaingMemberId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarResDescription_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarResDetailResult_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarResMethod_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarResPhoneNumberInt_Type");
            cachedSerQNames.add(qName);
            cls = java.math.BigInteger.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarResResponseDateTime_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarResResult_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarResSourceCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MarResType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MBCInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.MBCInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MDMClientInfoUpdateRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.MDMClientInfoUpdateRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MDMClientInfoUpdateRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.MDMClientInfoUpdateRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MediaType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Menu_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "C");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MenuItem");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MessageDeliveryType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.MessageDeliveryType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MessageRecvRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.MessageRecvRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MessageRecvRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.MessageRecvRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MiddleName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "MinBankInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.MinBankInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NextPayDate_Type");
            cachedSerQNames.add(qName);
            cls = java.util.Date.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NextPaySum_Type");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OpCount_Type");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OpenEnum");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.OperInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperName_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.OperName_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperNameSec_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OperUID_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrderNum_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgAddress_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.OrgInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgPlace_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OrgRegion_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OTPRestriction_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.OTPRestriction_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OTPRestrictionModRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.OTPRestrictionModRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "OTPRestrictionModRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.OTPRestrictionModRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Owner_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Owner_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Page_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Page_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ParticipantNumber_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayDay_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PayDay_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PayInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentDetails_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentDetails_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentList_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentList_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatus_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentStatus_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatusASAP_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusASAP_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatusInfoList_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusInfoList_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatusList_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentStatusASAP_Type[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatusASAP_Type");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentStatus");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PaymentTemp_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PaymentTemp_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PayrollAgree_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PayrollAgree_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Period_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Period_type");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PermissType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PermissType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonalText_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PersonInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonInfoSec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PersonInfoSec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

    }
    private void addBindings5() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PersonName_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PersonName_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PfrAccount_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PfrGetInfoInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PfrGetInfoInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PfrGetInfoInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PfrGetInfoInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PfrHasInfoInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PfrHasInfoInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PfrHasInfoInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PfrHasInfoInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PfrResult_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PfrResult_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNum_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PhoneNum_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneNumber_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PhoneType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PinPack_Type");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Place_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PlaceCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PlasticInfoType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PlasticInfoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PmtKind_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PolicyDetails_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PolicyDetails_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PostAddr_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PostAddr_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PostalCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrevPayDate_Type");
            cachedSerQNames.add(qName);
            cls = java.util.Date.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrevPaySum_Type");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrimaryFlag_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Priority_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrivateBlock_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PrivateBlock_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrivateEarlyTerminationResult_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PrivateEarlyTerminationResult_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrivateLoanDetails_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanDetails_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "PrivateLoanInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.PrivateLoanInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProdType2_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProdType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductASName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductASPriority_Type");
            cachedSerQNames.add(qName);
            cls = java.math.BigInteger.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProductType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ProposalParameters_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ProposalParameters_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Rate_Type");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RateOffer_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RbBrchType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RbTbBrchType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RcptKind_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReasonCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RecipientRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.RecipientRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegExp_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.RegExp_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegionId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegisterRespondToMarketingProposeRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.RegisterRespondToMarketingProposeRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegisterRespondToMarketingProposeRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.RegisterRespondToMarketingProposeRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegNextPayDate_Type");
            cachedSerQNames.add(qName);
            cls = java.util.Date.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RegPayAmount_Type");
            cachedSerQNames.add(qName);
            cls = java.math.BigDecimal.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Regular_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Regular_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RepayLoan_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.RepayLoan_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RepaymentRequest_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.RepaymentRequest_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportDeliveryType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ReportDeliveryType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportLangType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ReportLangType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportOrderType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ReportType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ReportType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RequestId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RequestPrivateEarlyRepaymentRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.RequestPrivateEarlyRepaymentRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RequestPrivateEarlyRepaymentRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.RequestPrivateEarlyRepaymentRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisite_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Requisite_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisites_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Requisite_Type[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisite_Type");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Requisite");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RequisiteTypes_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "NC");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RequisiteType");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Resident_Type");
            cachedSerQNames.add(qName);
            cls = boolean.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RqUID_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "RRN_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SearchSpec_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesAcctInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SecuritiesAcctInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesClient_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SecuritiesClient_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesDocument_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SecuritiesDocument_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesHolder_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SecuritiesHolder_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SecuritiesInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesInfoInqRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SecuritiesInfoInqRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesInfoInqRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SecuritiesInfoInqRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesRec_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SecuritiesRec_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecuritiesRecShort_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SecuritiesRecShort_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SecurityMarker_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SecurityMarker_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SegmentCodeType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SelRangeDt_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SelRangeDt_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SelRangeDtTm_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SelRangeDtTm_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServerStatusDesc_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ServiceInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceStmtRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ServiceStmtRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceStmtRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ServiceStmtRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ServiceTarif_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ServiceTarif_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SetAccountStateRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SetAccountStateRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SetAccountStateRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SetAccountStateRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Source_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Source_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SourceCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SourceId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SourceId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SourceName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPDefField_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SPDefField_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPDefFieldShort_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SPDefFieldShort_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPName_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SPName_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SPNum_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SrcLayoutInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "State_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.State_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StateType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatNotRqDocumentsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.StatNotRqDocumentType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatNotRqDocumentType");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Document");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatNotRqDocumentType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.StatNotRqDocumentType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatNotRsDocumentsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.ResultType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "ResultType");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Result");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

    }
    private void addBindings6() {
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Status_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Status_way_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Status_way_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusCode_Type");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusDesc_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StatusType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummAmt_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.StmtSummAmt_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtSummType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StmtType_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.StmtType_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Street_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "StreetCode_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String255Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "String50Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SummaKindCodeASAP_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SummaKindCodeASAP_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAcctAudRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcAcctAudRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAcctAudRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcAcctAudRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAcctDelRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcAcctDelRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAcctDelRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcAcctDelRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAcctId_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcAcctId_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcActInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcActInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAddRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcAddRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcAddRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcAddRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SvcsAcct_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.SvcsAcct_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "SystemId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Table_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Table_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TableName_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TargetProduct_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TargetProductSub_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TargetProductType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TariffClassifier_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.TariffClassifier_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TarifPlanCodeType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TarifPlanInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.TarifPlanInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TarifUnionType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.TarifUnionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxColl_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.TaxColl_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TaxId_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TINInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.TINInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TINType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TopUp_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.TopUp_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TransDirection_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.TransDirection_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TransferInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.TransferInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TransferRcpInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.TransferRcpInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TreatmentType_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "TSMConture_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Turnover_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Turnover_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UpdateCardReportSubscriptionRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.UpdateCardReportSubscriptionRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UpdateCardReportSubscriptionRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.UpdateCardReportSubscriptionRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "URL");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UsageInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.UsageInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UserCategoryType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.UserCategoryType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UserInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.UserInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UTRRNO");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "UUID");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Validator_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Validator_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Validators_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.Validator_Type[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Validator_Type");
            qName2 = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "Validator");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VariantInterestPayment_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.VariantInterestPayment_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "VSPOperation_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.VSPOperation_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "WeekDay_Type");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "WriteDownOperation_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.WriteDownOperation_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "XferAddRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferAddRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "XferAddRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferAddRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "XferIMAInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferIMAInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "XferInfo_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferInfo_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "XferOperStatusInfoRq_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferOperStatusInfoRq_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "XferOperStatusInfoRs_Type");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.webgate.esberib.generated.XferOperStatusInfoRs_Type.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://sbrf.ru/baseproduct/erib/adapter/1", "YesNoType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.rssl.phizic.test.webgate.esberib.generated.IFXRs_Type doIFX(com.rssl.phizic.test.webgate.esberib.generated.IFXRq_Type parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://sbrf.ru/baseproduct/erib/adapter/DoIFX");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "DoIFX"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.test.webgate.esberib.generated.IFXRs_Type) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.test.webgate.esberib.generated.IFXRs_Type) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.test.webgate.esberib.generated.IFXRs_Type.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
