<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<%--@elvariable id="form" type="com.rssl.phizic.web.client.ext.sbrf.mobilebank.register.EditRegistrationForm"--%>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

<tiles:insert definition="roundBorder" flush="false">
    <c:if test="${param.afterLogin == 'true'}">
        <tiles:put name="color" value="greenTop"/>
    </c:if>
    <tiles:put name="title">
        <div class="align-left">����������� ������ "��������� ����"</div>
    </tiles:put>
    <tiles:put name="data">

        <%-- ��������� --%>
        <tiles:insert definition="formHeader" flush="false">
            <tiles:put name="description">
                <h3>��������� ��������� ����������� ������ � ������� �� ������ &laquo;�����������&raquo;.</h3>
            </tiles:put>
        </tiles:insert>

        <%-- ����� ����� --%>
        <div id="paymentStripe" <c:if test="${param.afterLogin == 'true'}">class="login-register-stripe"</c:if>>
           <tiles:insert definition="stripe" flush="false">
               <tiles:put name="name" value="����� ������"/>
               <tiles:put name="future" value="false"/>
           </tiles:insert>
           <tiles:insert definition="stripe" flush="false">
               <tiles:put name="name" value="���������� ������"/>
               <tiles:put name="current" value="true"/>
           </tiles:insert>
           <tiles:insert definition="stripe" flush="false">
               <tiles:put name="name" value="�������������"/>
           </tiles:insert>
           <tiles:insert definition="stripe" flush="false">
               <tiles:put name="name" value="����������� ������"/>
           </tiles:insert>
           <div class="clear"></div>
        </div>

        <%-- ����� --%>
        <%-- 1. field(tariff) - ����� FULL/ECONOM --%>
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">����� ������������:</tiles:put>
            <tiles:put name="data">
                <bean:define id="tariff" name="form" property="field(tariff)"/>
                <c:choose>
                    <c:when test="${tariff == 'FULL'}">
                        <b>������ �����</b>
                    </c:when>
                    <c:when test="${tariff == 'ECONOM'}">
                        <b>��������� �����</b>
                    </c:when>
                </c:choose>
                <html:hidden name="form" property="field(tariff)"/>
            </tiles:put>
        </tiles:insert>

        <%-- 2. field(phone) - ������������� ����� �������� --%>
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title">����� ��������:</tiles:put>
            <tiles:put name="data">
                <b><bean:write name="form" property="field(phone)"/></b>
                <html:hidden name="form" property="field(phone)"/>
                &nbsp;
                <span class="notation noItalic">��� ��������� ����� ������ �������� ���������� ���������� � ��������� �����.</span>
            </tiles:put>
            <tiles:put name="description">
                ������ &laquo;���������  ����&raquo; ����� ���������� �� ���� ����� ��������.
            </tiles:put>
        </tiles:insert>

        <%-- 3. field(card) - ������������� ����� ����� --%>
        <tiles:insert definition="formRow" flush="false">
            <tiles:put name="title"><span>����� �����:</span></tiles:put>
            <tiles:put name="isNecessary" value="true"/>
            <tiles:put name="data">
                <c:choose>
                    <c:when test="${fn:length(form.maskedCards)>1}">
                        <html:select name="form" property="field(card)">
                            <html:options name="form" property="maskedCards"/>
                        </html:select>
                    </c:when>
                    <c:otherwise>
                        <b><bean:write name="form" property="field(card)"/></b>
                        <html:hidden name="form" property="field(card)"/>
                    </c:otherwise>
                </c:choose>
            </tiles:put>
            <tiles:put name="description">
                ��� ������ ����� ����� ���������� ������ &laquo;���������  ����&raquo;.
            </tiles:put>
        </tiles:insert>

        <html:hidden name="form" property="returnURL"/>

        <%-- ������ --%>
        <div class="buttonsArea">
            <div class="float">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.register.back"/>
                    <tiles:put name="commandHelpKey" value="button.register.back"/>
                    <tiles:put name="bundle" value="mobilebankBundle"/>
                    <tiles:put name="viewType" value="blueGrayLink"/>
                    <tiles:put name="action" value="${form.backActionPath}"/>
                </tiles:insert>
            </div>
            <c:choose>
                <c:when test="${not empty form.returnURL}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.register.skip"/>
                        <tiles:put name="commandHelpKey" value="button.register.skip"/>
                        <tiles:put name="bundle" value="mobilebankBundle"/>
                        <tiles:put name="viewType" value="simpleLink"/>
                        <tiles:put name="onclick">goTo('${form.returnURL}');</tiles:put>
                    </tiles:insert>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="skip"/>
                        <tiles:put name="commandTextKey" value="button.register.skip"/>
                        <tiles:put name="commandHelpKey" value="button.register.skip"/>
                        <tiles:put name="bundle" value="mobilebankBundle"/>
                        <tiles:put name="viewType" value="simpleLink"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
            &nbsp;&nbsp;
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="save"/>
                <tiles:put name="commandTextKey" value="button.register.confirm"/>
                <tiles:put name="commandHelpKey" value="button.register.confirm"/>
                <tiles:put name="bundle" value="mobilebankBundle"/>
                <tiles:put name="isDefault" value="true"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>
