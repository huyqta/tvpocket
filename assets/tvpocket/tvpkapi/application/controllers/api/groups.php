<?php defined('BASEPATH') or exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';

class Groups extends REST_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->model('main_model');                
    }

    function GetAllGroups_get() {
        $groups = $this->main_model->getall('groups', '*');
        if ($groups != null) {
            $this->response($groups, 200);
        } else {
            $this->response(array('error' => "Couldn't find any groups!" ), 404);
        }
    }
}
