package com.work.selenium.controller;


import com.work.selenium.dto.request.FranchiseCreate;
import com.work.selenium.service.FranchiseService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;

    @PostMapping("/franchise")
    public ResponseEntity franchiseCreate(@RequestBody FranchiseCreate franchiseCreate){

        int start = franchiseCreate.getStart();
        int end = franchiseCreate.getEnd();

        int count = franchiseService.franchiseDataExtractor(start, end);

        return ResponseEntity.ok(count);
    }

}
