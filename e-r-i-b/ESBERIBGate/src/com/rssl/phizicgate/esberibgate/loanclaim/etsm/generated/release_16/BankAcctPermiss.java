
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип "Права доступа к счету или карте"
 * 
 * <p>Java class for BankAcctPermiss_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctPermiss_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PermissType" type="{}PermissType_Type"/>
 *         &lt;element name="PermissValue" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SPName" type="{}SPName_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctPermiss_Type", propOrder = {
    "permissType",
    "permissValue",
    "spName"
})
@XmlRootElement(name = "BankAcctPermiss")
public class BankAcctPermiss {

    @XmlElement(name = "PermissType", required = true)
    protected PermissTypeType permissType;
    @XmlElement(name = "PermissValue")
    protected boolean permissValue;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;

    /**
     * Gets the value of the permissType property.
     * 
     * @return
     *     possible object is
     *     {@link PermissTypeType }
     *     
     */
    public PermissTypeType getPermissType() {
        return permissType;
    }

    /**
     * Sets the value of the permissType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PermissTypeType }
     *     
     */
    public void setPermissType(PermissTypeType value) {
        this.permissType = value;
    }

    /**
     * Gets the value of the permissValue property.
     * 
     */
    public boolean isPermissValue() {
        return permissValue;
    }

    /**
     * Sets the value of the permissValue property.
     * 
     */
    public void setPermissValue(boolean value) {
        this.permissValue = value;
    }

    /**
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
    }

}
