package za.ac.tut.ptms.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import za.ac.tut.ptms.model.Parent;
import za.ac.tut.ptms.repository.ParentRepository;

@Controller @RequestMapping("/parents")
public class ParentWebController {
    private final ParentRepository repo;
    public ParentWebController(ParentRepository r) { repo = r; }

    @GetMapping
    public String handle(@RequestParam(required=false) String action,
                         @RequestParam(required=false) Integer id, Model m) {
        if ("new".equals(action))  { return "parentForm"; }
        if ("edit".equals(action)) { m.addAttribute("parent", repo.getById(id)); return "parentForm"; }
        if ("delete".equals(action)) { repo.delete(id); return "redirect:/parents?msg=deleted"; }
        m.addAttribute("parents", repo.getAll()); return "parents";
    }

    @PostMapping
    public String submit(@RequestParam String action,
                         @RequestParam(required=false) Integer parentId,
                         @RequestParam String name, @RequestParam String surname,
                         @RequestParam String email,
                         @RequestParam(required=false) String password) {
        Parent p = new Parent();
        p.setName(name); p.setSurname(surname); p.setEmail(email);
        if ("insert".equals(action)) { p.setPassword(password); repo.insert(p); return "redirect:/parents?msg=inserted"; }
        p.setParentId(parentId);
        boolean cp = password != null && !password.trim().isEmpty();
        if (cp) p.setPassword(password);
        repo.update(p, cp); return "redirect:/parents?msg=updated";
    }
}
