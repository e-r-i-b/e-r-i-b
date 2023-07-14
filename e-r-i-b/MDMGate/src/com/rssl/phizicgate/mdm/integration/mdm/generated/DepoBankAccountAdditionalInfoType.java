
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Доп. информация по клиентским счетам ДЕПО
 * 
 * <p>Java class for DepoBankAccountAdditionalInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoBankAccountAdditionalInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecIncomeMethod" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RecInstructionMethods">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RecInstructionMethod" type="{}DepoRecMethodr_Type" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RecInfoMethods">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RecInfoMethod" type="{}DepoRecMethodr_Type" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "DepoBankAccountAdditionalInfo_Type", propOrder = {
    "recIncomeMethod",
    "recInstructionMethods",
    "recInfoMethods"
})
public class DepoBankAccountAdditionalInfoType {

    @XmlElement(name = "RecIncomeMethod", required = true)
    protected String recIncomeMethod;
    @XmlElement(name = "RecInstructionMethods", required = true)
    protected DepoBankAccountAdditionalInfoType.RecInstructionMethods recInstructionMethods;
    @XmlElement(name = "RecInfoMethods", required = true)
    protected DepoBankAccountAdditionalInfoType.RecInfoMethods recInfoMethods;

    /**
     * Gets the value of the recIncomeMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecIncomeMethod() {
        return recIncomeMethod;
    }

    /**
     * Sets the value of the recIncomeMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecIncomeMethod(String value) {
        this.recIncomeMethod = value;
    }

    /**
     * Gets the value of the recInstructionMethods property.
     * 
     * @return
     *     possible object is
     *     {@link DepoBankAccountAdditionalInfoType.RecInstructionMethods }
     *     
     */
    public DepoBankAccountAdditionalInfoType.RecInstructionMethods getRecInstructionMethods() {
        return recInstructionMethods;
    }

    /**
     * Sets the value of the recInstructionMethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoBankAccountAdditionalInfoType.RecInstructionMethods }
     *     
     */
    public void setRecInstructionMethods(DepoBankAccountAdditionalInfoType.RecInstructionMethods value) {
        this.recInstructionMethods = value;
    }

    /**
     * Gets the value of the recInfoMethods property.
     * 
     * @return
     *     possible object is
     *     {@link DepoBankAccountAdditionalInfoType.RecInfoMethods }
     *     
     */
    public DepoBankAccountAdditionalInfoType.RecInfoMethods getRecInfoMethods() {
        return recInfoMethods;
    }

    /**
     * Sets the value of the recInfoMethods property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoBankAccountAdditionalInfoType.RecInfoMethods }
     *     
     */
    public void setRecInfoMethods(DepoBankAccountAdditionalInfoType.RecInfoMethods value) {
        this.recInfoMethods = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RecInfoMethod" type="{}DepoRecMethodr_Type" maxOccurs="unbounded" minOccurs="0"/>
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
        "recInfoMethods"
    })
    public static class RecInfoMethods {

        @XmlElement(name = "RecInfoMethod")
        protected List<DepoRecMethodrType> recInfoMethods;

        /**
         * Gets the value of the recInfoMethods property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the recInfoMethods property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRecInfoMethods().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DepoRecMethodrType }
         * 
         * 
         */
        public List<DepoRecMethodrType> getRecInfoMethods() {
            if (recInfoMethods == null) {
                recInfoMethods = new ArrayList<DepoRecMethodrType>();
            }
            return this.recInfoMethods;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="RecInstructionMethod" type="{}DepoRecMethodr_Type" maxOccurs="unbounded" minOccurs="0"/>
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
        "recInstructionMethods"
    })
    public static class RecInstructionMethods {

        @XmlElement(name = "RecInstructionMethod")
        protected List<DepoRecMethodrType> recInstructionMethods;

        /**
         * Gets the value of the recInstructionMethods property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the recInstructionMethods property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRecInstructionMethods().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link DepoRecMethodrType }
         * 
         * 
         */
        public List<DepoRecMethodrType> getRecInstructionMethods() {
            if (recInstructionMethods == null) {
                recInstructionMethods = new ArrayList<DepoRecMethodrType>();
            }
            return this.recInstructionMethods;
        }

    }

}
