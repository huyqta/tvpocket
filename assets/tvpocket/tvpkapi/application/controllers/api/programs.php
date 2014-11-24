<?php defined('BASEPATH') or exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';

class Programs extends REST_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->model('main_model');                
    }

    function GetAllPrograms_get() {
        $programs = $this->main_model->getall('programs', '*');
        if ($programs != null) {
            $this->response($programs, 200);
        } else {
            $this->response(array('error' => "Couldn't find any programs!" ), 404);
        }
    }

    function AddNewPrograms_get(){
		$success = 0;
		$fail = 0;
        //$datajson = $this->input->post('json');			
		$datajson = file_get_contents('./tvpocket/28092014.json', FILE_USE_INCLUDE_PATH);
        if ($datajson != ""){			
			$data = json_decode($datajson, true);

			foreach ($data as $item) {				
				$data = array(	'refchannel'    => $item['refchannel'], 
								'name'    		=> $item['name'],
								'dateStart'     => $item['dateStart'],
								'timeStart'     => $item['timeStart'],
								'duration'     	=> $item['duration'],
								'posterurl'		=>$item['posterurl']
								);
				$result = $this->main_model->add('programs', $data);
				if ($result === false) {
					$fail = $fail + 1;
				} else {
					$success = $success + 1;
				}
			}
        }
		$this->response("Success: " . $success . " & Fail: " . $fail);
    }
		 
	 function ReadJsonToDB_get($date){
		$success = 0;
		$fail = 0;
		$jsn1 = file_get_contents('./tvpocket/'.$date.'.json');		
		
		// $jsn1 = substr($jsn1,3,strlen($jsn1) - 3);
		
		$arr1 = json_decode($jsn1, true);		
		// $this->response($arr1);
		if ($arr1 != ""){		
			foreach ($arr1 as $item) {		
				$data = array(	'refchannel'    => $item['refchannel'], 
								'name'    		=> $item['name'],
								'dateStart'     => $item['dateStart'],
								'timeStart'     => $item['timeStart'],
								'duration'     	=> $item['duration'],
								'posterurl'		=>$item['posterurl']
								);
				
				$result = $this->main_model->add('programs', $data);				
				if ($result === false) {
					$fail = $fail + 1;
				} else {
					$success = $success + 1;
				}				
			}
        }
		$this->response("Success: " . $success . " & Fail: " . $fail);
	 }
	 
	function GetProgramsByChannel_get($channel, $date){		
		$where = "refchannel = '" . $channel . "' AND dateStart = '" . $date . "'";
		$programs = $this->main_model->getsimple('programs', '*', $where);
		if ($programs != null) {
            $this->response($programs, 200);
        } else {
            $this->response(array('error' => "Couldn't find any programs!" ), 404);
        }
	 }
	 
	 function GetProgramsOnAir_get($date, $time){		
		$query = "SELECT result.refchannel, result.name, result.datestart, result.timestart, result.posterurl, result.duration 
					FROM(
					SELECT IF( @refchannel != temp.refchannel, @rownum :=1, @rownum := @rownum +1 ) AS rownumber
						, temp.name
						, @refchannel := refchannel AS refchannel
						, temp.datestart
						, temp.timestart
						, temp.posterurl
						, temp.duration
						FROM (

					SELECT refchannel, name, datestart, timestart, posterurl, duration
					FROM programs, (

					SELECT @rownum :=0, @refchannel := ''
					)sq
					WHERE datestart = '".$date."' AND timestart < '".$time."'
					ORDER BY refchannel, timestart DESC
					)temp
					)result
					WHERE result.rownumber < 2";		
		$result_array = DBQuery($query);
		if ($result_array != null) {
            $this->response($result_array, 200);
        } else {
            $this->response(array('error' => "Couldn't find any programs!" ), 404);
        }
	 }
	 
	 function GetNextPrograms_get($date, $time, $range){		
		$query = "SELECT result.refchannel, result.name, result.datestart, result.timestart, result.posterurl, result.duration 
					FROM(
					SELECT IF( @refchannel != temp.refchannel, @rownum :=1, @rownum := @rownum +1 ) AS rownumber
						, temp.name
						, @refchannel := refchannel AS refchannel
						, temp.datestart
						, temp.timestart
						, temp.posterurl
						, temp.duration
						FROM (

					SELECT refchannel, name, datestart, timestart, posterurl, duration
					FROM programs, (

					SELECT @rownum :=0, @refchannel := ''
					)sq
					WHERE datestart = '".$date."' AND timestart > '".$time."'
					ORDER BY refchannel, timestart ASC
					)temp
					)result
					WHERE result.rownumber <= ".$range;		
		$result_array = DBQuery($query);
		if ($result_array != null) {
            $this->response($result_array, 200);
        } else {
            $this->response(array('error' => "Couldn't find any programs!" ), 404);
        }
	 }
	 
	 function GetAllProgramsByDate_get($day, $month, $year, $from_channel, $to_channel){
		$channel_query = "SELECT urlcrawl FROM channels WHERE id > " . $from_channel . " AND id < " . $to_channel;
		
		$arr_channels = DBQuery($channel_query);
		$date = $day . "/" . $month . "/" . $year;
		foreach ($arr_channels as $channel){
			
			$urlcrawl = str_replace('%', $date, $channel["urlcrawl"]);
			print_r("url : " . $urlcrawl);
			$json = file_get_contents($urlcrawl);
			print_r("json : " . $json);
			$parsed_arr = json_decode($json,true);
			print_r("data : " . $parsed_arr);
		}
	 }
	 
	 function SearchPrograms_get($input){
		$input = str_replace('huyqtahuyqta', ' ', $input);
		$query = "SELECT * FROM programs WHERE name LIKE '%" . $input . "%' ORDER BY `programs`.`datestart` DESC LIMIT 30";
		$result_array = DBQuery($query);
		if ($result_array != null) {
            $this->response($result_array, 200);
        } else {
            $this->response(array('error' => "Couldn't find any programs!" ), 404);
        }
	 }
	 
	 function ReadHTML_get($page){
		ini_set('memory_limit', '12M'); 
		$html = file_get_contents('http://www.htvonline.com.vn/livetv/?utm_source=hdviet');
		//foreach($html->find('script') as $element)
		//	echo $element->src . '<br>';
		
		$begin = strpos($html, 'file: "');
		$end = strpos($html, '.m3u8');
		$pos_token = strpos($html, 'm3u8?t=');
				
		$pos_http = $begin + 7;
		$pos_m3u8 = ($end - $begin) + 5;
		$text = substr($html, $pos_http, $pos_m3u8);
		$token = substr($html, $pos_token, $pos_token);
		
		//print_r($text . $token . $expire);
		//$this->response($text . "|" . $token . "|" . $pos_http . "|" . $pos_token . "|" . $pos_expire, 200);
		$this->response($token, 200);
	 }
}