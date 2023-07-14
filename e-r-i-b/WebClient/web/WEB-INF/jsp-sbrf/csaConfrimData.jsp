<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<html:form action="/confirm/csa" show="true" >
    <c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>
    <%--<c:set var="globalImagePath" value="${globalUrl}/images"/>--%>
    <%--<c:set var="imagePath" value="${skinUrl}/images"/>--%>
    <tiles:insert definition="login">
        <tiles:put name="pageTitle" type="string" value="Регистрация."/>
        <%--<tiles:put type="page" name="data" value="/WEB-INF/jsp-sbrf/loginData.jsp"/>--%>
        <tiles:put name="data" type="string">


            <div class="Login" id="LoginDiv" align="center" height="100%">
                <table height="100%" cellpadding="0" cellspacing="0" border="0">
                    <tr>
                        <td valign="top" align="center">
                            <table cellpadding="0" cellspacing="0" border="0" width="920px">
                                <tr>
                                    <td valign="top" width="50px" style="text-align:right;">
                                        <img src="${imagePath}/login_leftCorner.gif" alt="" border="0">
                                    </td>
                                    <td valign="top" class="login_whiteBg">
                                        <img src="${imagePath}/login_logo.png" alt="" border="0">
                                    </td>
                                    <td class="login_greenBg" valign="top">
                                        <table cellpadding="0" cellspacing="0" width="100%"
                                               onkeypress="onEnterKey(event);">
                                            <tr>
                                                <td colspan="2" align="right" style="padding-top:9px;">
                                                    <table cellpadding="0" cellspacing="0">
                                                        <tr>
                                                            <td style="color:#cc0033;">
                                                                <c:set var="error">
                                                                    <html:messages id="error" bundle="securityBundle">
                                                                        <%=error%>
                                                                        <br/>
                                                                        Для повторной попытки аутентификации
                                                                        <a href="http://localhost:8888/PhizIC/login.do">нажмите здесь</a>
                                                                    </html:messages>
                                                                </c:set>
                                                                <c:if test="${not empty error}">
                                                                    &nbsp;${error}&nbsp;
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td colspan="2" style="padding-top:30px;">
                                                    <bean:message bundle="commonBundle" key="text.SBOL.questions"/>
                                                </td>
                                            </tr>
                                            <tr valign="top">
                                                <td colspan="2" style="padding-top:30px;">+7 (495) 500-5550
                                                    или
                                                    8-800-555-5550
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                    <td valign="top">
                                        <img src="${imagePath}/login_rightCorner.gif" alt="" border="0">
                                    </td>
                                </tr>
                                <c:if test="${not empty form.csa}">
                                    <c:if test="${form.csa}">
                                        <tr>
                                            <td colspan="4" >
                                                <iframe class="MaxSize" frameborder="0" src="${form.path}"></iframe>
                                            </td>
                                        </tr>
                                    </c:if>
                                </c:if>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>

        </tiles:put>

    </tiles:insert>
</html:form>