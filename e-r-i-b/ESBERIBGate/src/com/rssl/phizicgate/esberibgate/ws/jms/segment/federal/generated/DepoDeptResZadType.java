
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Запись о задолженности на счете ДЕПО
 * 
 * <p>Java class for DepoDeptResZad_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoDeptResZad_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EffDt" type="{}Date"/>
 *         &lt;element ref="{}DeptRec" minOccurs="0"/>
 *         &lt;element ref="{}Status"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoDeptResZad_Type", propOrder = {
    "recNumber",
    "effDt",
    "deptRec",
    "status"
})
public class DepoDeptResZadType {

    @XmlElement(name = "RecNumber", required = true)
    protected String recNumber;
    @XmlElement(name = "EffDt", required = true)
    protected String effDt;
    @XmlElement(name = "DeptRec")
    protected DeptRec deptRec;
    @XmlElement(name = "Status", required = true)
    protected StatusType status;

    /**
     * Gets the value of the recNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecNumber() {
        return recNumber;
    }

    /**
     * Sets the value of the recNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecNumber(String value) {
        this.recNumber = value;
    }

    /**
     * Gets the value of the effDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffDt() {
        return effDt;
    }

    /**
     * Sets the value of the effDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffDt(String value) {
        this.effDt = value;
    }

    /**
     * Gets the value of the deptRec property.
     * 
     * @return
     *     possible object is
     *     {@link DeptRec }
     *     
     */
    public DeptRec getDeptRec() {
        return deptRec;
    }

    /**
     * Sets the value of the deptRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeptRec }
     *     
     */
    public void setDeptRec(DeptRec value) {
        this.deptRec = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

}
