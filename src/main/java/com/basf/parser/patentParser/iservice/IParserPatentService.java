package com.basf.parser.patentParser.iservice;

import org.springframework.web.multipart.MultipartFile;

import com.basf.parser.patentParser.entity.Patent;

public interface IParserPatentService {

	void savePatentRepository(Patent patent);
	Patent getPatentByTitle(String title);
	String saveParsedFile(MultipartFile file);
	String saveParsed(String patentURL);
	void deletePatent(long idPatent);

}
