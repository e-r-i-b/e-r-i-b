
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StatNotRqDocumentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StatNotRqDocumentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ERIBUID" type="{}UUID"/>
 *         &lt;element name="Id" type="{}String255Type"/>
 *         &lt;element name="UTRRNO" type="{}UTRRNO" minOccurs="0"/>
 *         &lt;element name="State" type="{}StateType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatNotRqDocumentType", propOrder = {
    "eribuid",
    "id",
    "utrrno",
    "state"
})
public class StatNotRqDocumentType {

    @XmlElement(name = "ERIBUID", required = true)
    protected String eribuid;
    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "UTRRNO")
    protected String utrrno;
    @XmlElement(name = "State", required = true)
    protected StateType2 state;

    /**
     * Gets the value of the eribuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERIBUID() {
        return eribuid;
    }

    /**
     * Sets the value of the eribuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERIBUID(String value) {
        this.eribuid = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the utrrno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUTRRNO() {
        return utrrno;
    }

    /**
     * Sets the value of the utrrno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUTRRNO(String value) {
        this.utrrno = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link StateType2 }
     *     
     */
    public StateType2 getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateType2 }
     *     
     */
    public void setState(StateType2 value) {
        this.state = value;
    }

}
