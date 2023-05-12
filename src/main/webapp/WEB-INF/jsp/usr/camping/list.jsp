<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<c:set var="pageTitle" value="CAMPING" />
<%@ include file="../common/head.jspf"%>
<%@ include file="../common/toastUiEditorLib.jspf"%>
<%
List<String[]> data = (List<String[]>) request.getAttribute("data");
%>
    <meta charset="UTF-8">
    <h1>Camping List</h1>
   <table class="table-box-type-1 table w-full"
			style="border-collaspe: collaspe; width: 700px;">
        <thead>
            <tr>
                <th>캠핑(야영)장명</th>
                <th>주소</th>
                <th>상세보기</th>
            </tr>
        </thead>
        <tbody>
            <% for (String[] row : data) { %>
                <tr>
                    <th><%= row[0] %></th>
                    <th><%= row[4] %></th>
                    <th><a href="#">클릭</a></th>
                </tr>
            <% } %>
        </tbody>
    </table>
<%@ include file="../common/foot.jspf"%>
