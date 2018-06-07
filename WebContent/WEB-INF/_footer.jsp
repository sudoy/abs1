<%@page pageEncoding="UTF-8"%>
	<footer class="footer">
		<div class="container">
			<p class="text-muted small">&copy; 2018 SIE Inc.</p>
		</div>
	</footer>

	<script src="js/jquery-3.3.1.slim.min.js"></script>
	<script src="js/popper.min.js"></script>
	<script src="js/bootstrap.min.js"></script>

	<% session.setAttribute("errors", null); %>

<script>
$(function(){
	$('.delete-btn').on('click', function(){
		return confirm('削除してよろしいですか？');
	});
});
</script>
</body>
</html>