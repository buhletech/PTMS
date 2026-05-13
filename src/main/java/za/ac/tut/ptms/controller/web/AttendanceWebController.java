package za.ac.tut.ptms.controller.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Attendance;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.*;

@Controller
@RequestMapping("/attendance")
public class AttendanceWebController {

    private final AttendanceRepository  repo;
    private final StudentRepository     studentRepo;
    private final SubjectRepository     subjectRepo;
    private final SchoolClassRepository classRepo;

    public AttendanceWebController(AttendanceRepository r, StudentRepository sr,
                                   SubjectRepository sub, SchoolClassRepository cr) {
        repo = r; studentRepo = sr; subjectRepo = sub; classRepo = cr;
    }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         @RequestParam(required = false) Integer classId,
                         @RequestParam(required = false) Integer studentId,
                         HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        String role = (String) session.getAttribute("userRole");

        if (!"teacher".equals(role)) return "redirect:/dashboard";

        Teacher teacher = (Teacher) session.getAttribute("loggedInUser");
        int teacherId = teacher.getTeacherId();

        // Always load teacher's classes for the dropdown
        model.addAttribute("teacherClasses", classRepo.getByTeacherId(teacherId));
        model.addAttribute("selectedClassId", classId);

        if ("new".equals(action)) {
            if (studentId != null) {
                model.addAttribute("preStudent", studentRepo.getById(studentId));
            }
            // Load subjects for this teacher in this class
            if (classId != null) {
                model.addAttribute("subjects",
                        subjectRepo.getByTeacherAndClass(teacherId, classId));
            } else {
                model.addAttribute("subjects",
                        subjectRepo.getByTeacherId(teacherId));
            }
            model.addAttribute("selectedClassId", classId);
            return "attendanceForm";
        }

        if ("edit".equals(action)) {
            Attendance a = repo.getById(id);
            model.addAttribute("attendance", a);
            model.addAttribute("subjects", subjectRepo.getByTeacherId(teacherId));
            return "attendanceForm";
        }

        if ("delete".equals(action)) {
            repo.delete(id);
            return "redirect:/attendance?msg=deleted" + (classId != null ? "&classId=" + classId : "");
        }

        // List view — if class selected, load students and recent attendance
        if (classId != null) {
            model.addAttribute("students",        studentRepo.getByClassId(classId));
            model.addAttribute("recentAttendance", repo.getByClassId(classId, teacherId));
        }

        return "attendance";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required = false) Integer attendanceId,
                         @RequestParam int studentId,
                         @RequestParam int subjectId,
                         @RequestParam String date,
                         @RequestParam String status,
                         @RequestParam(required = false) Integer classId) {
        Attendance a = new Attendance();
        a.setStudentId(studentId); a.setSubjectId(subjectId);
        a.setDate(date); a.setStatus(status);
        if ("insert".equals(action)) {
            repo.insert(a);
            return "redirect:/attendance?msg=inserted" + (classId != null ? "&classId=" + classId : "");
        }
        a.setAttendanceId(attendanceId);
        repo.update(a);
        return "redirect:/attendance?msg=updated" + (classId != null ? "&classId=" + classId : "");
    }
}