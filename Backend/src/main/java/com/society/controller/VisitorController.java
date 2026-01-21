package com.society.controller;

/* ========= SPRING ========= */
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.MediaType;

/* ========= JAVA ========= */
import java.util.List;

/* ========= DTO ========= */
import com.society.dto.VisitorEntryDTO;

/* ========= ENTITY ========= */
import com.society.entity.Visitor;
import com.society.entity.User;

/* ========= ENUM ========= */
import com.society.entityenum.Role;

/* ========= SERVICE ========= */
import com.society.service.VisitorService;
import com.society.service.UserService;

@RestController
@RequestMapping("/api/visitor")
public class VisitorController {

    private final VisitorService visitorService;
    private final UserService userService;

    public VisitorController(VisitorService visitorService,
                             UserService userService) {
        this.visitorService = visitorService;
        this.userService = userService;
    }

    // =====================================================
    // GUARD : ADD VISITOR ENTRY
    // =====================================================
    @PostMapping(
        value = "/entry",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String visitorEntry(
            @RequestParam String guardEmail,
            @RequestBody VisitorEntryDTO dto) {

        User guard = userService.getUserByEmail(guardEmail);

        if (guard.getRole() != Role.GUARD) {
            throw new RuntimeException("Access denied: Only GUARD allowed");
        }

        Visitor visitor = new Visitor();
        visitor.setVisitorName(dto.getVisitorName());
        visitor.setPhone(dto.getPhone());
        visitor.setPurpose(dto.getPurpose());

        visitorService.addVisitor(visitor);
        return "Visitor entry recorded";
    }

    // =====================================================
    // GUARD : MARK VISITOR EXIT
    // =====================================================
    @PostMapping(
        value = "/exit/{visitorId}",
        produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String visitorExit(
            @RequestParam String guardEmail,
            @PathVariable Integer visitorId) {

        User guard = userService.getUserByEmail(guardEmail);

        if (guard.getRole() != Role.GUARD) {
            throw new RuntimeException("Access denied: Only GUARD allowed");
        }

        visitorService.markExit(visitorId);
        return "Visitor exit recorded";
    }

    // =====================================================
    // ADMIN : VIEW ALL VISITORS
    // =====================================================
    @GetMapping(
        value = "/all",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<Visitor> getAllVisitors(
            @RequestParam String adminEmail) {

        User admin = userService.getUserByEmail(adminEmail);

        if (admin.getRole() != Role.ADMIN) {
            throw new RuntimeException("Access denied: Only ADMIN allowed");
        }

        return visitorService.getAllVisitors();
    }
}
