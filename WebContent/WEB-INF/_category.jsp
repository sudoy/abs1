<%@page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="form-group row">
	<label for="category" class="offset-2 col-sm-2 col-form-label font-weight-bold">カテゴリー <span class="badge badge-danger">必須</span></label>
	<div class="col-4">
		<select class="custom-select" name="category" id="category">
			<option value="0">選択して下さい</option>

			<c:forEach var="v" items="${categories}">
				<c:if test="${data.category eq v.category_id || param.category eq v.category_id}">
					<option value="${v.category_id}" selected>${v.category_data}</option>
				</c:if>
			 	<c:if test="${data.category ne v.category_id && param.category ne v.category_id}">
					<option value="${v.category_id}">${v.category_data}</option>
				</c:if>
			</c:forEach>

		</select>
	</div>
</div>