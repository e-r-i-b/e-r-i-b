/**
 * AdaptiveAuthenticationSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.rsa.integration.ws.control.generated;

public class AdaptiveAuthenticationSoapBindingStub extends org.apache.axis.client.Stub implements com.rssl.phizic.rsa.integration.ws.control.generated.AdaptiveAuthenticationInterface {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[8];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("notify");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.csd.rsa.com", "NotifyRequest"), com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "NotifyResponse"));
        oper.setReturnClass(com.rssl.phizic.rsa.integration.ws.control.generated.NotifyResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "notifyReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFault"),
                      "com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType",
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFaultType"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("query");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryRequest"), com.rssl.phizic.rsa.integration.ws.control.generated.QueryRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryResponse"));
        oper.setReturnClass(com.rssl.phizic.rsa.integration.ws.control.generated.QueryResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "queryReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFault"),
                      "com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType",
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFaultType"), 
                      true
                     ));
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("analyze");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AnalyzeRequest"), com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AnalyzeResponse"));
        oper.setReturnClass(com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "analyzeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFault"),
                      "com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType",
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFaultType"), 
                      true
                     ));
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("authenticate");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticateRequest"), com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticateRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticateResponse"));
        oper.setReturnClass(com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticateResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "authenticateReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFault"),
                      "com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType",
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFaultType"), 
                      true
                     ));
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("challenge");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeRequest"), com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeResponse"));
        oper.setReturnClass(com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFault"),
                      "com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType",
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFaultType"), 
                      true
                     ));
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("createUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CreateUserRequest"), com.rssl.phizic.rsa.integration.ws.control.generated.CreateUserRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CreateUserResponse"));
        oper.setReturnClass(com.rssl.phizic.rsa.integration.ws.control.generated.CreateUserResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "createUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFault"),
                      "com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType",
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFaultType"), 
                      true
                     ));
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("queryAuthStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryAuthStatusRequest"), com.rssl.phizic.rsa.integration.ws.control.generated.QueryAuthStatusRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryAuthStatusResponse"));
        oper.setReturnClass(com.rssl.phizic.rsa.integration.ws.control.generated.QueryAuthStatusResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "queryAuthStatusReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFault"),
                      "com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType",
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFaultType"), 
                      true
                     ));
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("updateUser");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UpdateUserRequest"), com.rssl.phizic.rsa.integration.ws.control.generated.UpdateUserRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UpdateUserResponse"));
        oper.setReturnClass(com.rssl.phizic.rsa.integration.ws.control.generated.UpdateUserResponse.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "updateUserReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFault"),
                      "com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType",
                      new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFaultType"), 
                      true
                     ));
        _operations[7] = oper;

    }

    public AdaptiveAuthenticationSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public AdaptiveAuthenticationSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public AdaptiveAuthenticationSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AccountData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountOwnershipType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AccountOwnershipType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountRelationType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AccountRelationType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AccountType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AccountType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthenticationRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthenticationRequestData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationRequestData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthenticationResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthenticationResponseData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthenticationResponseData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthStatusRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthStatusRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthStatusRequestData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthStatusRequestData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthStatusResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthStatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspAuthStatusResponseData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspAuthStatusResponseData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspChallengeRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspChallengeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspChallengeRequestData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspChallengeRequestData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspChallengeResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspChallengeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspChallengeResponseData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspChallengeResponseData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspManagementRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspManagementRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspManagementRequestData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspManagementRequestData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspManagementResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspManagementResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AcspManagementResponseData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AcspManagementResponseData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ActionApplyType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ActionApplyType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ActionCode");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ActionCode.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ActionTypeList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ActionTypeList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Amount");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.Amount.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AnalyzeRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AnalyzeResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "APIType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.APIType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ATM");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ATM.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ATMLocation");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ATMLocation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ATMLocationTypes");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ATMLocationTypes.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ATMOwnerType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ATMOwnerType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticateRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticateRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticateResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticateResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticationLevel");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticationLevel.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthenticationResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticationResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "AuthorizationMethod");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.AuthorizationMethod.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "BindingType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.BindingType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CallStatus");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CallStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestion");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionActionType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionActionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionActionTypeList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionActionTypeList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionAuthResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionAuthResultPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthResultPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionAuthStatusRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthStatusRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionAuthStatusResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionAuthStatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionChallenge");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionChallenge.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionChallengePayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionChallengePayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionChallengeRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionChallengeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionChallengeRequestPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionChallengeRequestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionConfig");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionConfig.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionDataPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestion[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestion");
            qName2 = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestion");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionGroup");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionGroup.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionGroupList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionGroup[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionGroup");
            qName2 = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "questionGroup");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionIdList");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "questionId");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestion[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestion");
            qName2 = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challengeQuestion");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionManagementRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionManagementRequestPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementRequestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionManagementResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionManagementResponsePayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionManagementResponsePayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeQuestionMatchResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeQuestionMatchResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChallengeResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ChannelIndicatorType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ChannelIndicatorType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ClientDefinedFact");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ClientGenCookie");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ClientGenCookie.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ClientReturnData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ClientReturnData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectableCredential");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CollectableCredential.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectableCredentialList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CollectableCredential[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectableCredential");
            qName2 = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "collectableCredential");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionInitiator");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CollectionInitiator.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionReason");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CollectionReason.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CollectionRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CollectionType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CollectionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ConfigurationHeader");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ConfigurationHeader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Coordinates");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.Coordinates.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CreateUserRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CreateUserRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CreateUserResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CreateUserResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Credential");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.Credential.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialAuthResultList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthResultList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialAuthStatusRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthStatusRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialAuthStatusResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialAuthStatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialChallengeList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialChallengeList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialChallengeRequestList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialChallengeRequestList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialDataList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialDataList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.Credential[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Credential");
            qName2 = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "credential");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialManagementRequestList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialManagementRequestList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialManagementResponseList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialManagementResponseList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialProvisioningStatus");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialProvisioningStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialRequestList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialRequestList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialResponseList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialResponseList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialStatus");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "CredentialType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.CredentialType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DataType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.DataType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceActionType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.DeviceActionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceActionTypeList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.DeviceActionTypeList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.DeviceData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceIdentifier");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.DeviceIdentifier.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceManagementRequestPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementRequestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceManagementResponsePayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.DeviceManagementResponsePayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.DeviceRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

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
            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "DeviceResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.DeviceResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EmailInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.EmailInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EmailManagementRequestPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.EmailManagementRequestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EmailManagementResponsePayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.EmailManagementResponsePayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EventData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.EventData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EventDataList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.EventData[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EventData");
            qName2 = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "eventData");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "EventType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.EventType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ExecutionSpeed");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ExecutionSpeed.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "FactList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ClientDefinedFact");
            qName2 = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "fact");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Gender");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.Gender.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GenericActionType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.GenericActionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GenericActionTypeList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.GenericActionTypeList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GenericRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.GenericRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GenericResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.GenericResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "GeoLocation");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.GeoLocation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "IdentificationData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.IdentificationData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "LoginFailureType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.LoginFailureType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "MessageHeader");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.MessageHeader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "MessageVersion");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.MessageVersion.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "MilterOption");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.MilterOption.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "MobileDevice");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.MobileDevice.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "NamedValue");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.NamedValue.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "NotifyRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "NotifyResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.NotifyResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBActionType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OOBActionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBActionTypeList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OOBActionTypeList.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBContactInfoObject");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OOBContactInfoObject.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailAuthResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailAuthStatusRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthStatusRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailAuthStatusResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailAuthStatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailChallenge");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailChallenge.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailChallengeRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailChallengeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBEmailChallengeRequestPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OOBEmailChallengeRequestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailManagementRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailManagementRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobEmailManagementResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobEmailManagementResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBInfoRequestPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OOBInfoRequestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBInfoResponsePayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OOBInfoResponsePayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBManagementRequestPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OOBManagementRequestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBManagementResponsePayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OOBManagementResponsePayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneAuthResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneAuthStatusRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthStatusRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneAuthStatusResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneAuthStatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneChallenge");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneChallenge.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneChallengeRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneChallengeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OOBPhoneChallengeRequestPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OOBPhoneChallengeRequestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneManagementRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneManagementRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OobPhoneManagementResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OobPhoneManagementResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OtherAccountBankType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OtherAccountBankType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OtherAccountOwnershipType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OtherAccountOwnershipType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OtherAccountType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OtherAccountType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OTPAuthenticationRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OTPAuthenticationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OTPAuthenticationResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OTPAuthenticationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OTPAuthStatusRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OTPAuthStatusRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OTPAuthStatusResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OTPAuthStatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OTPChallengeRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OTPChallengeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OTPChallengeResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OTPChallengeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OTPManagementRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OTPManagementRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "OTPManagementResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.OTPManagementResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "PhoneData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.PhoneData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "PhoneInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.PhoneInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "PhoneManagementRequestPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.PhoneManagementRequestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "PhoneManagementResponsePayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.PhoneManagementResponsePayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "PriceType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.PriceType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryAuthStatusRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.QueryAuthStatusRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryAuthStatusResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.QueryAuthStatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.QueryRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "QueryResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.QueryResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RequestType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.RequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RequiredCredential");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.RequiredCredential.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RequiredCredentialList");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.RequiredCredential[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RequiredCredential");
            qName2 = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "requiredCredential");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RiskResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.RiskResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "RunRiskType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.RunRiskType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "Schedule");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.Schedule.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SecurityHeader");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.SecurityHeader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "ServerRedirectData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.ServerRedirectData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "SoapFaultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "StatusHeader");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.StatusHeader.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "StockData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.StockData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "StockTradeData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.StockTradeData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TermType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.TermType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TradeType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.TradeType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TransactionData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.TransactionData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TransferMediumType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.TransferMediumType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "TriggeredRule");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.TriggeredRule.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UnsupportedAuthStatusRequestPayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.UnsupportedAuthStatusRequestPayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UnsupportedAuthStatusResponsePayload");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.UnsupportedAuthStatusResponsePayload.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UpdateUserRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.UpdateUserRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UpdateUserResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.UpdateUserResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserAddress");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.UserAddress.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.UserData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserName");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.UserName.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserPreference");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.UserPreference.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "UserStatus");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.UserStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "WiFiNetworkData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.WiFiNetworkData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.csd.rsa.com", "WSUserType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.WSUserType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Action");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.Action.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "AddressInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.AddressInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Answer");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.Answer.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

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
            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "BirthdayInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.BirthdayInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Choice");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.Choice.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAAuthenticationRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.KBAAuthenticationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAAuthenticationResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.KBAAuthenticationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAAuthStatusRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.KBAAuthStatusRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAAuthStatusResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.KBAAuthStatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAChallengeRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.KBAChallengeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAChallengeResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.KBAChallengeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAManagementRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.KBAManagementRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "KBAManagementResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.KBAManagementResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "NameInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.NameInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "PersonInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.PersonInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "Question");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.Question.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "ResultStatus");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.ResultStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "SSNInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.SSNInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.kba.csd.rsa.com", "SSNType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.kba.SSNType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBGenAuthenticationRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBGenAuthenticationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBGenAuthenticationResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBGenAuthenticationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBGenAuthStatusRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBGenAuthStatusRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBGenAuthStatusResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBGenAuthStatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBGenChallengeRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBGenChallengeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBGenChallengeResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBGenChallengeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBGenManagementRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBGenManagementRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBGenManagementResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBGenManagementResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobgen.csd.rsa.com", "OOBPhoneInfo");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobgen.OOBPhoneInfo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobsms.csd.rsa.com", "OOBSMSAuthenticationRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobsms.OOBSMSAuthenticationRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobsms.csd.rsa.com", "OOBSMSAuthenticationResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobsms.OOBSMSAuthenticationResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobsms.csd.rsa.com", "OOBSMSAuthStatusRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobsms.OOBSMSAuthStatusRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobsms.csd.rsa.com", "OOBSMSAuthStatusResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobsms.OOBSMSAuthStatusResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobsms.csd.rsa.com", "OOBSMSChallengeRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobsms.OOBSMSChallengeRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobsms.csd.rsa.com", "OOBSMSChallengeResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobsms.OOBSMSChallengeResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobsms.csd.rsa.com", "OOBSMSManagementRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobsms.OOBSMSManagementRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://ws.oobsms.csd.rsa.com", "OOBSMSManagementResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.rsa.integration.ws.control.generated.oobsms.OOBSMSManagementResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

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

    public com.rssl.phizic.rsa.integration.ws.control.generated.NotifyResponse notify(com.rssl.phizic.rsa.integration.ws.control.generated.NotifyRequest request) throws java.rmi.RemoteException, com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("rsa:notify:Notify");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "notify"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.NotifyResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.NotifyResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.rsa.integration.ws.control.generated.NotifyResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) {
              throw (com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.QueryResponse query(com.rssl.phizic.rsa.integration.ws.control.generated.QueryRequest request) throws java.rmi.RemoteException, com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("rsa:query:Query");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "query"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.QueryResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.QueryResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.rsa.integration.ws.control.generated.QueryResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) {
              throw (com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse analyze(com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeRequest request) throws java.rmi.RemoteException, com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("rsa:analyze:Analyze");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "analyze"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.rsa.integration.ws.control.generated.AnalyzeResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) {
              throw (com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticateResponse authenticate(com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticateRequest request) throws java.rmi.RemoteException, com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("rsa:authenticate:Authenticate");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "authenticate"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticateResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticateResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.rsa.integration.ws.control.generated.AuthenticateResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) {
              throw (com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeResponse challenge(com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeRequest request) throws java.rmi.RemoteException, com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("rsa:challenge:Challenge");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "challenge"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.rsa.integration.ws.control.generated.ChallengeResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) {
              throw (com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.CreateUserResponse createUser(com.rssl.phizic.rsa.integration.ws.control.generated.CreateUserRequest request) throws java.rmi.RemoteException, com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("rsa:createuser:CreateUser");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "createUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.CreateUserResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.CreateUserResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.rsa.integration.ws.control.generated.CreateUserResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) {
              throw (com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.QueryAuthStatusResponse queryAuthStatus(com.rssl.phizic.rsa.integration.ws.control.generated.QueryAuthStatusRequest request) throws java.rmi.RemoteException, com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("rsa:queryauthstatus:QueryAuthStatus");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "queryAuthStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.QueryAuthStatusResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.QueryAuthStatusResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.rsa.integration.ws.control.generated.QueryAuthStatusResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) {
              throw (com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.rssl.phizic.rsa.integration.ws.control.generated.UpdateUserResponse updateUser(com.rssl.phizic.rsa.integration.ws.control.generated.UpdateUserRequest request) throws java.rmi.RemoteException, com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("rsa:udpateuser:UpdateUser");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://ws.csd.rsa.com", "updateUser"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.UpdateUserResponse) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.rsa.integration.ws.control.generated.UpdateUserResponse) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.rsa.integration.ws.control.generated.UpdateUserResponse.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) {
              throw (com.rssl.phizic.rsa.integration.ws.control.generated.SoapFaultType) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
