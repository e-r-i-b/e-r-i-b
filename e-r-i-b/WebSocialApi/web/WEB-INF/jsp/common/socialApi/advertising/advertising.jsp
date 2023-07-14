<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="sl" uri="http://struts.application-servers.com/layout" %>

<html:form action="/private/advertising">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="iphone" flush="false">
        <tiles:put name="data">
            <sl:collection id="banner" property="listBanners" model="xml-list" title="banners">
                <c:if test="${not form.mapAreasEmpty[banner.id]}">
                    <sl:collectionItem title="banner">
                        <areas>
                            <c:forEach var="area" items="${banner.areas}">
                                <c:set var="type" value="${area.areaName}"/>

                                <c:choose>
                                    <c:when test="${type == 'title' and not empty banner.title}">
                                        <area>
                                            <order>${area.orderIndex}</order>
                                            <type>${type}</type>
                                            <titleType>
                                                <title><c:out value="${banner.title}"/></title>
                                            </titleType>
                                        </area>
                                    </c:when>
                                    <c:when test="${type == 'text' and not empty banner.text}">
                                        <area>
                                            <order>${area.orderIndex}</order>
                                            <type>${type}</type>
                                            <textType>
                                                <text><c:out value="${banner.text}"/></text>
                                            </textType>
                                        </area>
                                    </c:when>
                                    <c:when test="${type == 'image' and banner.image != null}">
                                        <area>
                                            <order>${area.orderIndex}</order>
                                            <type>${type}</type>
                                            <imageType>
                                                <tiles:insert definition="imageType" flush="false">
                                                    <tiles:put name="name" value="image720x125"/>
                                                    <tiles:put name="imageData" value="${banner.image}"/>
                                                </tiles:insert>
                                                <c:set var="image" value="${banner.image}"/>
                                                <c:set var="imageLinkUrl" value="${image.linkURL}"/>
                                                <c:if test="${not empty imageLinkUrl}">
                                                    <imageLink><c:out value="${imageLinkUrl}"/></imageLink>
                                                </c:if>
                                            </imageType>
                                        </area>
                                    </c:when>
                                    <c:when test="${type == 'buttons' and not form.mapButtonsEmpty[banner.id]}">
                                        <area>
                                            <order>${area.orderIndex}</order>
                                            <type>${type}</type>
                                            <buttonsType>
                                                <c:forEach var="button" items="${banner.buttons}">
                                                    <c:if test="${button.show and (not empty button.title or not empty button.image)}">
                                                        <button>
                                                            <order>${button.orderIndex}</order>
                                                            <url>${button.url}</url> <%--если url пуст, то так и отправляем: делаем логику идентичную основному приложению--%>
                                                            <c:if test="${button.image != null}">
                                                                <tiles:insert definition="imageType" flush="false">
                                                                    <tiles:put name="name" value="image"/>
                                                                    <tiles:put name="imageData" value="${button.image}"/>
                                                                </tiles:insert>
                                                            </c:if>
                                                            <c:if test="${not empty button.title}">
                                                                <title><c:out value="${button.title}"/></title>
                                                            </c:if>
                                                        </button>
                                                    </c:if>
                                                </c:forEach>
                                            </buttonsType>
                                        </area>
                                    </c:when>
                                </c:choose>

                            </c:forEach>
                        </areas>
                    </sl:collectionItem>
                </c:if>
            </sl:collection>
        </tiles:put>
    </tiles:insert>
</html:form>
