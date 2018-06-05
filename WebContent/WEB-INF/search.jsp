<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="ja">
<head>

	<jsp:include page="_header.jsp" />
	<title>My家計簿アプリ|検索フォーム</title>
</head>
<body>
	<jsp:include page="_navbar.jsp" />

	<div class="container pt-6">

		<div class="row">
			<div class="col">
				<div class="alert alert-danger alert-dismissible fade show" role="alert">
					<h4 class="alert-heading h5 font-weight-bold"><span class="oi oi-pin"></span> エラーが発生しました！</h4>
					<button type="button" class="close" data-dismiss="alert" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<ul>
						<li>検索結果は1件もありません。</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="row justify-content-between">
			<div class="offset-1 col">
				<h2 class="font-weight-bold">検索フォーム</h2>
			</div>
		</div>

		<hr class="mt-1">

		<form action="result.html" method="POST">
			<div class="form-group row">
				<label for="date" class="offset-1 col-sm-2 col-form-label font-weight-bold">日付</label>

				<div class="col-2">
					<input type="text" class="form-control" name="start" id="date" placeholder="日付" aria-describedby="dateHelp" value="">
				</div>
				<div class="col-1">
					<input type="text" readonly class="form-control-plaintext text-center" id="staticEmail" value="～">
				</div>
				<div class="col-2">
					<input type="text" class="form-control" name="end" id="date" placeholder="日付" aria-describedby="dateHelp" value="">
				</div>
				<div class="col-4">
					<small id="dateHelp" class="text-muted align-bottom">「YYYY/MM/DD」形式で入力してください。</small>
				</div>
			</div>

			<fieldset class="form-group">
				<div class="row">
					<legend class="offset-1 col-form-label col-2 pt-0 font-weight-bold">カテゴリー</legend>
					<div class="col-sm-8">
						<div class="custom-control custom-checkbox custom-control-inline">
							<input type="checkbox" id="category-all" name="customRadioInline1" class="custom-control-input category-all" checked>
							<label class="custom-control-label" for="category-all">全て</label>
						</div>
					</div>


					<div class="offset-3 col-sm-8">
						<div class="custom-control custom-checkbox custom-control-inline">
 							<input type="checkbox" id="category1" name="eat" class="custom-control-input category" value="食費" checked>
							<label class="custom-control-label"  for="category1">食費</label>
						</div>
						<div class="custom-control custom-checkbox custom-control-inline">
							<input type="checkbox" id="category2" name="life" class="custom-control-input category" value="日用品" checked>
							<label class="custom-control-label"  for="category2">日用品</label>
						</div>
						<div class="custom-control custom-checkbox custom-control-inline">
							<input type="checkbox" id="category3" name="money" class="custom-control-input category" value="交際費" checked>
							<label class="custom-control-label"  for="category3">交際費</label>
						</div>
					</div>
				</div>
			</fieldset>

			<div class="form-group row">
				<label for="note" class="offset-1 col-sm-2 col-form-label font-weight-bold">備考 <span class="badge badge-success	">部分一致</span></label>
				<div class="col-5">
					<input type="text" class="form-control" name="note" id="note" placeholder="備考">
				</div>
			</div>

			<div class="form-group row">
				<div class="offset-3 col-8">
					<a href="index.html" class="btn btn-secondary">キャンセル</a>
					<button type="submit" class="btn btn-primary"><span class="oi oi-magnifying-glass"></span> 検 索</button>
				</div>
			</div>
		</form>
	</div>

	<hr>

	<footer class="footer">
		<div class="container">
			<p class="text-muted small">&copy; 2018 SIE Inc.</p>
		</div>
	</footer>

	<script src="js/jquery-3.3.1.slim.min.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>

<script>
$(function(){
	$('.delete-btn').on('click', function(){
		return confirm('削除してよろしいですか？');
	});

	$('.category-all').on('click', function(){
		// allのチェック状態と他の選択肢のチェック状態をリンク
		$('.category').prop('checked', $(this).prop('checked'));
	});

	$('.category').on('click', function(){
		if(!$(this).prop('checked')){
			// チェックが外れたときは、allのチェックも外す
			$('.category-all').prop('checked', false);

		}else{
			// チェックが入ったときは、
			// 他の選択肢もすべてチェックだった場合に、allをチェックする
			var isChange = true;

			$('.category').each(function(){
				if(!$(this).prop('checked')){
					isChange = false;
				}
			});
			if(isChange){
				$('.category-all').prop('checked', true);
			}
		}
	});
});
</script>
</body>
</html>