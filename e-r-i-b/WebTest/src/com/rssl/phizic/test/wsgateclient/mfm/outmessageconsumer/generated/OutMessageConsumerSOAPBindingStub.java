/**
 * OutMessageConsumerSOAPBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated;

public class OutMessageConsumerSOAPBindingStub extends org.apache.axis.client.Stub implements com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumer {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[2];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("consumeOutMessage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "ConsumeOutMessageRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", ">ConsumeOutMessageRequest"), com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", ">ConsumeOutMessageResponse"));
        oper.setReturnClass(com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageResult[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "ConsumeOutMessageResponse"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "consumeOutMessageResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("", "fault"),
                      "com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault",
                      new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "OutMessageConsumerFault"), 
                      true
                     ));
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("findOutMessage");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "FindOutMessageRequest"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", ">FindOutMessageRequest"), com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageRequest.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", ">FindOutMessageResponse"));
        oper.setReturnClass(com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageResult[].class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "FindOutMessageResponse"));
        param = oper.getReturnParamDesc();
        param.setItemQName(new javax.xml.namespace.QName("", "findOutMessageResult"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        oper.addFault(new org.apache.axis.description.FaultDesc(
                      new javax.xml.namespace.QName("", "fault"),
                      "com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault",
                      new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "OutMessageConsumerFault"), 
                      true
                     ));
        _operations[1] = oper;

    }

    public OutMessageConsumerSOAPBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public OutMessageConsumerSOAPBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public OutMessageConsumerSOAPBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", ">ConsumeOutMessageRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", ">ConsumeOutMessageResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageResult[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "ConsumeOutMessageResult");
            qName2 = new javax.xml.namespace.QName("", "consumeOutMessageResult");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", ">FindOutMessageRequest");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageRequest.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", ">FindOutMessageResponse");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageResult[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "FindOutMessageResult");
            qName2 = new javax.xml.namespace.QName("", "findOutMessageResult");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "Auth");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.Auth.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "ConsumeOutMessageArg");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageArg.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "ConsumeOutMessageResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "FindOutMessageArg");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageArg.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "FindOutMessageResult");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageResult.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "OutMessage");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessage.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer", "OutMessageConsumerFault");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault.class;
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

    public com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageResult[] consumeOutMessage(com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageRequest parameters) throws java.rmi.RemoteException, com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer/consumeOutMessage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "consumeOutMessage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageResult[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageResult[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.ConsumeOutMessageResult[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault) {
              throw (com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

    public com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageResult[] findOutMessage(com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageRequest parameters) throws java.rmi.RemoteException, com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://www.mfms.ru/sb/ifm/sms/sb0/out-message-consumer/findOutMessage");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "findOutMessage"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {parameters});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageResult[]) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageResult[]) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.FindOutMessageResult[].class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
    if (axisFaultException.detail != null) {
        if (axisFaultException.detail instanceof java.rmi.RemoteException) {
              throw (java.rmi.RemoteException) axisFaultException.detail;
         }
        if (axisFaultException.detail instanceof com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault) {
              throw (com.rssl.phizic.test.wsgateclient.mfm.outmessageconsumer.generated.OutMessageConsumerFault) axisFaultException.detail;
         }
   }
  throw axisFaultException;
}
    }

}
