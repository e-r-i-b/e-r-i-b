/**
 * ASFilialInfoServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.asfilial.listener.generated;

public class ASFilialInfoServiceSoapBindingStub extends org.apache.axis.client.Stub implements com.rssl.phizic.asfilial.listener.generated.ASFilialInfoService {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[4];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("QueryProfile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "QueryProfileRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "QueryProfileRqType"), com.rssl.phizic.asfilial.listener.generated.QueryProfileRqType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "QueryProfileRsType"));
        oper.setReturnClass(com.rssl.phizic.asfilial.listener.generated.QueryProfileRsType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "QueryProfileRs"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("UpdateProfile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "UpdateProfileRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "UpdateProfileRqType"), com.rssl.phizic.asfilial.listener.generated.UpdateProfileRqType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "UpdateProfileRsType"));
        oper.setReturnClass(com.rssl.phizic.asfilial.listener.generated.UpdateProfileRsType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "UpdateProfileRs"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("ConfirmPhoneHolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ConfirmPhoneHolderRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ConfirmPhoneHolderRqType"), com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRqType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ConfirmPhoneHolderRsType"));
        oper.setReturnClass(com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRsType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ConfirmPhoneHolderRs"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("RequestPhoneHolder");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RequestPhoneHolderRq"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RequestPhoneHolderRqType"), com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRqType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RequestPhoneHolderRsType"));
        oper.setReturnClass(com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRsType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RequestPhoneHolderRs"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[3] = oper;

    }

    public ASFilialInfoServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ASFilialInfoServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ASFilialInfoServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "AdditionalCardInfoType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.AdditionalCardInfoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "BankInfoType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.BankInfoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "CardType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.CardType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientDataType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.ClientPhonesType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientPhonesType");
            qName2 = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientPhones");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientPhonesType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.ClientPhonesType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ClientServiceType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.ResourcesType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType");
            qName2 = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "VisibleResources");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ConfirmPhoneHolderRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ConfirmPhoneHolderRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "DaytimePeriodType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.DaytimePeriodType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdentityCardType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.IdentityCardType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "IdentityType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.IdentityType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "MobileBankServiceQueryProfileRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.MobileBankServiceQueryProfileRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "MobileBankServiceUpdateProfileRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.MobileBankServiceUpdateProfileRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PayResourceCardType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.PayResourceCardType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PayResourceType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.PayResourceType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "PhoneNumberType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.PhoneNumberType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "QueryProfileRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.QueryProfileRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "QueryProfileRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.QueryProfileRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RequestPhoneHolderRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "RequestPhoneHolderRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResourcesType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.ResourcesType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ResponseType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.ResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "ServiceParamsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.ServiceParamsType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "StatusType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.StatusType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "UpdateProfileRqType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.UpdateProfileRqType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://generated.listener.asfilial.phizic.rssl.com", "UpdateProfileRsType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.asfilial.listener.generated.UpdateProfileRsType.class;
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

    public com.rssl.phizic.asfilial.listener.generated.QueryProfileRsType queryProfile(com.rssl.phizic.asfilial.listener.generated.QueryProfileRqType parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://generated.listener.asfilial.phizic.rssl.com/QueryProfileRq");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "QueryProfile"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.asfilial.listener.generated.QueryProfileRsType) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.asfilial.listener.generated.QueryProfileRsType) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.asfilial.listener.generated.QueryProfileRsType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.rssl.phizic.asfilial.listener.generated.UpdateProfileRsType updateProfile(com.rssl.phizic.asfilial.listener.generated.UpdateProfileRqType parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://generated.listener.asfilial.phizic.rssl.com/UpdateProfileRq");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "UpdateProfile"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.asfilial.listener.generated.UpdateProfileRsType) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.asfilial.listener.generated.UpdateProfileRsType) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.asfilial.listener.generated.UpdateProfileRsType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRsType confirmPhoneHolder(com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRqType parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://generated.listener.asfilial.phizic.rssl.com/ConfirmPhoneHolderRq");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "ConfirmPhoneHolder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRsType) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRsType) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.asfilial.listener.generated.ConfirmPhoneHolderRsType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRsType requestPhoneHolder(com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRqType parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://generated.listener.asfilial.phizic.rssl.com/RequestPhoneHolderRq");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "RequestPhoneHolder"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRsType) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRsType) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.asfilial.listener.generated.RequestPhoneHolderRsType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
