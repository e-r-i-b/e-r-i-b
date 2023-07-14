/**
 * XsbCheckNamesSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizicgate.bars.ws.axis;

public class XsbCheckNamesSoapBindingStub extends org.apache.axis.client.Stub implements com.rssl.phizicgate.bars.ws.axis.XsbCheckNames_PortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("checkRemoteClientName");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "xsbDocument"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "ArrayOfXsbParameter"), com.rssl.phizicgate.bars.ws.axis.XsbParameter[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "XsbParameter"));
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://checks.xsb.webservices.bars.sbrf", "XsbChecksReturn"));
        oper.setReturnClass(com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "checkRemoteClientNameReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("readRemoteClientName");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "xsbDocument"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "ArrayOfXsbParameter"), com.rssl.phizicgate.bars.ws.axis.XsbParameter[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "XsbParameter"));
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameReturn"));
        oper.setReturnClass(com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "readRemoteClientNameReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("readRemoteClientNameExtended");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "xsbDocument"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        param.setNillable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "parameters"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "ArrayOfXsbParameter"), com.rssl.phizicgate.bars.ws.axis.XsbParameter[].class, false, false);
        param.setItemQName(new javax.xml.namespace.QName("", "XsbParameter"));
        param.setNillable(true);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameExtendedReturn"));
        oper.setReturnClass(com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedReturn.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "readRemoteClientNameExtendedReturn"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public XsbCheckNamesSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public XsbCheckNamesSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public XsbCheckNamesSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", ">checkRemoteClientName");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.CheckRemoteClientName.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", ">checkRemoteClientNameResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.CheckRemoteClientNameResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", ">readRemoteClientName");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.ReadRemoteClientName.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", ">readRemoteClientNameExtended");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.ReadRemoteClientNameExtended.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", ">readRemoteClientNameExtendedResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.ReadRemoteClientNameExtendedResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", ">readRemoteClientNameResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.ReadRemoteClientNameResponse.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "ArrayOfXsbRemoteClientNameExtendedResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedResult[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameExtendedResult");
            qName2 = new javax.xml.namespace.QName("", "XsbRemoteClientNameExtendedResult");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "ArrayOfXsbRemoteClientNameResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameResult[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameResult");
            qName2 = new javax.xml.namespace.QName("", "XsbRemoteClientNameResult");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameExtendedResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameExtendedReturn");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "XsbRemoteClientNameReturn");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checks.xsb.webservices.bars.sbrf", "ArrayOfXsbChecksDocResults");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbChecksDocResults[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://checks.xsb.webservices.bars.sbrf", "XsbChecksDocResults");
            qName2 = new javax.xml.namespace.QName("", "XsbChecksDocResults");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://checks.xsb.webservices.bars.sbrf", "XsbChecksDocResults");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbChecksDocResults.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://checks.xsb.webservices.bars.sbrf", "XsbChecksReturn");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "ArrayOfXsbExceptionItem");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbExceptionItem[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbExceptionItem");
            qName2 = new javax.xml.namespace.QName("", "XsbExceptionItem");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "ArrayOfXsbParameter");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbParameter[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbParameter");
            qName2 = new javax.xml.namespace.QName("", "XsbParameter");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "ArrayOfXsbResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbResult[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbResult");
            qName2 = new javax.xml.namespace.QName("", "XsbResult");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbDocID");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbDocID.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbDocResults");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbDocResults.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbExceptionItem");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbExceptionItem.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbParameter");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbParameter.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://common.xsb.webservices.bars.sbrf", "XsbReturn");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizicgate.bars.ws.axis.XsbReturn.class;
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

    public com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn checkRemoteClientName(java.lang.String xsbDocument, com.rssl.phizicgate.bars.ws.axis.XsbParameter[] parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "checkRemoteClientName"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {xsbDocument, parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizicgate.bars.ws.axis.XsbChecksReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn readRemoteClientName(java.lang.String xsbDocument, com.rssl.phizicgate.bars.ws.axis.XsbParameter[] parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "readRemoteClientName"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {xsbDocument, parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedReturn readRemoteClientNameExtended(java.lang.String xsbDocument, com.rssl.phizicgate.bars.ws.axis.XsbParameter[] parameters) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("http://checkNames.xsb.webservices.bars.sbrf", "readRemoteClientNameExtended"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {xsbDocument, parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedReturn) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedReturn) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizicgate.bars.ws.axis.XsbRemoteClientNameExtendedReturn.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
