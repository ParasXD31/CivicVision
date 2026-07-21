package com.paras.civicvision.backend.controller;

import com.paras.civicvision.backend.dto.ComplaintRequest;
import com.paras.civicvision.backend.dto.ComplaintResponse;
import com.paras.civicvision.backend.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ComplaintResponse createComplaint(
            @Valid @RequestBody ComplaintRequest request) {

        return complaintService.createComplaint(request);
    }

    @GetMapping("/{id}")
    public ComplaintResponse getComplaintById(@PathVariable Long id) {

        return complaintService.getComplaintById(id);
    }

    @PutMapping("/{id}")
    public ComplaintResponse updateComplaint(
            @PathVariable Long id,
            @Valid @RequestBody ComplaintRequest request) {

        return complaintService.updateComplaint(id, request);
    }

}   