package com.pharmanuman.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pharmanuman.services.PdfService;

@Controller
@RequestMapping("/pdf")
public class PdfController {
	
	

	@Autowired
	PdfService ps;

	/*
	 * @PostMapping("/create-pdf") public ResponseEntity<InputStreamResource>
	 * createPdf() { ByteArrayInputStream pdf = ps.createPdf();
	 * 
	 * HttpHeaders httpHeaders = new HttpHeaders();
	 * httpHeaders.add("Content-diposition", "inline;file=prabin.pdf");
	 * 
	 * return ResponseEntity.ok().headers(httpHeaders).contentType(MediaType.
	 * APPLICATION_PDF) .body(new InputStreamResource(pdf));
	 * 
	 * }
	 */

}
