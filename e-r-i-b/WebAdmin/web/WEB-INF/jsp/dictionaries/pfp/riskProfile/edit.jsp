<%@ page contentType="text/html;charset=windows-1251" language="java"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<html:form action="/pfp/riskProfile/edit" onsubmit="return setEmptyAction();">
	<tiles:insert definition="editPFPRiskProfile">
        <tiles:put name="submenu" type="string" value="riskProfileEdit"/>
        <tiles:put name="menu" type="string">
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
                <c:set var="productTypeSegmentDependence" value="${form.productTypeSegmentDependence}"/>
                <tiles:put name="id"  value="pfpDictionary"/>
                <tiles:put name="name">
                    <bean:message bundle="pfpRiskProfileBundle" key="label.edit.title"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message bundle="pfpRiskProfileBundle" key="label.edit.description"/>
                </tiles:put>
                <tiles:put name="data">
                    <script type="text/javascript">
                        function onChangeSegment(newSegment)
                        {
                            $('.productPercent:not(.' + newSegment + ')').hide();
                            $('.productPercent.' + newSegment).show();
                            $('.productPercent input').val('0');
                        }
                    </script>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpRiskProfileBundle" key="label.edit.field.name"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text property="fields(name)" size="70" maxlength="250"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpRiskProfileBundle" key="label.edit.field.segment"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fields(segment)" styleClass="required" onchange="onChangeSegment(this.value)">
                                <c:forEach var="segment" items="${form.fields['segmentList']}">
                                    <html:option value="${segment}"><bean:message bundle="pfpRiskProfileBundle" key="segment.${segment}"/></html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpRiskProfileBundle" key="label.edit.field.weight"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            &nbsp;<bean:message bundle="pfpRiskProfileBundle" key="label.edit.field.weight.from"/>&nbsp;
                            <html:text property="fields(minWeight)" size="3" maxlength="3"/>
                            &nbsp;<bean:message bundle="pfpRiskProfileBundle" key="label.edit.field.weight.to"/>&nbsp;
                            <html:text property="fields(maxWeight)" size="3" maxlength="3"/>
                        </tiles:put>
                    </tiles:insert>

                    <fieldset>
                        <legend><bean:message bundle="pfpRiskProfileBundle" key="label.edit.field.products"/></legend>
                            <c:set var="currentSegment" value="${form.fields['segment']}"/>
                            <logic:iterate id="type" name="EditRiskProfileForm" property="productTypeList">
                                <c:set var="availableSegments" value="${productTypeSegmentDependence[type]}"/>
                                <c:set var="displayMarker"> style="display:none"</c:set>
                                <c:set var="classesForTr" value=""/>
                                <c:forEach var="availableSegment" items="${availableSegments}">
                                    <c:if test="${currentSegment eq availableSegment}">
                                        <c:set var="displayMarker" value=""/>
                                    </c:if>
                                    <c:set var="classesForTr" value="${classesForTr} ${availableSegment}"/>
                                </c:forEach>
                                <div  class="productPercent${classesForTr}"${displayMarker}>
                                    <tiles:insert definition="simpleFormRow" flush="false">
                                        <tiles:put name="title">
                                            ${type.description}
                                        </tiles:put>
                                        <tiles:put name="data">
                                            <html:text property="fields(productType${type})" size="4" maxlength="3"/>&nbsp;
                                            <bean:message bundle="pfpRiskProfileBundle" key="label.edit.field.percent"/>
                                        </tiles:put>
                                    </tiles:insert>
                                </div>
                            </logic:iterate>
                    </fieldset>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="pfpRiskProfileBundle" key="label.edit.field.description"/>
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:textarea property="fields(description)" cols="63" rows="3"/>
                        </tiles:put>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false" operation="ListRiskProfileOperation">
                        <tiles:put name="commandTextKey"    value="button.cancel"/>
                        <tiles:put name="commandHelpKey"    value="button.cancel.help"/>
                        <tiles:put name="bundle"            value="pfpRiskProfileBundle"/>
                        <tiles:put name="action"            value="/pfp/riskProfile/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditRiskProfileOperation">
                        <tiles:put name="commandKey"         value="button.save"/>
                        <tiles:put name="commandHelpKey"     value="button.save.help"/>
                        <tiles:put name="bundle"             value="pfpRiskProfileBundle"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
