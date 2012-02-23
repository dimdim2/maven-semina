<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/header.inc" %>

<script type="text/javascript">

function insert(){
	document.location = "/content/create.htm";
}

function detail(id){
	document.location = "/content/detail.htm?id=" + id;
}

function update(id){
	document.location = "/content/update.htm?id=" + id;
}

function goDelete(id) {
	if(confirm('정말로 삭제하시겠습니까?')) {
		$.ajax({
			url: '/content/delete.json',
			type : "POST",
			data: {
				'id' : id
			},
			dataType: 'json',
			success: function(data) {
				alert(data.resultMsg);
				if(data.isSuccess) {
					window.location.reload(true);
				}
			}
		});
	}
}

function search() {
	document.vForm.submit();
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<div class="container">
<form method="post" name="vForm" action="/content/search.htm">

	<div class="well form-search">
		<input type="text" id="name" name="name" value="${name}"/>
		<button type="button" class="btn" onclick="search();">검색</button>
	</div>

	<display:table name="contents" id="content" class="table table-striped table-bordered table-condensed"
			requestURI="/content/search.htm" pagesize="10">

		<display:column title="ID" property="id"/>
		<display:column title="이름" property="name"/>
		<display:column title="생성일">
			<fmt:formatDate value="${content.createTime}" pattern="yyyy/MM/dd" />
		</display:column>

		<display:column title="명령" style="text-align:center;" media="html">
			<button type="button" class="btn btn-info" onclick="detail('${content.id}')">조회</button>
			<button type="button" class="btn btn-info" onclick="update('${content.id}')">수정</button>
			<button type="button" class="btn btn-info" onclick="goDelete('${content.id}')">삭제</button>
		</display:column>
	</display:table>

	<!-- add button start -->
	<div>
		<button type="button" class="btn btn-primary" onclick="insert()">생성</button>
	</div>
	<!-- add button end -->

</form>
</div>

<%@ include file="/WEB-INF/views/include/footer.inc" %>