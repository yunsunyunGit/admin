<%@tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="listData" type="java.util.List" required="true" %>
<%@ attribute name="style" type="java.lang.String" required="false" %>
<%@ attribute name="choose" type="java.lang.String" required="false" %>
<c:if test="${choose ==null || choose == '1'}">
    <option value="-1">请选择</option>
</c:if>
<c:if test="${choose == '2'}">
    <option value="-1">请选择</option>
    <option value="-2">未分配</option>
</c:if>
<c:if test="${style == null}">
    <c:forEach items="${listData}" var="ls">
        <option value="${ls.id}">${ls.theme}</option>
    </c:forEach>
</c:if>
<c:if test="${style == 'checkbox-inline'}">
    <label class="checkbox-inline"><input type="checkbox" value="${ls.id}">${ls.theme}</label>
</c:if>
<c:if test="${style == 'table'}">
    <thead>
    <tr>
        <th class="table-checkbox">
            <input type="checkbox" class="group-checkable" onclick="checkAllBox(this,'appTd')"/>
        </th>
        <th>会议主题</th>
        <th>开始时间</th>
        <th>举办单位</th>
        <th>举办地址</th>
    </thead>
    <tbody>
    <c:forEach items="${listData}" var="ls">
        <tr class="odd gradeX">
            <td><input type="checkbox" name="appTd" class="checkboxes" value="${ls.id}"/></td>
            <td>${ls.theme}</td>
            <td><fmt:parseDate value="${ls.startTime}" pattern="yyyy-MM-dd"/></td>
            <td>${ls.conductUnit}</td>
            <td>${ls.conductAddress}</td>
        </tr>
        </tbody>
    </c:forEach>
</c:if>

<c:if test="${style != 'table' && style != 'checkbox-inline' && style != null}">
    <c:forEach items="${listData}" var="ls">
        <${style} name="${ls.id}"><a href='javascript:void(0)'>${ls.theme}</a></${style}>
    </c:forEach>
</c:if>
