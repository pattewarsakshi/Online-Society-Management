package com.society.service;

/* ========= SPRING ========= */
import org.springframework.stereotype.Service;

/* ========= JAVA ========= */
import java.time.LocalDateTime;
import java.util.List;

/* ========= ENTITY ========= */
import com.society.entity.Visitor;

/* ========= REPOSITORY ========= */
import com.society.repository.VisitorRepository;

@Service
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public VisitorService(VisitorRepository visitorRepository) {
        this.visitorRepository = visitorRepository;
    }

    // ================= ENTRY =================
    public void addVisitor(Visitor visitor) {
        visitor.setEntryTime(LocalDateTime.now()); // ✅ AUTO ENTRY TIME
        visitorRepository.save(visitor);
    }

    // ================= EXIT =================
    public void markExit(Integer visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor not found"));

        visitor.setExitTime(LocalDateTime.now()); // ✅ AUTO EXIT TIME
        visitorRepository.save(visitor);
    }

    // ================= ADMIN =================
    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }
}
