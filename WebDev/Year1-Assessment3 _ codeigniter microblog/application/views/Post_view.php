<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Roar - Post Message</title>
		<link rel="stylesheet" href="<?php echo base_url(); ?>css/style.css">
	</head>
	
	<body id = "wrapper">
		<header>
			<img src="<?php echo base_url(); ?>assets/images/header.jpg" width="100%" height="100%" alt="">;	
		</header>
		
		<div id="navBar">
			<ul>
				<li>Logged in as: <?php echo $this->session->userdata('username') ?> <a href = "<?php echo base_url(); ?>index.php/user/logout">Logout</a></li>
				<li><a href = "<?php echo base_url(); ?>index.php/user/view/<?php echo $this->session->userdata('username')?>">My messages</a></li>
				<li><a href = "<?php echo base_url(); ?>index.php/message">Post a message</a></li>
				<li><a href = "<?php echo base_url(); ?>index.php/search">Search posts</a></li>
				<li><a href = "<?php echo base_url(); ?>index.php/user/feed/<?php echo $this->session->userdata('username')?>">Following feed</a></li>
			</ul>
		</div>
	
		
		
		<?php if($this->session->userdata('username') !== null){ ?>
		
		<div id = "contentArea">
		
			<h1>Enter Your Message Post</h1>
		
			<?php
				echo form_open('message/dopost');
				echo form_input('message','','required');
				echo form_submit('submit','Submit');
				echo form_close();
			?>
			
			
		</div>
		
		<?php } else {
			echo '<script>alert("You do not seem to be logged in, you will be redirected to the login page");</script>';
			redirect('user/login','refresh');
		}?>
		
		<footer>
		Priyesh Patel - prp6
		</footer>
			
	</body>
</html>