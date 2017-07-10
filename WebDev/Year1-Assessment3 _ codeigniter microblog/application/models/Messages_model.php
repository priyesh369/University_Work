<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Messages_model extends CI_Model {

	public function getMessagesByPoster($name = null){
		$sql = "SELECT * FROM Messages WHERE user_username = ? ORDER BY posted_at DESC";
		$query = $this->db->query($sql,$name);
		
		if($query -> num_rows() > 0){
			return $query->result();
		}
	}

	public function searchMessages($string = null){
		$query = $this->db->select('*')
						->from('Messages')
						->like('text', $string)
						->order_by('posted_at','DESC')
						->get();
		if($query -> num_rows() > 0){
			return $query->result();
		}
	}
	
	public function insertMessage($poster = null, $string = null){
		$id = $this -> db -> select('id')
				   -> from('Messages')
				   -> order_by('id','DESC')
				   -> limit(1)
				   -> get()
				   -> result_array()[0]['id'];
		$nextID = $id + 1; //this is to get the id of the last message in the database and increment by 1 for the new message
		
		$data = array(
		   'user_username' => $poster,
		   'text' => $string,
		   'posted_at' => date("Y-m-d H:i:s"), //gets the current date and time in the format that the database uses
		   'id' => $nextID
		);
		
		$this->db->insert('Messages',$data);
		redirect('/user/view/'.$this->session->userdata('username'), 'refresh');
	}

	public function getFollowedMessages($name = null){
		$sql = "SELECT * FROM Messages WHERE user_username IN(SELECT followed_username FROM User_Follows WHERE follower_username = ?) ORDER BY posted_at DESC";
		
		$query = $this->db->query($sql,$name);
		
		if($query -> num_rows() > 0){
			return $query->result();
		}
	}
}
