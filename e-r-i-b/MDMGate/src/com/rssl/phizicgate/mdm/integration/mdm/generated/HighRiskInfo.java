
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * <p>Java class for HighRiskInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HighRiskInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}HighRiskInd" minOccurs="0"/>
 *         &lt;element ref="{}UpdateDate" minOccurs="0"/>
 *         &lt;element ref="{}Comment" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HighRiskInfo_Type", propOrder = {
    "highRiskInd",
    "updateDate",
    "comment"
})
@XmlRootElement(name = "HighRiskInfo")
public class HighRiskInfo {

    @XmlElement(name = "HighRiskInd")
    protected String highRiskInd;
    @XmlElement(name = "UpdateDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar updateDate;
    @XmlElement(name = "Comment")
    protected String comment;

    /**
     * Gets the value of the highRiskInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHighRiskInd() {
        return highRiskInd;
    }

    /**
     * Sets the value of the highRiskInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHighRiskInd(String value) {
        this.highRiskInd = value;
    }

    /**
     * Gets the value of the updateDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getUpdateDate() {
        return updateDate;
    }

    /**
     * Sets the value of the updateDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateDate(Calendar value) {
        this.updateDate = value;
    }

    /**
     * Gets the value of the comment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the value of the comment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComment(String value) {
        this.comment = value;
    }

}
