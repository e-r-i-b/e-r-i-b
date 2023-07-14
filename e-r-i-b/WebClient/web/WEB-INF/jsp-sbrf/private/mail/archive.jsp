<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html:form action="/private/mail/archive">
    <tiles:importAttribute/>
    <c:set var="imagePath" value="${skinUrl}/images"/>

    <tiles:insert definition="mailMain">

          <tiles:put name="data" type="string">
              <script type="text/javascript">
                function checkSelection()
                {
                    checkIfOneItem("selectedIds");
                    var qnt = getSelectedQnt("selectedIds");
                    if (qnt < 1)
                    {
                        addError("Выберите хотя бы одно письмо для восстановления!");
                        return false;
                    }
                    return true;
                }

            </script>
                 <div id="feedback">
                    <tiles:insert definition="mainWorkspace" flush="false">
                          <tiles:put name="title"><bean:message bundle="mailBundle" key="list.title"/></tiles:put>
                          <tiles:put name="data">
                              <c:set var="form" value="${phiz:currentForm(pageContext)}" scope="request"/>
                              <c:set var="searchText" value="${fn:trim(form.filters.subject)}"/>
                              <c:set var="searchInputText" value="${searchText}"/>
                              <c:if test="${searchText == ''}">
                                  <c:set var="searchInputText" value="введите тему письма"/>
                              </c:if>
                                <tiles:insert definition="formHeader" flush="false">
                                    <tiles:put name="image" value="${imagePath}/MailTo_big.png"/>
                                    <tiles:put name="description">
                                        <c:set var="mess" value="${phiz:getStaticMessage('com.rssl.iccs.CREATE_MAIL_FORM')}"/>
                                        <c:set var="processed" value="${phiz:processBBCode(mess)}"/>
                                        <h3>${processed}</h3>
                                    </tiles:put>
                                </tiles:insert>
                                <div class="tabContainer">
                                    <tiles:insert definition="paymentTabs" flush="false">
                                        <tiles:put name="count" value="3"/>
                                        <tiles:put name="tabItems">
                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                <tiles:put name="position" value="first"/>
                                                <tiles:put name="active" value="false"/>
                                                <tiles:put name="title"><bean:message key="sentList.title" bundle="mailBundle"/></tiles:put>
                                                <tiles:put name="action" value="/private/mail/sentList.do"/>
                                            </tiles:insert>
                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                <tiles:put name="active" value="false"/>
                                                <tiles:put name="title"><bean:message key="receivedList.title" bundle="mailBundle"/></tiles:put>
                                                <tiles:put name="action" value="/private/mail/list"/>
                                            </tiles:insert>
                                            <tiles:insert definition="paymentTabItem" flush="false">
                                                <tiles:put name="position" value="last"/>
                                                <tiles:put name="active" value="true"/>
                                                <tiles:put name="title"><bean:message key="removedList.title" bundle="mailBundle"/></tiles:put>
                                                <tiles:put name="action" value="/private/mail/archive.do"/>
                                            </tiles:insert>
                                        </tiles:put>
                                    </tiles:insert>

                                <tiles:insert definition="addToFavouriteButton" flush="false">
                                    <tiles:put name="formName"><bean:message bundle="mailBundle" key="removedList.title"/></tiles:put>
                                    <tiles:put name="typeFormat">ARCHIVE_MAIL_LINK</tiles:put>
                                </tiles:insert>

                                <div class="clear"></div>
                                <c:set var="direction" value="true"/>
                                <%@ include file="mailFilter.jsp"%>

                                <tiles:insert definition="simpleTableTemplate" flush="false">
                                     <tiles:put name="grid">
                                         <sl:collection id="listElement" model="simple-pagination" property="data" bundle="mailBundle" selectBean="email">
                                             <sl:collectionParam id="selectType" value="checkbox"/>
                                             <sl:collectionParam id="selectName" value="selectedIds"/>
                                             <sl:collectionParam id="selectProperty" value="id"/>

                                             <c:set var="state" value="${listElement[0]}"/>
                                             <c:set var="email" value="${listElement[1]}"/>

                                             <sl:collectionItem title="label.number">
                                                 <c:out value="${email.num}"/>
                                             </sl:collectionItem>

                                             <sl:collectionItem title="label.subject" styleClass="text-green bold">
												<c:out value="${email.subject}"/>
                                             </sl:collectionItem>
                                             <sl:collectionItem title="label.dateTime">
                                                ${phiz:formatDateDependsOnSysDate(email.date, true, false)}                                                
                                             </sl:collectionItem>

                                             <sl:collectionItem title="label.status">
                                                  <c:set var="mailState" value="${state}"/>
                                                  <c:choose>
                                                      <c:when test="${mailState == 'READ'}">
                                                          <bean:message key="label.statusReceived" bundle="mailBundle"/>
                                                      </c:when>
                                                      <c:when test="${mailState == 'ANSWER'}">
                                                          <bean:message key="label.statusAnswer" bundle="mailBundle"/>
                                                      </c:when>
                                                      <c:when test="${mailState == 'DRAFT'}">
                                                          <bean:message key="label.statusDraft" bundle="mailBundle"/>
                                                      </c:when>
                                                      <c:when test="${mailState == 'ADMIN'}">
                                                          <c:set var="mState" value="Отправлено"/>
                                                          <c:if test="${email.state == 'CLIENT_DRAFT'}">
                                                               <c:set var="mState" value="Черновик"/>
                                                          </c:if>
                                                          <c:out value="${mState}"/>
                                                      </c:when>
                                                      <c:otherwise>
                                                          <bean:message key="label.statusNew" bundle="mailBundle"/>
                                                      </c:otherwise>
                                                  </c:choose>
                                             </sl:collectionItem>
                                             <sl:collectionItem title="label.type">
                                                 <sl:collectionItemParam id="value" condition="${email.direction=='CLIENT'}"><bean:message key="directionC" bundle="mailBundle"/></sl:collectionItemParam>
                                                 <sl:collectionItemParam id="value" condition="${email.direction=='ADMIN'}"><bean:message key="directionA" bundle="mailBundle"/></sl:collectionItemParam>
                                             </sl:collectionItem>
                                         </sl:collection>
                                     </tiles:put>
                                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                                    <tiles:put name="emptyMessage">
                                        <bean:message key="message.empty.del" bundle="mailBundle"/>
                                    </tiles:put>
                                </tiles:insert>
                                <div class="buttonsArea">
                                <div style="height:20px;">&nbsp;</div>
                                <tiles:insert definition="confirmationButton" flush="false" operation="RemoveClientMailOperation">
                                    <tiles:put name="winId" value="confirmation"/>
                                    <tiles:put name="title" value="Подтверждение восстановления письма из удаленных"/>
                                    <tiles:put name="currentBundle" value="mailBundle"/>
                                    <tiles:put name="confirmCommandKey" value="button.rollback"/>
                                    <tiles:put name="validationFunction">
                                        function()
                                        {
                                            return checkSelection();
                                        }
                                    </tiles:put>
                                    <tiles:put name="message"><bean:message key="confirmRestore.text" bundle="mailBundle"/></tiles:put>
                                </tiles:insert>
                                <div class="clear"></div>
                                </div>
                        </tiles:put>
                    </tiles:insert>
              </div>
            </div>
        </tiles:put>
    </tiles:insert>
 </html:form>