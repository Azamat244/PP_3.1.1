package ru.khafizov.springboot.controller;


import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.khafizov.springboot.model.User;
import ru.khafizov.springboot.service.UserService;


@Controller
@RequestMapping("/user")
public class UsersController {


    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showUsers(Model model) {
        model.addAttribute("user", userService.getAll());
        return "/pages/showUsers";
    }


    @GetMapping("/new")
    public String getAddForm(@ModelAttribute("user") User user) {
        return "/pages/add";
    }

    @PostMapping
    //эта аннотация гвоорит о том, что этот метод будет вызываться по ссылке /user, но
    //только с POST запросами (при обработе POST запроса)
    public String adduser(@ModelAttribute("user") @Valid User user,
                            BindingResult bindingResult) {
        //аннотация @ModelAttribute говорит о том что в полях объекта user
        //будут лежать значения которые пришли в теле запроса, то есть связывает
        //данные из тела запроса с полями объекта, и назначает через сеттеры
        if (bindingResult.hasErrors()) {
            return "/pages/add";
        }
        userService.save(user);
        return "redirect:/user"; //по дефолу GET запрос
    }


    @GetMapping("/edit")
    public String getEditForm(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "/pages/edit";
    }


    @PostMapping("/edit")
    public String editUser(@ModelAttribute("user") @Valid User user,
                       BindingResult bindingResult,
                       @RequestParam("id") int id) {
        if (bindingResult.hasErrors()) {
            return "/pages/edit";
        }
        userService.update(id, user);
        return "redirect:/user";
    }


    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/user";
    }

}
