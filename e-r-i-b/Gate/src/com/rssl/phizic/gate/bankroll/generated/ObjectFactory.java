//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.11 at 07:52:58 PM MSK 
//


package com.rssl.phizic.gate.bankroll.generated;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.rssl.phizic.gate.bankroll.generated package. 
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
public class ObjectFactory
    extends com.rssl.phizic.gate.bankroll.generated.impl.runtime.DefaultJAXBContextImpl
{

    private static java.util.HashMap defaultImplementations = new java.util.HashMap(16, 0.75F);
    private static java.util.HashMap rootTagMap = new java.util.HashMap();
    public final static com.rssl.phizic.gate.bankroll.generated.impl.runtime.GrammarInfo grammarInfo = new com.rssl.phizic.gate.bankroll.generated.impl.runtime.GrammarInfoImpl(rootTagMap, defaultImplementations, (com.rssl.phizic.gate.bankroll.generated.ObjectFactory.class));
    public final static java.lang.Class version = (com.rssl.phizic.gate.bankroll.generated.impl.JAXBVersion.class);

    static {
        defaultImplementations.put((com.rssl.phizic.gate.bankroll.generated.ServicesType.class), "com.rssl.phizic.gate.bankroll.generated.impl.ServicesTypeImpl");
        defaultImplementations.put((com.rssl.phizic.gate.bankroll.generated.ServicesType.MethodsType.class), "com.rssl.phizic.gate.bankroll.generated.impl.ServicesTypeImpl.MethodsTypeImpl");
        defaultImplementations.put((com.rssl.phizic.gate.bankroll.generated.ServicesElement.class), "com.rssl.phizic.gate.bankroll.generated.impl.ServicesElementImpl");
        defaultImplementations.put((com.rssl.phizic.gate.bankroll.generated.ServicesType.SourcesType.class), "com.rssl.phizic.gate.bankroll.generated.impl.ServicesTypeImpl.SourcesTypeImpl");
        defaultImplementations.put((com.rssl.phizic.gate.bankroll.generated.SourceDescriptor.class), "com.rssl.phizic.gate.bankroll.generated.impl.SourceDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.gate.bankroll.generated.FieldDescriptor.class), "com.rssl.phizic.gate.bankroll.generated.impl.FieldDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.gate.bankroll.generated.CaseDescriptor.class), "com.rssl.phizic.gate.bankroll.generated.impl.CaseDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.gate.bankroll.generated.ConditionDescriptor.class), "com.rssl.phizic.gate.bankroll.generated.impl.ConditionDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.gate.bankroll.generated.MethodDescriptor.ConditionsType.class), "com.rssl.phizic.gate.bankroll.generated.impl.MethodDescriptorImpl.ConditionsTypeImpl");
        defaultImplementations.put((com.rssl.phizic.gate.bankroll.generated.MethodDescriptor.class), "com.rssl.phizic.gate.bankroll.generated.impl.MethodDescriptorImpl");
        rootTagMap.put(new javax.xml.namespace.QName("", "services"), (com.rssl.phizic.gate.bankroll.generated.ServicesElement.class));
    }

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rssl.phizic.gate.bankroll.generated
     * 
     */
    public ObjectFactory() {
        super(grammarInfo);
    }

    /**
     * Create an instance of the specified Java content interface.
     * 
     * @param javaContentInterface
     *     the Class object of the javacontent interface to instantiate
     * @return
     *     a new instance
     * @throws JAXBException
     *     if an error occurs
     */
    public java.lang.Object newInstance(java.lang.Class javaContentInterface)
        throws javax.xml.bind.JAXBException
    {
        return super.newInstance(javaContentInterface);
    }

    /**
     * Get the specified property. This method can only be
     * used to get provider specific properties.
     * Attempting to get an undefined property will result
     * in a PropertyException being thrown.
     * 
     * @param name
     *     the name of the property to retrieve
     * @return
     *     the value of the requested property
     * @throws PropertyException
     *     when there is an error retrieving the given property or value
     */
    public java.lang.Object getProperty(java.lang.String name)
        throws javax.xml.bind.PropertyException
    {
        return super.getProperty(name);
    }

    /**
     * Set the specified property. This method can only be
     * used to set provider specific properties.
     * Attempting to set an undefined property will result
     * in a PropertyException being thrown.
     * 
     * @param value
     *     the value of the property to be set
     * @param name
     *     the name of the property to retrieve
     * @throws PropertyException
     *     when there is an error processing the given property or value
     */
    public void setProperty(java.lang.String name, java.lang.Object value)
        throws javax.xml.bind.PropertyException
    {
        super.setProperty(name, value);
    }

    /**
     * Create an instance of ServicesType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.gate.bankroll.generated.ServicesType createServicesType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.gate.bankroll.generated.impl.ServicesTypeImpl();
    }

    /**
     * Create an instance of ServicesTypeMethodsType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.gate.bankroll.generated.ServicesType.MethodsType createServicesTypeMethodsType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.gate.bankroll.generated.impl.ServicesTypeImpl.MethodsTypeImpl();
    }

    /**
     * Create an instance of ServicesElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.gate.bankroll.generated.ServicesElement createServicesElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.gate.bankroll.generated.impl.ServicesElementImpl();
    }

    /**
     * Create an instance of ServicesTypeSourcesType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.gate.bankroll.generated.ServicesType.SourcesType createServicesTypeSourcesType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.gate.bankroll.generated.impl.ServicesTypeImpl.SourcesTypeImpl();
    }

    /**
     * Create an instance of SourceDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.gate.bankroll.generated.SourceDescriptor createSourceDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.gate.bankroll.generated.impl.SourceDescriptorImpl();
    }

    /**
     * Create an instance of FieldDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.gate.bankroll.generated.FieldDescriptor createFieldDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.gate.bankroll.generated.impl.FieldDescriptorImpl();
    }

    /**
     * Create an instance of CaseDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.gate.bankroll.generated.CaseDescriptor createCaseDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.gate.bankroll.generated.impl.CaseDescriptorImpl();
    }

    /**
     * Create an instance of ConditionDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.gate.bankroll.generated.ConditionDescriptor createConditionDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.gate.bankroll.generated.impl.ConditionDescriptorImpl();
    }

    /**
     * Create an instance of MethodDescriptorConditionsType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.gate.bankroll.generated.MethodDescriptor.ConditionsType createMethodDescriptorConditionsType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.gate.bankroll.generated.impl.MethodDescriptorImpl.ConditionsTypeImpl();
    }

    /**
     * Create an instance of MethodDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.gate.bankroll.generated.MethodDescriptor createMethodDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.gate.bankroll.generated.impl.MethodDescriptorImpl();
    }

}
