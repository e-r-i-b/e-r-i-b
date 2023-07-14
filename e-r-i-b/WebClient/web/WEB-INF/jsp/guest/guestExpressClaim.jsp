<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--������ ��� �������� ��������-������--%>
<%--pageTitle - ��������� ��������--%>
<%--phone - ������� �������--%>
<%--data - ������� ��������--%>

<tiles:importAttribute/>
<c:set var="imagesPath" value="${globalUrl}/commonSkin/images/guest"/>
<c:set var="hasAccount" value="${phiz:hasGuestAnyAccount()}"/>

<html:html>
    <%@ include file="html-head.jsp" %>
    <body class="gPage">
        <html:form show="true">
            <div id="workspace" class="fullHeight">

                <div class="mainContent">
                    <div class="header noButtonRegistration">
                        <a class="logoSB" href="http://www.sberbank.ru/">
                            <img src="${imagesPath}/logoSB.png" height="72" width="289"/>
                        </a>

                        <div class="headPhones">
                            <div class="federal">
                                <span class="phoneIco"></span>
                                8 (800) 555 55 50
                            </div>
                            <div class="regional">+7 (495) 500-55-50</div>
                        </div>
                    </div>

                    <div class="title">
                        <p>${pageTitle}</p>

                        <div class="g-num">
                            ��������� �����
                            <div class="g-num-mask">${phiz:getCutPhoneNumber(phone)}</div>
                        </div>
                    </div>
                    <div class="pageBackground">
                        <div class="pageContent">
                            <div class="wrapper">
                                <div class="ears-content">
                                    <div class="formContent">
                                        <div class="formContentMargin">
                                            <tiles:insert page="/WEB-INF/jsp-sbrf/common/layout/messages.jsp">
                                                <tiles:put name="bundle" type="string" value="commonBundle"/>
                                            </tiles:insert>
                                            ${data}
                                        </div>
                                    </div>
                                </div>
                                <div class="base">
                                    <div class="main_row">
                                        <div class="content_row"></div>
                                    </div>
                                    <!--ears-s/ears-b-->
                                    <div class="ears ears-b"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="clear"></div>
                <div class="footer">
                    <div class="references">
                        <a href="http://www.sberbank.ru/moscow/ru/person/contributions/max_stavka/">���������� � ���������� ������� �� ��������� ����������� ������ � �����������
                            ������</a><br/>
                        <a href="http://www.sberbank.ru/moscow/ru/investor_relations/disclosure/shareholders/">���������� � �����, ��� ��������� ������� ��������� ����</a>

                        <div class="copyright">
                            &copy 1997 � 2015 �������� ������, ������, ������, 117997, ��. ��������, �.19, ���. +7(495) 500
                            5550 <br/>
                            8 800 555 5550. ����������� �������� �� ������������� ���������� �������� �� 8 ������� 2012
                            ����. <br/>
                            ��������������� ����� - 1481.
                            <div class="icon"></div>
                            <a class="icon" href="http://www.sberbank.ru/moscow/ru/important/"><span>�������� �����</span>.</a>
                        </div>
                    </div>
                </div>
                <div id="loading" style="left:-3300px;">
                    <div id="loadingImg"><img src="${globalUrl}/commonSkin/images/ajax-loader64.gif"/></div>
                    <span>����������, ���������,<br/> ��� ������ ��������������.</span>
                </div>
            </div>
        </html:form>
    </body>
</html:html>