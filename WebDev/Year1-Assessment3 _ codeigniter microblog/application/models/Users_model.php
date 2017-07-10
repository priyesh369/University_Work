<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Users_model extends CI_Model {

	public function checkLogin($username, $password){
		
		$query = $this->db->select('*')
						->from('Users')
						->where('username', $username)
						->where('password', $password)
						->get();

	   if( $query->num_rows() == 1 )  {// if 1 row is returned that means that a user & password match has been found hence user exisits
			return true;//true to signify match found else false to say not found 
		}
		else{
			return false;
		}
		
	}

	public function isFollowing($follower = null, $followed = null){
		$query = $this->db->select('*')
						->from('User_Follows')
						->where('follower_username', $follower)
						->where('followed_username', $followed)
						->get();

	   if( $query->num_rows() == 1 )  { // if 1 row is returned that means that a user & password match has been found hence user is already following
			return true; //true to signify match found else false to say not found 
		}
		else{
			return false;
		}
	}
	
	public function follow($followed = null){
		$data = array(
		   'follower_username' => $this->session->userdata('username'),
		   'followed_username' => $followed
		);
		
		$this->db->insert('User_Follows',$data);
	}

}
