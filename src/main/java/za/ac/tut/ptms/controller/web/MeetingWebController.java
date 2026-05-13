package za.ac.tut.ptms.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Meeting;
import za.ac.tut.ptms.model.Parent;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.MeetingRepository;
import za.ac.tut.ptms.repository.ParentRepository;
import za.ac.tut.ptms.repository.TeacherRepository;

@Controller
@RequestMapping("/meetings")
public class MeetingWebController {

    private final MeetingRepository repo;
    private final ParentRepository  parentRepo;
    private final TeacherRepository teacherRepo;

    public MeetingWebController(MeetingRepository r,
                                ParentRepository pr,
                                TeacherRepository tr) {
        this.repo        = r;
        this.parentRepo  = pr;
        this.teacherRepo = tr;
    }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         HttpServletRequest request,
                         Model model) {

        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("userRole");

        try {
            if ("new".equals(action)) {
                // Only show the relevant parent or teacher in the form
                if ("teacher".equals(role)) {
                    Teacher t = (Teacher) session.getAttribute("loggedInUser");
                    model.addAttribute("parents",  parentRepo.getAll());
                    model.addAttribute("teachers", teacherRepo.getAll());
                    // Pre-select the logged-in teacher
                    model.addAttribute("loggedInTeacherId", t.getTeacherId());
                } else if ("parent".equals(role)) {
                    Parent p = (Parent) session.getAttribute("loggedInUser");
                    model.addAttribute("parents",  parentRepo.getAll());
                    model.addAttribute("teachers", teacherRepo.getAll());
                    // Pre-select the logged-in parent
                    model.addAttribute("loggedInParentId", p.getParentId());
                }
                return "meetingForm";

            } else if ("edit".equals(action)) {
                Meeting m = repo.getById(id);
                // Security check — only allow editing own meetings
                if (!isOwner(m, role, session)) {
                    return "redirect:/meetings";
                }
                model.addAttribute("meeting",  m);
                model.addAttribute("parents",  parentRepo.getAll());
                model.addAttribute("teachers", teacherRepo.getAll());
                return "meetingForm";

            } else if ("delete".equals(action)) {
                Meeting m = repo.getById(id);
                // Security check — only allow deleting own meetings
                if (isOwner(m, role, session)) {
                    repo.delete(id);
                }
                return "redirect:/meetings?msg=deleted";

            } else {
                // List — show only meetings relevant to the logged-in user
                if ("teacher".equals(role)) {
                    Teacher t = (Teacher) session.getAttribute("loggedInUser");
                    model.addAttribute("meetings", repo.getByTeacherId(t.getTeacherId()));
                } else if ("parent".equals(role)) {
                    Parent p = (Parent) session.getAttribute("loggedInUser");
                    model.addAttribute("meetings", repo.getByParentId(p.getParentId()));
                }
                return "meetings";
            }
        } catch (Exception e) {
            return "redirect:/dashboard";
        }
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required = false) Integer meetingId,
                         @RequestParam int parentId,
                         @RequestParam int teacherId,
                         @RequestParam String meetingDate,
                         @RequestParam String meetingTime,
                         @RequestParam(required = false) String notes) {
        Meeting m = new Meeting();
        m.setParentId(parentId);
        m.setTeacherId(teacherId);
        m.setMeetingDate(meetingDate);
        m.setMeetingTime(meetingTime);
        m.setNotes(notes);

        if ("insert".equals(action)) {
            repo.insert(m);
            return "redirect:/meetings?msg=inserted";
        }
        m.setMeetingId(meetingId);
        repo.update(m);
        return "redirect:/meetings?msg=updated";
    }

    /** Returns true if the logged-in user owns this meeting */
    private boolean isOwner(Meeting m, String role, HttpSession session) {
        if (m == null) return false;
        if ("teacher".equals(role)) {
            Teacher t = (Teacher) session.getAttribute("loggedInUser");
            return m.getTeacherId() == t.getTeacherId();
        } else if ("parent".equals(role)) {
            Parent p = (Parent) session.getAttribute("loggedInUser");
            return m.getParentId() == p.getParentId();
        }
        return false;
    }
}