//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.06.26 at 01:33:00 PM MSD 
//


package com.rssl.phizic.business.test.atm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ������ �� ��������� ������� �� ���
 * 
 * <p>Java class for InitPFRStatementClaim complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitPFRStatementClaim">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="documentDate" type="{}Field"/>
 *         &lt;element name="passport" type="{}Field" minOccurs="0"/>
 *         &lt;element name="SNILS" type="{}Field" minOccurs="0"/>
 *         &lt;element name="claimSendMethod" type="{}Field"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitPFRStatementClaim", propOrder = {
    "documentNumber",
    "documentDate",
    "passport",
    "snils",
    "claimSendMethod"
})
public class InitPFRStatementClaimDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor documentDate;
    protected FieldDescriptor passport;
    @XmlElement(name = "SNILS")
    protected FieldDescriptor snils;
    @XmlElement(required = true)
    protected FieldDescriptor claimSendMethod;

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDocumentNumber(FieldDescriptor value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the documentDate property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getDocumentDate() {
        return documentDate;
    }

    /**
     * Sets the value of the documentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setDocumentDate(FieldDescriptor value) {
        this.documentDate = value;
    }

    /**
     * Gets the value of the passport property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getPassport() {
        return passport;
    }

    /**
     * Sets the value of the passport property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setPassport(FieldDescriptor value) {
        this.passport = value;
    }

    /**
     * Gets the value of the snils property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getSNILS() {
        return snils;
    }

    /**
     * Sets the value of the snils property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setSNILS(FieldDescriptor value) {
        this.snils = value;
    }

    /**
     * Gets the value of the claimSendMethod property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getClaimSendMethod() {
        return claimSendMethod;
    }

    /**
     * Sets the value of the claimSendMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setClaimSendMethod(FieldDescriptor value) {
        this.claimSendMethod = value;
    }

}