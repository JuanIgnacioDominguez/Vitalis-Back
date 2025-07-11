package com.uade.dam.demo.service;

import com.uade.dam.demo.entity.Appointment;
import com.uade.dam.demo.entity.User;
import com.uade.dam.demo.entity.Professional;
import com.uade.dam.demo.repository.AppointmentRepository;
import com.uade.dam.demo.repository.UserRepository;
import com.uade.dam.demo.repository.ProfessionalRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentReminderService {
    
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final ProfessionalRepository professionalRepository;
    private final JavaMailSender mailSender;
    
    public AppointmentReminderService(
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            ProfessionalRepository professionalRepository,
            JavaMailSender mailSender) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.professionalRepository = professionalRepository;
        this.mailSender = mailSender;
    }
    
    @Scheduled(cron = "0 0 8 * * *")
    public void sendAppointmentReminders() {
        System.out.println("Iniciando envío de recordatorios de turnos...");
        
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String tomorrowStr = tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        List<Appointment> tomorrowAppointments = appointmentRepository
            .findByDateAndStatus(tomorrowStr, "pending");
        
        System.out.println("Encontrados " + tomorrowAppointments.size() + " turnos para mañana");
        
        for (Appointment appointment : tomorrowAppointments) {
            sendReminderEmail(appointment);
        }
        
        System.out.println("Recordatorios enviados completamente");
    }
    
    @Scheduled(fixedRate = 3600000) 
    public void sendPreciseReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        LocalTime currentTime = LocalTime.now();
        
        LocalTime nextHour = currentTime.plusHours(1);
        
        List<Appointment> appointments = appointmentRepository
            .findByDateAndStatus(tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), "pending");
        
        for (Appointment appointment : appointments) {
            try {
                LocalTime appointmentTime = LocalTime.parse(appointment.getTime());
                if (appointmentTime.isAfter(currentTime) && appointmentTime.isBefore(nextHour)) {
                    sendReminderEmail(appointment);
                }
            } catch (Exception e) {
            }
        }
    }
    
    private void sendReminderEmail(Appointment appointment) {
        try {
            Optional<User> userOpt = userRepository.findById(appointment.getUserId());
            if (userOpt.isEmpty()) {
                System.err.println("Usuario no encontrado para appointment: " + appointment.getId());
                return;
            }
            User user = userOpt.get();
            
            Optional<Professional> professionalOpt = professionalRepository.findById(appointment.getProfessionalId());
            String professionalName = professionalOpt.isPresent() 
                ? professionalOpt.get().getName() 
                : "Profesional";
            
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Recordatorio de turno - Vitalis");
            
            String emailBody = String.format("""
                Hola %s,
                
                Te recordamos que tienes un turno agendado para mañana:
                
                📅 Fecha: %s
                🕐 Hora: %s
                👨‍⚕️ Profesional: %s
                
                Por favor, asegúrate de llegar puntual a tu cita.
                
                Si necesitas cancelar o reprogramar tu turno, puedes hacerlo desde la aplicación.
                
                ¡Nos vemos mañana!
                
                Equipo Vitalis
                """, 
                user.getNombre(),
                appointment.getDate(),
                appointment.getTime(),
                professionalName
            );
            
            message.setText(emailBody);
            mailSender.send(message);
            
            System.out.println("Recordatorio enviado a: " + user.getEmail() + 
                                " para turno del " + appointment.getDate() + " a las " + appointment.getTime());
                                
        } catch (Exception e) {
            System.err.println("Error enviando recordatorio para appointment " + appointment.getId() + ": " + e.getMessage());
        }
    }
}
