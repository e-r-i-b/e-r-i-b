
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * –егул€рное выражение
 * 
 * <p>Java class for RegExp_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RegExp_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Exp" type="{}String"/>
 *         &lt;element name="Mess" type="{}String"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegExp_Type", propOrder = {
    "exp",
    "mess"
})
@XmlRootElement(name = "RegExp")
public class RegExp {

    @XmlElement(name = "Exp", required = true)
    protected String exp;
    @XmlElement(name = "Mess", required = true)
    protected String mess;

    /**
     * Gets the value of the exp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExp() {
        return exp;
    }

    /**
     * Sets the value of the exp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExp(String value) {
        this.exp = value;
    }

    /**
     * Gets the value of the mess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMess() {
        return mess;
    }

    /**
     * Sets the value of the mess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMess(String value) {
        this.mess = value;
    }

}
