<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>用户列表</title>
	</head>
	<body>
		<table border="1" width="50%">
			<tr>
				<td>ID</td>
				<td>Name</td>
				<td>Age</td>
			</tr>
			<#list users as user>
				<tr>
					<td>${user.id}</td>
					<td>${user.name}</td>
					<td>${user.age}</td>
				</tr>
			</#list>
		</table>
	</body>
</html>
