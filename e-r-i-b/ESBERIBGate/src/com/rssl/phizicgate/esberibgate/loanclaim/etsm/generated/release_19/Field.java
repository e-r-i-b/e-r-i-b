
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Поле дополнительного реквизита
 * 
 * <p>Java class for Field_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Field_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SysFieldName" type="{}String"/>
 *         &lt;element name="TemplateType" type="{}String" minOccurs="0"/>
 *         &lt;element ref="{}RegExp" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Length" type="{}Long" minOccurs="0"/>
 *         &lt;element name="Prefix" type="{}String" minOccurs="0"/>
 *         &lt;element name="Postfix" type="{}String" minOccurs="0"/>
 *         &lt;element ref="{}Menu" minOccurs="0"/>
 *         &lt;element name="EnteredData" type="{}String" minOccurs="0"/>
 *         &lt;element name="DefaultData" type="{}String" minOccurs="0"/>
 *         &lt;element name="Error" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Field_Type", propOrder = {
    "sysFieldName",
    "templateType",
    "regExps",
    "length",
    "prefix",
    "postfix",
    "menu",
    "enteredData",
    "defaultData",
    "error"
})
@XmlRootElement(name = "Field")
public class Field {

    @XmlElement(name = "SysFieldName", required = true)
    protected String sysFieldName;
    @XmlElement(name = "TemplateType")
    protected String templateType;
    @XmlElement(name = "RegExp")
    protected List<RegExp> regExps;
    @XmlElement(name = "Length")
    protected Long length;
    @XmlElement(name = "Prefix")
    protected String prefix;
    @XmlElement(name = "Postfix")
    protected String postfix;
    @XmlElement(name = "Menu")
    protected Menu menu;
    @XmlElement(name = "EnteredData")
    protected String enteredData;
    @XmlElement(name = "DefaultData")
    protected String defaultData;
    @XmlElement(name = "Error")
    protected String error;

    /**
     * Gets the value of the sysFieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysFieldName() {
        return sysFieldName;
    }

    /**
     * Sets the value of the sysFieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysFieldName(String value) {
        this.sysFieldName = value;
    }

    /**
     * Gets the value of the templateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateType() {
        return templateType;
    }

    /**
     * Sets the value of the templateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateType(String value) {
        this.templateType = value;
    }

    /**
     * Gets the value of the regExps property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the regExps property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegExps().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegExp }
     * 
     * 
     */
    public List<RegExp> getRegExps() {
        if (regExps == null) {
            regExps = new ArrayList<RegExp>();
        }
        return this.regExps;
    }

    /**
     * Gets the value of the length property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setLength(Long value) {
        this.length = value;
    }

    /**
     * Gets the value of the prefix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Sets the value of the prefix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefix(String value) {
        this.prefix = value;
    }

    /**
     * Gets the value of the postfix property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostfix() {
        return postfix;
    }

    /**
     * Sets the value of the postfix property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostfix(String value) {
        this.postfix = value;
    }

    /**
     * Gets the value of the menu property.
     * 
     * @return
     *     possible object is
     *     {@link Menu }
     *     
     */
    public Menu getMenu() {
        return menu;
    }

    /**
     * Sets the value of the menu property.
     * 
     * @param value
     *     allowed object is
     *     {@link Menu }
     *     
     */
    public void setMenu(Menu value) {
        this.menu = value;
    }

    /**
     * Gets the value of the enteredData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnteredData() {
        return enteredData;
    }

    /**
     * Sets the value of the enteredData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnteredData(String value) {
        this.enteredData = value;
    }

    /**
     * Gets the value of the defaultData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultData() {
        return defaultData;
    }

    /**
     * Sets the value of the defaultData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultData(String value) {
        this.defaultData = value;
    }

    /**
     * Gets the value of the error property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the value of the error property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setError(String value) {
        this.error = value;
    }

}
