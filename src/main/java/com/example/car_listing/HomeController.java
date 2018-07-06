package com.example.car_listing;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CarRepository carRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("cars", carRepository.findAll());
        return "index";
    }

    @GetMapping("/add")
    public String formCar(Model model){
        model.addAttribute("car", new Car());
        return "formcar";
    }

    @GetMapping("/addcategory")
    public String formCategory(Model model){
        model.addAttribute("category", new Category());
        return "formcategory";
    }


    @PostMapping("/processCategory")
    public String processCategory(@Valid Category category, BindingResult result){
        if(result.hasErrors()){
            return "formcategory";
        }
        categoryRepository.save(category);
        return "redirect:/";
    }

    @PostMapping("/process")
    public String processCar(@ModelAttribute @Valid Car car, @RequestParam("file")MultipartFile file, BindingResult result){
        if(file.isEmpty() || (result.hasErrors())){
            return "return:/add";
        }
        try{
            Map uploadResult= cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            car.setImg(uploadResult.get("url").toString());
            carRepository.save(car);
        }catch (IOException e){
            e.printStackTrace();
            return "return:/add";
        }
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showcar(@PathVariable("id") long id, Model model){
        model.addAttribute("car", carRepository.findById(id).get());
        return "showcar";
    }

    @RequestMapping("/update/{id}")
    public String updatecar(@PathVariable("id") long id, Model model){
        model.addAttribute("car", carRepository.findById(id));
        return "formcar";
    }

    @RequestMapping("/delete/{id}")
    public String delcar(@PathVariable("id") long id){
        carRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/detailcategory/{id}")
    public String showcategory(@PathVariable("id") long id, Model model){
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "showcategory";
    }

    @RequestMapping("/updatecategory/{id}")
    public String updatecategory(@PathVariable("id") long id, Model model){
        model.addAttribute("category", categoryRepository.findById(id));
        return "formcategory";
    }

    @RequestMapping("/deletecategory/{id}")
    public String delcategory(@PathVariable("id") long id){
        categoryRepository.deleteById(id);
        return "redirect:/";
    }
}
