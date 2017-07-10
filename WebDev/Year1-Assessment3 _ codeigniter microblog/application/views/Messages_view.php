<?php
defined('BASEPATH') OR exit('No direct script access allowed');
?><!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<title>Roar - Messages</title>
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
				
					<li>
						Logged in as: <?php echo $this->session->userdata('username') ?> 
						<a href = "<?php echo base_url(); ?>index.php/user/logout">Logout</a>
					</li>
				
				<!--checks if user following currently viewed user if yes then no follow option shown-->
				<!--if no and the currently viewed user is not the logged in user then displays the follow option-->

				<?php if(isset($isFollowing) && isset($sameUser)){ //to prevent errors when loading this view from feed as these variables are not used for that?>
				<?php if ($isFollowing == false && $sameUser == false) {?>
				
					<li>
						<a href = "<?php echo base_url(); ?>index.php/user/follow/<?php echo $this->uri->segment(3);?>">Follow <?php echo $this->uri->segment(3);?></a>
					</li>
				
				<?php }}} else {?>
				
					<li>
						Not logged in: <a href = "<?php echo base_url(); ?>index.php/user/login">Login</a>
					</li>

				<?php } ?>
				<li><a href = "<?php echo base_url(); ?>index.php/user/view/<?php echo $this->session->userdata('username')?>">My messages</a></li>
				<li><a href = "<?php echo base_url(); ?>index.php/message">Post a message</a></li>
				<li><a href = "<?php echo base_url(); ?>index.php/search">Search posts</a></li>
				<li><a href = "<?php echo base_url(); ?>index.php/user/feed/<?php echo $this->session->userdata('username')?>">Following feed</a></li>
			</ul>
		</div>
		
		<div id = "contentArea">
			<!--checks if there is message data to display if yes table of data is made else no message message is shown-->

			<?php
				if($message_data) { ?>
				<table>
				<tr>
					<th>Poster</th>
					<th>Post</th>
					<th>Post Time</th>
				</tr>
				<?php		
					foreach ($message_data as $row) {
						echo '<tr><td><a href = '.base_url().'index.php/user/view/'.$row->user_username.'>'.$row->user_username.'</a></td>';
						echo "<td>".$row->text."</td>";
						echo "<td>".$row->posted_at."</td></tr>";
					}
					echo "</table>";
				}else{echo "<p>no messages to display...</p>";}?>
					
			
		</div>
		
		<footer>
			Priyesh Patel - prp6
		</footer>
		
	</body>
</html>