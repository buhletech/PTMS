package za.ac.tut.ptms.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import za.ac.tut.ptms.model.Parent;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.AnnouncementRepository;
import za.ac.tut.ptms.repository.MeetingRepository;
import za.ac.tut.ptms.repository.StudentRepository;

@Controller
public class DashboardWebController {

    private final MeetingRepository      meetingRepo;
    private final AnnouncementRepository announcementRepo;
    private final StudentRepository      studentRepo;

    public DashboardWebController(MeetingRepository mr,
                                  AnnouncementRepository ar,
                                  StudentRepository sr) {
        this.meetingRepo      = mr;
        this.announcementRepo = ar;
        this.studentRepo      = sr;
    }

    @GetMapping({"/", "/dashboard", "/index"})
    public String dashboard(HttpServletRequest req, Model model) {
        HttpSession session = req.getSession(false);
        String role = (String) session.getAttribute("userRole");

        if ("teacher".equals(role)) {
            Teacher t = (Teacher) session.getAttribute("loggedInUser");
            model.addAttribute("myMeetings",    meetingRepo.getByTeacherId(t.getTeacherId()));
            model.addAttribute("myAnnouncements", announcementRepo.getAll());
            return "teacherDashboard";

        } else if ("parent".equals(role)) {
            Parent p = (Parent) session.getAttribute("loggedInUser");
            model.addAttribute("myMeetings",    meetingRepo.getByParentId(p.getParentId()));
            model.addAttribute("announcements", announcementRepo.getAll());
            model.addAttribute("children",      studentRepo.getBySurname(p.getSurname()));
            return "parentDashboard";
        }

        return "index";
    }
}