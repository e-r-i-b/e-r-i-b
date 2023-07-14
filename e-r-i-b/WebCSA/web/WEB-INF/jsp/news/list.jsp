<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>


<tiles:insert definition="main" flush="false">
    <tiles:put name="needRegionSelector" value="true"/>
    <tiles:put name="data" type="string">
        <html:form action="/news/list">
            <tiles:insert page="/WEB-INF/jsp/common/layout/messages.jsp" flush="false">
                <tiles:put name="bundle" type="string" value="commonBundle"/>
            </tiles:insert>
            <c:set var="form" value="${ListNewsForm}"/>
            <div id="breadcrumbs">
                <tiles:insert definition="breadcrumbsLink" flush="false">
                    <tiles:put name="main" value="true"/>
                    <tiles:put name="last" value="true"/>
                    <tiles:put name="action" value="${csa:calculateActionURL(pageContext, '/index')}"/>
                </tiles:insert>
            </div>
            <div id="CSAnews">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="title">�������</tiles:put>
                <tiles:put name="data">
                    <tiles:insert definition="formHeader" flush="false">
                        <tiles:put name="description">
                            <p>
                                �� ���� �������� �� ������ ������������ � ���������� ��������� � ������� ��������� ������. <br>
                                ��� ��������� ������������ ��� ������� ����� �������������� �������.
                            </p>
                        </tiles:put>
                    </tiles:insert>
                    <%-- ������� --%>
                    <%@ include file="filterNewsPeriod.jsp"%>
                    <tiles:insert definition="filter" flush="false">
                        <tiles:put name="hiddenData">
                            <table class="payment-templates-filter">
                                <tr>
                                    <td>
                                        <html:text property="filter(search)" styleId="filter(filterSearch)" styleClass="customPlaceholder" size="93" maxlength="256" title="������� ����� ��� ������"/>
                                    </td>
                                    <td class="alignRight">
                                        <span class="filterMoreButton">
                                            <div class="commandButton" onclick="callOperation(null, 'button.filter'); return false;">
                                                <div class="butGreen">
                                                    <div class="left-corner"></div>
                                                    <div class="text">
                                                        <span>�����</span>
                                                    </div>
                                                    <div class="right-corner"></div>
                                                </div>
                                            </div>
                                            <div class="clear"></div>
                                        </span>
                                    </td>
                                <tr>
                                <tr>
                                    <td>
                                        <div class="labelAndCkeckboxes">
                                            <html:checkbox property="filter(important)" styleId="filter(filterImportant)"/>
                                            <label for="filter(filterImportant)">������ ����� ������ �������</label>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </tiles:put>
                        <tiles:put name="hideFilterButton" value="true"/>
                    </tiles:insert>
                    <%-- /������ --%>
                    <%-- ������� --%>
                    <div class="news" id="news" >
                        <div class="news-list" id="news-list" >
                            <tiles:insert definition="simpleTableTemplate" flush="false">
                                <tiles:put name="grid">
                                    <sl:collection id="news" name="form" property="data" model="simple-pagination" styleClass="rowOver" bundle="commonBundle">
                                        <sl:collectionItem styleClass="align-left greenRepeatLink">
                                            <div class="separateNews">
                                                <div class="news-header">
                                                    <div class="news-title <c:if test="${news.important == 'HIGH'}">bold</c:if>">
                                                      <span class="word-wrap">
                                                          <span class="newsDate">${csa:formatDateDependsOnSysDate(news.newsDate, true, true)}&nbsp;</span>
                                                          <a class="news-title-text"  href="/${globalUrl}/news/view.do?id=${news.id}"><c:out value="${news.title}"/></a>
                                                      </span>
                                                    </div>
                                                </div>
                                                <br />
                                                <div>
                                                    <p class="introText">
                                                        <span class="word-wrap">
                                                            ${csa:process(news.shortText)}
                                                        </span>
                                                    </p>
                                                </div>
                                            </div>
                                        </sl:collectionItem>
                                    </sl:collection>
                                </tiles:put>
                                <tiles:put name="isEmpty" value="${empty form.data}"/>
                                <tiles:put name="emptyMessage">
                                    �� ������� �� ������ �������.
                                </tiles:put>
                            </tiles:insert>
                        </div>
                    </div>
                    <%-- /������� --%>
                </tiles:put>
            </tiles:insert></div>
        </html:form>
    </tiles:put>
    <tiles:put name="footer" value="/WEB-INF/jsp/common/copyright.jsp"/>
</tiles:insert>