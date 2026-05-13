package za.ac.tut.ptms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Parent;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.AnnouncementRepository;
import za.ac.tut.ptms.repository.MeetingRepository;

import java.util.Map;

/**
 * Replaces DashboardServlet.
 *
 * GET /api/dashboard
 *   → teacher: their meetings + all announcements
 *   → parent:  their meetings + all announcements
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final MeetingRepository      meetingRepo;
    private final AnnouncementRepository announcementRepo;

    public DashboardController(MeetingRepository meetingRepo,
                                AnnouncementRepository announcementRepo) {
        this.meetingRepo      = meetingRepo;
        this.announcementRepo = announcementRepo;
    }

    @GetMapping
    public ResponseEntity<?> dashboard(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not authenticated."));
        }

        String role = (String) session.getAttribute("userRole");

        if ("teacher".equals(role)) {
            Teacher teacher = (Teacher) session.getAttribute("loggedInUser");
            return ResponseEntity.ok(Map.of(
                    "role",          "teacher",
                    "userName",      teacher.getName() + " " + teacher.getSurname(),
                    "myMeetings",    meetingRepo.getByTeacherId(teacher.getTeacherId()),
                    "announcements", announcementRepo.getAll()
            ));

        } else if ("parent".equals(role)) {
            Parent parent = (Parent) session.getAttribute("loggedInUser");
            return ResponseEntity.ok(Map.of(
                    "role",          "parent",
                    "userName",      parent.getName() + " " + parent.getSurname(),
                    "myMeetings",    meetingRepo.getByParentId(parent.getParentId()),
                    "announcements", announcementRepo.getAll()
            ));
        }

        return ResponseEntity.badRequest().body(Map.of("error", "Unknown role."));
    }
}
