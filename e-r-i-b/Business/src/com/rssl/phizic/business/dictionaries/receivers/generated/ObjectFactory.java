//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.18 at 10:39:31 AM MSK 
//


package com.rssl.phizic.business.dictionaries.receivers.generated;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.rssl.phizic.business.dictionaries.receivers.generated package. 
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
    extends com.rssl.phizic.business.dictionaries.receivers.generated.impl.runtime.DefaultJAXBContextImpl
{

    private static java.util.HashMap defaultImplementations = new java.util.HashMap(16, 0.75F);
    private static java.util.HashMap rootTagMap = new java.util.HashMap();
    public final static com.rssl.phizic.business.dictionaries.receivers.generated.impl.runtime.GrammarInfo grammarInfo = new com.rssl.phizic.business.dictionaries.receivers.generated.impl.runtime.GrammarInfoImpl(rootTagMap, defaultImplementations, (com.rssl.phizic.business.dictionaries.receivers.generated.ObjectFactory.class));
    public final static java.lang.Class version = (com.rssl.phizic.business.dictionaries.receivers.generated.impl.JAXBVersion.class);

    static {
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor.FormValidatorsType.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.ReceiverDescriptorImpl.FormValidatorsTypeImpl");
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.PaymentReceiverListElement.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.PaymentReceiverListElementImpl");
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.FormValidatorDescriptor.FieldRefType.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.FormValidatorDescriptorImpl.FieldRefTypeImpl");
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.ParameterDescriptor.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.ParameterDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.ReceiverDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.FieldDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.FormValidatorDescriptor.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.FormValidatorDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.ValidatorDescriptor.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.ValidatorDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.PaymentDescriptor.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.PaymentDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor.ValidatorsType.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.FieldDescriptorImpl.ValidatorsTypeImpl");
        defaultImplementations.put((com.rssl.phizic.business.dictionaries.receivers.generated.PaymentReceiverListType.class), "com.rssl.phizic.business.dictionaries.receivers.generated.impl.PaymentReceiverListTypeImpl");
        rootTagMap.put(new javax.xml.namespace.QName("", "payment-receiver-list"), (com.rssl.phizic.business.dictionaries.receivers.generated.PaymentReceiverListElement.class));
    }

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rssl.phizic.business.dictionaries.receivers.generated
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
     * Create an instance of ReceiverDescriptorFormValidatorsType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor.FormValidatorsType createReceiverDescriptorFormValidatorsType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.ReceiverDescriptorImpl.FormValidatorsTypeImpl();
    }

    /**
     * Create an instance of PaymentReceiverListElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.PaymentReceiverListElement createPaymentReceiverListElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.PaymentReceiverListElementImpl();
    }

    /**
     * Create an instance of FormValidatorDescriptorFieldRefType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.FormValidatorDescriptor.FieldRefType createFormValidatorDescriptorFieldRefType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.FormValidatorDescriptorImpl.FieldRefTypeImpl();
    }

    /**
     * Create an instance of ParameterDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.ParameterDescriptor createParameterDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.ParameterDescriptorImpl();
    }

    /**
     * Create an instance of ReceiverDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor createReceiverDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.ReceiverDescriptorImpl();
    }

    /**
     * Create an instance of FieldDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor createFieldDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.FieldDescriptorImpl();
    }

    /**
     * Create an instance of FormValidatorDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.FormValidatorDescriptor createFormValidatorDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.FormValidatorDescriptorImpl();
    }

    /**
     * Create an instance of ValidatorDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.ValidatorDescriptor createValidatorDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.ValidatorDescriptorImpl();
    }

    /**
     * Create an instance of PaymentDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.PaymentDescriptor createPaymentDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.PaymentDescriptorImpl();
    }

    /**
     * Create an instance of FieldDescriptorValidatorsType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor.ValidatorsType createFieldDescriptorValidatorsType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.FieldDescriptorImpl.ValidatorsTypeImpl();
    }

    /**
     * Create an instance of PaymentReceiverListType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.business.dictionaries.receivers.generated.PaymentReceiverListType createPaymentReceiverListType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.business.dictionaries.receivers.generated.impl.PaymentReceiverListTypeImpl();
    }

}