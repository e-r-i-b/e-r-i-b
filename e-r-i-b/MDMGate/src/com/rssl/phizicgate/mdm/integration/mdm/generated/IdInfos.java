
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdInfos_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdInfos_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IdInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdInfos_Type", propOrder = {
    "idInfo"
})
@XmlRootElement(name = "IdInfos")
public class IdInfos {

    @XmlElement(name = "IdInfo", required = true)
    protected IdInfo idInfo;

    /**
     * Gets the value of the idInfo property.
     * 
     * @return
     *     possible object is
     *     {@link IdInfo }
     *     
     */
    public IdInfo getIdInfo() {
        return idInfo;
    }

    /**
     * Sets the value of the idInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdInfo }
     *     
     */
    public void setIdInfo(IdInfo value) {
        this.idInfo = value;
    }

}
