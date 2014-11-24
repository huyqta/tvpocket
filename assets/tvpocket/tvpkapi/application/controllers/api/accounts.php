<?php defined('BASEPATH') OR exit('No direct script access allowed');

/**
 * Example
 *
 * This is an example of a few basic user interaction methods you could use
 * all done with a hardcoded array.
 *
 * @package		CodeIgniter
 * @subpackage	Rest Server
 * @category	Controller
 * @author		Phil Sturgeon
 * @link		http://philsturgeon.co.uk/code/
 */

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
require APPPATH.'/libraries/REST_Controller.php';

class Accounts extends REST_Controller
{
	function __construct(){
		parent::__construct();
		$this->load->model('accounts_model');
	}

	//function accounts_post()
//	{
//		//$this->some_model->updateUser( $this->get('id') );
//		$message = array('id' => $this->get('id'), 'name' => $this->post('name'), 'email' => $this->post('email'), 'message' => 'ADDED!');
//
//		$this->response($message, 200); // 200 being the HTTP response code
//	}

	function user_delete()
	{
	 //$this->some_model->deletesomething( $this->get('id') );
	 $message = array('id' => $this->get('id'), 'message' => 'DELETED!');

	 $this->response($message, 200); // 200 being the HTTP response code
	}

	// Sample: http://localhost:8080/restserver/index.php/api/accounts/GetAllAccounts
	function GetAllAccounts_get()
	{
	 //$users = $this->some_model->getSomething( $this->get('limit') );
		$accounts = $this->accounts_model->GetAllAccounts();

		if($accounts)
		{
			$this->response($accounts, 200); // 200 being the HTTP response code
		}

		else
		{
			$this->response(array('error' => 'Couldn\'t find any users!'), 404);
		}
		
	}

	// Sample: http://localhost:8080/restserver/index.php/api/accounts/GetAccountByUsername/huyqta
	function GetAccountByUsername_get($username, $password)
	{
	    $data = array(
			'Username' => $username,
			'Password' => $password
		);
		$accounts = $this->accounts_model->GetAccountByUsername($data);

		if($accounts)
		{
			$this->response($accounts, 200); // 200 being the HTTP response code
		}

		else
		{
			$this->response(array('error' => 'Couldn\'t find any users!'), 404);
		}
		
	}

	function InsertAccount_get($username, $password, $active){
		$data = array(
			'Username' => $username,
			'Password' => $password,
			'Active' => $active
		);
		$result = $this->accounts_model->InsertAccount($data);

		if($result === FALSE)
		{
			$this->response(array('status' => 'failed'));
		}
		else
		{
			$this->response($result, 200);
		}
	}

	function ChangePassword_get($username, $password){
		$data = array(
			'Username' => $username, // Get from session
			'Password' => $password,
			'Active' => '1' // Get from session
		);
		$result = $this->accounts_model->UpdateAccount($data);

		if($result === FALSE)
		{
			$this->response(array('status' => 'failed'));
		}
		else
		{
			$this->response($result, 200);
		}
		
	}
	function LoginAccount_get($username, $password){
		$data = array(
		'Username'=>$username,
		'Password'=>$password
		);

		$account = $this->accounts_model->GetAccountByUsername($data);
		if ($account != null){
			$this->response($account[0]['Username']);
			return true;
		}
		else{ 
			$this->response('false');
			return false;
		}
	}
	/*
		public function send_post()
		{
		var_dump($this->request->body);
		}


		public function send_put()
		{
		var_dump($this->put('foo'));
		}
		*/
}