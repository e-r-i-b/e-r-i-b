//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.14 at 12:10:42 AM MSK 
//


package com.rssl.phizic.config.build.generated;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.rssl.phizic.config.build.generated package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.rssl.phizic.config.build.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BuildConfigElement }
     * 
     */
    public BuildConfigElement createBuildConfigElement() {
        return new BuildConfigElement();
    }

    /**
     * Create an instance of {@link BusinessModuleDescriptor }
     * 
     */
    public BusinessModuleDescriptor createBusinessModuleDescriptor() {
        return new BusinessModuleDescriptor();
    }

    /**
     * Create an instance of {@link EjbModuleDescriptor }
     * 
     */
    public EjbModuleDescriptor createEjbModuleDescriptor() {
        return new EjbModuleDescriptor();
    }

    /**
     * Create an instance of {@link WebModuleDescriptor }
     * 
     */
    public WebModuleDescriptor createWebModuleDescriptor() {
        return new WebModuleDescriptor();
    }

    /**
     * Create an instance of {@link WebApplicationDescriptor }
     * 
     */
    public WebApplicationDescriptor createWebApplicationDescriptor() {
        return new WebApplicationDescriptor();
    }

    /**
     * Create an instance of {@link BusinessModuleRefDescriptor }
     * 
     */
    public BusinessModuleRefDescriptor createBusinessModuleRefDescriptor() {
        return new BusinessModuleRefDescriptor();
    }

    /**
     * Create an instance of {@link WebModuleRefDescriptor }
     * 
     */
    public WebModuleRefDescriptor createWebModuleRefDescriptor() {
        return new WebModuleRefDescriptor();
    }

}