//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.14 at 12:10:42 AM MSK 
//


package com.rssl.phizic.config.build.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessModule complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BusinessModule">
 *   &lt;complexContent>
 *     &lt;extension base="{}Module">
 *       &lt;attribute name="jar-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessModule")
public class BusinessModuleDescriptor
    extends ModuleDescriptor
{

    @XmlAttribute(name = "jar-name")
    protected String jarName;

    /**
     * Gets the value of the jarName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJarName() {
        return jarName;
    }

    /**
     * Sets the value of the jarName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJarName(String value) {
        this.jarName = value;
    }

}
