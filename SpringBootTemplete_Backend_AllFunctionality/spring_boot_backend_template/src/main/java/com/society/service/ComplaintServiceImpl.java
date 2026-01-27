//package com.society.service;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//
//import lombok.RequiredArgsConstructor;
//
//import com.society.dto.*;
//import com.society.entity.*;
//import com.society.entityenum.ComplaintStatus;
//import com.society.entityenum.Role;
//import com.society.repository.*;
//import com.society.service.ComplaintService;
//
//@Service
//@RequiredArgsConstructor
//public class ComplaintServiceImpl implements ComplaintService {
//
//    private final ComplaintRepository complaintRepo;
//    private final UserRepository userRepo;
//
//    // TEMP login (replace with JWT later)
//    private User getLoggedInUser() {
//        return userRepo.findById(1)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
//
//    // ================= RESIDENT =================
//
//    @Override
//    public Complaint raiseComplaint(ComplaintRequestDTO dto) {
//
//        User user = getLoggedInUser();
//
//        if (user.getFlat() == null) {
//            throw new RuntimeException(
//                "Flat not assigned. Contact society admin."
//            );
//        }
//
//        Complaint complaint = new Complaint();
//        complaint.setUser(user);
//        complaint.setFlat(user.getFlat());
//        complaint.setComplaintType(dto.getComplaintType());
//        complaint.setDescription(dto.getDescription());
//        complaint.setStatus(ComplaintStatus.OPEN);
//        complaint.setDateFiled(LocalDate.now());
//
//        return complaintRepo.save(complaint);
//    }
//
//    @Override
//    public List<Complaint> getMyComplaints() {
//        return complaintRepo.findByUserUserId(
//                getLoggedInUser().getUserId()
//        );
//    }
//
//    // ================= ADMIN =================
//
//    @Override
//    public List<Complaint> getComplaintsByFlat(AdminComplaintSearchDTO dto) {
//
//        User admin = getLoggedInUser();
//
//        if (admin.getRole() != Role.ADMIN) {
//            throw new RuntimeException("Admin access only");
//        }
//
//        return complaintRepo
//            .findByFlatSocietySocietyIdAndFlatTowerNameAndFlatFlatNumber(
//                admin.getSociety().getSocietyId(),
//                dto.getTowerName(),
//                dto.getFlatNumber()
//            );
//    }
//
//    @Override
//    public Complaint updateStatus(Integer complaintId,
//                                  ComplaintStatusUpdateDTO dto) {
//
//        Complaint complaint = complaintRepo.findById(complaintId)
//                .orElseThrow(() -> new RuntimeException("Complaint not found"));
//
//        complaint.setStatus(dto.getStatus());
//
//        if (dto.getStatus() == ComplaintStatus.RESOLVED ||
//            dto.getStatus() == ComplaintStatus.CLOSED) {
//            complaint.setDateResolved(LocalDate.now());
//        }
//
//        return complaintRepo.save(complaint);
//    }
//}
package com.society.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import com.society.dto.AdminComplaintSearchDTO;
import com.society.dto.ComplaintRequestDTO;
import com.society.dto.ComplaintStatusUpdateDTO;
import com.society.entity.Complaint;
import com.society.entity.User;
import com.society.entityenum.ComplaintStatus;
import com.society.entityenum.Role;
import com.society.repository.ComplaintRepository;
import com.society.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepo;
    private final UserRepository userRepo;

    // TEMP login (replace with JWT later)
//    private User getLoggedInUser() {
//        return userRepo.findById(1)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }
    private User getLoggedInUser() {
        return userRepo.findByRole(Role.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }


    // ================= RESIDENT =================

    @Override
    public Complaint raiseComplaint(ComplaintRequestDTO dto) {

        User user = getLoggedInUser();

        if (user.getFlat() == null) {
            throw new RuntimeException(
                "Flat not assigned. Contact society admin."
            );
        }

        Complaint complaint = new Complaint();
        complaint.setUser(user);
        complaint.setFlat(user.getFlat());
        complaint.setComplaintType(dto.getComplaintType());
        complaint.setDescription(dto.getDescription());
        complaint.setStatus(ComplaintStatus.OPEN);
        complaint.setDateFiled(LocalDate.now());

        return complaintRepo.save(complaint);
    }

    @Override
    public List<Complaint> getMyComplaints() {
        return complaintRepo.findByUserUserId(
                getLoggedInUser().getUserId()
        );
    }

    // ================= ADMIN =================

    @Override
    public List<Complaint> getComplaintsByFlat(AdminComplaintSearchDTO dto) {

        User admin = getLoggedInUser();

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Admin access only");
        }

        return complaintRepo
            .findByFlatSocietySocietyIdAndFlatTowerNameAndFlatFlatNumber(
                admin.getSociety().getSocietyId(),
                dto.getTowerName(),
                dto.getFlatNumber()
            );
    }

    @Override
    public Complaint updateStatus(Integer complaintId,
                                  ComplaintStatusUpdateDTO dto) {

        User admin = getLoggedInUser();

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Only admin can update status");
        }

        Complaint complaint = complaintRepo.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setStatus(dto.getStatus());

        if (dto.getStatus() == ComplaintStatus.RESOLVED ||
            dto.getStatus() == ComplaintStatus.CLOSED) {
            complaint.setDateResolved(LocalDate.now());
        }

        return complaintRepo.save(complaint);
    }
}
