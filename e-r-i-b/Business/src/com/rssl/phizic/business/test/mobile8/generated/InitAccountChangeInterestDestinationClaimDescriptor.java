//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.06 at 04:05:33 PM MSK 
//


package com.rssl.phizic.business.test.mobile8.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ��������� ������� ������ ��������� �� ������
 * 
 * <p>Java class for InitAccountChangeInterestDestinationClaim complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitAccountChangeInterestDestinationClaim">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="documentNumber" type="{}Field"/>
 *         &lt;element name="documentDate" type="{}Field"/>
 *         &lt;element name="accountId" type="{}Field"/>
 *         &lt;element name="percentTransferSource" type="{}Field"/>
 *         &lt;element name="percentTransferCardSource" type="{}Field"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitAccountChangeInterestDestinationClaim", propOrder = {
    "documentNumber",
    "documentDate",
    "accountId",
    "percentTransferSource",
    "percentTransferCardSource"
})
public class InitAccountChangeInterestDestinationClaimDescriptor {

    @XmlElement(required = true)
    protected FieldDescriptor documentNumber;
    @XmlElement(required = true)
    protected FieldDescriptor documentDate;
    @XmlElement(required = true)
    protected FieldDescriptor accountId;
    @XmlElement(required = true)
    protected FieldDescriptor percentTransferSource;
    @XmlElement(required = true)
    protected FieldDescriptor percentTransferCardSource;

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
     * Gets the value of the accountId property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAccountId() {
        return accountId;
    }

    /**
     * Sets the value of the accountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAccountId(FieldDescriptor value) {
        this.accountId = value;
    }

    /**
     * Gets the value of the percentTransferSource property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getPercentTransferSource() {
        return percentTransferSource;
    }

    /**
     * Sets the value of the percentTransferSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setPercentTransferSource(FieldDescriptor value) {
        this.percentTransferSource = value;
    }

    /**
     * Gets the value of the percentTransferCardSource property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getPercentTransferCardSource() {
        return percentTransferCardSource;
    }

    /**
     * Sets the value of the percentTransferCardSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setPercentTransferCardSource(FieldDescriptor value) {
        this.percentTransferCardSource = value;
    }

}