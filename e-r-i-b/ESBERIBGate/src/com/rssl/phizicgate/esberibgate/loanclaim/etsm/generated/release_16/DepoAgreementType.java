
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


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
 *         &lt;element name="DateAgreement" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
    @XmlElement(name = "DateAgreement", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar dateAgreement;
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
    public Calendar getDateAgreement() {
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
    public void setDateAgreement(Calendar value) {
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
