<?php defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH.'./libraries/REST_Controller.php';

class Profiles extends REST_Controller{
    function __construct(){
        parent::__construct();
        $this->load->model('profiles_model');
    }
    
    function GetAllProfiles_get()
	{
	 //$users = $this->some_model->getSomething( $this->get('limit') );
		$profiles = $this->profiles_model->GetAllProfiles();

		if($profiles)
		{
			$this->response($profiles, 200); // 200 being the HTTP response code
		}

		else
		{
			$this->response(array('error' => 'Couldn\'t find any users!'), 404);
		}
		
	}
    
    function GetProfileByAccount_get($refAccount){
        $profile = $this->profiles_model->GetProfileByAccount($refAccount);
        if ($profile != null){
            $this->response($profile);
        }
        else{
            $this->response(array('status' => 'failed'));
        }
    } 
}