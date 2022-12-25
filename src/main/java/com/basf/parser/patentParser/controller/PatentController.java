package com.basf.parser.patentParser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.basf.parser.patentParser.service.ParserPatentService;

@Controller
public class PatentController {
	
	@Autowired
	private ParserPatentService parserPatentService;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("returnedTextBoolean", false);
		return "home";
	}
	
	@GetMapping("/savePatent")
	public String savePatent(Model model) {
		model.addAttribute("returnedTextBoolean", true);
		model.addAttribute("returnedText", parserPatentService.saveParsed("src/main/resources/patentResources/US06060048A.xml"));
		return "home";
	}
	
	@PostMapping("/uploadsavePatent")
	public String uploadSavePatent(@RequestParam("file") MultipartFile file, Model model) {
		model.addAttribute("returnedTextBoolean", true);
		model.addAttribute("returnedText", parserPatentService.saveParsedFile(file));
		return "home";
	}
}
