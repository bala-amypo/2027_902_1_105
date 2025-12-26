package com.example.demo.dto;

public class ApiResponse {

    private String message;

    public ApiResponse() {}

    public ApiResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}









// VisitorRequest.java

// package com.example.demo.dto;

// import jakarta.validation.constraints.NotBlank;

// public class VisitorRequest {

//     @NotBlank
//     private String name;

//     @NotBlank
//     private String email;

//     @NotBlank
//     private String phone;

//     public VisitorRequest() {}

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getEmail() {
//         return email;
//     }
 
//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public String getPhone() {
//         return phone;
//     }
 
//     public void setPhone(String phone) {
//         this.phone = phone;
//     }
// }


// VisitorResponse.java

// package com.example.demo.dto;

// public class VisitorResponse {

//     private Long id;
//     private String name;
//     private String email;
//     private String phone;

//     public VisitorResponse() {}

//     public VisitorResponse(Long id, String name, String email, String phone) {
//         this.id = id;
//         this.name = name;
//         this.email = email;
//         this.phone = phone;
//     }

//     public Long getId() {
//         return id;
//     }
 
//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getName() {
//         return name;
//     }
 
//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getEmail() {
//         return email;
//     }
 
//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public String getPhone() {
//         return phone;
//     }
 
//     public void setPhone(String phone) {
//         this.phone = phone;
//     }
// }


// HostRequest.java

// package com.example.demo.dto;

// import jakarta.validation.constraints.NotBlank;

// public class HostRequest {

//     @NotBlank
//     private String name;

//     @NotBlank
//     private String email;

//     public HostRequest() {}

//     public String getName() {
//         return name;
//     }
 
//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getEmail() {
//         return email;
//     }
 
//     public void setEmail(String email) {
//         this.email = email;
//     }
// }


// HostResponse.java

// package com.example.demo.dto;

// public class HostResponse {

//     private Long id;
//     private String name;
//     private String email;

//     public HostResponse() {}

//     public HostResponse(Long id, String name, String email) {
//         this.id = id;
//         this.name = name;
//         this.email = email;
//     }

//     public Long getId() {
//         return id;
//     }
 
//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getName() {
//         return name;
//     }
 
//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getEmail() {
//         return email;
//     }
 
//     public void setEmail(String email) {
//         this.email = email;
//     }
// }

// AppointmentRequest.java

// package com.example.demo.dto;

// import jakarta.validation.constraints.NotNull;
// import java.time.LocalDateTime;

// public class AppointmentRequest {

//     @NotNull
//     private Long visitorId;

//     @NotNull
//     private Long hostId;

//     @NotNull
//     private LocalDateTime appointmentDate;

//     public AppointmentRequest() {}

//     public Long getVisitorId() {
//         return visitorId;
//     }
 
//     public void setVisitorId(Long visitorId) {
//         this.visitorId = visitorId;
//     }

//     public Long getHostId() {
//         return hostId;
//     }
 
//     public void setHostId(Long hostId) {
//         this.hostId = hostId;
//     }

//     public LocalDateTime getAppointmentDate() {
//         return appointmentDate;
//     }
 
//     public void setAppointmentDate(LocalDateTime appointmentDate) {
//         this.appointmentDate = appointmentDate;
//     }
// }


// AppointmentResponse.java

// package com.example.demo.dto;

// import java.time.LocalDateTime;

// public class AppointmentResponse {

//     private Long id;
//     private String status;
//     private LocalDateTime appointmentDate;
//     private String visitorName;
//     private String hostName;

//     public AppointmentResponse() {}

//     public AppointmentResponse(
//             Long id,
//             String status,
//             LocalDateTime appointmentDate,
//             String visitorName,
//             String hostName
//     ) {
//         this.id = id;
//         this.status = status;
//         this.appointmentDate = appointmentDate;
//         this.visitorName = visitorName;
//         this.hostName = hostName;
//     }

//     public Long getId() {
//         return id;
//     }
 
//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getStatus() {
//         return status;
//     }
 
//     public void setStatus(String status) {
//         this.status = status;
//     }

//     public LocalDateTime getAppointmentDate() {
//         return appointmentDate;
//     }
 
//     public void setAppointmentDate(LocalDateTime appointmentDate) {
//         this.appointmentDate = appointmentDate;
//     }

//     public String getVisitorName() {
//         return visitorName;
//     }
 
//     public void setVisitorName(String visitorName) {
//         this.visitorName = visitorName;
//     }

//     public String getHostName() {
//         return hostName;
//     }
 
//     public void setHostName(String hostName) {
//         this.hostName = hostName;
//     }
// }


// 🔟 VisitLogResponse.java

// package com.example.demo.dto;

// import java.time.LocalDateTime;

// public class VisitLogResponse {

//     private Long id;
//     private LocalDateTime checkInTime;
//     private LocalDateTime checkOutTime;
//     private boolean accessGranted;

//     public VisitLogResponse() {}

//     public VisitLogResponse(
//             Long id,
//             LocalDateTime checkInTime,
//             LocalDateTime checkOutTime,
//             boolean accessGranted
//     ) {
//         this.id = id;
//         this.checkInTime = checkInTime;
//         this.checkOutTime = checkOutTime;
//         this.accessGranted = accessGranted;
//     }

//     public Long getId() {
//         return id;
//     }
 
//     public void setId(Long id) {
//         this.id = id;
//     }

//     public LocalDateTime getCheckInTime() {
//         return checkInTime;
//     }
 
//     public void setCheckInTime(LocalDateTime checkInTime) {
//         this.checkInTime = checkInTime;
//     }

//     public LocalDateTime getCheckOutTime() {
//         return checkOutTime;
//     }
 
//     public void setCheckOutTime(LocalDateTime checkOutTime) {
//         this.checkOutTime = checkOutTime;
//     }

//     public boolean isAccessGranted() {
//         return accessGranted;
//     }
 
//     public void setAccessGranted(boolean accessGranted) {
//         this.accessGranted = accessGranted;
//     }
// }


// AlertNotificationResponse.java

// package com.example.demo.dto;

// import java.time.LocalDateTime;

// public class AlertNotificationResponse {

//     private Long id;
//     private String message;
//     private String sentTo;
//     private LocalDateTime sentAt;

//     public AlertNotificationResponse() {}

//     public AlertNotificationResponse(
//             Long id,
//             String message,
//             String sentTo,
//             LocalDateTime sentAt
//     ) {
//         this.id = id;
//         this.message = message;
//         this.sentTo = sentTo;
//         this.sentAt = sentAt;
//     }

//     public Long getId() {
//         return id;
//     }
 
//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getMessage() {
//         return message;
//     }
 
//     public void setMessage(String message) {
//         this.message = message;
//     }

//     public String getSentTo() {
//         return sentTo;
//     }
 
//     public void setSentTo(String sentTo) {
//         this.sentTo = sentTo;
//     }

//     public LocalDateTime getSentAt() {
//         return sentAt;
//     }
 
//     public void setSentAt(LocalDateTime sentAt) {
//         this.sentAt = sentAt;
//     }
// }






