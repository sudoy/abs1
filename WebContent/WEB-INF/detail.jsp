<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="utils.HTMLUtils"%>

<!DOCTYPE html>
<html>
<head>
	<jsp:include page="_header.jsp" />
	<title>Insert title here</title>
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

		<div class="row">
			<div class="col">
				<div class="alert alert-danger alert-dismissible fade show" role="alert">
					<h4 class="alert-heading h5 font-weight-bold"><span class="oi oi-pin"></span> エラーが発生しました！</h4>
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<ul>
						<li>日付は必須入力です。</li>
						<li>カテゴリーは必須入力です。</li>
						<li>金額は必須入力です。</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="row justify-content-between">
			<div class="offset-1 col">
				<h2 class="font-weight-bold">詳細フォーム</h2>
			</div>
		</div>

		<hr class="mt-1">

		<form action="#" method="post">
			<div class="form-group row">
				<label for="date" class="offset-2 col-sm-2 col-form-label font-weight-bold">日付</label>
				<div class="col-2">
					<input type="text" class="form-control" name="date" id="date" placeholder="日付" aria-describedby="dateHelp" value="${HTMLUtils.dateFormat(data.date)}" readonly>
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
							<input type="radio" id="division1" name="division" class="custom-control-input" checked disabled>
							<label class="custom-control-label" for="division1">支出</label>
						</div>
						<div class="custom-control custom-radio custom-control-inline">
							<input type="radio" id="division2" name="division" class="custom-control-input" disabled>
							<label class="custom-control-label" for="division2">収入</label>
						</div>
					</div>
				</div>
			</fieldset>

			<div class="form-group row">
				<label for="category" class="offset-2 col-sm-2 col-form-label font-weight-bold">カテゴリー</label>
				<div class="col-4">
					<select class="custom-select" name="category" id="category" disabled>
						<option>選択して下さい</option>

						<option ${data.category eq '1' ? 'selected' : ''}>食費</option>
						<option ${data.category eq '2' ? 'selected' : ''}>日用品</option>
						<option ${data.category eq '3' ? 'selected' : ''}>交際費</option>
					</select>
				</div>
			</div>

			<div class="form-group row">
				<label for="note" class="offset-2 col-sm-2 col-form-label font-weight-bold">備考</label>
				<div class="col-6">
					<textarea class="form-control" name="note" id="note" placeholder="備考" rows="3" readonly>${data.note}</textarea>
				</div>
			</div>

			<div class="form-group row">
				<label for="amount" class="offset-2 col-sm-2 col-form-label font-weight-bold">金額</label>
				<div class="col-2">
					<input type="text" class="form-control" name="price" id="amount" placeholder="金額" value="${data.price}" readonly>
				</div>
			</div>

			<div class="form-group row">
				<div class="offset-4 col-6">
					<a href="index.html" class="btn btn-secondary">キャンセル</a>
					<a href="update.html?id=${data.id}" class="btn btn-primary"><span class="oi oi-pencil"></span> 修 正</a>
				</div>
				<div class="col-2 text-right">
					<a href="delete.html?id=${data.id}" class="btn btn-danger delete-btn"><span class="oi oi-trash"></span> 削 除</a>
				</div>
			</div>
		</form>
	</div>

	<hr>

	<jsp:include page="_footer.jsp" />

