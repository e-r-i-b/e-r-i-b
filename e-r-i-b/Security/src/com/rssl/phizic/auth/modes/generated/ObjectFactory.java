//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.10 at 01:19:03 PM MSK 
//


package com.rssl.phizic.auth.modes.generated;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.rssl.phizic.auth.modes.generated package. 
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
    extends com.rssl.phizic.auth.modes.generated.impl.runtime.DefaultJAXBContextImpl
{

    private static java.util.HashMap defaultImplementations = new java.util.HashMap(23, 0.75F);
    private static java.util.HashMap rootTagMap = new java.util.HashMap();
    public final static com.rssl.phizic.auth.modes.generated.impl.runtime.GrammarInfo grammarInfo = new com.rssl.phizic.auth.modes.generated.impl.runtime.GrammarInfoImpl(rootTagMap, defaultImplementations, (com.rssl.phizic.auth.modes.generated.ObjectFactory.class));
    public final static java.lang.Class version = (com.rssl.phizic.auth.modes.generated.impl.JAXBVersion.class);

    static {
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.CompositeStrategyDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor.DemandIfType.class), "com.rssl.phizic.auth.modes.generated.impl.AuthenticationStageDescriptorImpl.DemandIfTypeImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.AllowOperationTypeDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.AllowOperationTypeDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.AuthenticationStageDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.ChoiceDescriptor.OptionType.class), "com.rssl.phizic.auth.modes.generated.impl.ChoiceDescriptorImpl.OptionTypeImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.AuthChoiceDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.AuthChoiceDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.ConfirmationStrategyDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.ConfirmationStrategyDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.AccessRuleDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.AccessRuleDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.ConfirmationModeDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.ConfirmationModeDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor.DemandIfType.ParameterType.class), "com.rssl.phizic.auth.modes.generated.impl.AuthenticationStageDescriptorImpl.DemandIfTypeImpl.ParameterTypeImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.AuthenticationModeDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.AuthenticationModeDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.AccessRulesElement.class), "com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.AccessRulesDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.AuthChoiceDescriptor.OptionType.class), "com.rssl.phizic.auth.modes.generated.impl.AuthChoiceDescriptorImpl.OptionTypeImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.ChoiceDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.ChoiceDescriptorImpl");
        defaultImplementations.put((com.rssl.phizic.auth.modes.generated.CompositeDescriptor.class), "com.rssl.phizic.auth.modes.generated.impl.CompositeDescriptorImpl");
        rootTagMap.put(new javax.xml.namespace.QName("", "access-rules"), (com.rssl.phizic.auth.modes.generated.AccessRulesElement.class));
    }

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rssl.phizic.auth.modes.generated
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
     * Create an instance of CompositeStrategyDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.CompositeStrategyDescriptor createCompositeStrategyDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.CompositeStrategyDescriptorImpl();
    }

    /**
     * Create an instance of AuthenticationStageDescriptorDemandIfType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor.DemandIfType createAuthenticationStageDescriptorDemandIfType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.AuthenticationStageDescriptorImpl.DemandIfTypeImpl();
    }

    /**
     * Create an instance of AllowOperationTypeDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.AllowOperationTypeDescriptor createAllowOperationTypeDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.AllowOperationTypeDescriptorImpl();
    }

    /**
     * Create an instance of AuthenticationStageDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor createAuthenticationStageDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.AuthenticationStageDescriptorImpl();
    }

    /**
     * Create an instance of ChoiceDescriptorOptionType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.ChoiceDescriptor.OptionType createChoiceDescriptorOptionType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.ChoiceDescriptorImpl.OptionTypeImpl();
    }

    /**
     * Create an instance of AuthChoiceDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.AuthChoiceDescriptor createAuthChoiceDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.AuthChoiceDescriptorImpl();
    }

    /**
     * Create an instance of ConfirmationStrategyDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.ConfirmationStrategyDescriptor createConfirmationStrategyDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.ConfirmationStrategyDescriptorImpl();
    }

    /**
     * Create an instance of AccessRuleDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.AccessRuleDescriptor createAccessRuleDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.AccessRuleDescriptorImpl();
    }

    /**
     * Create an instance of ConfirmationModeDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.ConfirmationModeDescriptor createConfirmationModeDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.ConfirmationModeDescriptorImpl();
    }

    /**
     * Create an instance of AuthenticationStageDescriptorDemandIfTypeParameterType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor.DemandIfType.ParameterType createAuthenticationStageDescriptorDemandIfTypeParameterType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.AuthenticationStageDescriptorImpl.DemandIfTypeImpl.ParameterTypeImpl();
    }

    /**
     * Create an instance of AuthenticationModeDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.AuthenticationModeDescriptor createAuthenticationModeDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.AuthenticationModeDescriptorImpl();
    }

    /**
     * Create an instance of AccessRulesElement
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.AccessRulesElement createAccessRulesElement()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.AccessRulesElementImpl();
    }

    /**
     * Create an instance of AccessRulesDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.AccessRulesDescriptor createAccessRulesDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.AccessRulesDescriptorImpl();
    }

    /**
     * Create an instance of AuthChoiceDescriptorOptionType
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.AuthChoiceDescriptor.OptionType createAuthChoiceDescriptorOptionType()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.AuthChoiceDescriptorImpl.OptionTypeImpl();
    }

    /**
     * Create an instance of ChoiceDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.ChoiceDescriptor createChoiceDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.ChoiceDescriptorImpl();
    }

    /**
     * Create an instance of CompositeDescriptor
     * 
     * @throws JAXBException
     *     if an error occurs
     */
    public com.rssl.phizic.auth.modes.generated.CompositeDescriptor createCompositeDescriptor()
        throws javax.xml.bind.JAXBException
    {
        return new com.rssl.phizic.auth.modes.generated.impl.CompositeDescriptorImpl();
    }

}
