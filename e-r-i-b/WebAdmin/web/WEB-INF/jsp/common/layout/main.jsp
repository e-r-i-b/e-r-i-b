<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html:html>

	<%@ include file="/WEB-INF/jsp/common/layout/html-head.jsp"%>

	<tiles:importAttribute name="needSave" ignore="true" scope="request"/>

    <c:set var="globalImagePath" value="${globalUrl}/images"/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

	<body>
    <div id="pageContent" class="fonContainer">
		<c:catch var="errorJSP">
            <input type="hidden" name="operation"/>
            <tiles:insert attribute="messagesPage">
                <tiles:useAttribute name="messagesBundle"/>
                <tiles:put name="bundle" type="string" value="${messagesBundle}"/>
                <c:set var="bundleName" value="${messagesBundle}"/>
            </tiles:insert>
            <script type="text/javascript">
                document.imgPath = "${imagePath}/";

                <%-- ������� ������ �������� onload � body. ������ ������� ��������� � ����� �
                ������������ ������ �������. � ������ ������� ��������������� �������� onload
                ����� ��������� �� ������� ����� ��������--%>
                function onLoad(event)
                {
                    setWorkspaceWidth();
                    validCountMMIns();
                    showMessage();
                }
                doOnLoad(onLoad);
            </script>
            <html:form show="true" style="margin: 0;">
                <div id="centerLoadDiv">
                    <div id="loadingImg">
                        <img src="${skinUrl}/images/ajax-loader64.gif" alt=""/>
                    </div>
                    <span>����������, ���������,<br /> ��� ������ ��������������.</span>
                </div>
                <c:if test="${headerGroup == 'true'}">
                    <%@ include file="headerGroup.jsp"%>
                </c:if>
                <div class="clear"></div>
                <div id="wrapper">
                    <div id="workspace">
                        <div class="content <c:if test="${leftMenu == ''}">noLeftMenu</c:if>">
                            <div class="mainInnerBlock">
                                <c:if test="${additionalInfoBlock != ''}">
                                    <tiles:insert attribute="additionalInfoBlock">
                                        <tiles:put name="additionalInfoBlock" value="${additionalInfoBlock}"/>
                                    </tiles:insert>
                                </c:if>
                                <div class="pageTitle">${pageTitle}</div>
                                <c:if test="${descTitle != ''}">
                                    <div class="pmntTitleText">${descTitle}</div>
                                </c:if>
                                <!-- ���� ������ -->
                                <%@ include file="buttonMenu.jsp"%>

                                <!--������. ����� ����� ����� ��������-->
                                <%@ include file="filter.jsp"%>

                                <%@ include file="workspace.jsp"%>
                            </div>
                        </div>
                        <!-- ����� ���� -->
                        <c:if test="${leftMenu != ''}">
                            <%@ include file="leftSection.jsp"%>
                        </c:if>
                        <div class="clear"></div>
                    </div>
                </div>
                <div class="clear"></div>
            </html:form>
        </c:catch>
    </div>
    <c:if test="${headerGroup == 'true'}">
        <div id="footer">
            <%@ include file="/WEB-INF/jsp/common/layout/footer.jsp"%>
        </div>
    </c:if>
<c:if test="${not empty errorJSP}">
    ${phiz:writeLogMessage(errorJSP)}
    <script type="text/javascript">
        window.location = "/${phiz:loginContextName()}${initParam.errorRedirect}";
    </script>
</c:if>
</body>
</html:html>
