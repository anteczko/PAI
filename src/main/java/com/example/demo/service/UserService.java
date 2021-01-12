package com.example.demo.service;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User insertUser(User user){
        userRepository.save(user);
        return user;
    }

    public List<User> selectUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(int userId){
        return userRepository.findById(userId);
    }
    public Optional<User> getUserByEmail(String email){
        return userRepository.findAll().stream().filter(x->{return x.getEmail().equals(email);}).findFirst();
    }

    public boolean deleteUserRecurr(int userId){
        Optional<User> usr=getUserById(userId);
        if(!usr.isPresent()){
            return false;
        }else{
            //usr.get().getTasks().stream().forEach();
            return true;
        }
    }

    public boolean toggleUserStatus(int userId){
        Optional<User> search=userRepository.findById(userId);

        if(search.isEmpty())return false;
        else{
            search.get().setStatus( !search.get().getStatus() );
            return true;
        }
    }

    public boolean deleteUser(int userId){
        Optional<User> user=userRepository.findById(userId);

        if(user.isPresent()){
            List<Task> tasks=taskRepository.findAll().stream().filter(x->{return x.getUser().equals( userRepository.findById(userId) );}).collect(Collectors.toList());

            userRepository.deleteById(userId);
            return true;
        }else{
            return false;
        }
    }

    public boolean purgeUser(int userId){
        Optional<User> user=userRepository.findById(userId);

        if(user.isPresent()){
            List<Task> tasks=taskRepository.findAll().stream().filter(x->{return x.getUser().equals( userRepository.findById(userId) );}).collect(Collectors.toList());
            tasks.stream().forEach(x-> {
                taskRepository.deleteById(x.getTaskId());
            });
            userRepository.deleteById(userId);
            return true;
        }else{
            return false;
        }
    }

}
