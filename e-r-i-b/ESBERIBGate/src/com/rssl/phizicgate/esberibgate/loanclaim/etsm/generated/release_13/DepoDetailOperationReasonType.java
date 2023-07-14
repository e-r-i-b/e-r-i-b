
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Основание операции - описание договора (ДЕПО).
 * 
 * <p>Java class for DepoDetailOperationReason_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoDetailOperationReason_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AgreementDetail" type="{}DepoAgreement_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoDetailOperationReason_Type", propOrder = {
    "agreementDetail"
})
public class DepoDetailOperationReasonType {

    @XmlElement(name = "AgreementDetail", required = true)
    protected DepoAgreementType agreementDetail;

    /**
     * Gets the value of the agreementDetail property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAgreementType }
     *     
     */
    public DepoAgreementType getAgreementDetail() {
        return agreementDetail;
    }

    /**
     * Sets the value of the agreementDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAgreementType }
     *     
     */
    public void setAgreementDetail(DepoAgreementType value) {
        this.agreementDetail = value;
    }

}
