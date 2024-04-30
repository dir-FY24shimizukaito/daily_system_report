<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>


<c:set var="actAtt" value="${ForwardConst.ACT_ATT.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>勤怠 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${sessionScope.login_employee.name}" /></td>
                </tr>
                
                
                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${attendance.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <p>
            <a href="<c:url value='?action=${actAtt}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>