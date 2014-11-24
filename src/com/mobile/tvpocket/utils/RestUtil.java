package com.mobile.tvpocket.utils;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;

public class RestUtil {

	/**
	 * request get
	 * 
	 * @param uri
	 * @return
	 */
	public static String get(String uri) {
		return request(uri, Verb.GET, null, null);
	}

	/**
	 * request get
	 * 
	 * @param uri
	 * @param parameters
	 * @return
	 */
	public static String get(String uri, Map<String, String> parameters) {
		return request(uri, Verb.GET, parameters, null);
	}

	/**
	 * request get
	 * 
	 * @param uri
	 * @param parameters
	 * @param headers
	 * @return
	 */
	public static String get(String uri, Map<String, String> parameters,
			Map<String, String> headers) {
		return request(uri, Verb.GET, parameters, headers);
	}

	/**
	 * request post
	 * 
	 * @param uri
	 * @return
	 */
	public static String post(String uri) {
		return request(uri, Verb.POST, null, null);
	}

	/**
	 * request post
	 * 
	 * @param uri
	 * @param parameters
	 * @return
	 */
	public static String post(String uri, Map<String, String> parameters) {
		return request(uri, Verb.POST, parameters, null);
	}

	/**
	 * request post
	 * 
	 * @param uri
	 * @param parameters
	 * @param headers
	 * @return
	 */
	public static String post(String uri, Map<String, String> parameters,
			Map<String, String> headers) {
		return request(uri, Verb.POST, parameters, headers);
	}

	/**
	 * request post
	 * 
	 * @param uri
	 * @param parameters
	 * @param headers
	 * @return
	 */
	public static String request(String uri, Verb verb,
			Map<String, String> parameters, Map<String, String> headers) {
		try {
			OAuthRequest request = new OAuthRequest(verb, uri);
			if (headers != null) {
				Iterator<Entry<String, String>> it = headers.entrySet()
						.iterator();
				while (it.hasNext()) {
					Entry<String, String> pairs = it.next();
					request.addHeader(pairs.getKey().toString(), pairs
							.getValue().toString());
				}
			}

			if (parameters != null) {
				Iterator<Entry<String, String>> it = parameters.entrySet()
						.iterator();
				while (it.hasNext()) {
					Entry<String, String> pairs = it.next();
					request.addBodyParameter(pairs.getKey().toString(), pairs
							.getValue().toString());
				}
			}

			Response response = request.send();
			if (response.getCode() == 200) {
				return response.getBody();
			} else {
				return "";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}
}
