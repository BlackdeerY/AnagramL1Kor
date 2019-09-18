package anagram.rest.ydeer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import anagram.rest.ydeer.service.AnagramService;

@RestController
@RequestMapping(value = "/")
public class RootController {
	
	@Autowired
	private AnagramService anagramService;
	
	@PostMapping()
	@ResponseBody
	public String Anagram(@RequestParam(required = false) Object input) {
		return anagramService.RetrunAnagram(input);
	}
	
	@GetMapping()
	@ResponseBody
	public String get() {
		return "POST 형식으로 input 파라미터에 문자열을 넣어 전송하면, 아나그램을 응용한 문자열 섞기 결과를 얻으실 수 있습니다.";
	}
}
