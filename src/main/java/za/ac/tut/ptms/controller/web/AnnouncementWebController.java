package za.ac.tut.ptms.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Announcement;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.AnnouncementRepository;
import za.ac.tut.ptms.repository.TeacherRepository;

@Controller
@RequestMapping("/announcements")
public class AnnouncementWebController {
    private final AnnouncementRepository repo;
    private final TeacherRepository      teacherRepo;
    public AnnouncementWebController(AnnouncementRepository r, TeacherRepository tr) { repo=r; teacherRepo=tr; }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("userRole");

        if ("parent".equals(role) && action != null) {
            return "redirect:/announcements";
        }

        // Pass logged-in teacher's ID for auto-selection
        if ("teacher".equals(role)) {
            Teacher t = (Teacher) session.getAttribute("loggedInUser");
            model.addAttribute("loggedInTeacherId", t.getTeacherId());
        }

        if ("new".equals(action)) {
            model.addAttribute("teachers", teacherRepo.getAll());
            return "announcementForm";
        }
        if ("edit".equals(action)) {
            model.addAttribute("announcement", repo.getById(id));
            model.addAttribute("teachers", teacherRepo.getAll());
            return "announcementForm";
        }
        if ("delete".equals(action)) {
            repo.delete(id);
            return "redirect:/announcements?msg=deleted";
        }
        model.addAttribute("announcements", repo.getAll());
        return "announcements";
    }

    @PostMapping
    public String submit(@RequestParam String action,@RequestParam(required=false) Integer announcementId,
                         @RequestParam String title, @RequestParam String message,
                         @RequestParam String datePosted, @RequestParam int teacherId) {
        Announcement a = new Announcement();
        a.setTitle(title); a.setMessage(message); a.setDatePosted(datePosted); a.setTeacherId(teacherId);
        if ("insert".equals(action)){
            repo.insert(a);
            return "redirect:/announcements?msg=inserted";
        }

        a.setAnnouncementId(announcementId);
        repo.update(a);
        return "redirect:/announcements?msg=updated";
    }
}
