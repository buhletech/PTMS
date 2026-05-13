package za.ac.tut.ptms.controller.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Parent;
import za.ac.tut.ptms.repository.ParentRepository;

@Controller
@RequestMapping("/admin/parents")
public class AdminParentController {

    private final ParentRepository repo;
    public AdminParentController(ParentRepository r) { this.repo = r; }

    @GetMapping
    public String handle(@RequestParam(required = false) String action,
                         @RequestParam(required = false) Integer id,
                         Model model) {
        if ("new".equals(action))    { return "admin/parentForm"; }
        if ("edit".equals(action))   { model.addAttribute("parent", repo.getById(id)); return "admin/parentForm"; }
        if ("delete".equals(action)) { repo.delete(id); return "redirect:/admin/parents?msg=deleted"; }
        model.addAttribute("parents", repo.getAll());
        return "admin/parents";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required = false) Integer parentId,
                         @RequestParam String name,
                         @RequestParam String surname,
                         @RequestParam String email,
                         @RequestParam(required = false) String password) {
        Parent p = new Parent();
        p.setName(name); p.setSurname(surname); p.setEmail(email);
        if ("insert".equals(action)) {
            p.setPassword(password);
            repo.insert(p);
            return "redirect:/admin/parents?msg=inserted";
        }
        p.setParentId(parentId);
        boolean cp = password != null && !password.trim().isEmpty();
        if (cp) p.setPassword(password);
        repo.update(p, cp);
        return "redirect:/admin/parents?msg=updated";
    }
}
