<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="utils.HTMLUtils" %>


<!doctype html>
<html lang="ja">
<head>
	<jsp:include page="_header.jsp" />

	<title>My家計簿アプリ|TOP</title>
</head>
<body>
	<jsp:include page="_navbar.jsp" />

	<div class="container pt-6">

		<div class="row">
			<div class="col">
				<div class="alert alert-success alert-dismissible fade show" role="alert">
					<h4 class="alert-heading h5 font-weight-bold"><span class="oi oi-pin"></span> 成功しました！</h4>
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<ul>
						<li>「2018/05/30 交際費 -6,800」を登録しました。</li>
					</ul>
				</div>
			</div>
		</div>

		<c:if test="${errors != null}">
			<div class="row">
				<div class="col">
					<div class="alert alert-danger alert-dismissible fade show" role="alert">
						<h4 class="alert-heading h5 font-weight-bold"><span class="oi oi-pin"></span> エラーが発生しました！</h4>
						<button type="button" class="close" data-dismiss="alert" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<ul>
							<c:forEach var="error" items="${errors}">
								<li>${error}</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
		</c:if>
		<% session.setAttribute("errors", null); %>

		<div class="row pt-4">
			<div class="col">
				<nav class="float-left">
					<ul class="pagination">
						<li class="page-item">
							<a class="page-link" href="index.html?jumpY=0&now=${now}"><span class="oi oi-chevron-left"></span><span class="oi oi-chevron-left"></span> 前年</a>
						</li>
						<li class="page-item">
							<a class="page-link" href="index.html?jump=0&now=${now}"><span class="oi oi-chevron-left"></span> 前月</a>
						</li>
					</ul>
				</nav>
			</div>

			<div class="col text-center">
				<h2 class="font-weight-bold"><span class="oi oi-calendar"></span>${HTMLUtils.nowDateFormat(now)}</h2>
			</div>

			<div class="col">
				<nav class="float-right">
					<ul class="pagination">
						<li class="page-item">
							<a class="page-link" href="index.html?jump=1&now=${now}">翌月 <span class="oi oi-chevron-right"></span></a>
						</li>
						<li class="page-item">
							<a class="page-link" href="index.html?jumpY=1&now=${now}">翌年 <span class="oi oi-chevron-right"></span><span class="oi oi-chevron-right"></span></a>
						</li>
					</ul>
				</nav>
			</div>
		</div>

		<div class="row pt-1">
			<div class="col">
				<div class="card bg-light border-info mb-4">
					<div class="card-header text-center"><span class="oi oi-yen"></span> 今月の収入合計 <small class="text-muted">（先月比）</small></div>
					<div class="card-body">
						<p class="card-text text-center"><fmt:formatNumber value="${currentIncome}" pattern="0,000" /><small class="text-${HTMLUtils.color(compareIncome)}">（${HTMLUtils.addPlus(compareIncome)}）</small></p>
					</div>
				</div>
			</div>

			<div class="col">
				<div class="card bg-light border-dark mb-4">
					<div class="card-header text-center"><span class="oi oi-yen"></span> 今月の支出合計 <small class="text-muted">（先月比）</small></div>
					<div class="card-body">
						<p class="card-text text-center"><fmt:formatNumber value="${currentSpend}" pattern="0,000" /><small class="text-${HTMLUtils.color(compareSpend)}">（${HTMLUtils.addPlus(compareSpend)}）</small></p>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col">
				<table class="table table-hover">
					<thead>
						<tr>
							<th scope="col" style="width: 90px;">#</th>
							<th scope="col" style="width: 120px;">日付</th>
							<th scope="col">カテゴリー</th>
							<th scope="col">備考</th>
							<th scope="col" style="width: 120px;">金額</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="abs1" items="${list}">
							<tr class="table-light">
								<th scope="row">
									<div class="btn-group">
										<button type="button" class="btn btn-secondary btn-sm dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
											操作
										</button>
										<div class="dropdown-menu">
											<a class="dropdown-item" href="detail.html?id=${abs1.id}"><span class="oi oi-spreadsheet"></span> 詳細</a>
											<a class="dropdown-item" href="copy.html?id=${abs1.id}"><span class="oi oi-paperclip"></span> コピー</a>
											<div class="dropdown-divider"></div>
											<a class="dropdown-item delete-btn" href="delete.html?id=${abs1.id}"><span class="oi oi-trash"></span> 削除</a>
										</div>
									</div>
								</th>
								<td>${HTMLUtils.dateFormat(abs1.date)}</td>
								<td>${abs1.category}</td>
								<td>${abs1.note}</td>
								<td class="text-right"><fmt:formatNumber value="${abs1.price}" pattern="0,000" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<hr>

<jsp:include page="_footer.jsp" />