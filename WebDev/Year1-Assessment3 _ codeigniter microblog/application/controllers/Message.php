<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Message extends CI_Controller {

	public function __construct() {
		parent::__construct();
		$this->load->database();
	}
	
	public function index()
	{
		if($this->session->userdata('username') !== null){ //checks that user has logged in by seeing if a session has been made with a not null username field
		$this->load->view('Post_view');
		} else {			
			echo '<script>alert("You do not seem to be logged in, you will be redirected to the login page");</script>';
			redirect('user/login','refresh');}
	}
	
	public function doPost()
	{
		if($this->session->userdata('username') !== null){ 
		
		$username = $this->session->userdata('username');
		$message = $this->input->post('message'); //retrives a message typed by the user in post view then is used to insert into the database
		$this->load->model('Messages_model');
		$results = $this->Messages_model->insertMessage($username, $message);
		
		} else {			
			echo '<script>alert("You do not seem to be logged in, you will be redirected to the login page");</script>';
			redirect('user/login','refresh');}
	}
}
