package com.society.service;

import java.util.List;

import com.society.dto.*;
import com.society.entity.Complaint;

public interface ComplaintService {

    Complaint raiseComplaint(ComplaintRequestDTO dto);

    List<Complaint> getMyComplaints();

    List<Complaint> getComplaintsByFlat(AdminComplaintSearchDTO dto);

    Complaint updateStatus(Integer complaintId,
                           ComplaintStatusUpdateDTO dto);
}
