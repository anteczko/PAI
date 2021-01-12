package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.enums.Status;
import com.example.demo.model.enums.Type;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public Task addTask(Task task){
        taskRepository.save(task);
        return task;
    }
    public Optional<Task> getTaskById(int taskId){
        return taskRepository.findById(taskId);
    }
    public List<Task> getTaskByStatus(Status status){
        return taskRepository.findAll().stream().filter(x->{ return x.getStatus().equals(status); }).collect(Collectors.toList());
    }
    public List<Task> getTaskByName(String name){
        return taskRepository.findAll().stream().filter(x->{ return x.getTitle().equals(name); }).collect(Collectors.toList());
    }
    public List<Task> getTaskByType(Type type){
        return taskRepository.findAll().stream().filter(x->{ return x.getType().equals(type); }).collect(Collectors.toList());
    }

    public boolean setTaskStatus(int taskId,Status status){
        Optional<Task> task=taskRepository.findById(taskId);
        if(!task.isPresent()){
            return false;
        }else{
            task.get().setStatus(status);
            return true;
        }
    }

    public List<Task> getTasksSorted(){
        return taskRepository.findAll().stream().sorted(Comparator.comparing(Task::getDateAdded)).collect(Collectors.toList());
    }

    public boolean deleteTask(int taskId){
        Optional<Task> task=taskRepository.findById(taskId);
        if(task.isPresent()){
            taskRepository.deleteById(taskId);
            return true;
        }else{
            return false;
        }
    }


}
