
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информационная запись по ценной бумаге
 * 
 * <p>Java class for SecuritiesRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecuritiesRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Issuer" type="{}Issuer_Type"/>
 *         &lt;element name="Holder" type="{}SecuritiesHolder_Type"/>
 *         &lt;element name="BlankInfo" type="{}BlankInfo_Type"/>
 *         &lt;element name="FormDt" type="{}Date"/>
 *         &lt;element name="SecuritiesInfo" type="{}SecuritiesInfo_Type"/>
 *         &lt;element name="SecuritiesDocument" type="{}SecuritiesDocument_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecuritiesRec_Type", propOrder = {
    "issuer",
    "holder",
    "blankInfo",
    "formDt",
    "securitiesInfo",
    "securitiesDocument"
})
public class SecuritiesRecType {

    @XmlElement(name = "Issuer", required = true)
    protected IssuerType issuer;
    @XmlElement(name = "Holder", required = true)
    protected SecuritiesHolderType holder;
    @XmlElement(name = "BlankInfo", required = true)
    protected BlankInfoType blankInfo;
    @XmlElement(name = "FormDt", required = true)
    protected String formDt;
    @XmlElement(name = "SecuritiesInfo", required = true)
    protected SecuritiesInfoType securitiesInfo;
    @XmlElement(name = "SecuritiesDocument")
    protected SecuritiesDocumentType securitiesDocument;

    /**
     * Gets the value of the issuer property.
     * 
     * @return
     *     possible object is
     *     {@link IssuerType }
     *     
     */
    public IssuerType getIssuer() {
        return issuer;
    }

    /**
     * Sets the value of the issuer property.
     * 
     * @param value
     *     allowed object is
     *     {@link IssuerType }
     *     
     */
    public void setIssuer(IssuerType value) {
        this.issuer = value;
    }

    /**
     * Gets the value of the holder property.
     * 
     * @return
     *     possible object is
     *     {@link SecuritiesHolderType }
     *     
     */
    public SecuritiesHolderType getHolder() {
        return holder;
    }

    /**
     * Sets the value of the holder property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecuritiesHolderType }
     *     
     */
    public void setHolder(SecuritiesHolderType value) {
        this.holder = value;
    }

    /**
     * Gets the value of the blankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BlankInfoType }
     *     
     */
    public BlankInfoType getBlankInfo() {
        return blankInfo;
    }

    /**
     * Sets the value of the blankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BlankInfoType }
     *     
     */
    public void setBlankInfo(BlankInfoType value) {
        this.blankInfo = value;
    }

    /**
     * Gets the value of the formDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormDt() {
        return formDt;
    }

    /**
     * Sets the value of the formDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormDt(String value) {
        this.formDt = value;
    }

    /**
     * Gets the value of the securitiesInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SecuritiesInfoType }
     *     
     */
    public SecuritiesInfoType getSecuritiesInfo() {
        return securitiesInfo;
    }

    /**
     * Sets the value of the securitiesInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecuritiesInfoType }
     *     
     */
    public void setSecuritiesInfo(SecuritiesInfoType value) {
        this.securitiesInfo = value;
    }

    /**
     * Gets the value of the securitiesDocument property.
     * 
     * @return
     *     possible object is
     *     {@link SecuritiesDocumentType }
     *     
     */
    public SecuritiesDocumentType getSecuritiesDocument() {
        return securitiesDocument;
    }

    /**
     * Sets the value of the securitiesDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecuritiesDocumentType }
     *     
     */
    public void setSecuritiesDocument(SecuritiesDocumentType value) {
        this.securitiesDocument = value;
    }

}
