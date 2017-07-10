<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Roar - Search</title>
		<link rel="stylesheet" href="<?php echo base_url(); ?>css/style.css">
	</head>
	
	<body id = "wrapper">
	
		<header>
			<img src="<?php echo base_url(); ?>assets/images/header.jpg" width="100%" height="100%" alt="">;	
		</header>
		
		<div id="navBar">
			<ul>
				<!--checks if user is logged in, if yes thier name is displayed as well as a log out option-->
					<?php if($this->session->userdata('username') !== null){ ?>
					
						<li>Logged in as: <?php echo $this->session->userdata('username') ?> <a href = "<?php echo base_url(); ?>index.php/user/logout">Logout</a></li>
					<!--else if they are not logged in they are given the option to login-->
					<?php } else {?>
					
					<li>Not logged in: <a href = "<?php echo base_url(); ?>index.php/user/login">Login</a></li>
					
					<?php } ?>
				
				<li><a href = "<?php echo base_url(); ?>index.php/user/view/<?php echo $this->session->userdata('username')?>">My messages</a></li>
				<li><a href = "<?php echo base_url(); ?>index.php/message">Post a message</a></li>
				<li><a href = "<?php echo base_url(); ?>index.php/search">Search posts</a></li>
				<li><a href = "<?php echo base_url(); ?>index.php/user/feed/<?php echo $this->session->userdata('username')?>">Following feed</a></li>
			</ul>
		</div>
		
		<div id = "contentArea">
			<h1>Enter Your Search Query</h1>
			<!--Form creation for user to enter search query and submit via get method-->
			<?php
				echo form_open('search/dosearch', array('method' => 'get'));
				echo form_input('searchInput')."<br/>";
				echo "<br/>".form_submit('Search','Search');
				echo form_close();
			?>
			
		</div>
		
		<footer>
		Priyesh Patel - prp6
		</footer>
			
	</body>
</html>