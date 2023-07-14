<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html"  %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>

&nbsp;
<tiles:importAttribute/>
<html:form action="/private/async/userprofile/editIdentifier" appendToken="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <tiles:insert definition="confirm_${confirmRequest.strategyType}" flush="false">
        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
        <tiles:put name="useAjax" value="true"/>
        <tiles:put name="ajaxUrl" value="/private/async/userprofile/editIdentifier"/>
        <tiles:put name="data">
            <c:set var="docType" value="${form.documentType}"/>
            <div class="windowEdit${docType}">
            <div class="windowEditIcon"></div>
                <div class="windowEditFields">
                    <div class="documentNameView">
                        <span class="documentName">${form.fields.name}</span>
                    </div>
                    <div class="clear"></div>
                    <div class="windowEditFieldsContainer">
                        <c:choose>
                            <c:when test="${docType == 'INN'}">

                                <div class="fieldName">${phiz:getFieldNameForReq('numberINN')}</div>
                                <div class="fieldData">
                                        ${form.fields.number}
                                </div>
                            </c:when>
                            <c:when test="${docType == 'SNILS'}">
                                <div class="fieldName"><bean:message key="user.document.SNILS.shortname" bundle="userprofileBundle"/></div>
                                <div class="fieldData">
                                        ${form.fields.number}
                                </div>
                            </c:when>
                            <c:when test="${docType == 'DL'}">
                                <table>
                                    <tr>
                                        <td class="fieldName">${phiz:getFieldNameForReq('seriesDL')}</td>
                                        <td class="fieldName">${phiz:getFieldNameForReq('numberDL')}</td>
                                    </tr>
                                    <tr>
                                        <td class="fieldData">${form.fields.series}</td>
                                        <td class="fieldData">${form.fields.number}</td>
                                    </tr>
                                </table>
                                <div class="separator"></div>
                                <div class="fieldName">${phiz:getFieldNameForReq('issueOrgDL')}</div>
                                <div class="fieldData">
                                        ${form.fields.issueBy}
                                </div>
                                <table class="personDLDateTable">
                                    <tr>
                                        <td class="fieldName">${phiz:getFieldNameForReq('issueDateDL')}</td>
                                        <td class="fieldName">${phiz:getFieldNameForReq('expireDateDL')}</td>
                                    </tr>
                                    <tr>
                                        <td class="fieldDataSmall"><bean:write name="org.apache.struts.taglib.html.BEAN" property="field(issueDate)" format="dd.MM.yyyy"/></td>
                                        <td class="fieldDataSmall"><bean:write name="org.apache.struts.taglib.html.BEAN" property="field(expireDate)" format="dd.MM.yyyy"/></td>
                                    </tr>
                                </table>
                            </c:when>
                            <c:when test="${docType == 'RC'}">
                                <table>
                                    <tr>
                                        <td class="fieldName">${phiz:getFieldNameForReq('seriesRC')}</td>
                                        <td class="fieldName">${phiz:getFieldNameForReq('numberRC')}</td>
                                    </tr>
                                    <tr>
                                        <td class="fieldData">${form.fields.series}</td>
                                        <td class="fieldData">${form.fields.number}</td>
                                    </tr>
                                </table>
                            </c:when>
                        </c:choose>
                        <div class="separator"></div>
                    </div>
                </div>
            </div
        </tiles:put>
        <tiles:put name="anotherStrategy"       value="false"/>
        <tiles:put name="confirmableObject"     value="personalSettings"/>
        <tiles:put name="byCenter"              value="Center"/>
        <tiles:put name="confirmCommandKey"     value="button.confirm"/>
        <tiles:put name="showCancelButton"      value="false"/>
        <tiles:put name="buttonType"            value="singleRow"/>
        <tiles:put name="confirmStrategy"       beanName="confirmStrategy"/>
        <tiles:put name="ajaxOnCompleteCallback">win.close('addIdetnifierBasketSNILS');</tiles:put>
    </tiles:insert>
</html:form>