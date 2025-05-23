/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package demoproject.controller;

import demoproject.dao.UserDao;
import demoproject.model.LoginRequest;
import demoproject.model.UserData;
import demoproject.view.DashboardView;
import demoproject.view.LoginView;
import demoproject.view.RegistrationView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 *
 * @author sangyakoirala
 */
public class LoginController {
    private final UserDao userDao= new UserDao();
    private final LoginView loginView;
    private boolean isPasswordVisible = false;
    public LoginController( LoginView loginView){
        this.loginView=loginView;
        loginView.addLoginUserListener(new LoginUserListener());
        loginView.addRegisterListener(new RegistrationListener());
        loginView.showPasswordButtonListener(new ShowPasswordListener());

    }
    public void open(){
        this.loginView.setVisible(true);
    }
    public void close(){
        this.loginView.dispose();
    }
    class RegistrationListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            RegistrationView registrationView= new RegistrationView();
            RegistrationController registrationController= new RegistrationController(registrationView);
            registrationController.open();
            close();
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {
        
        }
        
    }
    class ShowPasswordListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        isPasswordVisible = !isPasswordVisible;
        loginView.togglePasswordField(isPasswordVisible);
    }
    }
    
    class LoginUserListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
             String email= loginView.getEmailTextField().getText();
            String password= String.valueOf(loginView.getPasswordField().getPassword());
            if(email.isEmpty()||password.isEmpty()){
                 
                JOptionPane.showMessageDialog(loginView, 
                        "Please fill in all the fields.", "Validation Error", 
                        JOptionPane.ERROR_MESSAGE);
                
            } else {
                LoginRequest loginRequest= new LoginRequest(email,password);
                UserData user=userDao.loginUser(loginRequest);
                System.out.println(user);
                if(user != null){
                    
                    JOptionPane.showMessageDialog(loginView, 
                        "Login Successfull");
                    DashboardView dashboardView = new DashboardView();
                    DashboardController dashboardController= new DashboardController(dashboardView);
                    dashboardController.open();
                    close();
                } else{
                         JOptionPane.showMessageDialog(loginView, 
                        "Login Failed");
                         System.out.println("Insert Failed");
                     }
                     
                
            }
        }
        
    }
}
