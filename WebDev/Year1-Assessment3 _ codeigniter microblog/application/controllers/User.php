<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class User extends CI_Controller {
	
	public function __construct() {
		parent::__construct();
		$this->load->database();
	}
	public function index()
	{
	}
	
	public function view($name = null)
	{
		if($name == null && $this->session->userdata('username') == null){
			echo '<script>alert("You do not seem to be logged in, you will be redirected to the login page");</script>';
			redirect('user/login','refresh');
		}else if ($name == null){
			echo "No User Specified.";
		} else {
		$this->load->model('Messages_model');
		$results = $this->Messages_model->getMessagesByPoster($name);
		
		$this->load->model('Users_model');
		$following = $this->Users_model->isFollowing($this->session->userdata('username'),$name);
		if($this->session->userdata('username') == $name){
			$sameUser = true;
		}else{
			$sameUser = false;
		}
		
		$this->load->view('Messages_view', array('message_data' => $results, 'isFollowing' => $following, 'sameUser' => $sameUser));
		}
	}
	
	public function login()
	{
		$this->load->view('Login_view');
	}
	
	public function doLogin()
	{
		$this->load->model('Users_model');
		$username = $this->input->post('username');
		$password = sha1($this->input->post('password')); //encrypt the user entered password with sha1 before searching the database 
		$result = $this->Users_model->checkLogin($username, $password);
		if($result){
			$newdata = array(
			'username'  => $username,
			'logged_in' => 1
			);
			$this->session->set_userdata($newdata);
			redirect('/user/view/'.$this->session->userdata('username'), 'refresh');
		}
		else{
			echo '<script>alert("Your details were not recognised, please try again.");</script>';
			redirect('/user/login', 'refresh');
		}
	}
	
	public function logout()
	{
		$this->session->sess_destroy();
		echo '<script>alert("Logout Successful, redirecting to login page.");</script>';
		redirect('user/login','refresh');
	}
	
	public function follow($followed = null) //parameter passed in by the message view, using the name of the currently select user using uri->segment
	{
		$this->load->model('Users_model');
		$this->Users_model->follow($followed);
		redirect('user/view/'.$followed);
	}
	
	public function feed($name = null)
	{
		if($this->session->userdata('username') == null){
			echo '<script>alert("You do not seem to be logged in, you will be redirected to the login page");</script>';
			redirect('user/login','refresh');
		}
		$this->load->model('Messages_model');
		$results = $this->Messages_model->getFollowedMessages($name);
		$this->load->view('Messages_view', array('message_data' => $results));
	}
}
