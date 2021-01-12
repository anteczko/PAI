package com.example.demo.controller;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.model.enums.Status;
import com.example.demo.model.enums.Type;
import com.example.demo.service.TaskService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class SiteController {
    private UserService userService;
    private TaskService taskService;

    @Autowired
    public SiteController(UserService userService, TaskService taskService) {
        this.userService = userService;
        this.taskService = taskService;
    }


    @PostMapping("/users/registration")
    public User registerUser(
            @RequestParam("name") String name,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ){
        return userService.insertUser(new User(name,lastName,email,password));
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userService.selectUsers();
    }
    
    @GetMapping("/userOld")
    public Optional<User> getUserById(
            @RequestParam("userId") int userId
    ){
        return userService.getUserById(userId);
    }

    @DeleteMapping("/user/delete")
    public boolean deleteUserById(
            @RequestParam("userId") int userId
    ){
        Optional<Task> temp=taskService.getTaskById(userId);
        if(temp.isPresent()){
            userService.deleteUser(userId);
            return true;
        }else return false;
    }

    @DeleteMapping("/user/purge")
    public boolean purgeUserById(
            @RequestParam("userId") int userId
    ){
        Optional<Task> temp=taskService.getTaskById(userId);
        if(temp.isPresent()){
            userService.purgeUser(userId);
            return true;
        }else return false;
    }

    @GetMapping("/user")
    public Optional<User> getUserByEmail(
            @RequestParam("email") Optional<String> email,
            @RequestParam("userId") Optional<Integer> userId
    ){
        if(!email.isPresent() && !userId.isPresent()){
            return null;
        }else if(!email.isPresent() && userId.isPresent()){
            return userService.getUserById(userId.get());
        }else if(email.isPresent() && !userId.isPresent()){
            return userService.getUserByEmail(email.get());
        }
        return userService.getUserById(userId.get());
    }
    @PutMapping("/users/toggle/id={userId}")
    public boolean toggleUserStatus(
            @RequestParam("userId") int userId
    ){
        Optional<User> temp=userService.getUserById(userId);
        if(temp.isPresent()){
            userService.toggleUserStatus(userId);
            return true;
        }else return false;
    }


    //////////////////////////////////////////////////////////////////////////
    //  Taksk related!!!

    @PostMapping("/tasks/add")
    public Task registerUser(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("type") Type type,
            @RequestParam("status") Status status,
            @RequestParam("user") User user
    ){
        return taskService.addTask(new Task(title,description,type,status,user));
    }

    @GetMapping("/tasks")
    public List<Task> getTasks(){
        return taskService.getTasksSorted();
    }

    @GetMapping("/tasks/search")
    public List<Task> searchTask(
            @RequestParam("name") Optional<String> name,
            @RequestParam("status") Optional<Status> status,
            @RequestParam("type") Optional<Type> type
    ){
        if(name.isEmpty() && status.isEmpty() && type.isEmpty()){
            return null;
        }else{
            if(name.isPresent()){
                return taskService.getTaskByName(name.get());
            }else if(status.isPresent()){
                return taskService.getTaskByStatus(Status.valueOf(status.toString()));
            }else if(type.isPresent()){
                return taskService.getTaskByType(Type.valueOf(type.toString()));
            }
        }
        return null;
    }

    @PutMapping("/tasks")
    public boolean setTaskStatus(
            @RequestParam("taskId") int taskId,
            @RequestParam("status") Status status
    ){
        Optional<Task> temp=taskService.getTaskById(taskId);
        if(temp.isPresent()){
            temp.get().setStatus(status);
            return true;
        }else return false;
    }

    @DeleteMapping("/tasks")
    public boolean deleteTask(
            @RequestParam("taskId") int taskId
    ){
        Optional<Task> temp=taskService.getTaskById(taskId);
        if(temp.isPresent()){
            taskService.deleteTask(taskId);
            return true;
        }else return false;
    }

}
