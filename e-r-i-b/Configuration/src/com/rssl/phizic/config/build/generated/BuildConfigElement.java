//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.14 at 12:10:42 AM MSK 
//


package com.rssl.phizic.config.build.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="business-module" type="{}BusinessModule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ejb-module" type="{}EjbModule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="web-module" type="{}WebModule" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="web-application" type="{}WebApplication" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="debug-mode" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "businessModule",
    "ejbModule",
    "webModule",
    "webApplication"
})
@XmlRootElement(name = "build-config")
public class BuildConfigElement {

    @XmlElement(name = "business-module")
    protected List<BusinessModuleDescriptor> businessModule;
    @XmlElement(name = "ejb-module")
    protected List<EjbModuleDescriptor> ejbModule;
    @XmlElement(name = "web-module")
    protected List<WebModuleDescriptor> webModule;
    @XmlElement(name = "web-application")
    protected List<WebApplicationDescriptor> webApplication;
    @XmlAttribute(name = "debug-mode")
    protected Boolean debugMode;

    /**
     * Gets the value of the businessModule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the businessModule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBusinessModule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BusinessModuleDescriptor }
     * 
     * 
     */
    public List<BusinessModuleDescriptor> getBusinessModule() {
        if (businessModule == null) {
            businessModule = new ArrayList<BusinessModuleDescriptor>();
        }
        return this.businessModule;
    }

    /**
     * Gets the value of the ejbModule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ejbModule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEjbModule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EjbModuleDescriptor }
     * 
     * 
     */
    public List<EjbModuleDescriptor> getEjbModule() {
        if (ejbModule == null) {
            ejbModule = new ArrayList<EjbModuleDescriptor>();
        }
        return this.ejbModule;
    }

    /**
     * Gets the value of the webModule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the webModule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWebModule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WebModuleDescriptor }
     * 
     * 
     */
    public List<WebModuleDescriptor> getWebModule() {
        if (webModule == null) {
            webModule = new ArrayList<WebModuleDescriptor>();
        }
        return this.webModule;
    }

    /**
     * Gets the value of the webApplication property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the webApplication property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWebApplication().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WebApplicationDescriptor }
     * 
     * 
     */
    public List<WebApplicationDescriptor> getWebApplication() {
        if (webApplication == null) {
            webApplication = new ArrayList<WebApplicationDescriptor>();
        }
        return this.webApplication;
    }

    /**
     * Gets the value of the debugMode property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDebugMode() {
        if (debugMode == null) {
            return false;
        } else {
            return debugMode;
        }
    }

    /**
     * Sets the value of the debugMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDebugMode(Boolean value) {
        this.debugMode = value;
    }

}
