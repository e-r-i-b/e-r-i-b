
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип - информация о новой заявке, отправляемая из ЕРИБ в CRM
 * 
 * <p>Java class for CRMNewApplRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CRMNewApplRq_Type">
 *   &lt;complexContent>
 *     &lt;extension base="{}HeaderInfoCRM_Type">
 *       &lt;sequence>
 *         &lt;element name="Application" type="{}ApplicationCRM_Type"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CRMNewApplRq_Type", propOrder = {
    "application"
})
@XmlRootElement(name = "CRMNewApplRq")
public class CRMNewApplRq
    extends HeaderInfoCRMType
{

    @XmlElement(name = "Application", required = true)
    protected ApplicationCRMType application;

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationCRMType }
     *     
     */
    public ApplicationCRMType getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationCRMType }
     *     
     */
    public void setApplication(ApplicationCRMType value) {
        this.application = value;
    }

}
