package za.ac.tut.ptms.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Announcement;
import za.ac.tut.ptms.repository.AnnouncementRepository;
import za.ac.tut.ptms.repository.TeacherRepository;

@Controller
@RequestMapping("/announcements")
public class AnnouncementWebController {
    private final AnnouncementRepository repo;
    private final TeacherRepository      teacherRepo;
    public AnnouncementWebController(AnnouncementRepository r, TeacherRepository tr) { repo=r; teacherRepo=tr; }

    @GetMapping
    public String handle(@RequestParam(required=false) String action,@RequestParam(required=false) Integer id, Model m) {
        if ("new".equals(action)){
            m.addAttribute("teachers", teacherRepo.getAll());
            return "announcementForm";
        }

        if ("edit".equals(action)){
            m.addAttribute("announcement", repo.getById(id));
            m.addAttribute("teachers", teacherRepo.getAll());
            return "announcementForm";
        }

        if ("delete".equals(action)){
            repo.delete(id);
            return "redirect:/announcements?msg=deleted";
        }

        m.addAttribute("announcements", repo.getAll());
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
