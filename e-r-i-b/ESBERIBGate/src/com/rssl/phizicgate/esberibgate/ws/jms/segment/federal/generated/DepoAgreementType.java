
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Описание договора (ДЕПО).
 * 
 * <p>Java class for DepoAgreement_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoAgreement_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumberAgreement" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DateAgreement" type="{}Date"/>
 *         &lt;element name="SummaAgreement" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoAgreement_Type", propOrder = {
    "numberAgreement",
    "dateAgreement",
    "summaAgreement"
})
public class DepoAgreementType {

    @XmlElement(name = "NumberAgreement", required = true)
    protected String numberAgreement;
    @XmlElement(name = "DateAgreement", required = true)
    protected String dateAgreement;
    @XmlElement(name = "SummaAgreement", required = true)
    protected BigDecimal summaAgreement;

    /**
     * Gets the value of the numberAgreement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberAgreement() {
        return numberAgreement;
    }

    /**
     * Sets the value of the numberAgreement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberAgreement(String value) {
        this.numberAgreement = value;
    }

    /**
     * Gets the value of the dateAgreement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateAgreement() {
        return dateAgreement;
    }

    /**
     * Sets the value of the dateAgreement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateAgreement(String value) {
        this.dateAgreement = value;
    }

    /**
     * Gets the value of the summaAgreement property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSummaAgreement() {
        return summaAgreement;
    }

    /**
     * Sets the value of the summaAgreement property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSummaAgreement(BigDecimal value) {
        this.summaAgreement = value;
    }

}
