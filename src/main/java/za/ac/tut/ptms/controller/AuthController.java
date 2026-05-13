package za.ac.tut.ptms.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Parent;
import za.ac.tut.ptms.model.Teacher;
import za.ac.tut.ptms.repository.ParentRepository;
import za.ac.tut.ptms.repository.TeacherRepository;

import java.util.Map;

/**
 * Replaces LoginServlet + LogoutServlet.
 *
 * POST /api/auth/login   { "role":"teacher|parent", "email":"...", "password":"..." }
 * POST /api/auth/logout
 * GET  /api/auth/me      → current session user info
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TeacherRepository teacherRepo;
    private final ParentRepository  parentRepo;

    public AuthController(TeacherRepository teacherRepo, ParentRepository parentRepo) {
        this.teacherRepo = teacherRepo;
        this.parentRepo  = parentRepo;
    }

    /** POST /api/auth/login */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body,
                                    HttpServletRequest request) {

        String role     = body.get("role");
        String email    = body.get("email");
        String password = body.get("password");

        if ("teacher".equals(role)) {
            Teacher teacher = teacherRepo.getByEmail(email);
            if (teacher != null && teacher.getPassword().equals(password)) {
                HttpSession session = request.getSession(true);
                session.setAttribute("loggedInUser", teacher);
                session.setAttribute("userRole",     "teacher");
                session.setAttribute("userName",     teacher.getName() + " " + teacher.getSurname());
                return ResponseEntity.ok(Map.of(
                        "role",     "teacher",
                        "userName", teacher.getName() + " " + teacher.getSurname(),
                        "id",       teacher.getTeacherId()
                ));
            }

        } else if ("parent".equals(role)) {
            Parent parent = parentRepo.getByEmail(email);
            if (parent != null && parent.getPassword().equals(password)) {
                HttpSession session = request.getSession(true);
                session.setAttribute("loggedInUser", parent);
                session.setAttribute("userRole",     "parent");
                session.setAttribute("userName",     parent.getName() + " " + parent.getSurname());
                return ResponseEntity.ok(Map.of(
                        "role",     "parent",
                        "userName", parent.getName() + " " + parent.getSurname(),
                        "id",       parent.getParentId()
                ));
            }
        }

        return ResponseEntity.status(401)
                .body(Map.of("error", "Invalid email or password."));
    }

    /** POST /api/auth/logout */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(Map.of("message", "Logged out successfully."));
    }

    /** GET /api/auth/me — returns current session info */
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Not logged in."));
        }
        return ResponseEntity.ok(Map.of(
                "role",     session.getAttribute("userRole"),
                "userName", session.getAttribute("userName")
        ));
    }
}
