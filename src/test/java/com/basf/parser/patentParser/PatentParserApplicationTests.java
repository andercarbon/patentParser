package com.basf.parser.patentParser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.basf.parser.patentParser.entity.Patent;
import com.basf.parser.patentParser.service.ParserPatentService;

@SpringBootTest
class PatentParserApplicationTests {
	
	@Autowired
	private ParserPatentService parserPatentService;
    
	@Test
	void contextLoads() {
		
	}
	
	@Test
	public void savePatentTest()
	  throws Exception {
		Patent tryalPatent = new Patent();
		Patent patentProof = new Patent();
		
		tryalPatent.setTitle("Patent1");
		tryalPatent.setPatentDate(new Date());
		tryalPatent.setPatentAbstract("Abstract1");
		
		parserPatentService.savePatentRepository(tryalPatent);
		
		patentProof = parserPatentService.getPatentByTitle("Patent1");
		
		assertEquals(tryalPatent, patentProof);
		
		parserPatentService.deletePatent(patentProof.getId());
	}
	
	@Test
	public void saveParsedPatentTest() {
		String title = "Method for transplanting cells into the brain and therapeutic uses therefor";
		String returnedText = title + " Saved Properly";
		
		Patent patentDelete = new Patent();
		
		assertEquals(parserPatentService.saveParsed("src/main/resources/patentResources/US06060048A.xml"), returnedText);
		
		patentDelete = parserPatentService.getPatentByTitle(title);
		
		parserPatentService.deletePatent(patentDelete.getId());
	}

}
