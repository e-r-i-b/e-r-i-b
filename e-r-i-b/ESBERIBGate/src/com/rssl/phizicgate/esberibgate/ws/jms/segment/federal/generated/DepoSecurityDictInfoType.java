
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Запись о ценной бумаге в справочнике (ДЕПО).
 * 
 * <p>Java class for DepoSecurityDictInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoSecurityDictInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Issuer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SecurityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SecurityNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SecurityType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SecurityNominal" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="SecurityNominalCur" type="{}AcctCur_Type" minOccurs="0"/>
 *         &lt;element name="InsideCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsDelete" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoSecurityDictInfo_Type", propOrder = {
    "issuer",
    "securityName",
    "securityNumber",
    "securityType",
    "securityNominal",
    "securityNominalCur",
    "insideCode",
    "isDelete"
})
public class DepoSecurityDictInfoType {

    @XmlElement(name = "Issuer", required = true)
    protected String issuer;
    @XmlElement(name = "SecurityName", required = true)
    protected String securityName;
    @XmlElement(name = "SecurityNumber")
    protected String securityNumber;
    @XmlElement(name = "SecurityType", required = true)
    protected String securityType;
    @XmlElement(name = "SecurityNominal")
    protected BigDecimal securityNominal;
    @XmlElement(name = "SecurityNominalCur")
    protected String securityNominalCur;
    @XmlElement(name = "InsideCode", required = true)
    protected String insideCode;
    @XmlElement(name = "IsDelete")
    protected boolean isDelete;

    /**
     * Gets the value of the issuer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssuer() {
        return issuer;
    }

    /**
     * Sets the value of the issuer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssuer(String value) {
        this.issuer = value;
    }

    /**
     * Gets the value of the securityName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityName() {
        return securityName;
    }

    /**
     * Sets the value of the securityName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityName(String value) {
        this.securityName = value;
    }

    /**
     * Gets the value of the securityNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityNumber() {
        return securityNumber;
    }

    /**
     * Sets the value of the securityNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityNumber(String value) {
        this.securityNumber = value;
    }

    /**
     * Gets the value of the securityType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityType() {
        return securityType;
    }

    /**
     * Sets the value of the securityType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityType(String value) {
        this.securityType = value;
    }

    /**
     * Gets the value of the securityNominal property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSecurityNominal() {
        return securityNominal;
    }

    /**
     * Sets the value of the securityNominal property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSecurityNominal(BigDecimal value) {
        this.securityNominal = value;
    }

    /**
     * Gets the value of the securityNominalCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityNominalCur() {
        return securityNominalCur;
    }

    /**
     * Sets the value of the securityNominalCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityNominalCur(String value) {
        this.securityNominalCur = value;
    }

    /**
     * Gets the value of the insideCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsideCode() {
        return insideCode;
    }

    /**
     * Sets the value of the insideCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsideCode(String value) {
        this.insideCode = value;
    }

    /**
     * Gets the value of the isDelete property.
     * 
     */
    public boolean isIsDelete() {
        return isDelete;
    }

    /**
     * Sets the value of the isDelete property.
     * 
     */
    public void setIsDelete(boolean value) {
        this.isDelete = value;
    }

}
