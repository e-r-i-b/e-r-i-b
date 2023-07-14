
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * <p>Java class for TPInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TPInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ProdName"/>
 *         &lt;element name="ProdType" type="{}ProdType_TP_Type"/>
 *         &lt;element ref="{}PromoType" minOccurs="0"/>
 *         &lt;element ref="{}PromoName" minOccurs="0"/>
 *         &lt;element ref="{}PromoPeriod" minOccurs="0"/>
 *         &lt;element ref="{}GracePeriod" minOccurs="0"/>
 *         &lt;element ref="{}TPCloseRsn" minOccurs="0"/>
 *         &lt;element ref="{}TPCloseCom" minOccurs="0"/>
 *         &lt;element ref="{}TPRegionId"/>
 *         &lt;element ref="{}Operator" minOccurs="0"/>
 *         &lt;element ref="{}CustId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}AgreemtCustRole" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPInfo_Type", propOrder = {
    "prodName",
    "prodType",
    "promoType",
    "promoName",
    "promoPeriod",
    "gracePeriod",
    "tpCloseRsn",
    "tpCloseCom",
    "tpRegionId",
    "operator",
    "custIds",
    "agreemtCustRoles"
})
@XmlRootElement(name = "TPInfo")
public class TPInfo {

    @XmlElement(name = "ProdName", required = true)
    protected String prodName;
    @XmlElement(name = "ProdType", required = true)
    protected String prodType;
    @XmlElement(name = "PromoType")
    protected String promoType;
    @XmlElement(name = "PromoName")
    protected String promoName;
    @XmlElement(name = "PromoPeriod", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar promoPeriod;
    @XmlElement(name = "GracePeriod", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar gracePeriod;
    @XmlElement(name = "TPCloseRsn")
    protected String tpCloseRsn;
    @XmlElement(name = "TPCloseCom")
    protected String tpCloseCom;
    @XmlElement(name = "TPRegionId", required = true)
    protected String tpRegionId;
    @XmlElement(name = "Operator")
    protected Operator operator;
    @XmlElement(name = "CustId")
    protected List<CustId> custIds;
    @XmlElement(name = "AgreemtCustRole")
    protected List<AgreemtCustRole> agreemtCustRoles;

    /**
     * Gets the value of the prodName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdName() {
        return prodName;
    }

    /**
     * Sets the value of the prodName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdName(String value) {
        this.prodName = value;
    }

    /**
     * Gets the value of the prodType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdType() {
        return prodType;
    }

    /**
     * Sets the value of the prodType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdType(String value) {
        this.prodType = value;
    }

    /**
     * Gets the value of the promoType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromoType() {
        return promoType;
    }

    /**
     * Sets the value of the promoType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoType(String value) {
        this.promoType = value;
    }

    /**
     * Gets the value of the promoName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPromoName() {
        return promoName;
    }

    /**
     * Sets the value of the promoName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoName(String value) {
        this.promoName = value;
    }

    /**
     * Gets the value of the promoPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getPromoPeriod() {
        return promoPeriod;
    }

    /**
     * Sets the value of the promoPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPromoPeriod(Calendar value) {
        this.promoPeriod = value;
    }

    /**
     * Gets the value of the gracePeriod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getGracePeriod() {
        return gracePeriod;
    }

    /**
     * Sets the value of the gracePeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGracePeriod(Calendar value) {
        this.gracePeriod = value;
    }

    /**
     * Gets the value of the tpCloseRsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTPCloseRsn() {
        return tpCloseRsn;
    }

    /**
     * Sets the value of the tpCloseRsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTPCloseRsn(String value) {
        this.tpCloseRsn = value;
    }

    /**
     * Gets the value of the tpCloseCom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTPCloseCom() {
        return tpCloseCom;
    }

    /**
     * Sets the value of the tpCloseCom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTPCloseCom(String value) {
        this.tpCloseCom = value;
    }

    /**
     * Gets the value of the tpRegionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTPRegionId() {
        return tpRegionId;
    }

    /**
     * Sets the value of the tpRegionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTPRegionId(String value) {
        this.tpRegionId = value;
    }

    /**
     * Gets the value of the operator property.
     * 
     * @return
     *     possible object is
     *     {@link Operator }
     *     
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Sets the value of the operator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Operator }
     *     
     */
    public void setOperator(Operator value) {
        this.operator = value;
    }

    /**
     * Gets the value of the custIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the custIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustId }
     * 
     * 
     */
    public List<CustId> getCustIds() {
        if (custIds == null) {
            custIds = new ArrayList<CustId>();
        }
        return this.custIds;
    }

    /**
     * Gets the value of the agreemtCustRoles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agreemtCustRoles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgreemtCustRoles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgreemtCustRole }
     * 
     * 
     */
    public List<AgreemtCustRole> getAgreemtCustRoles() {
        if (agreemtCustRoles == null) {
            agreemtCustRoles = new ArrayList<AgreemtCustRole>();
        }
        return this.agreemtCustRoles;
    }

}
