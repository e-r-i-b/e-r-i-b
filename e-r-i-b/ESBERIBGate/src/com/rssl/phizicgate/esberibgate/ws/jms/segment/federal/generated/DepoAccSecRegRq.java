
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Подача заявки на регистрацию новой ценной бумаги ДЕПО
 * 
 * <p>Java class for DepoAccSecRegRqType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoAccSecRegRqType">
 *   &lt;complexContent>
 *     &lt;extension base="{}DepoAccInfoRqType">
 *       &lt;sequence>
 *         &lt;element name="OperationInfo" type="{}DepoSecurityOperationInfo_Type"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoAccSecRegRqType", propOrder = {
    "operationInfo"
})
@XmlRootElement(name = "DepoAccSecRegRq")
public class DepoAccSecRegRq
    extends DepoAccInfoRqType
{

    @XmlElement(name = "OperationInfo", required = true)
    protected DepoSecurityOperationInfoType operationInfo;

    /**
     * Gets the value of the operationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DepoSecurityOperationInfoType }
     *     
     */
    public DepoSecurityOperationInfoType getOperationInfo() {
        return operationInfo;
    }

    /**
     * Sets the value of the operationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoSecurityOperationInfoType }
     *     
     */
    public void setOperationInfo(DepoSecurityOperationInfoType value) {
        this.operationInfo = value;
    }

}
