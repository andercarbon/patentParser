package com.basf.parser.patentParser.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.basf.parser.patentParser.entity.Patent;
import com.basf.parser.patentParser.iservice.IParserPatentService;
import com.basf.parser.patentParser.reposity.PatentRepository;

@Service
public class ParserPatentService implements IParserPatentService{
	
	@Autowired
	private PatentRepository patentRepository;
	
	@Override
	public Patent getPatentByTitle(String title) {
		return patentRepository.findBytitle(title);
	}
	
	@Override
	public void savePatentRepository(Patent patent) {
		patentRepository.save(patent);
	}
	
	@Override
	public String saveParsed(String patentURL) {
		String returnedText="";
		
		Patent patent = parseXML(new File(patentURL));
		
		savePatentRepository(patent);
		returnedText= patent.getTitle() + " Saved Properly";
		
		return returnedText;
	}
	
	@Override
	public String saveParsedFile(MultipartFile file) {
		String returnedText="";
		
		File filePatent = multipartFileToFile(file);
		
		Patent patent = parseXML(filePatent);
		
		filePatent.delete();
		
		savePatentRepository(patent);
		returnedText= patent.getTitle() + " Saved Properly";
		
		return returnedText;
	}
	
	@Override
	public void deletePatent(long idPatent) {
		Optional<Patent> patentOptional = patentRepository.findById(idPatent);
		
		if(patentOptional.isPresent()) {
			patentRepository.delete(patentOptional.get());
		}
	}

	private Patent parseXML(File file) {
		Patent patent = new Patent();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			
			dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			
	        DocumentBuilder db = dbf.newDocumentBuilder();
	
	        Document doc = db.parse(file);
	        
	        doc.getDocumentElement().normalize();
	        
	        Element element = null;
	        
	        element = doc.getDocumentElement();
	        
	        patent.setTitle(getTextOfNode(doc.getElementsByTagName("invention-title")));
	        patent.setPatentAbstract(getTextOfNode(doc.getElementsByTagName("abstract")));
	        patent.setPatentAbstract("");
	        
	        patent.setPatentDate(new SimpleDateFormat("yyyyMMdd").parse(element.getAttribute("date-produced")));
        
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return patent;
	}
	
	private String getTextOfNode(NodeList nList) {
		String returnedText="";
		
		for(int i=0; i< nList.getLength();i++) {
        	if(i==0) {
        		returnedText = nList.item(0).getTextContent();
        	}else {
        		returnedText = "\n" + nList.item(i).getTextContent();
        	}
        }
		
		return returnedText;
	}
	
	private File multipartFileToFile(MultipartFile multipart) {
		String dir="";
		
	    Path filepath = Paths.get(dir.toString(), multipart.getOriginalFilename());
		    
	    try {
			multipart.transferTo(filepath);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		    
	    return filepath.toFile();
	}
}
