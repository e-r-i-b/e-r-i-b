/**
 * CSAAdminServiceSoapBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.rssl.phizic.csaadmin.listeners.generated;

public class CSAAdminServiceSoapBindingStub extends org.apache.axis.client.Stub implements com.rssl.phizic.csaadmin.listeners.generated.CSAAdminService_PortType {
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
        oper.setName("exec");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "Request"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "RequestType"), com.rssl.phizic.csaadmin.listeners.generated.RequestType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ResponseType"));
        oper.setReturnClass(com.rssl.phizic.csaadmin.listeners.generated.ResponseType.class);
        oper.setReturnQName(new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "Response"));
        oper.setStyle(org.apache.axis.constants.Style.DOCUMENT);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

    }

    public CSAAdminServiceSoapBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public CSAAdminServiceSoapBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public CSAAdminServiceSoapBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AccessSchemeListFilterParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.AccessSchemeListFilterParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AccessSchemeType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.AccessSchemeType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ArrayOfAccessSchemeType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.AccessSchemeType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AccessSchemeType");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ArrayOfCategoriesType");
            cachedSerQNames.add(qName);
            cls = java.lang.String[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ArrayOfContactCenterEmployeeType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.ContactCenterEmployeeType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ContactCenterEmployeeType");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ArrayOfDepartmentType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.DepartmentType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DepartmentType");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ArrayOfEmployeeType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.EmployeeType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeType");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ArrayOfLoginBlockType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.LoginBlockType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginBlockType");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AssignAccessSchemeEmployeeParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.AssignAccessSchemeEmployeeParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AssignAccessSchemeEmployeeResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.AssignAccessSchemeEmployeeResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AuthenticationParametersRequestType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.AuthenticationParametersRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "AuthenticationParametersResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.AuthenticationParametersResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ChangeEmployeePasswordParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.ChangeEmployeePasswordParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ChangeEmployeePasswordResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.ChangeEmployeePasswordResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ChangeNodeRequestType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.ChangeNodeRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ChangeNodeResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.ChangeNodeResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "CloseSessionParametersRequestType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.CloseSessionParametersRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ContactCenterEmployeeType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.ContactCenterEmployeeType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DateTime");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DeleteAccessSchemeParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.DeleteAccessSchemeParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DeleteAccessSchemeResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.DeleteAccessSchemeResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DeleteEmployeeParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.DeleteEmployeeParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DeleteEmployeeResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.DeleteEmployeeResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "DepartmentType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.DepartmentType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeListFilterParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.EmployeeListFilterParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeMailManagerFilterParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.EmployeeMailManagerFilterParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "EmployeeType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.EmployeeType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAccessSchemeByIdParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetAccessSchemeByIdParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAccessSchemeByIdResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetAccessSchemeByIdResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAccessSchemeListParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetAccessSchemeListParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAccessSchemeListResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetAccessSchemeListResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAllowedDepartmentsParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetAllowedDepartmentsParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetAllowedDepartmentsResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetAllowedDepartmentsResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetCurrentEmployeeContextParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetCurrentEmployeeContextParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetCurrentEmployeeContextResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetCurrentEmployeeContextResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetCurrentEmployeeParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetCurrentEmployeeParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetCurrentEmployeeResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetCurrentEmployeeResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeByIdParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetEmployeeByIdParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeByIdResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetEmployeeByIdResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeListParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetEmployeeListParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeListResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetEmployeeListResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeMailManagerListParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetEmployeeMailManagerListParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeMailManagerListResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetEmployeeMailManagerListResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeManagerInfoParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetEmployeeManagerInfoParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeManagerInfoResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetEmployeeManagerInfoResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeSynchronizationDataRequestType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetEmployeeSynchronizationDataRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetEmployeeSynchronizationDataResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetEmployeeSynchronizationDataResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetFirstMailDateParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetFirstMailDateParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetFirstMailDateResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetFirstMailDateResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetIncomeMailListParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetIncomeMailListParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetIncomeMailListResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetIncomeMailListResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailAverageTimeParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetMailAverageTimeParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailAverageTimeResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetMailAverageTimeResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailEmployeeStatisticsParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetMailEmployeeStatisticsParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailEmployeeStatisticsResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetMailEmployeeStatisticsResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailStatisticsParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetMailStatisticsParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMailStatisticsResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetMailStatisticsResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMultiNodeListParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetMultiNodeListParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetMultiNodeParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetMultiNodeParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetOperationContextRequestType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetOperationContextRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetOperationContextResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetOperationContextResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetOutcomeMailListParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetOutcomeMailListParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetOutcomeMailListResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetOutcomeMailListResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetRemovedMailListParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetRemovedMailListParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "GetRemovedMailListResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.GetRemovedMailListResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "IncomeMailListArrayType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.IncomeMailListDataType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "IncomeMailListDataType");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "IncomeMailListDataType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.IncomeMailListDataType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LockEmployeeParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.LockEmployeeParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LockEmployeeResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.LockEmployeeResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginBlockType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.LoginBlockType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "LoginType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.LoginType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailAverageTimeType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.MailAverageTimeType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailEmployeeStatisticsDataArrayType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.MailEmployeeStatisticsDataType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailEmployeeStatisticsDataType");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailEmployeeStatisticsDataType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.MailEmployeeStatisticsDataType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailListDataTypeBase");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.MailListDataTypeBase.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailStatisticsDataArrayType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.MailStatisticsDataType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailStatisticsDataType");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MailStatisticsDataType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.MailStatisticsDataType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ManagerInfoType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.ManagerInfoType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "MapEntryType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.MapEntryType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "OutcomeMailListArrayType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.OutcomeMailListDataType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "OutcomeMailListDataType");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "OutcomeMailListDataType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.OutcomeMailListDataType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "OUUIDType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "RemovedMailListArrayType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.RemovedMailListDataType[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "RemovedMailListDataType");
            qName2 = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "item");
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "RemovedMailListDataType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.RemovedMailListDataType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "RequestData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.RequestData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "RequestType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.RequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ResponseData");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.ResponseData.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "ResponseType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.ResponseType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveAccessSchemeParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.SaveAccessSchemeParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveAccessSchemeResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.SaveAccessSchemeResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveAllowedDepartmentsParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.SaveAllowedDepartmentsParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveEmployeeParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.SaveEmployeeParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveEmployeeResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.SaveEmployeeResultType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SaveOperationContextRequestType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.SaveOperationContextRequestType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "SelfChangePasswordParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.SelfChangePasswordParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "StatusCodeType");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "StatusDescType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "StatusType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.StatusType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "UnlockEmployeeParametersType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.UnlockEmployeeParametersType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "UnlockEmployeeResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.UnlockEmployeeResultType.class;
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
            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "UUIDType");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("http://csa.admin/erib/adapter", "VoidResultType");
            cachedSerQNames.add(qName);
            cls = com.rssl.phizic.csaadmin.listeners.generated.VoidResultType.class;
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

    public com.rssl.phizic.csaadmin.listeners.generated.ResponseType exec(com.rssl.phizic.csaadmin.listeners.generated.RequestType request) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://csa.admin/erib/adapter/exec");
        _call.setEncodingStyle(null);
        _call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("", "exec"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {request});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.rssl.phizic.csaadmin.listeners.generated.ResponseType) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.rssl.phizic.csaadmin.listeners.generated.ResponseType) org.apache.axis.utils.JavaUtils.convert(_resp, com.rssl.phizic.csaadmin.listeners.generated.ResponseType.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
