//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.18 at 10:52:54 AM MSK 
//


package com.rssl.phizic.business.loans.claims.generated;


/**
 * Java content class for field element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Programs/Sun/jwsdp-1.6/jaxb/bin/loan-definition.xsd line 71)
 * <p>
 * <pre>
 * &lt;element name="field">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;extension base="{}Entity">
 *         &lt;sequence>
 *           &lt;element name="validators" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="validator" type="{}Validator" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="actions" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="action" type="{}ClientAction" maxOccurs="unbounded" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *         &lt;attribute name="dictionary" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="guarantor" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *         &lt;attribute name="guarantor-inital" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="hidden" type="{http://www.w3.org/2001/XMLSchema}string" default="false" />
 *         &lt;attribute name="hint" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="inital" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="input-template" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="key" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="mandatory" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="readonly" type="{http://www.w3.org/2001/XMLSchema}string" default="false" />
 *         &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;/extension>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface FieldElement
    extends javax.xml.bind.Element, com.rssl.phizic.business.loans.claims.generated.FieldDescriptor
{


}