
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип сигнал отправки заявки ЕРИБ в ETSM
 * 
 * <p>Java class for ERIBSendETSMApplRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ERIBSendETSMApplRq_Type">
 *   &lt;complexContent>
 *     &lt;extension base="{}HeaderInfoCRM_Type">
 *       &lt;sequence>
 *         &lt;element name="Application" type="{}ApplicationForSendingToETSM_Type"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ERIBSendETSMApplRq_Type", propOrder = {
    "application"
})
@XmlRootElement(name = "ERIBSendETSMApplRq")
public class ERIBSendETSMApplRq
    extends HeaderInfoCRMType
{

    @XmlElement(name = "Application", required = true)
    protected ApplicationForSendingToETSMType application;

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link ApplicationForSendingToETSMType }
     *     
     */
    public ApplicationForSendingToETSMType getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link ApplicationForSendingToETSMType }
     *     
     */
    public void setApplication(ApplicationForSendingToETSMType value) {
        this.application = value;
    }

}
