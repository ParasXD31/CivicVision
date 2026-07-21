package com.paras.civicvision.backend.service;

import com.paras.civicvision.backend.dto.ComplaintRequest;
import com.paras.civicvision.backend.dto.ComplaintResponse;
import com.paras.civicvision.backend.entity.Complaint;
import com.paras.civicvision.backend.exception.ResourceNotFoundException;
import com.paras.civicvision.backend.repository.ComplaintRepository;
import org.springframework.stereotype.Service;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    public ComplaintResponse createComplaint(ComplaintRequest request) {

        Complaint complaint = new Complaint();

        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());
        complaint.setStatus("OPEN");

        Complaint savedComplaint = complaintRepository.save(complaint);

        return new ComplaintResponse(
                savedComplaint.getId(),
                savedComplaint.getTitle(),
                savedComplaint.getDescription(),
                savedComplaint.getStatus()
        );
    }

    public ComplaintResponse getComplaintById(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Complaint not found with id: " + id));

        return new ComplaintResponse(
                complaint.getId(),
                complaint.getTitle(),
                complaint.getDescription(),
                complaint.getStatus()
        );
    }

    public ComplaintResponse updateComplaint(Long id, ComplaintRequest request) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Complaint not found with id: " + id));

        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());

        Complaint updatedComplaint = complaintRepository.save(complaint);

        return new ComplaintResponse(
                updatedComplaint.getId(),
                updatedComplaint.getTitle(),
                updatedComplaint.getDescription(),
                updatedComplaint.getStatus()
        );
    }
}