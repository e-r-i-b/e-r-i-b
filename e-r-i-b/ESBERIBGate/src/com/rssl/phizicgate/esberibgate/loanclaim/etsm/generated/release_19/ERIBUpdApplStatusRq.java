
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип изменение статуса существующей заявки
 * 
 * <p>Java class for ERIBUpdApplStatusRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ERIBUpdApplStatusRq_Type">
 *   &lt;complexContent>
 *     &lt;extension base="{}HeaderInfoCRM_Type">
 *       &lt;sequence>
 *         &lt;element name="Application" type="{}ErrorStatus_Type"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ERIBUpdApplStatusRq_Type", propOrder = {
    "application"
})
@XmlRootElement(name = "ERIBUpdApplStatusRq")
public class ERIBUpdApplStatusRq
    extends HeaderInfoCRMType
{

    @XmlElement(name = "Application", required = true)
    protected ErrorStatusType application;

    /**
     * Gets the value of the application property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorStatusType }
     *     
     */
    public ErrorStatusType getApplication() {
        return application;
    }

    /**
     * Sets the value of the application property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorStatusType }
     *     
     */
    public void setApplication(ErrorStatusType value) {
        this.application = value;
    }

}
