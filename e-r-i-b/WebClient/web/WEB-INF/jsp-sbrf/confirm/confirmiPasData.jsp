<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<tiles:importAttribute />
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="helpLink" value="${phiz:calculateActionURL(pageContext,'/help.do?id=')}${$$helpId}"/>
<c:set var="faqLink" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'p11')}"/>
<br/>
<script type="text/javascript">

    function onLoadFn()
	{
        clientBeforeUnload.init();
	}

	function checkData()
	{
		var p=getElement("$$confirmCardPassword");
        if (p == null)
            p=getElement("$$confirmSmsPassword");
		if ( p.value.length == 0 ) {
			alert ("������� ������.");
			return false;
		}

		return true;
	}
	function demo()
	{
		alert("� ����-������ ������� ����������.");
	}

    doOnLoad (onLoadFn);
</script>
<html:form action="/confirm/way4">
    <html:hidden  property="receiptNo"/>
    <html:hidden  property="passwordsLeft"/>
    <html:hidden  property="passwordNo"/>
    <html:hidden  property="SID"/>
    <c:set var="form" value="${ConfirmWay4PasswordForm}" scope="request"/>
    <c:set var="confirmRequest" value="${form.confirmRequest}"/>
    <c:set var="anotherStrategy" value="${form.anotherStrategyAvailable}"/>
    <c:set var="hasCapButton" value="${form.hasCapButton}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>

    <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp">
        <tiles:put name="bundle" type="string" value="commonBundle"/>
    </tiles:insert>
    
                <tiles:insert definition="roundBorder" flush="false">
                    <tiles:put name="color" value="greenTop"/>
                    <tiles:put name="title" value="���� �� ������ ��������"/>
                    <tiles:put name="data">

                        <h3 class="confirmTitle">��� ����������� ����� � ������� ��� ���������� ������ ����������� ������. ���� �� ������ ����������� ���� SMS-�������, ������� �� ������ ������������ �� SMS�, � ���������� �� ��� ��������� ������� ������ ��������� � �������. ��� ������������� ������� � ���� ������� �� ������ ������������ �����.
                            <c:if test="${hasCapButton}">
                                ��� ������������� ������� � ����� ������� �� ������ ������������ �� �����.
                            </c:if>
                            <c:set var="faqConfirm" value="${phiz:calculateActionURLWithAnchor(pageContext, '/faq.do', 'm6')}"/>
                            �� ������ ��������� ����������������
                            <a href="#" onclick="javascript:openFAQ('${faqConfirm}');"> ������ �������������</a>
                            ����� � ������� SMS-������� ��� ������� � ���� � ������� ��������� ������������ - ��������� ������������� � �������.
                        </h3>

                        <c:choose>
                            <c:when test="${not empty confirmRequest}">

                                <c:choose>
                                    <c:when test="${hasCapButton}">
                                        <div class="confirmButtonsExpandedArea">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="confirmButtons">
                                    </c:otherwise>
                                </c:choose>
                                    <tiles:insert definition="confirmButtons" flush="false">
                                        <tiles:put name="ajaxUrl" value="/async/confirm"/>
                                        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                                        <tiles:put name="anotherStrategy" beanName="anotherStrategy"/>
                                        <tiles:put name="hasCapButton" beanName="hasCapButton"/>
                                        <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                                        <tiles:put name="message" value="${message}"/>
                                    </tiles:insert>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <tiles:insert definition="commandButton" flush="false">
                                    <tiles:put name="commandKey"     value="button.confirm"/>
                                    <tiles:put name="commandHelpKey" value="button.validate"/>
                                    <tiles:put name="bundle"         value="commonBundle"/>
                                    <tiles:put name="isDefault"        value="true"/>
                                </tiles:insert>
                            </c:otherwise>
                        </c:choose>
                    </tiles:put>
                </tiles:insert>
                <div class="clear"></div>
                <div id="infoOnConfirm">
                    <tiles:insert definition="roundBorder" flush="false">
                        <tiles:put name="color" value="gray"/>
                        <tiles:put name="data">
                            <div class="loginDataBlock">
                                <div class="login_info">
                                    <h1><bean:message bundle="commonBundle" key="text.SBOL.info"/></h1>
                                    <p class="paperEnterLink">
                                        <a href="https://esk.sberbank.ru/esClient/_ns/ProtectInfoPage.aspx" target="_blank"><bean:message bundle="commonBundle" key="text.SBOL.security"/></a>
                                    </p>
                                    <p class="paperEnterLink">
                                        <a href="http://sberbank.ru/ru/person/dist_services/warning/" target="_blank">� ������ ��� ������������� ���������� ������������</a>
                                    </p>
                                    <p>
                                        <bean:message bundle="commonBundle" key="text.SBOL.about"/>
                                        �� ������ ��� ������� 2 ���������:
                                    </p>
                                        <ul>
                                            <li>
                                                � ������� ���������� ����� ����� ���������� ���������������� ��������� ������ (<a class="sbrfLink" href="http://sberbank.ru/ru/about/today/oib/" target="_blank">������ ���������</a>);
                                            </li>
                                            <li>
                                                � ������� &laquo;���������� �����&raquo;, �������� SMS-��������� �� ����� 900 (��� �� ������ +7 926 2000 900, +7 916 572 3900) �
                                                �������: Parol xxxxx (��� xxxxx - ��������� 5 ���� ������ ����� �����). � ���������� ��� ������ ������ � SMS-���������.
                                                ����� ��������� �� ��������� ����������� ������ ��������� ������ +7 (495) 500 5550, 8 800 555 5550 � �������� �������������
                                                ������������.
                                            </li>
                                        </ul>
                                    <p>
                                        <bean:message bundle="commonBundle" key="text.SBOL.fullAccess"/>
                                        <br/>
                                        <bean:message bundle="commonBundle" key="text.SBOL.iPas"/>
                                        ��� ���� ����� �������� ����������� ������:
                                    </p>
                                    <ul>
                                        <li>
                                            ������������ ��� � �������� ����� ��������, �������� ��������� ������ � ������� ���������� �����. ���� ��� �������� 20 ����������� �������;
                                        </li>
                                        <li>
                                            ��� ����� � ������� ������� �� ������ &laquo;SMS-�������������&raquo; � �� �������� SMS-��������� � �������, ���� �� ���������� � ������ ��������� ����.
                                        </li>
                                    </ul>
                                    <p>
                                        ���� �� ������ ��� �������� ������������� ������������ � ������, �� �� ������ �������� ����� ����������� �������
                                        (������� ������ � ������������� ��� ���� ����� �������������).
                                    </p>
                                    <p class="important">
                                        <span class="bold">�������� ��������:</span> <span class="italic">���� �� ��� ����� � ������� ��� ���� ������ ����������� ����� ���������� ������,
                                        ���� � ������� ����� ������������� ���������� �� 1 ���. �� ������ ����� ����� � ������� ����� ��� ���� �������� ����� ���������� ������.</span>
                                    </p>
                                    <p class="FAQ"><a href="#" onclick="openFAQ('${faqLink}')">����� ���������� �������</a></p>
                                </div>
                            </div>
                        </tiles:put>
                    </tiles:insert>
                </div>

                <div class="clear"></div>          
</html:form>