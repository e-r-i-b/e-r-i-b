<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib prefix="tiles" uri="http://jakarta.apache.org/struts/tags-tiles" %>
<%@ taglib prefix="html" uri="http://jakarta.apache.org/struts/tags-html" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="mobile" uri="http://rssl.com/tags/mobile" %>

<html:form action="/private/finances/financeCalendar/showSelected">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="currentDate" value="${phiz:currentDate()}"/>
    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <data>
                <c:if test="${form.dayExtractByOperations != null}">
                    <sl:collection id="data" property="dayExtractByOperations" model="xml-list" title="operations">
                        <sl:collectionItem title="operation">
                            <id>${data.id}</id>
                            <description>${data.description}</description>
                            <categoryName>${data.categoryName}</categoryName>
                            <categoryColor>${data.categoryColor}</categoryColor>
                            <amount>${data.amount}</amount>
                        </sl:collectionItem>
                    </sl:collection>
                </c:if>
                <c:if test="${form.dayExtractByAutoSubscriptions != null}">
                    <sl:collection id="data" property="dayExtractByAutoSubscriptions" model="xml-list" title="autoSubscriptions">
                        <sl:collectionItem title="autoSubscription">
                            <number>${data.number}</number>
                            <description>${data.description}</description>
                            <receiverName>${data.receiverName}</receiverName>
                            <executionEventType>${data.executionEventType}</executionEventType>
                            <sumType>${data.sumType}</sumType>
                            <amount>${data.amount}</amount>
                            <c:if test="${data.payDate != null}">
                                <payDate><fmt:formatDate value="${data.payDate.time}" pattern="dd.MM.yyyy"/></payDate>
                            </c:if>
                        </sl:collectionItem>
                    </sl:collection>
                </c:if>
                <c:if test="${form.dayExtractByInvoiceSubscriptions != null}">
                    <sl:collection id="data" property="dayExtractByInvoiceSubscriptions" model="xml-list" title="invoiceSubscriptions">
                        <sl:collectionItem title="invoiceSubscription">
                            <id>${data.id}</id>
                            <type>${data.type}</type>
                            <c:if test="${data.payDate != null}">
                                <payDate><fmt:formatDate value="${data.payDate.time}" pattern="dd.MM.yyyy"/></payDate>
                            </c:if>
                            <serviceName>${data.serviceName}</serviceName>
                            <accountingEntityName>${data.accountingEntityName}</accountingEntityName>
                            <receiverName>${data.receiverName}</receiverName>
                            <requisites>${data.requisites}</requisites>
                            <amount>${data.amount}</amount>
                        </sl:collectionItem>
                    </sl:collection>
                </c:if>
                <c:if test="${form.dayExtractByReminders != null}">
                    <sl:collection id="data" property="dayExtractByReminders" model="xml-list" title="reminderDescriptions">
                        <sl:collectionItem title="reminderDescription">
                            <id>${data.id}</id>
                            <info>
                                <c:if test="${data.info != null}">
                                    <type>${data.info.type}</type>
                                    <c:if test="${data.info.onceDate != null}">
                                        <onceDate><fmt:formatDate value="${data.info.onceDate.time}" pattern="dd.MM.yyyy"/></onceDate>
                                    </c:if>
                                    <dayOfMonth>${data.info.dayOfMonth}</dayOfMonth>
                                    <monthOfQuarter>${data.info.monthOfQuarter}</monthOfQuarter>
                                    <c:if test="${data.info.createdDate != null}">
                                        <createdDate><fmt:formatDate value="${data.info.createdDate.time}" pattern="dd.MM.yyyy"/></createdDate>
                                    </c:if>
                                </c:if>
                            </info>
                            <name>${data.name}</name>
                            <c:if test="${data.nextReminderDate != null}">
                                <nextReminderDate><fmt:formatDate value="${data.nextReminderDate.time}" pattern="dd.MM.yyyy"/></nextReminderDate>
                            </c:if>
                            <amount>${data.amount}</amount>
                        </sl:collectionItem>
                    </sl:collection>
                </c:if>
            </data>
        </tiles:put>
    </tiles:insert>
</html:form>