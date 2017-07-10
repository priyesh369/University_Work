<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Roar - Login</title>
		<link rel="stylesheet" href="<?php echo base_url(); ?>css/style.css">
	</head>
	
	<body id = "wrapper">
	
		<header>
			<img src="<?php echo base_url(); ?>assets/images/header.jpg" width="100%" height="100%" alt="">;	
		</header>
		
		<div id="navBar">
			<ul>
				<li><a href = "<?php echo base_url(); ?>index.php/search">Search posts</a></li>
			</ul>
		</div>
		
		<div id = "contentAreaLogin">
			<h1>Enter Login Details</h1>
			
			<?php
				echo form_open('user/doLogin');
				echo "Username: ".form_input('username','','required')."<br/>";
				echo "<br/>&nbspPassword: ".form_password('password','','required')."<br/>";
				echo "<br/>".form_submit('submitButton','Login');
				echo form_close();
			?>
		</div>
		
		<footer>
		Priyesh Patel - prp6
		</footer>
			
	</body>
</html>