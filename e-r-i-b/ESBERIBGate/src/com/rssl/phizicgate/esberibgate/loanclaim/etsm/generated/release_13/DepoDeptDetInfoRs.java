
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * Получение детальной информации по задолженности по счету ДЕПО
 * 
 * <p>Java class for DepoDeptDetInfoRsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoDeptDetInfoRsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}Status" minOccurs="0"/>
 *         &lt;element name="DepoAcctDeptInfoRec">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}DepoAcctId"/>
 *                   &lt;element name="DepoDeptRes" type="{}DepoDeptResZad_Type" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoDeptDetInfoRsType", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "status",
    "depoAcctDeptInfoRec"
})
@XmlRootElement(name = "DepoDeptDetInfoRs")
public class DepoDeptDetInfoRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "Status")
    protected Status status;
    @XmlElement(name = "DepoAcctDeptInfoRec", required = true)
    protected DepoDeptDetInfoRs.DepoAcctDeptInfoRec depoAcctDeptInfoRec;

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getRqTm() {
        return rqTm;
    }

    /**
     * Sets the value of the rqTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTm(Calendar value) {
        this.rqTm = value;
    }

    /**
     * Gets the value of the operUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperUID() {
        return operUID;
    }

    /**
     * Sets the value of the operUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperUID(String value) {
        this.operUID = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the depoAcctDeptInfoRec property.
     * 
     * @return
     *     possible object is
     *     {@link DepoDeptDetInfoRs.DepoAcctDeptInfoRec }
     *     
     */
    public DepoDeptDetInfoRs.DepoAcctDeptInfoRec getDepoAcctDeptInfoRec() {
        return depoAcctDeptInfoRec;
    }

    /**
     * Sets the value of the depoAcctDeptInfoRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoDeptDetInfoRs.DepoAcctDeptInfoRec }
     *     
     */
    public void setDepoAcctDeptInfoRec(DepoDeptDetInfoRs.DepoAcctDeptInfoRec value) {
        this.depoAcctDeptInfoRec = value;
    }


    /**
     * Запись о счете депо
     * 
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{}DepoAcctId"/>
     *         &lt;element name="DepoDeptRes" type="{}DepoDeptResZad_Type" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "depoAcctId",
        "depoDeptRes"
    })
    public static class DepoAcctDeptInfoRec {

        @XmlElement(name = "DepoAcctId", required = true)
        protected DepoAcctId depoAcctId;
        @XmlElement(name = "DepoDeptRes", required = true)
        protected List<DepoDeptResZadType> depoDeptRes;

        /**
         * Gets the value of the depoAcctId property.
         * 
         * @return
         *     possible object is
         *     {@link DepoAcctId }
         *     
         */
        public DepoAcctId getDepoAcctId() {
            return depoAcctId;
        }

        /**
         * Sets the value of the depoAcctId property.
         * 
         * @param value
         *     allowed object is
         *     {@link DepoAcctId }
         *     
         */
        public void setDepoAcctId(DepoAcctId value) {
            this.depoAcctId = value;
        }

        /**
         * Gets the value of the depoDeptRes property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the depoDeptRes property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDepoDeptRes().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DepoDeptResZadType }
         * 
         * 
         */
        public List<DepoDeptResZadType> getDepoDeptRes() {
            if (depoDeptRes == null) {
                depoDeptRes = new ArrayList<DepoDeptResZadType>();
            }
            return this.depoDeptRes;
        }

    }

}
