<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="utils.HTMLUtils"%>

<!doctype html>
<html lang="ja">
<head>

	<jsp:include page="_header.jsp" />
	<title>My家計簿アプリ|更新フォーム</title>
</head>
<body>
	<jsp:include page="_navbar.jsp" />

	<div class="container pt-6">


		<%-- error文のinclude --%>
		<jsp:include page="_errors.jsp" />


		<div class="row justify-content-between">
			<div class="offset-1 col">
				<h2 class="font-weight-bold">修正フォーム</h2>
			</div>
		</div>

		<hr class="mt-1">

		<form action="update.html?id=${param.id}" method="post">
			<div class="form-group row">
				<label for="date" class="offset-2 col-sm-2 col-form-label font-weight-bold">日付 <span class="badge badge-danger">必須</span></label>
				<div class="col-2">
					<input type="text" class="form-control" name="date" id="date" placeholder="日付" aria-describedby="dateHelp" value="${param.date != null ? param.date : HTMLUtils.dateFormat(data.date)}">
				</div>
				<div class="col-4">
					<small id="dateHelp" class="text-muted align-bottom">「YYYY/MM/DD」形式で入力してください。</small>
				</div>
			</div>

			<fieldset class="form-group">
				<div class="row">
					<legend class="offset-2 col-form-label col-2 pt-0 font-weight-bold">区分</legend>
					<div class="col-sm-8">
						<div class="custom-control custom-radio custom-control-inline">
							<input type="radio" id="division1" name="division" class="custom-control-input" value="支出" ${param.division eq '支出' ? 'checked' : data.price eq null ? '' : HTMLUtils.checkDivision(data.price) eq '支出' ? 'checked' : '' } >
							<label class="custom-control-label" for="division1">支出</label>
						</div>
						<div class="custom-control custom-radio custom-control-inline">
							<input type="radio" id="division2" name="division" class="custom-control-input" value="収入" ${param.division eq '収入' ? 'checked' : data.price eq null ? '' : HTMLUtils.checkDivision(data.price) eq '収入' ? 'checked' : '' }>
							<label class="custom-control-label" for="division2">収入</label>
						</div>
					</div>
				</div>
			</fieldset>

			<%-- categoryのinclude --%>
			<jsp:include page="_category.jsp" />

			<div class="form-group row">
				<label for="note" class="offset-2 col-sm-2 col-form-label font-weight-bold">備考</label>
				<div class="col-6">
					<textarea class="form-control" name="note" id="note" placeholder="備考" rows="3">${param.note != null ? param.note : data.note}</textarea>
				</div>
			</div>
			<div class="form-group row">
				<label for="amount" class="offset-2 col-sm-2 col-form-label font-weight-bold">金額 <span class="badge badge-danger">必須</span></label>
				<div class="col-2">
					<input type="text" class="form-control" name="price" id="amount" placeholder="金額" value="${param.price != null ? param.price : HTMLUtils.priceFormat(data.price)}">
				</div>
			</div>

			<div class="form-group row">
				<div class="offset-4 col-8">
					<a href="detail.html?id=${param.id }" class="btn btn-secondary">キャンセル</a>
					<button type="submit" class="btn btn-primary"><span class="oi oi-check"></span> 修正OK</button>
				</div>
			</div>
		</form>
	</div>

	<hr>


<jsp:include page="_footer.jsp" />