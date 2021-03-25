package com.example.importCsv.controller;

import java.io.BufferedReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.example.importCsv.model.Records;
import com.example.importCsv.repository.RecordsRepository;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.ui.Model;

@Controller
public class ImportController {
	
	@Autowired
	RecordsRepository repository;
	
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/import-to-csv")
    public String importCsv(@RequestParam("file") MultipartFile file, Model model) {
        
    	// check if file is empty
        if (file.isEmpty()) {
            model.addAttribute("message", "select a CSV file to import.");
            model.addAttribute("status", false);
        }
        else {
        	
        	 // parse CSV file
            try (Reader reader = new BufferedReader(new java.io.InputStreamReader(file.getInputStream()))) {
            	
            	// reader
                CsvToBean<Records> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Records.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
                
                // convert CsvToBean object to list of records
                ArrayList<Records> records = (ArrayList<Records>) csvToBean.parse();
                // ArrayList<Records> records = (ArrayList<Records>) csvToBean.parse();
                // sort
                //Collections.sort(records);
                Records.insertionSort(records);
                
                // save to db
                repository.saveAll(records);
                
                // save users list on model
                model.addAttribute("records", records);
                model.addAttribute("status", true);
                
                // catch
            } catch (Exception e) {
                model.addAttribute("message", "error");
                model.addAttribute("status", false);
            }
        }
        return "import-csv";
    }
}