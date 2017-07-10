<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Search extends CI_Controller {
	
	public function __construct() {
		parent::__construct();
		$this->load->database();
	}

	public function index()
	{
		$this->load->view('Search_view');
	}
	
	public function doSearch()
	{
		$searchString = $this->input->get('searchInput'); //Gets what the user typed and submitted on the search page.
		$this->load->model('Messages_model');
		$results = $this->Messages_model->searchMessages($searchString); //passes the string to messages model to serach and return matches
		$this->load->view('Messages_view', array('message_data' => $results)); //passes the matches to the message view to display them
	}
	
}
