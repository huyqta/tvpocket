<?php defined('BASEPATH') or exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';

class Channels extends REST_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->model('main_model');                
    }

	function ReadJsonToDB_get(){
		$success = 0;
		$fail = 0;
		$jsn1 = file_get_contents('./tvpocket/channel.json');
		$jsn1 = substr($jsn1,3,strlen($jsn1) - 3);		
		$arr1 = json_decode($jsn1, true);
		if ($arr1 != ""){
			foreach ($arr1 as $item) {		
				$data = array(  'id'      => $item['id'],
								'name'    => $item['name'], 
								'urlcrawl'=> $item['urlcrawl'],
								'urlapi'  => $item['urlapi'],
								'urllogo' => $item['urllogo'],
								'refgroup'=> $item['refgroup']
                     );
				$result = $this->main_model->add('channels', $data);
				if ($result === false) {
					$fail = $fail + 1;
				} else {
					$success = $success + 1;
				}				
			}
        }
		$this->response("Success: " . $success . " & Fail: " . $fail);
	 }
	 
    function AddChannel_post() {
        $data = array(  'name'    => $this->input->post('name'), 
                        'urlcrawl'     => $this->input->post('urlcrawl'),
						'urlapi'     => $this->input->post('urlapi'),
						'urllogo'     => $this->input->post('urllogo'),
						'refgroup'     => $this->input->post('refgroup')
                     );

        $result = $this->main_model->add('channels', $data);
        if ($result === false) {
            $this->response(array('error' => "Can't add Channel!" ), 404);
        } else {
            $this->response($result, 200);
        }
    }

    function DeleteChannel_get($id) {
        $id = $id;
        $result = $this->main_model->delete('channels', 'id', $id);
        if ($result === false) {
            $this->response(array('error' => "Can't delete Channel!" ), 404);
        } else {
            $this->response($result, 200);
        }
    }

    function UpdateChannel_get($id, $name, $urlcrawl, $urlapi, $urllogo, $refgroup) {
        $dataupdate = array(  	'id'    => $id, 
								'name'    => $this->input->post('name'), 
								'urlcrawl'     => $this->input->post('urlcrawl'),
								'urlapi'     => $this->input->post('urlapi'),
								'urllogo'     => $this->input->post('urllogo'),
								'refgroup'     => $this->input->post('refgroup')
						);
		$datainsert = array('channel'    => $channel, 
							'url'     => $url
						);        
        /* Call update action */
		$isExist = CheckChannelExist($channel);
		if ($isExist == true){
			$result = $this->main_model->edit('channels', $dataupdate, 'id', $dataupdate['id']);
		}
		else{
			$result = $this->main_model->add('channels', $datainsert);
		}
        if ($result === false) {
            $this->response(array('error' => "Couldn't find any channels!"), 404);
        } else {
            $this->response($result, 200);
        }
    }
	
	function UpdateChannel_post() {
		$channel = $this->input->post('name');
        /*$dataupdate = array(  	'id'   		 	=> $this->input->post('id'), 
								'name'    		=> $this->input->post('name'), 
								'urlcrawl'    	=> $this->input->post('urlcrawl'),
								'urlapi'     	=> $this->input->post('urlapi'),
								'urllogo'     	=> $this->input->post('urllogo'),
								'refgroup'     	=> $this->input->post('refgroup')
						);*/
		$data = array(	'name'    		=> $this->input->post('name'), 
						'urlcrawl'    	=> $this->input->post('urlcrawl'),
						'urlapi'     	=> $this->input->post('urlapi'),
						'urllogo'     	=> $this->input->post('urllogo'),
						'refgroup'     	=> $this->input->post('refgroup')
						);        
        // Call update action 
		$where = "name = '" . $channel . "'";
		$isExist = $this->main_model->getsimple('channels', 'id', $where);
		if ($isExist){
			$result = $this->main_model->edit('channels', $data, 'id', $isExist[0]['id']);
		}
		else{
			$result = $this->main_model->add('channels', $data);
		}
        if ($result === false) {
            $this->response(array('error' => "Couldn't find any channels!"), 404);
        } else {
            $this->response($result, 200);
        }
    }

	function BulkUpdateChannel_post() {
		$bulkdata = $this->input->post('bulkdata');
        /*$dataupdate = array(  	'id'   		 	=> $this->input->post('id'), 
								'name'    		=> $this->input->post('name'), 
								'urlcrawl'    	=> $this->input->post('urlcrawl'),
								'urlapi'     	=> $this->input->post('urlapi'),
								'urllogo'     	=> $this->input->post('urllogo'),
								'refgroup'     	=> $this->input->post('refgroup')
						);*/
		$data = array(	'name'    		=> $this->input->post('name'), 
						'urlcrawl'    	=> $this->input->post('urlcrawl'),
						'urlapi'     	=> $this->input->post('urlapi'),
						'urllogo'     	=> $this->input->post('urllogo'),
						'refgroup'     	=> $this->input->post('refgroup')
						);        
        // Call update action 
		$where = "name = '" . $channel . "'";
		$isExist = $this->main_model->getsimple('channels', 'id', $where);
		if ($isExist){
			$result = $this->main_model->edit('channels', $data, 'id', $isExist[0]['id']);
		}
		else{
			$result = $this->main_model->add('channels', $data);
		}
        if ($result === false) {
            $this->response(array('error' => "Couldn't find any channels!"), 404);
        } else {
            $this->response($result, 200);
        }
    }
	
    function GetAllChannels_get() {
        $locations = $this->main_model->getall('channels', '*');
        if ($locations != null) {
            $this->response($locations, 200);
        } else {
            $this->response(array('error' => "Couldn't find any channels!" ), 404);
        }
    }
	
	function CheckChannelExist($channel){
		$count = $this->main_model->count('channels', 'name', $channel);
		if ($count > 0){
			return true;
		}
		else{
			return false;
		}
	}
}
